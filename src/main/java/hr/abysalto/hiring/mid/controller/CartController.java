package hr.abysalto.hiring.mid.controller;

import hr.abysalto.hiring.mid.dto.CartItemRequest;
import hr.abysalto.hiring.mid.model.CartItem;
import hr.abysalto.hiring.mid.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Shopping Cart", description = "APIs for shopping cart management")
public class CartController {
    
    private final CartService cartService;
    
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @GetMapping
    @Operation(summary = "Get user's shopping cart")
    public ResponseEntity<List<CartItem>> getUserCart(@RequestParam Integer userId) {
        List<CartItem> cartItems = cartService.getUserCart(userId);
        return ResponseEntity.ok(cartItems);
    }
    
    @PostMapping("/add")
    @Operation(summary = "Add product to cart")
    public ResponseEntity<CartItem> addToCart(@RequestParam Integer userId, @RequestBody CartItemRequest request) {
        try {
            CartItem cartItem = cartService.addToCart(userId, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/update")
    @Operation(summary = "Update cart item quantity")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @RequestParam Integer userId,
            @RequestParam Integer productId,
            @RequestParam Integer quantity) {
        try {
            CartItem cartItem = cartService.updateCartItemQuantity(userId, productId, quantity);
            return ResponseEntity.ok(cartItem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/remove")
    @Operation(summary = "Remove product from cart")
    public ResponseEntity<Void> removeFromCart(@RequestParam Integer userId, @RequestParam Integer productId) {
        boolean removed = cartService.removeFromCart(userId, productId);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/clear")
    @Operation(summary = "Clear user's cart")
    public ResponseEntity<Void> clearCart(@RequestParam Integer userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
} 