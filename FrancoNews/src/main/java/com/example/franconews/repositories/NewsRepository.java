package com.example.franconews.repositories;

import com.example.franconews.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    News findByTitleAndIsActiveIsTrue(String title);
}
