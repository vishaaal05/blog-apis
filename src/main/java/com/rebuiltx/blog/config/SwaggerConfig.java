package com.rebuiltx.blog.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

//@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		
		String schemeName = "bearerScheme";
		
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement()
						.addList(schemeName)
						)
				.components(new Components()
						.addSecuritySchemes(schemeName, new SecurityScheme()
								.name(schemeName)
								.type(SecurityScheme.Type.HTTP)
								.bearerFormat("JWT")
								.scheme("bearer")
								)
						);
			
	}
}
