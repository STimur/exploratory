package com.devatoms.cqrses.cqrses.projectors;

import com.devatoms.cqrses.cqrs.repositories.UserReadRepository;
import com.devatoms.cqrses.domain.Address;
import com.devatoms.cqrses.domain.Contact;
import com.devatoms.cqrses.domain.UserAddresses;
import com.devatoms.cqrses.domain.UserContacts;
import com.devatoms.cqrses.es.events.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserProjector {

	UserReadRepository readRepository = new UserReadRepository();

	public UserProjector(UserReadRepository readRepository) {
		this.readRepository = readRepository;
	}

	public void project(String userId, List<Event> events) {

		for (Event event : events) {
			if (event instanceof UserAddressAddedEvent)
				apply(userId, (UserAddressAddedEvent) event);
			if (event instanceof UserAddressRemovedEvent)
				apply(userId, (UserAddressRemovedEvent) event);
			if (event instanceof UserContactAddedEvent)
				apply(userId, (UserContactAddedEvent) event);
			if (event instanceof UserContactRemovedEvent)
				apply(userId, (UserContactRemovedEvent) event);
		}

	}

	public void apply(String userId, UserAddressAddedEvent event) {
		Address address = new Address(event.getCity(), event.getState(), event.getPostCode());
		UserAddresses userAddresses = Optional.ofNullable(readRepository.getUserAddresses(userId))
				.orElse(new UserAddresses());
		Set<Address> addresses = Optional.ofNullable(userAddresses.getAddressesByRegion()
				.get(address.getState()))
				.orElse(new HashSet<>());
		addresses.add(address);
		userAddresses.getAddressesByRegion()
				.put(address.getState(), addresses);
		readRepository.addUserAddresses(userId, userAddresses);
	}

	public void apply(String userId, UserAddressRemovedEvent event) {
		Address address = new Address(event.getCity(), event.getState(), event.getPostCode());
		UserAddresses userAddresses = readRepository.getUserAddresses(userId);
		if (userAddresses != null) {
			Set<Address> addresses = userAddresses.getAddressesByRegion()
					.get(address.getState());
			if (addresses != null)
				addresses.remove(address);
			readRepository.addUserAddresses(userId, userAddresses);
		}
	}

	public void apply(String userId, UserContactAddedEvent event) {
		Contact contact = new Contact(event.getContactType(), event.getContactDetails());
		UserContacts userContacts = Optional.ofNullable(readRepository.getUserContacts(userId))
				.orElse(new UserContacts());
		Set<Contact> contacts = Optional.ofNullable(userContacts.getContactsByType()
				.get(contact.getType()))
				.orElse(new HashSet<>());
		contacts.add(contact);
		userContacts.getContactsByType()
				.put(contact.getType(), contacts);
		readRepository.addUserContacts(userId, userContacts);
	}

	public void apply(String userId, UserContactRemovedEvent event) {
		Contact contact = new Contact(event.getContactType(), event.getContactDetails());
		UserContacts userContacts = readRepository.getUserContacts(userId);
		if (userContacts != null) {
			Set<Contact> contacts = userContacts.getContactsByType()
					.get(contact.getType());
			if (contacts != null)
				contacts.remove(contact);
			readRepository.addUserContacts(userId, userContacts);
		}
	}
}