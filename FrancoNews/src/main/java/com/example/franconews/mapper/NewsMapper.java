package com.example.franconews.mapper;

import com.example.franconews.dto.NewsDTO;
import com.example.franconews.entities.News;

public class NewsMapper {

    public static NewsDTO toNewsDTO(News news) {
        NewsDTO newsDTO = new NewsDTO();

        newsDTO.setTitle(news.getTitle());
        newsDTO.setBody(news.getBody());
        newsDTO.setActive(news.isActive());
        newsDTO.setId_news(news.getId_news());

        return newsDTO;
    }

    public static News toNews(NewsDTO newsDto) {
        News news = new News();

        news.setTitle(newsDto.getTitle());
        news.setBody(newsDto.getBody());
        news.setActive(newsDto.isActive());
        news.setId_news(news.getId_news());

        return news;
    }

}
