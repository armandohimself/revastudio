package com.revastudio.revastudio.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired EmployeeRepository employeeRepo;
    @Autowired CustomerRepository customerRepo;

    

}
