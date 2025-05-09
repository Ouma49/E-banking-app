package org.bank.ebankingbackend.repositories;

import org.bank.ebankingbackend.entities.AccountOperation;
import org.bank.ebankingbackend.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {

  public List<AccountOperation> findByBankAccountId(String accountId);

 Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);
}
