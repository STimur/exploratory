package com.devatoms.cqrses.domain;

import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class User {

	@NonNull
	private String id;
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	private Set<Contact> contacts;
	private Set<Address> addresses;
}
