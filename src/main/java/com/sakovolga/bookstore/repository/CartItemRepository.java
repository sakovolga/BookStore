package com.sakovolga.bookstore.repository;

import com.sakovolga.bookstore.entity.Book;
import com.sakovolga.bookstore.entity.CartItem;
import com.sakovolga.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByUser(User user);

    @Query("SELECT c FROM CartItem c WHERE c.id IN :cartItemIds")
    List<CartItem> findAllByIds(@Param("cartItemIds") List<Long> cartItemIds);

    void deleteAllByCartItemIdIn(List<Long> cartItemIds);

    Optional<CartItem> findByUserAndBook(User user, Book book);
}
