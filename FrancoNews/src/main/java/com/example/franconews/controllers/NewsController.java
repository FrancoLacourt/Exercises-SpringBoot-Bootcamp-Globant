package com.example.franconews.controllers;

import com.example.franconews.dto.NewsDTO;
import com.example.franconews.entities.Admin;
import com.example.franconews.entities.Journalist;
import com.example.franconews.entities.News;
import com.example.franconews.entities.UserEntity;
import com.example.franconews.exceptions.MyException;
import com.example.franconews.mapper.NewsMapper;
import com.example.franconews.repositories.AdminRepository;
import com.example.franconews.repositories.UserRepository;
import com.example.franconews.services.AdminService;
import com.example.franconews.services.JournalistService;
import com.example.franconews.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private JournalistService journalistService;

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;


    @PostMapping("/register/{id}")
    @Secured({"ADMIN", "JOURNALIST"})
    public ResponseEntity<NewsDTO> register(@RequestBody NewsDTO newsDTO, @PathVariable Long id) {

        try {
            Journalist journalist = journalistService.findJournalistById(id);

            if (journalist == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            News news = NewsMapper.toNews(newsDTO);
            News savedNews = newsService.createNews(news);
            NewsDTO savedNewsDTO = NewsMapper.toNewsDTO(savedNews);
            Long id_news = savedNews.getId_news();

            if (savedNewsDTO.getTitle() == null || savedNewsDTO.getBody() == null
                    || savedNewsDTO.getTitle().isEmpty() || savedNewsDTO.getBody().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            journalistService.addNewsToJournalist(id, id_news);

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
    @Secured({"ADMIN", "JOURNALIST"})
    public ResponseEntity<NewsDTO> findNewsById(@PathVariable Long id_news) {

        News news = newsService.findNewsById(id_news);
        NewsDTO newsDTO = NewsMapper.toNewsDTO(news);

        return ResponseEntity.status(HttpStatus.OK).body(newsDTO);
    }

    @GetMapping("/findByTitle")
    @Secured({"ADMIN", "JOURNALIST"})
    public ResponseEntity<NewsDTO> findByTitle(@RequestParam String title) {

        News news = newsService.findByTitle(title);
        NewsDTO newsDTO = NewsMapper.toNewsDTO(news);

        return ResponseEntity.status(HttpStatus.OK).body(newsDTO);
    }


    @PutMapping("/update/{id_news}")
    @Secured({"ADMIN", "JOURNALIST"})
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Long id_news, @RequestBody NewsDTO updatedNewsDTO) {

        try {

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

        } catch (MyException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/activate/{id_news}")
    @Secured({"ADMIN"})
    public ResponseEntity<NewsDTO> activateNews(@PathVariable Long id_news) throws MyException {

        News news = newsService.findNewsById(id_news);

        if (news == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        news.setActive(true);
        News activatedNews = newsService.updateNews(id_news, news);
        NewsDTO activatedNewsDTO = NewsMapper.toNewsDTO(activatedNews);

        return ResponseEntity.status(HttpStatus.OK).body(activatedNewsDTO);
    }

    @PutMapping("/deactivate/{id_news}")
    @Secured({"ADMIN"})
    public ResponseEntity<NewsDTO> deactivateNews(@PathVariable Long id_news) throws MyException {

        News news = newsService.findNewsById(id_news);

        if (news == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        news.setActive(false);
        News activatedNews = newsService.updateNews(id_news, news);
        NewsDTO activatedNewsDTO = NewsMapper.toNewsDTO(activatedNews);

        return ResponseEntity.status(HttpStatus.OK).body(activatedNewsDTO);
    }
}


