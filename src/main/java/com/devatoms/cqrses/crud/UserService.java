package com.devatoms.cqrses.crud;

import com.devatoms.cqrses.domain.Address;
import com.devatoms.cqrses.domain.Contact;
import com.devatoms.cqrses.domain.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserService {

	private final UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public void createUser(String id, String firstName, String lastName) {
		User user = new User(id, firstName, lastName);
		repository.addUser(id, user);
	}

	public void updateUser(String id, Set<Contact> contacts, Set<Address> addresses) throws Exception {
		User user = repository.getUser(id);
		if (user == null)
			throw new Exception("User does not exist.");
		user.setContacts(contacts);
		user.setAddresses(addresses);
		repository.addUser(id, user);
	}

	public Set<Contact> getContactByType(String userId, String type) throws Exception {
		User user = repository.getUser(userId);
		if (user == null)
			throw new Exception("User does not exist.");
		Set<Contact> contacts = user.getContacts();
		return contacts.stream().filter(c -> c.getType().equals(type)).collect(Collectors.toSet());
	}

	public <R> Set<Address> getAddressByRegion(String userId, String state) throws Exception {
		User user = repository.getUser(userId);
		if (user == null)
			throw new Exception("User does not exist.");
		Set<Address> addresses = user.getAddresses();
		return addresses.stream().filter(a -> a.getState().equals(state)).collect(Collectors.toSet());
	}
}
