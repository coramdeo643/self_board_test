package com.example.demo.__core.errors;

import com.example.demo.__core.common.ApiUtil;
import com.example.demo.__core.errors.except.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(1)
@RestControllerAdvice
public class ExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

	public ResponseEntity<?> ex400(Ex400 e, HttpServletRequest request) {
		log.warn("=== 400 BAD_REQUEST");
		log.warn("REQ URL : {}", request.getRequestURL());
		log.warn("AUTH ERR : {}", e.getMessage());
		ApiUtil apiUtil = new ApiUtil(400, e.getMessage());
		return new ResponseEntity<>(apiUtil, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> ex401(Ex401 e, HttpServletRequest request) {
		log.warn("=== 401 UNAUTHORIZED");
		log.warn("REQ URL : {}", request.getRequestURL());
		log.warn("AUTH ERR : {}", e.getMessage());
		ApiUtil apiUtil = new ApiUtil(401, e.getMessage());
		return new ResponseEntity<>(apiUtil, HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<?> ex403(Ex403 e, HttpServletRequest request) {
		log.warn("=== 403 FORBIDDEN");
		log.warn("REQ URL : {}", request.getRequestURL());
		log.warn("AUTH ERR : {}", e.getMessage());
		ApiUtil apiUtil = new ApiUtil(403, e.getMessage());
		return new ResponseEntity<>(apiUtil, HttpStatus.FORBIDDEN);
	}

	public ResponseEntity<?> ex404(Ex404 e, HttpServletRequest request) {
		log.warn("=== 404 NOT_FOUND");
		log.warn("REQ URL : {}", request.getRequestURL());
		log.warn("AUTH ERR : {}", e.getMessage());
		ApiUtil apiUtil = new ApiUtil(404, e.getMessage());
		return new ResponseEntity<>(apiUtil, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<?> ex500(Ex500 e, HttpServletRequest request) {
		log.warn("=== 500 INTERNAL_SERVER_ERROR");
		log.warn("REQ URL : {}", request.getRequestURL());
		log.warn("AUTH ERR : {}", e.getMessage());
		ApiUtil apiUtil = new ApiUtil(500, e.getMessage());
		return new ResponseEntity<>(apiUtil, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
