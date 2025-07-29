package hr.abysalto.hiring.mid.repository;

import hr.abysalto.hiring.mid.model.User;
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
public class UserRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getInt("user_id"),
            rs.getString("username"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("phone"),
            rs.getString("address")
    );
    
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users ORDER BY user_id", userRowMapper);
    }
    
    public Optional<User> findById(Integer id) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE user_id = ?",
                userRowMapper,
                id
        );
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
    
    public Optional<User> findByUsername(String username) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE username = ?",
                userRowMapper,
                username
        );
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
    
    public Optional<User> findByEmail(String email) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE email = ?",
                userRowMapper,
                email
        );
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
    
    public User save(User user) {
        if (user.getUserId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO users (username, email, password, first_name, last_name, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getFirstName());
                ps.setString(5, user.getLastName());
                ps.setString(6, user.getPhone());
                ps.setString(7, user.getAddress());
                return ps;
            }, keyHolder);
            user.setUserId(keyHolder.getKey().intValue());
        } else {
            jdbcTemplate.update(
                    "UPDATE users SET username = ?, email = ?, password = ?, first_name = ?, last_name = ?, phone = ?, address = ? WHERE user_id = ?",
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPhone(),
                    user.getAddress(),
                    user.getUserId()
            );
        }
        return user;
    }
    
    public boolean deleteById(Integer id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM users WHERE user_id = ?", id);
        return rowsAffected > 0;
    }
} 