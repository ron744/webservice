package com.webservice.luxoft.websetvice.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.webservice.luxoft.websetvice.controller.EmployeeController;
import com.webservice.luxoft.websetvice.model.Employee;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.*;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

@Service
public class EmployeeFileWorking {
    private static final Logger log = Logger.getLogger(EmployeeFileWorking.class);

    private static final String EMPLOYEE = "employee";
    private final EmployeeCrud employeeCrud;
    private final static BlockingQueue<Employee> employeeQueue = new ArrayBlockingQueue<>(200);

    @Autowired
    public EmployeeFileWorking(EmployeeCrud employeeCrud) {
        this.employeeCrud = employeeCrud;
    }

    public void someMethod(String fileName) throws FileNotFoundException, XMLStreamException, IOException {
        new Thread(new XmlDeserializer(fileName));
    }

    class XmlDeserializer implements Runnable {
        private final String fileName;

        public XmlDeserializer(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void run() {
            try {
                XmlMapper xm = new XmlMapper();
                XMLInputFactory xif = XMLInputFactory.newInstance();

                XMLStreamReader xr = xif.createXMLStreamReader(new FileInputStream("C:\\test\\" + fileName + ".xml"));

                while (xr.hasNext()) {
                    xr.next();
                    if (xr.getEventType() == START_ELEMENT) {
                        if (EMPLOYEE.equals(xr.getLocalName())) {
                            employeeQueue.put(xm.readValue(xr, Employee.class));
                            new Thread(new DbWriter()).start();
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                log.error("File not found...", e.fillInStackTrace());

            } catch (XMLStreamException e) {
                log.error("Exception while creating new XMLStreamReader...", e.fillInStackTrace());

            } catch (IOException e) {
                log.error("Exception while reading xml file...", e.fillInStackTrace());

            } catch (InterruptedException e) {
                log.error("Exception while putting employees to queue...", e.fillInStackTrace());
            }
        }
    }

    class DbWriter implements Runnable {

        @Override
        public void run() {
            try {
                employeeCrud.add(employeeQueue.take());
            } catch (InterruptedException e) {
                log.error("Exception while taking employees from queue...", e.fillInStackTrace());
            }
        }
    }
}
