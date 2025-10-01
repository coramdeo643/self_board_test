package com.example.demo.board;

import com.example.demo.__core.common.ApiUtil;
import com.example.demo.__core.common.Def;
import com.example.demo.user.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardCont {

	private final BoardServ boardServ;

	@GetMapping("/")
	public ResponseEntity<ApiUtil<List<BoardResp.ListDTO>>> list(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size) {
		List<BoardResp.ListDTO> boardList = boardServ.list(page, size);
		return ResponseEntity.ok(new ApiUtil<>(boardList));
	}

	@GetMapping("/api/board/{id}/detail")
	public ResponseEntity<ApiUtil<BoardResp.DetailDTO>> detail(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(value = Def.S_USER, required = false) SessionUser sUser) {
		BoardResp.DetailDTO detailDTO = boardServ.detail(id, sUser);
		return ResponseEntity.ok(new ApiUtil<>(detailDTO));
	}

	@PostMapping("/api/boards")
	public ResponseEntity<?> save(
			@Valid @RequestBody BoardRequ.SaveDTO saveDTO, Error error,
			@RequestAttribute(value = Def.S_USER) SessionUser sUser) {
		BoardResp.SaveDTO sBoard = boardServ.save(saveDTO, sUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(sBoard));
	}

	@PutMapping("/api/board/{id}")
	public ResponseEntity<?> update(
			@PathVariable(name = "id") Long id,
			@Valid @RequestBody BoardRequ.UpdateDTO updateDTO, Error error,
			@RequestAttribute(Def.S_USER) SessionUser sUser) {
		BoardResp.UpdateDTO uBoard = boardServ.update(id, updateDTO, sUser);
		return ResponseEntity.ok(new ApiUtil<>(uBoard));
	}

	@DeleteMapping("/api/board/{id}")
	public ResponseEntity<ApiUtil<String>> delete(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(Def.S_USER) SessionUser sUser) {
		boardServ.deleteById(id, sUser);
		return ResponseEntity.ok(new ApiUtil<>("Board deleted"));
	}
}
