package com.devatoms.cqrses.cqrs.projectors;

import com.devatoms.cqrses.cqrs.repositories.UserReadRepository;
import com.devatoms.cqrses.domain.*;

import java.util.*;

public class UserProjector {

	private UserReadRepository readRepository;

	public UserProjector(UserReadRepository readRepository) {
		this.readRepository = readRepository;
	}

	public void project(User user) {
		UserContacts userContacts = Optional.ofNullable(readRepository.getUserContacts(user.getUserId()))
				.orElse(new UserContacts());
		Map<String, Set<Contact>> contactsByType = new HashMap<>();

		for (Contact contact : user.getContacts()) {
			Set<Contact> contacts = Optional.ofNullable(contactsByType.get(contact.getType()))
					.orElse(new HashSet<>());
			contacts.add(contact);
			contactsByType.put(contact.getType(), contacts);
		}
		userContacts.setContactsByType(contactsByType);
		readRepository.addUserContacts(user.getUserId(), userContacts);


		UserAddresses userAddresses = Optional.ofNullable(readRepository.getUserAddresses(user.getUserId()))
				.orElse(new UserAddresses());
		Map<String, Set<Address>> addressesByRegion = new HashMap<>();

		for (Address address : user.getAddresses()) {
			Set<Address> addresses = Optional.ofNullable(addressesByRegion.get(address.getState()))
					.orElse(new HashSet<>());
			addresses.add(address);
			addressesByRegion.put(address.getState(), addresses);
		}
		userAddresses.setAddressesByRegion(addressesByRegion);
		readRepository.addUserAddresses(user.getUserId(), userAddresses);
	}
}
