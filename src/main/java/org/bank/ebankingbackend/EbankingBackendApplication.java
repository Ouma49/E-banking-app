package org.bank.ebankingbackend;

import org.bank.ebankingbackend.entities.*;
import org.bank.ebankingbackend.enums.AccountStatus;
import org.bank.ebankingbackend.enums.OperationType;
import org.bank.ebankingbackend.repositories.AccountOperationRepository;
import org.bank.ebankingbackend.repositories.BankAccountRepository;
import org.bank.ebankingbackend.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository bankAccountRepository) {

return args -> {
    BankAccount bankAccount =
            bankAccountRepository.findById("000090b3-4843-4b25-b10c-583dcc3fc093").orElse(null);
    if (bankAccount != null) {
        System.out.println("******************************");
        System.out.println("Bank Account ID: " + bankAccount.getId());
        System.out.println("Name of the Account Holder: " + bankAccount.getCustomer().getName());
        System.out.println("Bank Account Balance: " + bankAccount.getBalance());
        System.out.println("Bank Account Created At: " + bankAccount.getCreatedAt());
        System.out.println("Bank Account Status: " + bankAccount.getStatus());
        System.out.println("Bank Account Customer: " + bankAccount.getCustomer().getName());
        System.out.println(bankAccount.getClass().getSimpleName());
        System.out.println("Bank Account Operations: ");
        bankAccount.getAccountOperations().forEach(accountOperation -> {
            System.out.println("Operation Date: " + accountOperation.getOperationDate());
            System.out.println("Operation Amount: " + accountOperation.getAmount());
            System.out.println("Operation Type: " + accountOperation.getType());

            System.out.println("******************************");
            if (bankAccount instanceof SavingAccount) {
                System.out.println("Interest Rate: " + ((SavingAccount) bankAccount).getInterestRate());
            } else if (bankAccount instanceof CurrentAccount) {
                System.out.println("Overdraft: " + ((CurrentAccount) bankAccount).getOverDraft());
            }
        });
    }
};
    }



//@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Hassan", "Mohamed", "Ali").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });
                customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random() * 90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(customer);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);


               SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
               savingAccount.setBalance(Math.random() * 90000);
               savingAccount.setCreatedAt(new Date());
               savingAccount.setStatus(AccountStatus.CREATED);
               savingAccount.setCustomer(customer);
               savingAccount.setInterestRate(5.5);
               bankAccountRepository.save(savingAccount);


           });
            bankAccountRepository.findAll().forEach(acc -> {
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 12000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }


            });
        };
    }
}