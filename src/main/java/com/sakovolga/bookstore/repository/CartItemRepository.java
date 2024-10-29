package com.sakovolga.bookstore.repository;

import com.sakovolga.bookstore.entity.CartItem;
import com.sakovolga.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
//    @Query("SELECT c FROM CartItem c WHERE c.user = :user")
    List<CartItem> findAllByUser(User user);

//    @Query(value = "SELECT COUNT(*) = :size FROM cart_items WHERE id IN :cartItemIds", nativeQuery = true)
//    boolean allExists(@Param("cartItemIds") List<Long> cartItemIds, @Param("size") int size);

    @Query("SELECT c FROM CartItem c WHERE c.id IN :cartItemIds")
    List<CartItem> findAllByIds(@Param("cartItemIds") List<Long> cartItemIds);

    void deleteAllByCartItemIdIn(List<Long> cartItemIds);
}
