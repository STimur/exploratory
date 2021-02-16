package com.devatoms.cqrses.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class Contact {

	@NonNull
	private String type;
	@NonNull
	private String detail;
}
