package com.howtodoinjava.demo.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RestResource 
{
	@RequestMapping("/api/users/me")
	public ResponseEntity<UserProfile> profile() 
	{
		UserProfile profile = new UserProfile();
		profile.setName("han");
		profile.setEmail("han.zhang@levvel.io");

		return ResponseEntity.ok(profile);
	}
}
