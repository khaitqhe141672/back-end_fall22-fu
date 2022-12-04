package com.homesharing_backend.data.repository;

import com.homesharing_backend.data.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer getByUser_Username(String useName);

    Customer getCustomerByUser_Id(Long userID);

    Customer getCustomerById(Long customerID);

    @Query(value = "SELECT count(id) FROM Customer ")
    int totalCustomer();

    int countCustomerByUser_Status(int status);
}
