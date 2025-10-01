package com.example.demo.board;

import com.example.demo.user.SessionUser;
import lombok.Builder;
import lombok.Data;

public class BoardResp {

	@Data
	public static class ListDTO {
		private Long id;
		private String title;
		private String content;
		private String authorName;
		private String createdAt;

		@Builder
		public ListDTO(Board b) {
			this.id = b.getId();
			this.title = b.getTitle();
			this.content = b.getContent();
			this.authorName = b.getUser().getUsername();
			this.createdAt = b.getCreatedAt().toString();
		}
	}

	@Data
	public static class DetailDTO {
		private Long id;
		private String title;
		private String content;
		private String authorName;
		private String createdAt;
		private boolean isBoardOwner;

		@Builder
		public DetailDTO(Board b, SessionUser sUser) {
			this.id = b.getId();
			this.title = b.getTitle();
			this.content = b.getContent();
			this.authorName = b.getUser().getUsername();
			this.createdAt = b.getCreatedAt().toString();
			this.isBoardOwner = sUser != null && b.isOwner(sUser.getId());
		}
	}

	@Data
	public static class SaveDTO {
		private Long id;
		private String title;
		private String content;
		private String authorName;
		private String createdAt;

		@Builder
		public SaveDTO(Board b) {
			this.id = b.getId();
			this.title = b.getTitle();
			this.content = b.getContent();
			this.authorName = b.getUser().getUsername();
			this.createdAt = b.getCreatedAt().toString();
		}
	}

	@Data
	public static class UpdateDTO {
		private Long id;
		private String title;
		private String content;
		private String authorName;
		private String createdAt;

		@Builder
		public UpdateDTO(Board b) {
			this.id = b.getId();
			this.title = b.getTitle();
			this.content = b.getContent();
			this.authorName = b.getUser().getUsername();
			this.createdAt = b.getCreatedAt().toString();
		}
	}
}