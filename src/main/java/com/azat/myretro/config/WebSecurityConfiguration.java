package com.azat.myretro.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.azat.myretro.cors.CorsSupportFilter;



@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
        .csrf().disable()
        .cors().and().addFilterBefore(corsSupportFilter(), BasicAuthenticationFilter.class)
        .exceptionHandling()
        .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.OPTIONS).permitAll()
        .antMatchers("/oauth/token", "/oauth/authorize").permitAll()
        .antMatchers("/**").authenticated()
        .and()
        .httpBasic();
	}
	
	@Bean
	public CorsSupportFilter corsSupportFilter() throws Exception {
		return new CorsSupportFilter();
	}

	/**
	 * 
	 * Needed for ignoring security check for OPTION requests. Putting this into
	 * configure(HttpSecurity http) is NOT working! Keep it here!
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.debug(false);
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
		web.ignoring().antMatchers("/my-retro-api/v1/registration/register/public");
		web.ignoring().antMatchers("/my-retro-api/v1/password/reset");
		web.ignoring().antMatchers("/my-retro-api/v1/country/public");
		web.ignoring().antMatchers("/my-retro-api/v1/users/getInfo/**");
		web.ignoring().antMatchers("/my-retro-api/v1/login-code");
	}
	


	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}