package com.example.store.dto;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO {

    private Long id_product;
    private String productName;
    private Integer price;
    private String color;
    private boolean isActive = true;

}
