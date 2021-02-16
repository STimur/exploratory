package com.devatoms.cqrses.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class Address {

	@NonNull
	private String city;
	@NonNull
	private String state;
	@NonNull
	private String postcode;
}
