
package com.wiz.chat.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.wiz.chat.boot.security.JwtAuthenticationEntryPoint;
import com.wiz.chat.boot.security.JwtAuthorizationTokenFilter;
import com.wiz.chat.boot.security.JwtTokenUtil;
import com.wiz.chat.boot.security.service.JwtUserDetailsService;
import com.wiz.chat.boot.util.SimpleMd5PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler = null;

	@Autowired
	private JwtTokenUtil jwtTokenUtil = null;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService = null;

	@Value("${jwt.header}")
	private String tokenHeader = null;

	@Value("${jwt.route.authentication.path}")
	private String authenticationPath = null;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoderBean());
	}

	@Bean
	public PasswordEncoder passwordEncoderBean() {
		return new SimpleMd5PasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			// we don't need CSRF because our token is invulnerable
			.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
			.and()
			// don't create session
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			// Un-secure H2 Database
			.antMatchers("/h2-console/**/**").permitAll()
			.antMatchers("/auth/**").permitAll()
			.anyRequest().authenticated();
		// Custom JWT based security filter
		httpSecurity.addFilterBefore(new JwtAuthorizationTokenFilter(userDetailsService(), jwtTokenUtil, tokenHeader), UsernamePasswordAuthenticationFilter.class);
		// disable page caching
		httpSecurity.headers().frameOptions().sameOrigin() // required to set for H2 else H2 Console will be blank.
			.cacheControl();
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		// AuthenticationTokenFilter will ignore the below paths
		webSecurity.ignoring().antMatchers(HttpMethod.POST, authenticationPath)
			.and()
			// allow anonymous resource requests
			.ignoring().antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.htm", "/**/*.css", "/**/*.js", "/favicon.ico", "/**/*.gif", "/**/*.jpg", "/**/*.png")
			.and()
			// Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
			.ignoring().antMatchers("/h2-console/**/**");
	}
}