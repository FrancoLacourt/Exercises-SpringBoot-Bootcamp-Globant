package com.example.store.controllers;

import com.example.store.dto.OrderDTO;
import com.example.store.exceptions.MyException;
import com.example.store.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class  OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/register")
    public ResponseEntity<OrderDTO> register(@RequestBody OrderDTO orderDTO) throws MyException{

        try {
            OrderDTO savedOrderDTO = orderService.createOrder(orderDTO);

            if (orderDTO.getDate() == null || orderDTO.getOrderNumber() == null || orderDTO.getClient() == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderDTO);

        } catch (DataIntegrityViolationException ex) {
            // Capturar errores de integridad de datos (p. ej., restricciones de base de datos)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/listOfOrders")
    public ResponseEntity<List<OrderDTO>> getOrders() {
        List<OrderDTO> ordersDTO = orderService.getActiveOrders();

        if (ordersDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(ordersDTO);
    }

    @GetMapping("/{id_order}")
    public ResponseEntity<OrderDTO> findOrderById(@PathVariable Long id_order) {
        OrderDTO orderDTO = orderService.findOrderById(id_order);

        if (orderDTO != null) {

            if (orderDTO.getProducts().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
        } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }


    @GetMapping("/findByOrderNumber")
    public ResponseEntity<OrderDTO> findByOrderNumber(@RequestParam Integer orderNumber) {
        OrderDTO orderDTO = orderService.findOrderByOrderNumber(orderNumber);

        if (orderDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update/{id_order}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id_order, @RequestBody OrderDTO updatedOrderDTO) throws MyException {

        OrderDTO orderDTO = orderService.updateOrder(id_order, updatedOrderDTO);

        if (updatedOrderDTO.getDate() == null
                || updatedOrderDTO.getOrderNumber() == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        if (orderDTO.getId_order() == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }

    @PutMapping("/deactivate/{id_order}")
    public ResponseEntity<OrderDTO> deactivateOrder(@PathVariable Long id_order) throws MyException {

        OrderDTO orderDTO = orderService.findOrderById(id_order);

        if (orderDTO != null) {
            OrderDTO deactivatedOrderDTO = orderService.deactivateOrder(id_order);
            return ResponseEntity.status(HttpStatus.OK).body(deactivatedOrderDTO);
        } else {
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body(null);
        }
    }
}
