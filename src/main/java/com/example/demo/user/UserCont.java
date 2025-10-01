package com.example.demo.user;

import com.example.demo.__core.common.ApiUtil;
import com.example.demo.__core.common.Def;
import com.example.demo.__core.errors.except.Ex401;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserCont {

	private final UserServ userServ;

	@PostMapping("/join")
	public ResponseEntity<?> join(
			@Valid @RequestBody UserRequ.JoinDTO joinDTO, Error error) {
		UserResp.JoinDTO jUser = userServ.join(joinDTO);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ApiUtil<>(jUser));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(
			@Valid @RequestBody UserRequ.LoginDTO loginDTO, Error error) {
		String jwtToken = userServ.login(loginDTO);
		return ResponseEntity.ok()
				.header(Def.AUTH, Def.BEAR + jwtToken)
				.body(new ApiUtil<>(null));
	}

	@GetMapping("/api/users/{id}")
	public ResponseEntity<?> getUserInfo(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(Def.S_USER) SessionUser sUser) {
		if (sUser == null) throw new Ex401("Not authorized access");
		UserResp.DetailDTO userInfo = userServ.findUserById(id, sUser.getId());
		return ResponseEntity.ok(new ApiUtil<>(userInfo));
	}

	@PutMapping("/api/users/{id}")
	public ResponseEntity<?> updateUserInfo(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(Def.S_USER) SessionUser sUser,
			@Valid @RequestBody UserRequ.UpdateDTO updateDTO, Error error) {
		if (sUser == null) throw new Ex401("Not authorized access");
		UserResp.UpdateDTO uUser = userServ.updateById(id, sUser.getId(), updateDTO);
		return ResponseEntity.ok().body(new ApiUtil<>(uUser));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout() {
		return ResponseEntity.ok(new ApiUtil("Logout success"));
	}
}
