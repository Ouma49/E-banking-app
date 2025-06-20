package org.bank.ebankingbackend.services;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.ebankingbackend.dtos.*;
import org.bank.ebankingbackend.entities.*;
import org.bank.ebankingbackend.enums.OperationType;
import org.bank.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.bank.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.bank.ebankingbackend.exceptions.CustomernotFoundExeption;
import org.bank.ebankingbackend.mappers.BankAccountMapperImpl;
import org.bank.ebankingbackend.repositories.AccountOperationRepository;
import org.bank.ebankingbackend.repositories.BankAccountRepository;
import org.bank.ebankingbackend.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;



import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
@AllArgsConstructor
@Slf4j


public class BankAccountServiceImpl implements BankAccountService {
   private BankAccountRepository bankAccountRepository;
   private CustomerRepository customerRepository;
   private AccountOperationRepository  accountOperationRepository;
   private BankAccountMapperImpl dtoMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer to the database");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer= customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomernotFoundExeption {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new CustomernotFoundExeption("Customer not found");
        }
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomernotFoundExeption {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new CustomernotFoundExeption("Customer not found");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(savedBankAccount);    }


    @Override
    public List<CustomerDTO> listCustomers() {
       List<Customer> customers = customerRepository.findAll();
             List<CustomerDTO> customerDTOs = customers.stream()
                 .map(custumor->dtoMapper.fromCustomer(custumor))
                 .collect(Collectors.toList());



      /* List<CustomerDTO> customerDTOs = new ArrayList<>();
        for(Customer customer : customers) {
            CustomerDTO customerDTO = dtoMapper.fromCustomer(customer);
            customerDTOs.add(customerDTO);
           }
       */
        return customerDTOs;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));;
        if (bankAccount instanceof SavingAccount) {
           SavingAccount savingAccount  = (SavingAccount) bankAccount;
            return dtoMapper.fromSavingBankAccount((SavingAccount) bankAccount);
        } else {
             CurrentAccount currentAccount = (CurrentAccount) bankAccount;
             return dtoMapper.fromCurrentBankAccount((CurrentAccount) bankAccount);
        }

    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
                if (bankAccount.getBalance() < amount) {
            throw new BalanceNotSufficientException("Balance not sufficient");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }


    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException{
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }


    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException {
        try {
            debit(accountIdSource, amount, "Transfer to " + accountIdDestination);
            credit(accountIdDestination, amount, "Transfer from " + accountIdSource);
        } catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
            log.error("Error during transfer: {}", e.getMessage());
        }
    }

@Override
public List<BankAccountDTO> bankAccountList(){
    List<BankAccount> bankAccounts = bankAccountRepository.findAll();

    List<BankAccountDTO>  bankAccountDTOS= bankAccounts.stream()
            .map(bankAccount -> {
                if (bankAccount instanceof SavingAccount) {
                    SavingAccount savingAccount = (SavingAccount) bankAccount;
                    return dtoMapper.fromSavingBankAccount(savingAccount);
                } else {
                    CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                    return dtoMapper.fromCurrentBankAccount(currentAccount);
                }
            }).collect(Collectors.toList());

                return bankAccountDTOS;
    }

@Override
public CustomerDTO getCustomer(Long customerId) throws CustomernotFoundExeption {
      Customer customer = customerRepository.findById(customerId)
              .orElseThrow(() -> new CustomernotFoundExeption("Customer not found"));
        return dtoMapper.fromCustomer(customer);
    }


    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer to the database");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer= customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }


    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId) throws BankAccountNotFoundException {
         List<AccountOperation> accountOperations= accountOperationRepository.findByBankAccountId(accountId);
        return  accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException{
    BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
    if (bankAccount == null) throw new BankAccountNotFoundException("Account not Found");
    Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
    AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
    List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    accountHistoryDTO.setAccountOperationsDTO(accountOperationDTOS);
    accountHistoryDTO.setAccountId(bankAccount.getId());
    accountHistoryDTO.setBalance(bankAccount.getBalance());
    accountHistoryDTO.setPageSize(page);
    accountHistoryDTO.setCurrentPage(page);
    accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers= customerRepository.searchCustomer(keyword);
        List<CustomerDTO> customerDTOS = customers.stream().map(cust->dtoMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customerDTOS;
    }
}

