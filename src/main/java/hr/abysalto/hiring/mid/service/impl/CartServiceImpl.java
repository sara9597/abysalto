package hr.abysalto.hiring.mid.service.impl;

import hr.abysalto.hiring.mid.dto.CartItemRequest;
import hr.abysalto.hiring.mid.model.CartItem;
import hr.abysalto.hiring.mid.model.Product;
import hr.abysalto.hiring.mid.repository.CartItemRepository;
import hr.abysalto.hiring.mid.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    
    private final CartItemRepository cartItemRepository;
    private final ProductServiceImpl productService;
    
    public CartServiceImpl(CartItemRepository cartItemRepository, ProductServiceImpl productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }
    
    public List<CartItem> getUserCart(Integer userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        
        return cartItems.stream()
                .map(item -> {
                    Optional<Product> product = productService.getProductById(item.getProductId());
                    product.ifPresent(item::setProduct);
                    return item;
                })
                .collect(Collectors.toList());
    }
    
    public CartItem addToCart(Integer userId, CartItemRequest request) {
        Optional<Product> product = productService.getProductById(request.getProductId());
        if (product.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        
        Optional<CartItem> existingItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
        
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            return cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUserId(userId);
            newItem.setProductId(request.getProductId());
            newItem.setQuantity(request.getQuantity());
            return cartItemRepository.save(newItem);
        }
    }
    
    public CartItem updateCartItemQuantity(Integer userId, Integer productId, Integer quantity) {
        Optional<CartItem> existingItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            if (quantity <= 0) {
                cartItemRepository.deleteByUserIdAndProductId(userId, productId);
                return null;
            } else {
                item.setQuantity(quantity);
                return cartItemRepository.save(item);
            }
        }
        throw new RuntimeException("Cart item not found");
    }
    
    public boolean removeFromCart(Integer userId, Integer productId) {
        return cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }
    
    public void clearCart(Integer userId) {
        cartItemRepository.deleteByUserId(userId);
    }
    
    public Optional<CartItem> getCartItem(Integer userId, Integer productId) {
        return cartItemRepository.findByUserIdAndProductId(userId, productId);
    }
} 