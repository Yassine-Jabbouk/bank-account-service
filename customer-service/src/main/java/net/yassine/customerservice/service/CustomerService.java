package net.yassine.customerservice.service;

import net.yassine.customerservice.entities.Customer;
import net.yassine.customerservice.repository.CustomerRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @McpTool(description = "Get All Customers")
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    @McpTool(description = "Find Customer By Id")
    public Customer findCustomerById(@McpToolParam(description = "The Customer id") Long id){
        return customerRepository.findById(id).orElseThrow(()->new RuntimeException("Customer Not Found"));
    }
    @McpTool(description = "Save a new customer")
    public Customer saveCustomer(@McpToolParam(description = "The Customer to save(name,email)") Customer customer){
        return customerRepository.save(customer);
    }
}
