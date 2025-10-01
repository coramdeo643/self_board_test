package com.example.demo.user;

import com.example.demo.__core.common.JwtUtil;
import com.example.demo.__core.errors.except.Ex400;
import com.example.demo.__core.errors.except.Ex401;
import com.example.demo.__core.errors.except.Ex403;
import com.example.demo.__core.errors.except.Ex404;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSer {

	private static final Logger log = LoggerFactory.getLogger(UserSer.class);
	private final UserRep userRep;

	@Transactional
	public UserRes.JoinDTO join(UserReq.JoinDTO joinDTO) {
		userRep.findByUsername(joinDTO.getUsername())
				.ifPresent(user -> {
					throw new Ex400("Username already exists");
				});
		User joinedUser = userRep.save(joinDTO.toEntity());
		return new UserRes.JoinDTO(joinedUser);
	}

	public String login(UserReq.LoginDTO loginDTO) {
		User selectedUser = userRep.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword())
				.orElseThrow(() -> new Ex401("Invalid username or password"));
		return JwtUtil.create(selectedUser);
	}

	public UserRes.DetailDTO findUserById(Long reqUserId, Long sessionUserId) {
		if (!reqUserId.equals(sessionUserId)) throw new Ex403("Not authorized access");
		User selectedUser = userRep.findById(reqUserId).orElseThrow(() -> new Ex404("User not found"));
		return new UserRes.DetailDTO(selectedUser);
	}

	@Transactional
	public UserRes.UpdateDTO updateById(
			Long reqUserId, Long sessionUserId, UserReq.UpdateDTO updateDTO) {
		if (!reqUserId.equals(sessionUserId)) throw new Ex403("Not authorized access");
		User sUser = userRep.findById(reqUserId).orElseThrow(() -> new Ex404("User not found"));
		return new UserRes.UpdateDTO(sUser);
	}
}
