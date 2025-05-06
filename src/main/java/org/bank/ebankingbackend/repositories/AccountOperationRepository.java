package org.bank.ebankingbackend.repositories;

import org.bank.ebankingbackend.entities.AccountOperation;
import org.bank.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {


}
