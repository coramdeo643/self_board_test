package com.example.demo.board;

import com.example.demo.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class BoardRequ {

	@Data
	public static class SaveDTO {

		@NotEmpty(message = "Title is required")
		@Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
		private String title;

		@NotEmpty(message = "Content is required")
		@Size(min = 1, max = 1000, message = "Content must be between 1 and 1000 characters")
		private String content;

		public Board toEntity(User user) {
			return Board.builder()
					.user(user)
					.title(this.title)
					.content(this.content)
					.build();
		}
	}

	@Data
	public static class UpdateDTO {

		@NotEmpty(message = "Title is required")
		@Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
		private String title;

		@NotEmpty(message = "Content is required")
		@Size(min = 1, max = 1000, message = "Content must be between 1 and 1000 characters")
		private String content;
	}
}
