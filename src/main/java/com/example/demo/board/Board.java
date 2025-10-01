package com.example.demo.board;

import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "boards")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@CreationTimestamp
	private Timestamp createdAt;

	@Transient
	private boolean isBoardOwner;

	public boolean isOwner(Long checkUserId) {
		return this.user.getId().equals(checkUserId);
	}

	public void update(BoardRequ.UpdateDTO updateDTO) {
		this.title = updateDTO.getTitle();
		this.content = updateDTO.getContent();
	}
}
