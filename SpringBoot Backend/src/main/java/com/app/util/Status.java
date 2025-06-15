package com.app.util;

import lombok.Data;

@Data
public class Status {
	public Status(String status) {
		this.status = status;
	}

	String status;
	int count;
}
