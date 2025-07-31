package hr.abysalto.hiring.mid.service.impl;

import hr.abysalto.hiring.mid.model.Favorite;
import hr.abysalto.hiring.mid.model.Product;
import hr.abysalto.hiring.mid.repository.FavoriteRepository;
import hr.abysalto.hiring.mid.service.FavoriteService;
import hr.abysalto.hiring.mid.service.ProductService;
import hr.abysalto.hiring.mid.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ProductService productService;
    private final UserService userService;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, ProductService productService, UserService userService) {
        this.favoriteRepository = favoriteRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public List<Favorite> getUserFavorites(Integer userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);

        for (Favorite favorite : favorites) {
            try {
                Product product = productService.getProductById(favorite.getProductId());
                favorite.setProduct(product);
            } catch (Exception e) {
                System.err.println("Product not found for ID: " + favorite.getProductId());
            }
        }

        return favorites;
    }

    public Favorite addToFavorites(Integer userId, Integer productId) {
        Product product;
        try {
            product = productService.getProductById(productId);
        } catch (Exception e) {
            throw new RuntimeException("Product not found with ID: " + productId, e);
        }

        Optional<Favorite> existingFavorite = favoriteRepository.findByUserIdAndProductId(userId, productId);

        if (existingFavorite.isPresent()) {
            throw new RuntimeException("Product already in favorites");
        }

        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductId(productId);
        favorite.setProduct(product);

        try {
            Favorite savedFavorite = favoriteRepository.save(favorite);
            return savedFavorite;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void removeFromFavorites(Integer userId, Integer productId) {
        favoriteRepository.deleteByUserIdAndProductId(userId, productId);
    }

    public boolean isFavorite(Integer userId, Integer productId) {
        return favoriteRepository.existsByUserIdAndProductId(userId, productId);
    }
} 