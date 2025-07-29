package hr.abysalto.hiring.mid.service;

import hr.abysalto.hiring.mid.model.Favorite;
import java.util.List;
import java.util.Optional;

public interface FavoriteService {
    
    List<Favorite> getUserFavorites(Integer userId);

    Favorite addToFavorites(Integer userId, Integer productId);
    
    boolean removeFromFavorites(Integer userId, Integer productId);

    boolean isFavorite(Integer userId, Integer productId);
    
    Optional<Favorite> getFavorite(Integer userId, Integer productId);
} 