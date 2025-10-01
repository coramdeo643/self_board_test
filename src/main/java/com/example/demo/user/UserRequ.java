package com.example.demo.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserRequ {

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class JoinDTO {

		@NotBlank(message = "Username is required")
		@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
		private String username;

		@NotBlank(message = "Password is required")
		@Size(min = 4, message = "Password must be at least 4 characters long")
		private String password;

		@NotBlank(message = "Email is required")
		@Email(message = "Invalid email format")
		private String email;

		public User toEntity() {
			return User.builder()
					.username(this.username)
					.password(this.password)
					.email(this.email)
					.build();
		}
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LoginDTO {
		@NotBlank(message = "Username is required")
		private String username;
		@NotBlank(message = "Password is required")
		private String password;
	}

	@Data
	public static class UpdateDTO {
		@NotBlank(message = "Password is required")
		private String password;
		@Email(message = "Invalid email format")
		private String email;
	}
}
