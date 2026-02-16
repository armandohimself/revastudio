package com.revastudio.revastudio.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revastudio.revastudio.entity.Customer;
import java.util.List;

/**
 * Default Methods Available to You; No Code Needed
// * SAVE/UPDATE
    * save(entity)
    * saveAll(entities)
    * saveAndFlush(entity) (forces SQL flush immediately)
    * flush() (push pending SQL to DB now)
// * READ
    * findById(id)
    * findAll()
    * findAllById(ids)
    * getReferenceById(id) (returns a lazy proxy; doesn’t hit DB until needed)
// * DELETE
    * delete(entity)
    * deleteById(id)
    * deleteAll(entities)
    * deleteAll()
    * deleteAllInBatch() / deleteAllByIdInBatch() (faster bulk deletes)
// * EXISTENCE / COUNTS
    * existsById(id)
    * count() (ex: long countBySupportRepEmployeeId(UUID employeeId);)
// * PAGING & SORTING
    * findAll(Pageable pageable)
    * findAll(Sort sort)
 */

public interface CustomerRepository extends JpaRepository<Customer, UUID>{
    List<Customer> findByCompany(String company);

    // List<Customer> findBySupportRepEmployeeId(UUID employeeId);
}
