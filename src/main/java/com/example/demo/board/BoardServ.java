package com.example.demo.board;

import com.example.demo.__core.errors.except.Ex403;
import com.example.demo.__core.errors.except.Ex404;
import com.example.demo.user.SessionUser;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServ {

	private final BoardRepo boardRepo;

	public List<BoardResp.ListDTO> list(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
		Page<Board> boardPage = boardRepo.findAllJoinUser(pageable);
		List<BoardResp.ListDTO> boardList = new ArrayList<>();
		for (Board b : boardPage.getContent()) {
			BoardResp.ListDTO listDTO = new BoardResp.ListDTO(b);
			boardList.add(listDTO);
		}
		return boardList;
	}

	public BoardResp.DetailDTO detail(
			Long id, SessionUser sUser) {
		Board board = boardRepo.findByIdJoinUser(id).orElseThrow(() -> new Ex404("Board not found"));
		return new BoardResp.DetailDTO(board, sUser);
	}

	@Transactional
	public BoardResp.SaveDTO save(
			BoardRequ.SaveDTO saveDTO, SessionUser sUser) {
		User user = User.builder()
				.id(sUser.getId())
				.username(sUser.getUsername())
				.email(sUser.getEmail())
				.build();
		Board board = saveDTO.toEntity(user);
		Board sBoard = boardRepo.save(board);
		return new BoardResp.SaveDTO(sBoard);
	}

	@Transactional
	public BoardResp.UpdateDTO update(
			Long id, BoardRequ.UpdateDTO updateDTO, SessionUser sUser) {
		Board board = boardRepo.findByIdJoinUser(id).orElseThrow(() -> new Ex404("Board not found"));
		if (!board.isOwner(sUser.getId())) throw new Ex403("Not authorized access");
		board.update(updateDTO);
		return new BoardResp.UpdateDTO(board);
	}

	@Transactional
	public void deleteById(Long id, SessionUser sUser) {
		Board board = boardRepo.findByIdJoinUser(id).orElseThrow(() -> new Ex404("Board not found"));
		if (!board.isOwner(sUser.getId())) throw new Ex403("Not authorized access");
		boardRepo.deleteById(id);
	}
}
