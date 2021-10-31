package com.webservice.luxoft.websetvice.service;

import com.webservice.luxoft.websetvice.model.Employee;
import com.webservice.luxoft.websetvice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private static final String EMPLOYEE = "employee";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String DIVISION = "division";

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getEmployeeByName(String name) {
        return employeeRepository.findByNameLike(name);
    }

    public String updateEmployee(Long id, Employee newEmployee) {
        Optional<Employee> optEmployee = employeeRepository.findById(id);
        if (optEmployee.isPresent()) {
            Employee oldEmployee = optEmployee.get();

            if (!oldEmployee.equals(newEmployee)) {
                oldEmployee.setAge(newEmployee.getAge());
                oldEmployee.setName(newEmployee.getName());
                oldEmployee.setDivision(newEmployee.getDivision());

                employeeRepository.save(oldEmployee);

                return "Object has been updated";
            }

            return "Object is equal to the object in the database";
        }

        return "Object not found";
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public void addEmployeeFromFile(String fileName) {
        List<Employee> employeeList = readEmployeesFile("C:\\test\\" + fileName + ".xml");
        for (Employee employee : employeeList) {
            employeeRepository.save(employee);
        }
    }

    private List<Employee> readEmployeesFile(String filePath) {
        List<Employee> employeeList = new ArrayList<>();
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(filePath);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            Employee employee = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have an employee element, we create a new employee
                    String elementName = startElement.getName().getLocalPart();
                    switch (elementName) {
                        case EMPLOYEE:
                            employee = new Employee();
                            break;
                        case AGE:
                            event = eventReader.nextEvent();
                            employee.setAge(Integer.parseInt(event.asCharacters().getData()));
                            break;
                        case DIVISION:
                            event = eventReader.nextEvent();
                            employee.setDivision(event.asCharacters().getData());
                            break;
                        case NAME:
                            event = eventReader.nextEvent();
                            employee.setName(event.asCharacters().getData());
                            break;
                    }
                }
                // If we reach the end of an employee element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(EMPLOYEE)) {
                        employeeList.add(employee);
                    }
                }

            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return employeeList;
    }
}
