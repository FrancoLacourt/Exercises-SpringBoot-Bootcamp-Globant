package com.example.store.services;

import com.example.store.dto.ClientDTO;
import com.example.store.dto.OrderDTO;
import com.example.store.dto.ProductDTO;
import com.example.store.entities.Client;
import com.example.store.entities.Orders;
import com.example.store.entities.Product;
import com.example.store.exceptions.MyException;
import com.example.store.mapper.OrderMapper;
import com.example.store.mapper.ProductMapper;
import com.example.store.repositories.ClientRepository;
import com.example.store.repositories.OrderRepository;
import com.example.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderService {



    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;

    public OrderDTO createOrder(OrderDTO orderDTO) throws MyException {
        validate(orderDTO);

        Orders order = OrderMapper.toOrder(orderDTO);

        Client client = clientRepository.findById(orderDTO.getClient().getId_client()).orElse(null);
        order.setClient(client);

        List<Product> products = new ArrayList<>();
        for (Long productId : orderDTO.getProductIds()) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                products.add(product);
            }
        }
        order.setProducts(products);

        Orders savedOrder = orderRepository.save(order);

        return OrderMapper.toOrderDTO(savedOrder);
    }

    public List<OrderDTO> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();
        List<OrderDTO> ordersDTO = new ArrayList<>();

        for (Orders order : orders) {
            OrderDTO orderDTO = OrderMapper.toOrderDTO(order);
            ordersDTO.add(orderDTO);
        }
        return ordersDTO;
    }

    public List<OrderDTO> getActiveOrders() {
        List<OrderDTO> ordersDTO = getAllOrders();

        return ordersDTO.stream().filter(OrderDTO::isActive).collect(Collectors.toList());
    }

    public OrderDTO findOrderById(long id_order) {

        Orders order = orderRepository.findById(id_order).orElse(null);

        if (order != null) {
            return OrderMapper.toOrderDTO(order);
        } else {
            System.out.println("It wasn´t possible to find an order with the ID: " + id_order);
            return null;
        }
    }

    public OrderDTO findOrderByOrderNumber(Integer orderNumber) {
        Orders order = orderRepository.findByOrderNumberAndIsActiveIsTrue(orderNumber);

        if (order != null) {
            return OrderMapper.toOrderDTO(order);
        } else {
            System.out.println("It wasn´t possible to find an order with the number: " + orderNumber);
            return null;
        }
    }



    public OrderDTO updateOrder(Long id_order, OrderDTO updatedOrderDTO) throws MyException {

        validate(updatedOrderDTO);

        boolean check = false;
        Orders order = orderRepository.findById(id_order).orElse(null);

        if (order != null) {
            order.setDate(updatedOrderDTO.getDate());
            order.setOrderNumber(updatedOrderDTO.getOrderNumber());

            Client client = clientRepository.findById(updatedOrderDTO.getClient().getId_client()).orElse(null);
            List<ClientDTO> clientsDTO = clientService.getActiveClients();

            for (ClientDTO clientDTO: clientsDTO) {
                if (Objects.equals(client.getId_client(), clientDTO.getId_client())) {
                    order.setClient(client);
                    check = true;
                    break;
                }
            }
            if (!check) {
                throw new MyException("There is no client with the entered ID.");
            }
            check = false;

            List<Product> products = updatedOrderDTO.getProducts();
            if (products.isEmpty()) {
                throw new MyException("List of prdoucts can't be empty.");
            } else {
                order.setProducts(products);
            }
        } else {
            System.out.println("It wasn't possible to find an order with the ID: " + updatedOrderDTO.getId_order());
        }

        Orders savedOrder = orderRepository.save(order);
        return OrderMapper.toOrderDTO(savedOrder);
    }

    public OrderDTO deactivateOrder(Long id_order) {

        Orders order = orderRepository.findById(id_order).orElse(null);

        if (order != null) {
            order.setActive(false);
            Orders savedOrder = orderRepository.save(order);
            OrderDTO orderDTO = OrderMapper.toOrderDTO(savedOrder);
            return orderDTO;
        } else {
            System.out.println("It wasn´t possible to find an order with the ID: " + id_order);
            return null;
        }
    }



    public boolean onlySpaces(String input) {
        return input.trim().isEmpty();
    }

    private void validate(OrderDTO orderDTO) throws MyException {

        if (orderDTO.getDate() == null) {
            throw new MyException("Date can't be null or empty");
        }
/*
        if (orderDTO.getProducts() == null || orderDTO.getProducts().isEmpty()) {
            throw new MyException("Orders's products can't be null or empty");
        }
*/
        if (orderDTO.getOrderNumber() == null) {
            throw new MyException("Orders number can't be null or empty");
        }
    }
}
