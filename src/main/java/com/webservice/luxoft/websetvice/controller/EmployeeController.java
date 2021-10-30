package com.webservice.luxoft.websetvice.controller;

import com.webservice.luxoft.websetvice.model.Employee;
import com.webservice.luxoft.websetvice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/employee")
public class EmployeeController {
    private static final String EMPLOYEE = "employee";
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String DIVISION = "division";

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/test")
    public String test() {
        return "Success";
    }

    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/getByName")
    public List<Employee> getEmployeeByName(@RequestParam String name) {
        return employeeRepository.findByNameLike(name);
    }

    @PutMapping("/updateById")
    public String updateEmployee(@RequestParam Long id, @RequestBody Employee newEmployee) {
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

    @DeleteMapping("/deleteById")
    public void deleteEmployee(@RequestParam Long id) {
        employeeRepository.deleteById(id);
    }

    @GetMapping("/addFromFile")
    public void addEmployeeFromFile(@RequestParam String fileName) {
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
