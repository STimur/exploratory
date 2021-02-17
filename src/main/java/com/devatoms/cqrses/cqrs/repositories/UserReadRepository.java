package com.devatoms.cqrses.cqrs.repositories;

import com.devatoms.cqrses.domain.UserAddresses;
import com.devatoms.cqrses.domain.UserContacts;

import java.util.HashMap;
import java.util.Map;

public class UserReadRepository {

	private Map<String, UserAddresses> userAddresses = new HashMap<>();
	private Map<String, UserContacts> userContacts = new HashMap<>();

	public UserContacts getUserContacts(String userId) {
		return userContacts.get(userId);
	}

	public UserAddresses getUserAddresses(String userId) {
		return userAddresses.get(userId);
	}

	public void addUserContacts(String userId, UserContacts userContacts) {
		this.userContacts.put(userId, userContacts);
	}

	public void addUserAddresses(String userId, UserAddresses userAddresses) {
		this.userAddresses.put(userId, userAddresses);
	}
}
