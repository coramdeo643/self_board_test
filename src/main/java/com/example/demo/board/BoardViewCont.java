package com.example.demo.board;

import com.example.demo.__core.common.Def;
import com.example.demo.user.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardViewCont {

	private final BoardServ boardServ;

	@GetMapping("/")
	public String index(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "3") int size,
			@RequestAttribute(value = Def.S_USER, required = false) SessionUser sUser,
			Model model) {
		Page<Board> boardPage = boardServ.list(page, size);
		List<BoardResp.ListDTO> boardList = new ArrayList<>();
		for (Board b : boardPage.getContent()) {
			boardList.add(new BoardResp.ListDTO(b));
		}

		model.addAttribute("boardList", boardList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", boardPage.getTotalPages());
		model.addAttribute("hasPrevious", boardPage.hasPrevious());
		model.addAttribute("hasNext", boardPage.hasNext());
		model.addAttribute("prevPage", page - 1);
		model.addAttribute("nextPage", page + 1);
		model.addAttribute("displayPage", page + 1);
		return "index";
	}

	@GetMapping("/board/save-form")
	public String saveForm(
			@RequestAttribute(value = Def.S_USER, required = false) SessionUser sUser,
			Model model) {
		return "board/save-form";
	}

	@GetMapping("/board/{id}")
	public String detail(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(value = Def.S_USER, required = false) SessionUser sUser,
			Model model) {
		BoardResp.DetailDTO board = boardServ.detail(id, sUser);
		model.addAttribute("board", board);
		return "board/detail";
	}

	@GetMapping("/board/{id}/update-form")
	public String updateForm(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(Def.S_USER) SessionUser sUser,
			Model model) {
		BoardResp.DetailDTO board = boardServ.detail(id, sUser);
		model.addAttribute("board", board);
		return "board/update-form";
	}
}
