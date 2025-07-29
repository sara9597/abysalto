package hr.abysalto.hiring.mid.repository;

import hr.abysalto.hiring.mid.model.CartItem;
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
public class CartItemRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    private final RowMapper<CartItem> cartItemRowMapper = (rs, rowNum) -> new CartItem(
            rs.getInt("cart_item_id"),
            rs.getInt("user_id"),
            rs.getInt("product_id"),
            rs.getInt("quantity"),
            null
    );
    
    public CartItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<CartItem> findByUserId(Integer userId) {
        return jdbcTemplate.query(
                "SELECT * FROM cart_items WHERE user_id = ? ORDER BY cart_item_id",
                cartItemRowMapper,
                userId
        );
    }
    
    public Optional<CartItem> findById(Integer id) {
        List<CartItem> items = jdbcTemplate.query(
                "SELECT * FROM cart_items WHERE cart_item_id = ?",
                cartItemRowMapper,
                id
        );
        return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
    }
    
    public Optional<CartItem> findByUserIdAndProductId(Integer userId, Integer productId) {
        List<CartItem> items = jdbcTemplate.query(
                "SELECT * FROM cart_items WHERE user_id = ? AND product_id = ?",
                cartItemRowMapper,
                userId, productId
        );
        return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
    }
    
    public CartItem save(CartItem cartItem) {
        if (cartItem.getCartItemId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO cart_items (user_id, product_id, quantity) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setInt(1, cartItem.getUserId());
                ps.setInt(2, cartItem.getProductId());
                ps.setInt(3, cartItem.getQuantity());
                return ps;
            }, keyHolder);
            cartItem.setCartItemId(keyHolder.getKey().intValue());
        } else {
            jdbcTemplate.update(
                    "UPDATE cart_items SET user_id = ?, product_id = ?, quantity = ? WHERE cart_item_id = ?",
                    cartItem.getUserId(),
                    cartItem.getProductId(),
                    cartItem.getQuantity(),
                    cartItem.getCartItemId()
            );
        }
        return cartItem;
    }
    
    public boolean deleteById(Integer id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM cart_items WHERE cart_item_id = ?", id);
        return rowsAffected > 0;
    }
    
    public boolean deleteByUserIdAndProductId(Integer userId, Integer productId) {
        int rowsAffected = jdbcTemplate.update(
                "DELETE FROM cart_items WHERE user_id = ? AND product_id = ?",
                userId, productId
        );
        return rowsAffected > 0;
    }
    
    public void deleteByUserId(Integer userId) {
        jdbcTemplate.update("DELETE FROM cart_items WHERE user_id = ?", userId);
    }
} 