package com.devatoms.cqrses.domain;

import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Data
public class User {

	@NonNull
	private String userId;
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	private Set<Contact> contacts = new HashSet<>();
	private Set<Address> addresses = new HashSet<>();
}
