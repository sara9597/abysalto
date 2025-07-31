package hr.abysalto.hiring.mid.controller;

import hr.abysalto.hiring.mid.dto.CartItemRequest;
import hr.abysalto.hiring.mid.model.CartItem;
import hr.abysalto.hiring.mid.service.impl.CartServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Shopping Cart", description = "APIs for shopping cart management")
public class CartController {
    
    private final CartServiceImpl cartService;
    
    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @Operation(summary = "Get user's shopping cart")
    public ResponseEntity<List<CartItem>> getUserCart(
            @Parameter(description = "User ID") @RequestParam Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        List<CartItem> cartItems = cartService.getUserCart(userId);
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/add")
    @Operation(summary = "Add product to cart")
    public ResponseEntity<CartItem> addToCart(
            @Parameter(description = "User ID") @RequestParam Integer userId,
            @RequestBody CartItemRequest request) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("Cart item request cannot be null");
        }
        if (request.getProductId() == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        try {
            CartItem cartItem = cartService.addToCart(userId, request.getProductId(), request.getQuantity());
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Failed to add product to cart: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    @Operation(summary = "Update cart item quantity")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @Parameter(description = "User ID") @RequestParam Integer userId,
            @Parameter(description = "Product ID") @RequestParam Integer productId,
            @Parameter(description = "New quantity") @RequestParam Integer quantity) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        try {
            CartItem cartItem = cartService.updateCartItemQuantity(userId, productId, quantity);
            return ResponseEntity.ok(cartItem);
        } catch (RuntimeException e) {
            throw new NoSuchElementException("Cart item not found");
        }
    }

    @DeleteMapping("/remove")
    @Operation(summary = "Remove product from cart")
    public ResponseEntity<Void> removeFromCart(
            @Parameter(description = "User ID") @RequestParam Integer userId,
            @Parameter(description = "Product ID") @RequestParam Integer productId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        cartService.removeFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Clear user's entire cart")
    public ResponseEntity<Void> clearCart(
            @Parameter(description = "User ID") @RequestParam Integer userId) {
            if (userId == null) {
                throw new IllegalArgumentException("User ID cannot be null");
            }

            cartService.clearCart(userId);
            return ResponseEntity.noContent().build();
    }
} 