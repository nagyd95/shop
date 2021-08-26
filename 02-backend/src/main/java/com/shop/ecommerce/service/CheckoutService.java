package com.shop.ecommerce.service;


import com.shop.ecommerce.dto.Purchase;
import com.shop.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
