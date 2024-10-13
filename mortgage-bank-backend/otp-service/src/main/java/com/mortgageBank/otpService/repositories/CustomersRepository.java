package com.mortgageBank.otpService.repositories;

import com.mortgageBank.otpService.model.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersRepository extends JpaRepository<Customer, Long> {

}
