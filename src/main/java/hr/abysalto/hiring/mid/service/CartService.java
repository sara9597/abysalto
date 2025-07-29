package hr.abysalto.hiring.mid.service;

import hr.abysalto.hiring.mid.dto.CartItemRequest;
import hr.abysalto.hiring.mid.model.CartItem;
import java.util.List;
import java.util.Optional;

public interface CartService {
    List<CartItem> getUserCart(Integer userId);

    CartItem addToCart(Integer userId, CartItemRequest request);

    CartItem updateCartItemQuantity(Integer userId, Integer productId, Integer quantity);

    boolean removeFromCart(Integer userId, Integer productId);

    void clearCart(Integer userId);

    Optional<CartItem> getCartItem(Integer userId, Integer productId);
} 