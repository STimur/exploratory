package com.devatoms.cqrses.cqrs.projections;

import com.devatoms.cqrses.domain.UserAddresses;
import com.devatoms.cqrses.domain.UserContacts;
import com.devatoms.cqrses.cqrs.queries.AddressByRegionQuery;
import com.devatoms.cqrses.cqrs.queries.ContactByTypeQuery;
import com.devatoms.cqrses.cqrs.repositories.UserReadRepository;
import com.devatoms.cqrses.domain.Address;
import com.devatoms.cqrses.domain.Contact;

import java.util.Set;

public class UserProjection {

	private final UserReadRepository readRepository;

	public UserProjection(UserReadRepository readRepository) {
		this.readRepository = readRepository;
	}

	public Set<Contact> handle(ContactByTypeQuery query) throws Exception {
		UserContacts userContacts = readRepository.getUserContacts(query.getUserId());
		if (userContacts == null)
			throw new Exception("User does not exist.");
		return userContacts.getContactByType().get(query.getContactType());
	}

	public Set<Address> handle(AddressByRegionQuery query) throws Exception {
		UserAddresses userAddresses = readRepository.getUserAddresses(query.getUserId());
		if (userAddresses == null)
			throw new Exception("User does not exist.");
		return userAddresses.getAddressesByRegion().get(query.getState());
	}
}
