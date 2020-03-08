package ru.sharashin.tacocloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;

	@Autowired
	public SecurityConfig(@Qualifier("userRepositoryUserDetailsService") UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new Pbkdf2PasswordEncoder("5seq4b");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/design", "/orders/**").hasRole("USER") // USER трансформируется в ROLE_USER в ExpressionUrlAuthorizationConfigurer.hasRole()
				.antMatchers("/**").permitAll()
				.and()
				.formLogin().loginPage("/login").defaultSuccessUrl("/design").failureUrl("/loginError")
				.and()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/");
	}

//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().mvcMatchers("/register", "/", "/images/*").and().;
//	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
//		auth.inMemoryAuthentication()
//				.withUser("mike").password("1234").authorities("ROLE_USER")
//				.and()
//				.withUser("notmike").password("123").authorities("ROLE_USER");

//		auth.jdbcAuthentication().dataSource(dataSource)
//				.usersByUsernameQuery(
//						"select username, password, enabled from Users " +
//								"where username=?")
//				.authoritiesByUsernameQuery(
//						"select username, authority from UserAuthorities " +
//								"where username=?")
//				.passwordEncoder(new StandardPasswordEncoder("53cr3t"));
		// DataSource the only required attribute
	}
}
