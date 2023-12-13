package com.example.franconews.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;


@Entity
@Getter
@Setter
@Data
@ToString
@RequiredArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_news;

    private String title;
    private String body;
    private boolean isActive = true;


    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    public News(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
