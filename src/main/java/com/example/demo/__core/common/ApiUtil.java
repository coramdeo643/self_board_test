package com.example.demo.__core.common;

import lombok.Data;

@Data
public class ApiUtil {

	private Integer status;
	private String msg;
	private String body;

	public ApiUtil(String body) {
		this.status = 200;
		this.msg = "success";
		this.body = body;
	}

	public ApiUtil(Integer status, String msg) {
		this.status = status;
		this.msg = msg;
		this.body = null;
	}
}
