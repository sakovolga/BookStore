package com.sakovolga.bookstore.service.impl;

import com.sakovolga.bookstore.dto.CartItemDto;
import com.sakovolga.bookstore.entity.Book;
import com.sakovolga.bookstore.entity.CartItem;
import com.sakovolga.bookstore.entity.User;
import com.sakovolga.bookstore.exception.NothingFoundException;
import com.sakovolga.bookstore.mapper.CartItemMapper;
import com.sakovolga.bookstore.repository.BookRepository;
import com.sakovolga.bookstore.repository.CartItemRepository;
import com.sakovolga.bookstore.security.UserProvider;
import com.sakovolga.bookstore.service.CartService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserProvider userProvider;
    @PersistenceContext
    private EntityManager entityManager;
    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public void addToCart(CartItemDto cartItemDto) throws BadRequestException {
        long bookId = cartItemDto.getBookId();
        short quantityToAdd = cartItemDto.getQuantity();

        // Проверка существования книги
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NothingFoundException("There is no book with id " + bookId));

        // Проверка остатка на складе
        Integer reminder = book.getReminder();
        int actualReminder = (reminder != null) ? reminder : 0;
        if (quantityToAdd > actualReminder) {
            throw new BadRequestException("The number of books ordered exceeds the number of books in stock");
        }

        // Получение текущего пользователя
        User user = entityManager.merge(userProvider.getCurrentUser());

        // Проверка, есть ли уже этот товар в корзине
        Optional<CartItem> existingCartItem = cartItemRepository.findByUserAndBook(user, book);
        if (existingCartItem.isPresent()) {
            // Если товар уже есть, обновляем его количество
            CartItem cartItem = existingCartItem.get();
            short newQuantity = (short) (cartItem.getQuantity() + quantityToAdd);

            // Проверяем, хватает ли остатка для добавления
            if (newQuantity > actualReminder) {
                throw new BadRequestException("The total number of books exceeds the number of books in stock");
            }

            // Обновляем остаток на складе
            book.setReminder(actualReminder - quantityToAdd);

            cartItem.setQuantity(newQuantity);
            cartItemRepository.saveAndFlush(cartItem);
        } else {
            // Если товара в корзине нет, создаем новый
            book.setReminder(actualReminder - quantityToAdd);
            CartItem cartItem = new CartItem();
            cartItem.setBook(book);
            cartItem.setQuantity(quantityToAdd);
            cartItem.setUser(user);
            cartItem.setCreatedAt(LocalDateTime.now());
            cartItemRepository.saveAndFlush(cartItem);
        }
    }


    @Override
    @Transactional
    public List<CartItemDto> getCart() {
        User user = entityManager.merge(userProvider.getCurrentUser());
        List<CartItem> cartItems = cartItemRepository.findAllByUser(user);
        return cartItems.stream()
                .map(cartItemMapper::toDto)
                .toList();
    }
}
