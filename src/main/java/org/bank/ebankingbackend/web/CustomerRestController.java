package org.bank.ebankingbackend.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bank.ebankingbackend.dtos.CustomerDTO;
import org.bank.ebankingbackend.entities.BankAccount;
import org.bank.ebankingbackend.entities.Customer;
import org.bank.ebankingbackend.exceptions.CustomernotFoundExeption;
import org.bank.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@Slf4j
@RestController
@CrossOrigin("*")
public class CustomerRestController {

    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers() {

        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/search")
    public List<CustomerDTO>searchCustomers(@RequestParam(name="keyword",defaultValue="") String keyword) {

        return bankAccountService.searchCustomers("%"+keyword+"%");
    }


    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId)  throws CustomernotFoundExeption {
        return bankAccountService.getCustomer(customerId);
    }


    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {

       return bankAccountService.saveCustomer(customerDTO);

    }

    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }


    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        bankAccountService.deleteCustomer(id);
    }
}
