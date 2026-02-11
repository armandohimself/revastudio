package com.revastudio.revastudio.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revastudio.revastudio.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID>{

}
