package com.krzysztofpapiernik.products.service;

import com.krzysztofpapiernik.products.dto.CreateCustomerDto;
import com.krzysztofpapiernik.products.dto.GetCustomerDto;
import com.krzysztofpapiernik.products.exception.CategoryServiceException;
import com.krzysztofpapiernik.products.model.Customer;
import com.krzysztofpapiernik.products.repository.CustomerRepository;
import com.krzysztofpapiernik.products.exception.CustomerServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public GetCustomerDto addCustomer(CreateCustomerDto createCustomerDto){

        if(customerRepository.findCustomerByEmail(createCustomerDto.email()).isPresent()){
            throw new CustomerServiceException("Provided email is taken");
        }

        return customerRepository
                .save(createCustomerDto.toCustomer())
                .toGetCustomerDto();
    }

    public List<GetCustomerDto> getAll(){
        return customerRepository
                .findAll()
                .stream()
                .map(Customer::toGetCustomerDto)
                .toList();
    }

    public GetCustomerDto getCustomerById(Long id){

        return customerRepository
                .findById(id)
                .map(Customer::toGetCustomerDto)
                .orElseThrow(() -> new CustomerServiceException(Map.of("id", "User with id: %s does not exist".formatted(id))));
    }

    public GetCustomerDto editCustomer(Long id, CreateCustomerDto createCustomerDto){
        var changedCustomer = customerRepository
                .findById(id)
                .map(team -> team.withChangedAttributes(createCustomerDto))
                .orElseThrow(() -> new CustomerServiceException(Map.of("customer", "does not exist")));

        return customerRepository
                .save(changedCustomer)
                .toGetCustomerDto();

    }

    public void deleteCustomer(Long id){
        var customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new CategoryServiceException(Map.of("id", "Customer with id: %s does not exist".formatted(id))));

        customerRepository.delete(customer);
    }



}
