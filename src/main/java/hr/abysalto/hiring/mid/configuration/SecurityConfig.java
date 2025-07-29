package hr.abysalto.hiring.mid.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/swagger-ui/**", "/v3/api-docs*/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(new Customizer<CsrfConfigurer<HttpSecurity>>() {
				@Override
				public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
					httpSecurityCsrfConfigurer.disable();
				}
			}).authorizeHttpRequests(authorizeRequests ->
											 authorizeRequests.requestMatchers("/swagger-ui/**").permitAll()
															  .requestMatchers("/v3/api-docs*/**").permitAll()
															  .requestMatchers("/buyer/**").permitAll()
															  .requestMatchers("/api/buyers/**").permitAll()
															  .requestMatchers("/api/products/**").permitAll()
															  .requestMatchers("/api/users/**").permitAll()
															  .requestMatchers("/api/cart/**").permitAll()
															  .requestMatchers("/api/favorites/**").permitAll()
															  .requestMatchers("/ecommerce/**").permitAll()
															  .requestMatchers("/h2-console/**").permitAll()
															  .anyRequest().authenticated())
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());
		return http.build();
	}

@Bean
public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
	UserDetails userDetails = User.builder()
			.username("user")
			.password(passwordEncoder.encode("password")) // enkodirana lozinka
			.roles("USER")
			.build();

	return new InMemoryUserDetailsManager(userDetails);
}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
