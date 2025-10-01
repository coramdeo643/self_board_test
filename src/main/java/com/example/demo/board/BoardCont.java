package com.example.demo.board;

import com.example.demo.__core.common.ApiUtil;
import com.example.demo.__core.common.Def;
import com.example.demo.user.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardCont {

	private final BoardServ boardServ;

	@GetMapping("/")
	public ResponseEntity<ApiUtil<List<BoardResp.ListDTO>>> list(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "3") int size) {
		Page<Board> boardPage = boardServ.list(page, size);
		List<BoardResp.ListDTO> boardList = new ArrayList<>();
		for (Board b : boardPage.getContent()) {
			boardList.add(new BoardResp.ListDTO(b));
		}

		Map<String, Object> res = new HashMap<>();
		res.put("boardList", boardList);
		res.put("currentPage", page);
		res.put("totalPages", boardPage.getTotalPages());
		res.put("hasPrevious", boardPage.hasPrevious());
		res.put("hasNext", boardPage.hasNext());

		return ResponseEntity.ok(new ApiUtil<>(boardList));
	}

	@GetMapping("/{id}/detail")
	public ResponseEntity<ApiUtil<BoardResp.DetailDTO>> detail(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(value = Def.S_USER, required = false) SessionUser sUser) {
		BoardResp.DetailDTO detailDTO = boardServ.detail(id, sUser);
		return ResponseEntity.ok(new ApiUtil<>(detailDTO));
	}

	@PostMapping
	public ResponseEntity<?> save(
			@Valid @RequestBody BoardRequ.SaveDTO saveDTO, Error error,
			@RequestAttribute(value = Def.S_USER) SessionUser sUser) {
		BoardResp.SaveDTO sBoard = boardServ.save(saveDTO, sUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(sBoard));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(
			@PathVariable(name = "id") Long id,
			@Valid @RequestBody BoardRequ.UpdateDTO updateDTO, Error error,
			@RequestAttribute(Def.S_USER) SessionUser sUser) {
		BoardResp.UpdateDTO uBoard = boardServ.update(id, updateDTO, sUser);
		return ResponseEntity.ok(new ApiUtil<>(uBoard));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiUtil<String>> delete(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(Def.S_USER) SessionUser sUser) {
		boardServ.deleteById(id, sUser);
		return ResponseEntity.ok(new ApiUtil<>("Board deleted"));
	}
}
