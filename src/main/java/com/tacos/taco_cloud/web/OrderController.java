package com.tacos.taco_cloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import com.tacos.taco_cloud.TacoOrder;
import com.tacos.taco_cloud.data.OrderRepository;
import com.tacos.taco_cloud.models.User;

import org.springframework.validation.Errors;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")

@ConfigurationProperties(prefix = "taco.orders")
public class OrderController {
  private OrderProps props;
  @Autowired
  private OrderRepository orderRepository;

  public OrderController(OrderRepository orderRepo,
      OrderProps props) {
    this.orderRepository = orderRepo;
    this.props = props;
  }

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
      SessionStatus sessionStatus,
      @AuthenticationPrincipal User user) {
    if (errors.hasErrors()) {
      return "orderForm";
    }

    log.info("Order submitted: {}", order);
    sessionStatus.setComplete();
    return "redirect:/";
  }

  @GetMapping
  public String ordersForUser(
      @AuthenticationPrincipal User user, Model model) {
    org.springframework.data.domain.Pageable pageable = PageRequest.of(0, props.getPageSize());
    model.addAttribute("orders",
        orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
    return "orderList";
  }
}
