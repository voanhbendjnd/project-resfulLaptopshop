package com.laptopshopResful.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.domain.request.RequestCheckoutCart;
import com.laptopshopResful.domain.request.RequestOrderForDetail;
import com.laptopshopResful.domain.response.cart.ResCartCheckoutDTO;
import com.laptopshopResful.domain.response.order.ResOrderDTO;
import com.laptopshopResful.service.CartService;
import com.laptopshopResful.service.OrderService;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;

@RestController
@RequestMapping("/client/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @PostMapping("")
    @ApiMessage("Get infomation for order")
    public ResponseEntity<ResOrderDTO> create(@RequestBody RequestOrderForDetail request)
            throws IdInvalidException {
        return ResponseEntity.ok(this.orderService.placeOrder(request));
    }

    @PostMapping("/checkout")
    @ApiMessage("Checkout cart before order")
    public ResponseEntity<ResCartCheckoutDTO> checkout(@RequestBody RequestCheckoutCart re) throws IdInvalidException {
        return ResponseEntity.ok(this.cartService.checkoutCart(re));
    }
}
