package org.bank.ebankingbackend.repositories;

import org.bank.ebankingbackend.entities.BankAccount;
import org.bank.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {

    
}
