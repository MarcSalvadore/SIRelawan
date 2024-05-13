// package propensi.proyek.siRelawan.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {
//     @Bean
// 	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
// 		http
// 			.authorizeHttpRequests((requests) -> requests
// 				.requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
//                 .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
//                 .requestMatchers(new AntPathRequestMatcher("/register")).permitAll()
//                 .requestMatchers(new AntPathRequestMatcher("/logout")).permitAll()
//                 .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
// 				.requestMatchers(new AntPathRequestMatcher("/home")).permitAll()
// 				.requestMatchers(new AntPathRequestMatcher("catalog/detail-program/{id}")).permitAll()
// 				.requestMatchers(new AntPathRequestMatcher("catalog/edit-program")).authenticated()
// 				.requestMatchers(new AntPathRequestMatcher("catalog/delete-program/{id}")).authenticated()
//                 .requestMatchers(new AntPathRequestMatcher("catalog/statistik")).authenticated()
//                 .requestMatchers(new AntPathRequestMatcher("catalog/create")).authenticated()
//                 .anyRequest().authenticated()
// 			)
// 			.formLogin((form) -> form
// 				.loginPage("/login")
// 				.permitAll()
// 			)
// 			.logout((logout) -> logout.permitAll());

// 		return http.build();
// 	}
// }
