package hr.abysalto.hiring.mid.service;

import hr.abysalto.hiring.mid.model.Favorite;
import java.util.List;

public interface FavoriteService {
    
    List<Favorite> getUserFavorites(Integer userId);

    Favorite addToFavorites(Integer userId, Integer productId);

    void removeFromFavorites(Integer userId, Integer productId);

    boolean isFavorite(Integer userId, Integer productId);
}