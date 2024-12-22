package com.sakovolga.bookstore.repository;

import com.sakovolga.bookstore.entity.Order;
import com.sakovolga.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    //    List<Order> findAllByUser(User user);
    @Query("SELECT o.id FROM Order o WHERE o.user = :user")
    List<Long> findIdByUser(@Param("user") User user);

}
