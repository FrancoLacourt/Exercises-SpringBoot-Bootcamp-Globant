package com.example.store.repositories;

import com.example.store.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    Orders findByOrderNumberAndIsActiveIsTrue(Integer orderNumber);
}
