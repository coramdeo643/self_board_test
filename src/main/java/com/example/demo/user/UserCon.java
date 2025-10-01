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
public class UserCon {

	private final UserSer userSer;

	@PostMapping("/join")
	public ResponseEntity<?> join(
			@Valid @RequestBody UserReq.JoinDTO joinDTO, Error error) {
		UserRes.JoinDTO jUser = userSer.join(joinDTO);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ApiUtil<>(jUser));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(
			@Valid @RequestBody UserReq.LoginDTO loginDTO, Error error) {
		String jwtToken = userSer.login(loginDTO);
		return ResponseEntity.ok()
				.header(Def.AUTH, Def.BEAR + jwtToken)
				.body(new ApiUtil<>(null));
	}

	@GetMapping("/api/users/{id}")
	public ResponseEntity<?> getUserInfo(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(Def.S_USER) SessionUser sUser) {
		if (sUser == null) throw new Ex401("Not authorized access");
		UserRes.DetailDTO userInfo = userSer.findUserById(id, sUser.getId());
		return ResponseEntity.ok(new ApiUtil<>(userInfo));
	}

	@PutMapping("/api/users/{id}")
	public ResponseEntity<?> updateUserInfo(
			@PathVariable(name = "id") Long id,
			@RequestAttribute(Def.S_USER) SessionUser sUser,
			@Valid @RequestBody UserReq.UpdateDTO updateDTO, Error error) {
		if (sUser == null) throw new Ex401("Not authorized access");
		UserRes.UpdateDTO uUser = userSer.updateById(id, sUser.getId(), updateDTO);
		return ResponseEntity.ok().body(new ApiUtil<>(uUser));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout() {
		return ResponseEntity.ok(new ApiUtil("Logout success"));
	}
}
