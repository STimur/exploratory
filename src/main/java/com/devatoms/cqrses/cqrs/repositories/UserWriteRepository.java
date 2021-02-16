package com.devatoms.cqrses.cqrs.repositories;

import com.devatoms.cqrses.domain.User;

import java.util.HashMap;
import java.util.Map;

public class UserWriteRepository {

	Map<String, User> store = new HashMap<>();

	public void addUser(String userId, User user) {
		store.put(userId, user);
	}

	public User getUser(String userId) {
		return store.get(userId);
	}
}
