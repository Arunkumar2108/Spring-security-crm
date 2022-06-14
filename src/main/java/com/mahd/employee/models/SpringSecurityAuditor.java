package com.mahd.employee.models;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SpringSecurityAuditor implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		log.info("Security Context Holder {}",SecurityContextHolder.getContext());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info("Authentication {}",authentication);
		String user;
		if(authentication  == null) {
			 user = "CUSTOMER";
		}
		else {
		Collection<?> authorities = authentication.getAuthorities();
		user = authorities.iterator().next().toString();
		}
		return Optional.ofNullable(user).filter(s -> !s.isEmpty());
	}

}
