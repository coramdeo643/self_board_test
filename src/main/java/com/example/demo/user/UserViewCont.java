package com.example.demo.user;


import com.example.demo.__core.common.Def;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserViewCont {

	private final UserServ userServ;

	@GetMapping("/join-form")
	public String joinForm() {
		return "user/join-form";
	}

	@GetMapping("/login-form")
	public String loginForm() {
		return "user/login-form";
	}

	@GetMapping("/{id}/update-form")
	public String updateForm(
			@PathVariable Long id,
			@RequestAttribute(Def.S_USER) SessionUser sUser,
			Model model) {
		UserResp.DetailDTO user = userServ.findUserById(id, sUser.getId());
		model.addAttribute("user", user);
		return "user/update-form";
	}
}
