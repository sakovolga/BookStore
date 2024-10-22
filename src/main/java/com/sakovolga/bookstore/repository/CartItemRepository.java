package com.sakovolga.bookstore.repository;

import com.sakovolga.bookstore.entity.CartItem;
import com.sakovolga.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
//    @Query("SELECT c FROM CartItem c WHERE c.user = :user")
    List<CartItem> findAllByUser(User user);
}
