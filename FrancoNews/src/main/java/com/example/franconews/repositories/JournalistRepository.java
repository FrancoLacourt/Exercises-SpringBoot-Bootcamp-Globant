package com.example.franconews.repositories;

import com.example.franconews.entities.Journalist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalistRepository extends JpaRepository<Journalist, Long> {
}
