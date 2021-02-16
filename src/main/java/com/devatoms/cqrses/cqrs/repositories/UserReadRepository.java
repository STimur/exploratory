package com.devatoms.cqrses.cqrs.repositories;

import com.devatoms.cqrses.domain.UserAddress;
import com.devatoms.cqrses.domain.UserContact;

import java.util.HashMap;
import java.util.Map;

public class UserReadRepository {

	private Map<String, UserAddress> userAddress = new HashMap<>();
	private Map<String, UserContact> userContact = new HashMap<>();

	public UserContact getUserContact(String userId) {
		return userContact.get(userId);
	}

	public UserAddress getUserAddress(String userId) {
		return userAddress.get(userId);
	}
}
