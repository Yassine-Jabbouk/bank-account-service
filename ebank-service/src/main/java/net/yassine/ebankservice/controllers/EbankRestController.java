package net.yassine.ebankservice.controllers;

import net.yassine.ebankservice.feign.CustomerRestClient;
import net.yassine.ebankservice.entities.BankAccount;
import net.yassine.ebankservice.model.Customer;
import net.yassine.ebankservice.repositories.BankAccountRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class EbankRestController {
    private final BankAccountRepository bankAccountRepository;
    private final CustomerRestClient customerRestClient;

    public EbankRestController(BankAccountRepository bankAccountRepository, CustomerRestClient customerRestClient) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRestClient = customerRestClient;
    }

    @GetMapping("/accounts")
    public List<BankAccount> getAllAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        bankAccounts.forEach(acc -> {
            Customer customer = customerRestClient.getCustomerById(acc.getCustomerId());
            acc.setCustomer(customer);
        });
        return bankAccounts;
    }

    @GetMapping("/accounts/{id}")
    public BankAccount getAccountById(@PathVariable String id) {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Customer customer = customerRestClient.getCustomerById(bankAccount.getCustomerId());
        bankAccount.setCustomer(customer);
        return bankAccount;
    }
}
