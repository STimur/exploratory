package com.devatoms.cqrses.cqrs.aggregates;

import com.devatoms.cqrses.cqrs.commands.CreateUserCommand;
import com.devatoms.cqrses.cqrs.commands.UpdateUserCommand;
import com.devatoms.cqrses.cqrs.repositories.UserWriteRepository;
import com.devatoms.cqrses.domain.User;

public class UserAggregate {

	private UserWriteRepository writeRepository;

	public UserAggregate(UserWriteRepository repository) {
		this.writeRepository = repository;
	}

	public User handleCreateUserCommand(CreateUserCommand command) {
		User user = new User(command.getUserId(), command.getFirstName(), command.getLastName());
		writeRepository.addUser(user.getUserId(), user);
		return user;
	}

	public User handleUpdateUserCommand(UpdateUserCommand command) {
		User user = writeRepository.getUser(command.getUserId());
		user.setAddresses(command.getAddresses());
		user.setContacts(command.getContacts());
		writeRepository.addUser(user.getUserId(), user);
		return user;
	}
}
