package com.shop.ecommerce.service;

import com.shop.ecommerce.dao.CustomerRepository;
import com.shop.ecommerce.dto.Purchase;
import com.shop.ecommerce.dto.PurchaseResponse;
import com.shop.ecommerce.entity.Customer;
import com.shop.ecommerce.entity.Order;
import com.shop.ecommerce.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository){
        this.customerRepository=customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        Order order=purchase.getOrder();

        String orderTrackingNumber=generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems=purchase.getOrderItems();
        orderItems.forEach(item-> order.add(item));

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        Customer customer= purchase.getCustomer();
        customer.add(order);

        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
