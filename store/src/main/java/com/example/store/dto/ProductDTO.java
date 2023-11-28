package com.example.store.dto;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO {

    private Long id_news;
    private String productName;
    private Integer price;
    private String color;



}
