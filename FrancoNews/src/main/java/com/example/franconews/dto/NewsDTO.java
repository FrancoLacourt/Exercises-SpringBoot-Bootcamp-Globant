package com.example.franconews.dto;

import lombok.Data;

@Data
public class NewsDTO {

    private String title;
    private String body;
    private boolean isActive = true;
    private Long id_news;


}
