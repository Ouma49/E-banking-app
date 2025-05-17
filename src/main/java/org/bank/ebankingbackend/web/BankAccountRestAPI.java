package org.bank.ebankingbackend.web;


import org.bank.ebankingbackend.dtos.AccountHistoryDTO;
import org.bank.ebankingbackend.dtos.AccountOperationDTO;
import org.bank.ebankingbackend.dtos.BankAccountDTO;
import org.bank.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.bank.ebankingbackend.exceptions.CustomernotFoundExeption;
import org.bank.ebankingbackend.repositories.BankAccountRepository;
import org.bank.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
public class BankAccountRestAPI {

    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;

    }

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
    return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts() throws BankAccountNotFoundException {
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public  List<AccountOperationDTO> getHistory(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam (name = "page",defaultValue = "0") int page,
                                               @RequestParam(name="size",defaultValue = "5") int size) throws BankAccountNotFoundException {
                                                         return bankAccountService.getAccountHistory(accountId,page,size);


    }
    }





