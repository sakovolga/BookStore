package com.sakovolga.bookstore.repository;

import com.sakovolga.bookstore.entity.Order;
import com.sakovolga.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
