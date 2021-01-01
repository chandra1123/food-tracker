package com.cupriver.nutrition;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.server.ResponseStatusException;

/**
 * Security settings for the application.
 *  
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable()
		.and().csrf().disable()
		.authorizeRequests()
        .antMatchers("/register")
        .permitAll().
        and()
		.authorizeRequests()
		.antMatchers("/userprofile*").hasAnyRole("ADMIN", "MANAGER", "USER")
		.antMatchers("/foodlog*").hasAnyRole("ADMIN", "USER")
		.anyRequest().authenticated()
		.and().formLogin().permitAll()
		.and().logout().permitAll();
	}

	@Bean
	UserDetailsManager users(DataSource dataSource) {
		UserDetails user = User.withUsername("user")
				.password(getPasswordEncoder().encode("password"))
				.roles("USER")
				.build();
		UserDetails manager = User.withUsername("manager")
				.password(getPasswordEncoder().encode("password"))
				.roles("MANAGER")
				.build();
		UserDetails admin = User.withUsername("admin")
				.password(getPasswordEncoder().encode("password"))
				.roles("ADMIN")
				.build();

		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.createUser(user);
		users.createUser(manager);
		users.createUser(admin);
		return users;
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	/**
	 * Checks record level authorization. A regular user can only access his records.
	 * @param key
	 */
	public static void checkAuthorization(String key) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		for (GrantedAuthority authority: authentication.getAuthorities()) {
			String authorityName =authority.getAuthority();
			if ("ROLE_MANAGER".equals(authorityName) 
					|| "ROLE_ADMIN".equals(authorityName)) {
				return;
			}
		}
		String userName = authentication.getName();
		if (!userName.equals(key)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to the record");
		}
	}
}
