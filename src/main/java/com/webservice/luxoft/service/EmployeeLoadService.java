package com.webservice.luxoft.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.webservice.luxoft.ecxeption.LoadingException;
import com.webservice.luxoft.model.Employee;
import com.webservice.luxoft.model.ShellEmployee;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

@Service
public class EmployeeLoadService {
    private static final Logger log = Logger.getLogger(EmployeeLoadService.class);

    private static final String EMPLOYEE = "employee";
    private final static ArrayBlockingQueue<ShellEmployee> employeeQueue = new ArrayBlockingQueue<>(200);
    private final ConcurrentMap<UUID, List<Exception>> exceptionsMap = new ConcurrentHashMap<>();

    private final Object monitor = new Object();

    @Autowired
    public EmployeeLoadService(EmployeeCrud employeeCrud) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    ShellEmployee shellEmployee = null;

                    try {
                        shellEmployee = employeeQueue.take();
                        Employee employee = shellEmployee.getEmployee();
                        employeeCrud.add(employee);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.error("Exception while taking employees from queue...", e);
                    } catch (Exception e) {
                        UUID uuid = shellEmployee.getUuid();

                        synchronized (monitor) {
                            List<Exception> exceptionList = exceptionsMap.get(shellEmployee.getUuid());
                            exceptionList.add(e);

                            exceptionsMap.put(uuid, exceptionList);
                        }
                    }

                }
            });
        }
    }

    public void loadFromXml(String fileName) throws FileNotFoundException, LoadingException {
        readStream(new FileInputStream("C:\\test\\" + fileName + ".xml"));
    }

    public void readStream(InputStream employeeXmlStream) throws LoadingException {
        UUID uuid = UUID.randomUUID();
        List<Exception> exceptionList = new ArrayList<>();
        exceptionsMap.put(uuid, exceptionList);

        try {
            XmlMapper xm = new XmlMapper();
            XMLInputFactory xif = XMLInputFactory.newInstance();

            XMLStreamReader xr = xif.createXMLStreamReader(employeeXmlStream);

            while (xr.hasNext()) {
                xr.next();
                if (xr.getEventType() == START_ELEMENT) {
                    if (EMPLOYEE.equals(xr.getLocalName())) {
                        Employee employee = null;
                        try {
                            employee = xm.readValue(xr, Employee.class);
                        } catch (IOException e) {
                            exceptionList.add(e);
                        }
                        ShellEmployee shellEmployee = new ShellEmployee(uuid, employee);
                        employeeQueue.put(shellEmployee);
                    }
                }
            }
        } catch (XMLStreamException | InterruptedException e) {
            exceptionList.add(e);
        }

        if (exceptionList.size() > 0) {
            throw new LoadingException(exceptionList);
        }
    }
}
