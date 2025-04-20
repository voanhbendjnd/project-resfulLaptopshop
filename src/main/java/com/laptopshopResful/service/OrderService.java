package com.laptopshopResful.service;

import org.springframework.stereotype.Service;

import com.laptopshopResful.domain.entity.Order;
import com.laptopshopResful.domain.entity.OrderDetail;
import com.laptopshopResful.domain.entity.Product;
import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.domain.request.RequestCheckoutCart;
import com.laptopshopResful.domain.request.RequestOrderForDetail;
import com.laptopshopResful.domain.response.order.ResOrderDTO;
import com.laptopshopResful.repository.OrderDetailRepository;
import com.laptopshopResful.repository.OrderRepository;
import com.laptopshopResful.repository.ProductRepository;
import com.laptopshopResful.repository.UserRepository;
import com.laptopshopResful.utils.SecurityUtils;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
            ProductRepository productRepository,
            SecurityUtils securityUtils,
            UserRepository userRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
    }

    public void updateOrderDetail(Long id, Long quantity) {

        Product product = this.productRepository.findById(id).get();
        product.setQuantity(product.getQuantity() - quantity);
        product.setSold((product.getSold() == null || product.getSold() == 0) ? 0L + 1L : product.getSold() + 1L);
        this.productRepository.save(product);
    }

    public ResOrderDTO resOrder(Order order, RequestOrderForDetail re) {
        String email = this.securityUtils.getCurrentUserLogin().get();
        User user = this.userRepository.findByEmail(email);
        Order currentOrder = new Order();
        // Order currentOrder = this.orderRepository.save(order);
        currentOrder.setReceiverAddress(order.getReceiverAddress());
        currentOrder.setReceiverName(order.getReceiverName());
        currentOrder.setReceiverPhone(order.getReceiverPhone());
        currentOrder.setStatus(order.getStatus());
        currentOrder.setTotalPrice(order.getTotalPrice());
        currentOrder.setUser(user);
        this.orderRepository.save(currentOrder);

        OrderDetail orderDetail = new OrderDetail();
        Product product = productRepository.findById(re.getId()).get();
        orderDetail.setOrder(currentOrder);
        orderDetail.setPrice(product.getPrice() * 1D);
        orderDetail.setQuantity(re.getQuantity());
        orderDetail.setProduct(product);
        this.orderDetailRepository.save(orderDetail);

        this.updateOrderDetail(product.getId(), re.getQuantity());

        ResOrderDTO res = new ResOrderDTO();
        res.setAddress(order.getReceiverAddress());
        res.setName(order.getReceiverName());
        res.setPhone(order.getReceiverPhone());
        res.setTotalPrice(order.getTotalPrice());
        res.setStatus(order.getStatus());
        return res;
    }
}
