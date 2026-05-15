package com.example.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @PostMapping
    public String processPayment(@RequestBody String paymentDetails) {
        return "Payment processed successfully!";
    }
}
