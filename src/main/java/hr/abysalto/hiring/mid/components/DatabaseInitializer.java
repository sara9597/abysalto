package hr.abysalto.hiring.mid.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private boolean dataInitialized = false;

	public boolean isDataInitialized() {
		return this.dataInitialized;
	}

	public void initialize() {
		initTables();
		initData();
		this.dataInitialized = true;
	}

	private void initTables() {
		this.jdbcTemplate.execute("""
			 CREATE TABLE IF NOT EXISTS users (
				 user_id INT auto_increment PRIMARY KEY,
				 username varchar(50) NOT NULL UNIQUE,
				 email varchar(100) NOT NULL UNIQUE,
				 password varchar(255) NOT NULL,
				 first_name varchar(100) NOT NULL,
				 last_name varchar(100) NOT NULL,
				 phone varchar(20) NULL,
				 address text NULL
			 );
 		""");

		this.jdbcTemplate.execute("""
			 CREATE TABLE IF NOT EXISTS cart_items (
				 cart_item_id INT auto_increment PRIMARY KEY,
				 user_id INT NOT NULL,
				 product_id INT NOT NULL,
				 quantity INT NOT NULL DEFAULT 1,
				 FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
			 );
 		""");

		this.jdbcTemplate.execute("""
			 CREATE TABLE IF NOT EXISTS favorites (
				 favorite_id INT auto_increment PRIMARY KEY,
				 user_id INT NOT NULL,
				 product_id INT NOT NULL,
				 UNIQUE(user_id, product_id),
				 FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
			 );
 		""");

		this.jdbcTemplate.execute("""
			 CREATE TABLE IF NOT EXISTS buyer (
				 buyer_id INT auto_increment PRIMARY KEY,
				 first_name varchar(100) NOT NULL,
				 last_name varchar(100) NOT NULL,
				 title varchar(100) NULL
			 );
 		""");

	}

	private void initData() {
		Integer userCount = this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE username = 'john_doe'", Integer.class);
		if (userCount == null || userCount == 0) {
			this.jdbcTemplate.execute("INSERT INTO users (username, email, password, first_name, last_name, phone, address) VALUES ('john_doe', 'john@example.com', '$2a$10$dummy_hash', 'John', 'Doe', '+1234567890', '123 Main St')");
		}

		userCount = this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE username = 'jane_smith'", Integer.class);
		if (userCount == null || userCount == 0) {
			this.jdbcTemplate.execute("INSERT INTO users (username, email, password, first_name, last_name, phone, address) VALUES ('jane_smith', 'jane@example.com', '$2a$10$dummy_hash', 'Jane', 'Smith', '+0987654321', '456 Oak Ave')");
		}

		Integer buyerCount = this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM buyer WHERE first_name = 'Jabba'", Integer.class);
		if (buyerCount == null || buyerCount == 0) {
			this.jdbcTemplate.execute("INSERT INTO buyer (first_name, last_name, title) VALUES ('Jabba', 'Hutt', 'the')");
		}

		buyerCount = this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM buyer WHERE first_name = 'Anakin'", Integer.class);
		if (buyerCount == null || buyerCount == 0) {
			this.jdbcTemplate.execute("INSERT INTO buyer (first_name, last_name, title) VALUES ('Anakin', 'Skywalker', NULL)");
		}

		buyerCount = this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM buyer WHERE first_name = 'Jar Jar'", Integer.class);
		if (buyerCount == null || buyerCount == 0) {
			this.jdbcTemplate.execute("INSERT INTO buyer (first_name, last_name, title) VALUES ('Jar Jar', 'Binks', NULL)");
		}

		buyerCount = this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM buyer WHERE first_name = 'Han'", Integer.class);
		if (buyerCount == null || buyerCount == 0) {
			this.jdbcTemplate.execute("INSERT INTO buyer (first_name, last_name, title) VALUES ('Han', 'Solo', NULL)");
		}

		buyerCount = this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM buyer WHERE first_name = 'Leia'", Integer.class);
		if (buyerCount == null || buyerCount == 0) {
			this.jdbcTemplate.execute("INSERT INTO buyer (first_name, last_name, title) VALUES ('Leia', 'Organa', 'Princess')");
		}
	}
}
