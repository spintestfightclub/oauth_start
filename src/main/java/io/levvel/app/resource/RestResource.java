package io.levvel.app.resource;

import org.springframework.http.ResponseEntity;
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
