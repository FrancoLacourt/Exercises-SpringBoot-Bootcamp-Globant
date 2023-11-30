package com.example.store.dto;

import com.example.store.entities.Client;
import com.example.store.entities.Product;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {

    private Long id_order;
    private LocalDate date;
    private Client client;
    private List<Product> products;
    private List<Long> productIds;
    private Long orderNumber;
    private boolean isActive = true;

}
