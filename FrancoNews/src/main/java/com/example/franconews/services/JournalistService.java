package com.example.franconews.services;

import com.example.franconews.dto.JournalistDTO;
import com.example.franconews.entities.Journalist;
import com.example.franconews.entities.News;
import com.example.franconews.exceptions.MyException;
import com.example.franconews.mapper.JournalistMapper;
import com.example.franconews.repositories.JournalistRepository;
import com.example.franconews.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class JournalistService extends UserService {

    @Autowired
    private JournalistRepository journalistRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private JournalistMapper journalistMapper;


    public Journalist findJournalistById(Long id) {

        Journalist journalist = journalistRepository.findById(id).orElse(null);

        if (journalist != null) {
            return journalist;
        } else {
            System.out.println("It wasn't possible to find a journalist with the ID: " + id);
            return null;
        }
    }

    public JournalistDTO setMonthlySalary(Long id, Integer monthlySalary) {

        Journalist journalist = journalistRepository.findById(id).orElse(null);

        if (journalist != null) {

            journalist.setMonthlySalary(monthlySalary);

            Journalist savedJournalist = journalistRepository.save(journalist);
            JournalistDTO journalistDTO = journalistMapper.journalistToJournalistDTO(savedJournalist);
            journalistDTO.setActive(true);
            return journalistDTO;
        } else {
            System.out.println("It wasn't possible to find a journalist with the ID: " + id);
            return null;
        }
    }

    public void addNewsToJournalist(Long id, Long id_news) throws MyException {
        Journalist journalist = findJournalistById(id);
        News news = newsRepository.findById(id_news).orElseThrow(null);

        if (journalist == null || news == null) {
            throw new MyException("Journalist or news can't be null or empty.");
        }

        journalist.getMyNews().add(news);
        journalistRepository.save(journalist);
    }

    public JournalistDTO deactivateJournalist(Long id) {

        Journalist journalist = journalistRepository.findById(id).orElseThrow(null);

        if (journalist != null) {
            journalist.setActive(false);
            Journalist savedJournalist = journalistRepository.save(journalist);
            JournalistDTO journalistDTO = journalistMapper.journalistToJournalistDTO(savedJournalist);
            journalistDTO.setActive(false);
            return journalistDTO;
        } else {
            System.out.println("It wasn't possible to find a journalist with the ID: " + id);
            return null;
        }
    }

    public JournalistDTO activateJournalist(Long id) {

        Journalist journalist = journalistRepository.findById(id).orElseThrow(null);

        if (journalist != null) {
            journalist.setActive(true);
            journalist.setRegistrationDate(LocalDate.now());
            Journalist savedJournalist = journalistRepository.save(journalist);
            JournalistDTO journalistDTO = journalistMapper.journalistToJournalistDTO(savedJournalist);
            journalistDTO.setActive(true);
            return journalistDTO;
        } else {
            System.out.println("It wasn't possible to find a journalist with the ID: " + id);
            return null;
        }
    }
}
