package com.tacos.taco_cloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import com.tacos.taco_cloud.TacoOrder;
import org.springframework.validation.Errors;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }
    
    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }
    
    @PostMapping
    public String processOrder(@Valid TacoOrder order, 
                             Errors errors,
                             SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}