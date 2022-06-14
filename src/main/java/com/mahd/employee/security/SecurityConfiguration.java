package com.mahd.employee.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mahd.employee.filter.CustomAuthenticationFilter;
import com.mahd.employee.filter.CustomAuthorizationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	public final UserDetailsService userDetailsService;
	public final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("Running Authentication Manager");
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/signup","/getUser/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("Http Security Running");
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/login/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/v1/company/admin/**").hasAuthority("viewAll");
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/v1/users/**","/api/v1/account/**","/api/v1/roles/**","/api/v1/scopes/**","/api/v1/company/**","/api/v1/contact/**","/api/v1/pipeline/**","/api/v1/project/**","/api/v1/tag/**","/api/v1/team/**","/api/v1/cannedMsg/**","/uuid/checker","/api/v1/company/username/**","/count/**").hasAnyAuthority("read");
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/v1/users/create","/api/v1/scopes/create","/api/v1/roles/create","/api/v1/company/create","/api/v1/roles/scopeToRole/**","/api/v1/users/roleToUser/**","/api/v1/account/create","/api/v1/contact/create","/api/v1/pipeline/create/**","/api/v1/project/create","/api/v1/tag/create","/api/v1/team/create","/api/v1/cannedMsg/create").hasAuthority("write");
		http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/v1/users/**","/api/v1/roles/**","/api/v1/scopes/**","/api/v1/company/**","/api/v1/account/**","/api/v1/contact/**","/api/v1/pipeline/**","/api/v1/project/**","/api/v1/tag/**","/api/v1/team/**","/api/v1/cannedMsg/**").hasAuthority("modify");
		http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/v1/users/**","/api/v1/roles/**","/api/v1/scopes/**","/api/v1/company/**","/api/v1/account/**","/api/v1/contact/**","/api/v1/pipeline/**","/api/v1/project/**","/api/v1/tag/**","/api/v1/team/**","/api/v1/cannedMsg/**").hasAuthority("delete");
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(new CustomAuthenticationFilter(authenticationManagerbean()));
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
 	public AuthenticationManager authenticationManagerbean() throws Exception{
		return super.authenticationManagerBean();
	}
	
}
