package com.example.store.mapper;

import com.example.store.dto.OrderDTO;
import com.example.store.entities.Orders;

public class OrderMapper {

    public static OrderDTO toOrderDTO(Orders order) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId_order(order.getId_order());
        orderDTO.setDate(order.getDate());
        orderDTO.setProducts(order.getProducts());
        orderDTO.setOrderNumber(order.getOrderNumber());
        orderDTO.setClient(order.getClient());
        orderDTO.setActive(order.isActive());

        return orderDTO;
    }

    public static Orders toOrder(OrderDTO orderDTO) {
        Orders order = new Orders();

        order.setId_order(orderDTO.getId_order());
        order.setDate(orderDTO.getDate());
        order.setProducts(orderDTO.getProducts());
        order.setOrderNumber(orderDTO.getOrderNumber());
        order.setClient(orderDTO.getClient());
        order.setActive(orderDTO.isActive());

        return order;
    }

}
