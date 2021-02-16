package com.devatoms.cqrses.cqrs.commands;

import com.devatoms.cqrses.domain.Address;
import com.devatoms.cqrses.domain.Contact;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UpdateUserCommand {

	private String userId;
	private Set<Address> addresses;
	private Set<Contact> contacts ;
}
