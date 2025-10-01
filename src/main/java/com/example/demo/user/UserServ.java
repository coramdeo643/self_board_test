package com.example.demo.user;

import com.example.demo.__core.common.JwtUtil;
import com.example.demo.__core.errors.except.Ex400;
import com.example.demo.__core.errors.except.Ex401;
import com.example.demo.__core.errors.except.Ex403;
import com.example.demo.__core.errors.except.Ex404;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServ {

	private final UserRepo userRepo;

	@Transactional
	public UserResp.JoinDTO join(UserRequ.JoinDTO joinDTO) {
		userRepo.findByUsername(joinDTO.getUsername())
				.ifPresent(user -> {
					throw new Ex400("Username already exists");
				});
		User joinedUser = userRepo.save(joinDTO.toEntity());
		return new UserResp.JoinDTO(joinedUser);
	}

	public UserResp.LoginDTO login(UserRequ.LoginDTO loginDTO) {
		User selectedUser = userRepo.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
				.orElseThrow(() -> new Ex401("Invalid username or password"));
		String jwtToken = JwtUtil.create(selectedUser);
		return new UserResp.LoginDTO(selectedUser, jwtToken);
	}

	public UserResp.DetailDTO findUserById(Long reqUserId, Long sessionUserId) {
		if (!reqUserId.equals(sessionUserId)) throw new Ex403("Not authorized access");
		User selectedUser = userRepo.findById(reqUserId).orElseThrow(() -> new Ex404("User not found"));
		return new UserResp.DetailDTO(selectedUser);
	}

	@Transactional
	public UserResp.UpdateDTO updateById(
			Long reqUserId, Long sessionUserId, UserRequ.UpdateDTO updateDTO) {
		if (!reqUserId.equals(sessionUserId)) throw new Ex403("Not authorized access");
		User sUser = userRepo.findById(reqUserId).orElseThrow(() -> new Ex404("User not found"));
		return new UserResp.UpdateDTO(sUser);
	}
}
