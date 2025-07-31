package hr.abysalto.hiring.mid.controller;

import hr.abysalto.hiring.mid.model.Favorite;
import hr.abysalto.hiring.mid.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@Tag(name = "Favorites", description = "APIs for managing user favorites")
public class FavoriteController {
    
    private final FavoriteService favoriteService;
    
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }
    
    @GetMapping
    @Operation(summary = "Get user's favorites")
    public ResponseEntity<List<Favorite>> getUserFavorites(@RequestParam Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        List<Favorite> favorites = favoriteService.getUserFavorites(userId);
        return ResponseEntity.ok(favorites);
    }
    
    @PostMapping("/add")
    @Operation(summary = "Add product to favorites")
    public ResponseEntity<Favorite> addToFavorites(@RequestParam Integer userId, @RequestParam Integer productId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }

        try {
            Favorite favorite = favoriteService.addToFavorites(userId, productId);
            return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Failed to add product to favorites: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove")
    @Operation(summary = "Remove product from favorites")
    public ResponseEntity<Void> removeFromFavorites(
            @Parameter(description = "User ID") @RequestParam Integer userId,
            @Parameter(description = "Product ID") @RequestParam Integer productId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        favoriteService.removeFromFavorites(userId, productId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/check")
    @Operation(summary = "Check if product is in user's favorites")
    public ResponseEntity<Boolean> isFavorite(@RequestParam Integer userId, @RequestParam Integer productId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        boolean isFavorite = favoriteService.isFavorite(userId, productId);
        return ResponseEntity.ok(isFavorite);
    }
} 