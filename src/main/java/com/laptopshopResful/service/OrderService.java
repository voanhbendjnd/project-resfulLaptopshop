package com.laptopshopResful.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laptopshopResful.domain.entity.Order;
import com.laptopshopResful.domain.entity.OrderDetail;
import com.laptopshopResful.domain.entity.Product;
import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.domain.request.RequestCheckoutCart;
import com.laptopshopResful.domain.request.RequestOrderForDetail;
import com.laptopshopResful.domain.response.order.ResOrderDTO;
import com.laptopshopResful.repository.DiscountRepository;
import com.laptopshopResful.repository.OrderDetailRepository;
import com.laptopshopResful.repository.OrderRepository;
import com.laptopshopResful.repository.ProductRepository;
import com.laptopshopResful.repository.UserRepository;
import com.laptopshopResful.utils.SecurityUtils;
import com.laptopshopResful.utils.constant.StatusEnum;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final DiscountRepository discountRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
            ProductRepository productRepository,
            SecurityUtils securityUtils,
            UserRepository userRepository,
            DiscountRepository discountRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
        this.discountRepository = discountRepository;
    }

    public void updateOrderDetail(Product product, Long quantity) {

        // Product product = this.productRepository.findById(id).get();
        product.setQuantity(product.getQuantity() - quantity);
        product.setSold(
                (product.getSold() == null || product.getSold() == 0) ? 0L + quantity : product.getSold() + quantity);
        this.productRepository.save(product);
    }

    @Transactional
    public ResOrderDTO placeOrder(RequestOrderForDetail orderItems) {
        // start get info account
        String email = this.securityUtils.getCurrentUserLogin().get();
        User user = this.userRepository.findByEmail(email);

        // end get info account

        // start save order
        Order order = new Order();
        order.setReceiverAddress(orderItems.getAddress());
        order.setReceiverName(orderItems.getName());
        order.setReceiverPhone(orderItems.getPhone());
        order.setStatus(StatusEnum.PENDING);
        order.setTotalPrice(orderItems.getTotalPrice());
        order.setUser(user);
        this.orderRepository.save(order);
        // end save order

        // start save dto
        ResOrderDTO res = new ResOrderDTO();
        res.setAddress(order.getReceiverAddress());
        res.setName(order.getReceiverName());
        res.setPhone(order.getReceiverPhone());
        res.setStatus(order.getStatus());
        res.setTotalPrice(order.getTotalPrice());

        // change quantity and sold of product

        for (RequestOrderForDetail.Items x : orderItems.getItems()) {
            OrderDetail dt = new OrderDetail();
            dt.setOrder(order);
            Product product = this.productRepository.findById(x.getIdProduct()).get();
            this.updateOrderDetail(product, x.getQuantity());

            dt.setPrice(product.getPrice() * 1D);
            dt.setQuantity(x.getQuantity());
            dt.setProduct(product);
            this.orderDetailRepository.save(dt);
        }

        return res;
    }
}
