package com.rebuiltx.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	
	
	private int categoryId;
	
	@NotEmpty
	@Size(min = 4, message = "Must be atleast 4 characters")
	private String categoryTitle;

	@NotEmpty
	@Size(min = 10,  message = "Must be atleast 10 characters")
	private String categoryDescription;
}
