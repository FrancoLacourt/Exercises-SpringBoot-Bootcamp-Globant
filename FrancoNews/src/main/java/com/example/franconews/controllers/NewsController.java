package com.example.franconews.controllers;

import com.example.franconews.dto.NewsDTO;
import com.example.franconews.entities.News;
import com.example.franconews.exceptions.MyException;
import com.example.franconews.mapper.NewsMapper;
import com.example.franconews.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/register")
    public ResponseEntity<NewsDTO> register(@RequestBody NewsDTO newsDTO) {

        try {

            News news = NewsMapper.toNews(newsDTO);
            newsService.createNews(news);
            NewsDTO savedNewsDTO = NewsMapper.toNewsDTO(news);

            if (savedNewsDTO.getTitle() == null || savedNewsDTO.getBody() == null
                    || savedNewsDTO.getTitle().isEmpty() || savedNewsDTO.getBody().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(savedNewsDTO);

        } catch (MyException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/listOfNews")
    public ResponseEntity<List<NewsDTO>> getNews() {
        List<News> newsList = newsService.getActiveNews();
        List<NewsDTO> newsDTOList = new ArrayList<>();

        for (News news : newsList) {
            NewsDTO newsDTO = NewsMapper.toNewsDTO(news);
            newsDTOList.add(newsDTO);
        }

        if (newsDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(newsDTOList);
    }

    @GetMapping("/{id_news}")
    public ResponseEntity<NewsDTO> findNewsById(@PathVariable Long id_news) {
        News news = newsService.findNewsById(id_news);
        NewsDTO newsDTO = NewsMapper.toNewsDTO(news);

        if (newsDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(newsDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/findByTitle")
    public ResponseEntity<NewsDTO> findByTitle(@RequestParam String title) {
        News news = newsService.findByTitle(title);
        NewsDTO newsDTO = NewsMapper.toNewsDTO(news);

        if (newsDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(newsDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PutMapping("/update/{id_news}")
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Long id_news, @RequestBody NewsDTO updatedNewsDTO) throws MyException {

        News updatedNews = NewsMapper.toNews(updatedNewsDTO);
        News news = newsService.updateNews(id_news, updatedNews);
        NewsDTO newsDTO = NewsMapper.toNewsDTO(news);

        if (newsDTO.getTitle() == null || newsDTO.getBody() == null || newsDTO.getTitle().isEmpty()
                || newsDTO.getBody().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        if (news.getId_news() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(newsDTO);

    }

    @PutMapping("/activate/{id_news}")
    public ResponseEntity<NewsDTO> activateNews(@PathVariable Long id_news) throws MyException {

        News news = newsService.findNewsById(id_news);

        if (news != null) {
            news.setActive(true);
            News activatedNews = newsService.updateNews(id_news, news);
            NewsDTO activatedNewsDTO = NewsMapper.toNewsDTO(activatedNews);

            return ResponseEntity.status(HttpStatus.OK).body(activatedNewsDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/deactivate/{id_news}")
    public ResponseEntity<NewsDTO> deactivateNews(@PathVariable Long id_news) throws MyException {

        News news = newsService.findNewsById(id_news);

        if (news != null) {
            news.setActive(false);
            News deactivatedNews = newsService.updateNews(id_news, news);
            NewsDTO deactivatedNewsDTO = NewsMapper.toNewsDTO(deactivatedNews);

            return ResponseEntity.status(HttpStatus.OK).body(deactivatedNewsDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}