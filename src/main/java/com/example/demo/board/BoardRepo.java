package com.example.demo.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepo extends JpaRepository<Board, Long> {

	@Query("SELECT b FROM Board b JOIN FETCH b.user ORDER BY b.id DESC")
	Page<Board> findAllJoinUser(Pageable pageable);

	@Query("SELECT b FROM Board b JOIN FETCH b.user WHERE b.id = :id")
	Optional<Board> findByIdJoinUser(@Param("id") Long id);
}
