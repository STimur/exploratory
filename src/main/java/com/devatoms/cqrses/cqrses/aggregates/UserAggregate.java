package com.devatoms.cqrses.cqrses.aggregates;

import com.devatoms.cqrses.cqrs.commands.CreateUserCommand;
import com.devatoms.cqrses.cqrs.commands.UpdateUserCommand;
import com.devatoms.cqrses.domain.Address;
import com.devatoms.cqrses.domain.Contact;
import com.devatoms.cqrses.domain.User;
import com.devatoms.cqrses.es.events.*;
import com.devatoms.cqrses.es.repository.EventStore;
import com.devatoms.cqrses.es.service.UserUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserAggregate {

	private EventStore writeRepository;

	public UserAggregate(EventStore repository) {
		this.writeRepository = repository;
	}

	public List<Event> handleCreateUserCommand(CreateUserCommand command) {
		UserCreatedEvent event = new UserCreatedEvent(command.getUserId(), command.getFirstName(), command.getLastName());
		writeRepository.addEvent(command.getUserId(), event);
		return Arrays.asList(event);
	}

	public List<Event> handleUpdateUserCommand(UpdateUserCommand command) {
		User user = UserUtility.recreateUserState(writeRepository, command.getUserId());
		List<Event> events = new ArrayList<>();

		List<Contact> contactsToRemove = user.getContacts()
				.stream()
				.filter(c -> !command.getContacts()
						.contains(c))
				.collect(Collectors.toList());
		for (Contact contact : contactsToRemove) {
			UserContactRemovedEvent contactRemovedEvent = new UserContactRemovedEvent(contact.getType(), contact.getDetail());
			events.add(contactRemovedEvent);
			writeRepository.addEvent(command.getUserId(), contactRemovedEvent);
		}

		List<Contact> contactsToAdd = command.getContacts()
				.stream()
				.filter(c -> !user.getContacts()
						.contains(c))
				.collect(Collectors.toList());
		for (Contact contact : contactsToAdd) {
			UserContactAddedEvent contactAddedEvent = new UserContactAddedEvent(contact.getType(), contact.getDetail());
			events.add(contactAddedEvent);
			writeRepository.addEvent(command.getUserId(), contactAddedEvent);
		}

		List<Address> addressesToRemove = user.getAddresses()
				.stream()
				.filter(a -> !command.getAddresses()
						.contains(a))
				.collect(Collectors.toList());
		for (Address address : addressesToRemove) {
			UserAddressRemovedEvent addressRemovedEvent = new UserAddressRemovedEvent(address.getCity(), address.getState(), address.getPostcode());
			events.add(addressRemovedEvent);
			writeRepository.addEvent(command.getUserId(), addressRemovedEvent);
		}

		List<Address> addressesToAdd = command.getAddresses()
				.stream()
				.filter(a -> !user.getAddresses()
						.contains(a))
				.collect(Collectors.toList());
		for (Address address : addressesToAdd) {
			UserAddressAddedEvent addressAddedEvent = new UserAddressAddedEvent(address.getCity(), address.getState(), address.getPostcode());
			events.add(addressAddedEvent);
			writeRepository.addEvent(command.getUserId(), addressAddedEvent);
		}

		return events;
	}

}