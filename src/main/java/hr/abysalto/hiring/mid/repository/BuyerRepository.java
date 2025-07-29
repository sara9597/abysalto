package hr.abysalto.hiring.mid.repository;

import hr.abysalto.hiring.mid.model.Buyer;
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
public class BuyerRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Buyer> buyerRowMapper = (rs, rowNum) -> new Buyer(
            rs.getInt("buyer_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("title")
    );

    public BuyerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Buyer> findAll() {
        return jdbcTemplate.query("SELECT * FROM buyer ORDER BY buyer_id", buyerRowMapper);
    }

    public Optional<Buyer> findById(Integer id) {
        List<Buyer> buyers = jdbcTemplate.query(
                "SELECT * FROM buyer WHERE buyer_id = ?",
                buyerRowMapper,
                id
        );
        return buyers.isEmpty() ? Optional.empty() : Optional.of(buyers.get(0));
    }

    public Buyer save(Buyer buyer) {
        if (buyer.getBuyerId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO buyer (first_name, last_name, title) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, buyer.getFirstName());
                ps.setString(2, buyer.getLastName());
                ps.setString(3, buyer.getTitle());
                return ps;
            }, keyHolder);
            buyer.setBuyerId(keyHolder.getKey().intValue());
        } else {
            jdbcTemplate.update(
                    "UPDATE buyer SET first_name = ?, last_name = ?, title = ? WHERE buyer_id = ?",
                    buyer.getFirstName(),
                    buyer.getLastName(),
                    buyer.getTitle(),
                    buyer.getBuyerId()
            );
        }
        return buyer;
    }

    public boolean deleteById(Integer id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM buyer WHERE buyer_id = ?", id);
        return rowsAffected > 0;
    }
}