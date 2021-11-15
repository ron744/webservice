package com.websetvice.luxoft.webservice.service;

import com.webservice.luxoft.webservice.ecxeption.LoadingException;
import com.webservice.luxoft.webservice.model.Employee;
import com.webservice.luxoft.webservice.service.EmployeeCrud;
import com.webservice.luxoft.webservice.service.EmployeeLoadService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

public class EmployeeLoadServiceTest {
    private final EmployeeCrud employeeCrud = mock(EmployeeCrud.class);
    private final EmployeeLoadService service = new EmployeeLoadService(employeeCrud);
//    private final EmployeeCrud employeeCrud = Mockito.verify(new EmployeeCrud(employeeRepository), Mockito.times(5000));

    @Test
    public void readStream() throws LoadingException, InterruptedException {
        InputStream is = EmployeeLoadServiceTest.class.getResourceAsStream("employee.xml");

        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mockito.doAnswer(answer -> {
            countDownLatch.countDown();
            return answer.callRealMethod();
        }).when(employeeCrud).add(any(Employee.class));

        service.readStream(is);
        countDownLatch.await(1, TimeUnit.MINUTES);

        verify(employeeCrud, times(1)).add(new Employee("new name", 200, "division"));
    }
}
