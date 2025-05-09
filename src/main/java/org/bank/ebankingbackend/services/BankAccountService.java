package org.bank.ebankingbackend.services;

import org.bank.ebankingbackend.dtos.*;
import org.bank.ebankingbackend.entities.BankAccount;
import org.bank.ebankingbackend.entities.CurrentAccount;
import org.bank.ebankingbackend.entities.Customer;
import org.bank.ebankingbackend.entities.SavingAccount;
import org.bank.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.bank.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.bank.ebankingbackend.exceptions.CustomernotFoundExeption;

import java.util.List;

public interface BankAccountService {


    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomernotFoundExeption;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomernotFoundExeption;

    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount,String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestination ,double amount) throws BankAccountNotFoundException;


    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomernotFoundExeption;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId) throws BankAccountNotFoundException;

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
