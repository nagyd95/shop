package com.shop.ecommerce.controller;

import com.shop.ecommerce.dto.Purchase;
import com.shop.ecommerce.dto.PurchaseResponse;
import com.shop.ecommerce.service.CheckoutService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("api/checkout")
public class CheckoutController {

    private CheckoutService checkoutService;


    private CheckoutController(CheckoutService checkoutService){
        this.checkoutService=checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase){
        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

        return purchaseResponse;
    }

}
