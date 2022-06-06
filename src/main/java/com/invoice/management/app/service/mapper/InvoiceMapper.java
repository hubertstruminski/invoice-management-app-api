package com.invoice.management.app.service.mapper;

import com.invoice.management.app.dto.PersistableInvoiceDto;
import com.invoice.management.app.dto.ReadableInvoiceDto;
import com.invoice.management.app.entity.Customer;
import com.invoice.management.app.entity.Invoice;
import com.invoice.management.app.entity.Product;
import com.invoice.management.app.exception.ResourceNotFoundException;
import com.invoice.management.app.repository.CustomerRepository;
import com.invoice.management.app.repository.ProductRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class InvoiceMapper {

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected CustomerRepository customerRepository;

    @Mapping(source = "invoiceDto.productIds", target = "invoice.products", qualifiedByName = "idsToProducts")
    @Mapping(source = "invoiceDto.customerId", target = "invoice.customer", qualifiedByName = "customerIdToCustomer")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract Invoice mapPersistableDTOToEntity(PersistableInvoiceDto invoiceDto, @MappingTarget Invoice invoice);

    @Mapping(source = "invoice.customer", target = "invoiceDto.customerId", qualifiedByName = "customerToId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void mapToReadableDTO(Invoice invoice, @MappingTarget ReadableInvoiceDto invoiceDto);

    @Named("idsToProducts")
    public List<Product> mapIdsToProducts(List<Long> ids) {
        return ids
                .stream()
                .map(productId -> {
                    Optional<Product> foundProduct = productRepository.findById(productId);
                    return foundProduct.get();
                })
                .collect(Collectors.toList());
    }

    @Named("customerIdToCustomer")
    public Customer mapCustomerIdToCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id.toString()));
        return customer;
    }

    @Named("customerToId")
    public Long mapCustomerToId(Customer customer) {
        return customer.getId();
    }
}
