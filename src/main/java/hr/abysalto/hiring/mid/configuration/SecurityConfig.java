package hr.abysalto.hiring.mid.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers("/swagger-ui/**", "/v3/api-docs*/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.cors(Customizer.withDefaults())

				.csrf(csrf -> csrf.disable())

				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/swagger-ui/**").permitAll()
						.requestMatchers("/v3/api-docs*/**").permitAll()
						.requestMatchers("/buyer/**").permitAll()
						.requestMatchers("/api/buyers/**").permitAll()
						.requestMatchers("/api/products/**").permitAll()
						.requestMatchers("/api/users/**").permitAll()
						.requestMatchers("/api/cart/**").permitAll()
						.requestMatchers("/api/favorites/**").permitAll()
						.requestMatchers("/ecommerce/**").permitAll()
						.requestMatchers("/h2-console/**").permitAll()
						.anyRequest().authenticated()
				);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOriginPatterns(List.of("http://localhost:3000"));

		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
