package com.bootcamp.books.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private String secretKey = "secret";

	//validity in milliseconds
	private long validityInMs = 3600000; // 1h

	public String getSecretKey() {
		return secretKey;
	}

	public long getValidityInMs() {
		return validityInMs;
	}
	
	
}
