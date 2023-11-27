package com.example.franconews.services;

import com.example.franconews.entities.News;
import com.example.franconews.exceptions.MyException;
import com.example.franconews.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.stream.Collectors;


@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Transactional
    public News createNews(News news) throws MyException{

        validate(news);

        return (News) newsRepository.save(news);
    }

    public List<News> getAllNews() {

        return (List<News>) newsRepository.findAll();
    }

    public List<News> getActiveNews() {

        List <News> newsList = getAllNews();

        return newsList.stream().filter(News::isActive).collect(Collectors.toList());
    }


    public News findNewsById(Long id_news) {

        News news = (News) newsRepository.findById(id_news).orElse(null);

        if (news != null) {
            return news;
        } else {
            System.out.println("It wasn't possible to find news with the ID: " + id_news);
            return null;
        }
    }

    public News findByTitle(String title) {

        News news = newsRepository.findByTitleAndIsActiveIsTrue(title);

        if (news != null) {
            return news;
        } else {
            System.out.println("It wasn't possible to find news with the title: " + title);
            return null;
        }
    }

    @Transactional
    public News updateNews(Long id_news, News updatedNews) throws MyException{

        validate(updatedNews);

        News existingNews = (News) newsRepository.findById(id_news).orElse(null);

        if (existingNews != null) {

            existingNews.setTitle(updatedNews.getTitle());
            existingNews.setBody(updatedNews.getBody());

           return (News) newsRepository.save(existingNews);
        } else {
            System.out.println("It wasn't possible to find news with the ID: " + id_news);
            return null;
        }
    }

    public void deactivateNews(Long id_news) {

        News news = (News) newsRepository.findById(id_news).orElse(null);

        if (news != null) {
            news.setActive(false);
            newsRepository.save(news);
        } else {
            System.out.println("It wasn't possible to find news with the ID: " + id_news);
        }
    }

    public void activateNews(Long id_news) {

        News news = (News) newsRepository.findById(id_news).orElse(null);

        if (news != null) {
            news.setActive(true);
            newsRepository.save(news);
        } else {
            System.out.println("It wasn't possible to find news with the ID: " + id_news);
        }
    }


    public boolean onlySpaces(String input) {
        return input.trim().isEmpty();
    }

    public boolean isActive(Long id_news) {
        News news = findNewsById(id_news);

        if (news.isActive()) {
            return true;
        } else {
            return false;
        }
    }

    private void validate(News news) throws MyException {

        if (news.getTitle().isEmpty() || news.getTitle() == null || onlySpaces(news.getTitle())) {
            throw new MyException("Title can't be null or empty.");
        }

        if (news.getBody().isEmpty() || news.getBody() == null || onlySpaces(news.getBody())) {
            throw new MyException("body can't be null or empty.");
        }

    }

}
