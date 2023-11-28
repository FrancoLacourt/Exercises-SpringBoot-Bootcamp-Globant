package com.example.store.mapper;

import com.example.store.dto.OrderDTO;
import com.example.store.entities.Order;

public class OrderMapper {

    public static OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId_order(order.getId_order());
        orderDTO.setDate(order.getDate());
        orderDTO.setProducts(order.getProducts());
        orderDTO.setOrderNumber(order.getOrderNumber());
        orderDTO.setActive(order.isActive());

        return orderDTO;
    }

    public static Order toOrder(OrderDTO orderDTO) {
        Order order = new Order();

        order.setId_order(orderDTO.getId_order());
        order.setDate(orderDTO.getDate());
        order.setProducts(orderDTO.getProducts());
        order.setOrderNumber(orderDTO.getOrderNumber());
        order.setActive(orderDTO.isActive());

        return order;
    }

}
