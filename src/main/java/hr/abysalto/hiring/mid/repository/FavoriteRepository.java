package hr.abysalto.hiring.mid.repository;

import hr.abysalto.hiring.mid.model.Favorite;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class FavoriteRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    private final RowMapper<Favorite> favoriteRowMapper = (rs, rowNum) -> new Favorite(
            rs.getInt("favorite_id"),
            rs.getInt("user_id"),
            rs.getInt("product_id"),
            null
    );
    
    public FavoriteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<Favorite> findByUserId(Integer userId) {
        return jdbcTemplate.query(
                "SELECT * FROM favorites WHERE user_id = ? ORDER BY favorite_id",
                favoriteRowMapper,
                userId
        );
    }
    
    public Optional<Favorite> findById(Integer id) {
        List<Favorite> favorites = jdbcTemplate.query(
                "SELECT * FROM favorites WHERE favorite_id = ?",
                favoriteRowMapper,
                id
        );
        return favorites.isEmpty() ? Optional.empty() : Optional.of(favorites.get(0));
    }
    
    public Optional<Favorite> findByUserIdAndProductId(Integer userId, Integer productId) {
        List<Favorite> favorites = jdbcTemplate.query(
                "SELECT * FROM favorites WHERE user_id = ? AND product_id = ?",
                favoriteRowMapper,
                userId, productId
        );
        return favorites.isEmpty() ? Optional.empty() : Optional.of(favorites.get(0));
    }
    
    public Favorite save(Favorite favorite) {
        if (favorite.getFavoriteId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO favorites (user_id, product_id) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setInt(1, favorite.getUserId());
                ps.setInt(2, favorite.getProductId());
                return ps;
            }, keyHolder);
            favorite.setFavoriteId(keyHolder.getKey().intValue());
        } else {
            jdbcTemplate.update(
                    "UPDATE favorites SET user_id = ?, product_id = ? WHERE favorite_id = ?",
                    favorite.getUserId(),
                    favorite.getProductId(),
                    favorite.getFavoriteId()
            );
        }
        return favorite;
    }
    
    public boolean deleteById(Integer id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM favorites WHERE favorite_id = ?", id);
        return rowsAffected > 0;
    }
    
    public boolean deleteByUserIdAndProductId(Integer userId, Integer productId) {
        int rowsAffected = jdbcTemplate.update(
                "DELETE FROM favorites WHERE user_id = ? AND product_id = ?",
                userId, productId
        );
        return rowsAffected > 0;
    }
    
    public boolean existsByUserIdAndProductId(Integer userId, Integer productId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM favorites WHERE user_id = ? AND product_id = ?",
                Integer.class,
                userId, productId
        );
        return count != null && count > 0;
    }
} 