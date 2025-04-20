package com.laptopshopResful.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.domain.entity.Order;
import com.laptopshopResful.domain.request.RequestOrderForDetail;
import com.laptopshopResful.domain.response.order.ResOrderDTO;
import com.laptopshopResful.service.OrderService;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;

@RestController
@RequestMapping("/client/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    @ApiMessage("Get infomation for order")
    public ResponseEntity<ResOrderDTO> create(@RequestBody Order order, @RequestBody RequestOrderForDetail re)
            throws IdInvalidException {

    }
}
