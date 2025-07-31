package hr.abysalto.hiring.mid.service.impl;

import hr.abysalto.hiring.mid.model.CartItem;
import hr.abysalto.hiring.mid.model.Product;
import hr.abysalto.hiring.mid.repository.CartItemRepository;
import hr.abysalto.hiring.mid.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public CartServiceImpl(CartItemRepository cartItemRepository, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    public List<CartItem> getUserCart(Integer userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        
        for (CartItem item : cartItems) {
            try {
                Product product = productService.getProductById(item.getProductId());
                item.setProduct(product);
            } catch (Exception e) {
                System.err.println("Product not found for ID: " + item.getProductId());
            }
        }
        
        return cartItems;
    }

    public CartItem addToCart(Integer userId, Integer productId, Integer quantity) {
        try {
            Product product = productService.getProductById(productId);
            
            Optional<CartItem> existingItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
            
            if (existingItem.isPresent()) {
                CartItem item = existingItem.get();
                item.setQuantity(item.getQuantity() + quantity);
                return cartItemRepository.save(item);
            } else {
                CartItem newItem = new CartItem();
                newItem.setUserId(userId);
                newItem.setProductId(productId);
                newItem.setQuantity(quantity);
                newItem.setProduct(product);
                return cartItemRepository.save(newItem);
            }
        } catch (Exception e) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }
    }

    public CartItem updateCartItemQuantity(Integer userId, Integer productId, Integer quantity) {
        Optional<CartItem> existingItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(quantity);
            return cartItemRepository.save(item);
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }

    public void removeFromCart(Integer userId, Integer productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }

    public void clearCart(Integer userId) {
        cartItemRepository.deleteByUserId(userId);
    }
} 