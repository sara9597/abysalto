package hr.abysalto.hiring.mid.service.impl;

import hr.abysalto.hiring.mid.model.Favorite;
import hr.abysalto.hiring.mid.model.Product;
import hr.abysalto.hiring.mid.repository.FavoriteRepository;
import hr.abysalto.hiring.mid.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    
    private final FavoriteRepository favoriteRepository;
    private final ProductServiceImpl productService;
    
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, ProductServiceImpl productService) {
        this.favoriteRepository = favoriteRepository;
        this.productService = productService;
    }
    
    public List<Favorite> getUserFavorites(Integer userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        
        return favorites.stream()
                .map(favorite -> {
                    Optional<Product> product = productService.getProductById(favorite.getProductId());
                    product.ifPresent(favorite::setProduct);
                    return favorite;
                })
                .collect(Collectors.toList());
    }
    
    public Favorite addToFavorites(Integer userId, Integer productId) {
        Optional<Product> product = productService.getProductById(productId);
        if (product.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        
        if (favoriteRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new RuntimeException("Product already in favorites");
        }
        
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductId(productId);
        return favoriteRepository.save(favorite);
    }
    
    public boolean removeFromFavorites(Integer userId, Integer productId) {
        return favoriteRepository.deleteByUserIdAndProductId(userId, productId);
    }
    
    public boolean isFavorite(Integer userId, Integer productId) {
        return favoriteRepository.existsByUserIdAndProductId(userId, productId);
    }
    
    public Optional<Favorite> getFavorite(Integer userId, Integer productId) {
        return favoriteRepository.findByUserIdAndProductId(userId, productId);
    }
} 