package com.devatoms.cqrses.cqrs.projections;

import com.devatoms.cqrses.domain.UserAddress;
import com.devatoms.cqrses.domain.UserContact;
import com.devatoms.cqrses.cqrs.queries.AddressByRegionQuery;
import com.devatoms.cqrses.cqrs.queries.ContactByTypeQuery;
import com.devatoms.cqrses.cqrs.repositories.UserReadRepository;
import com.devatoms.cqrses.domain.Address;
import com.devatoms.cqrses.domain.Contact;

import java.util.Set;

public class UserProjection {

	private final UserReadRepository repository;

	public UserProjection(UserReadRepository repository) {
		this.repository = repository;
	}

	public Set<Contact> handle(ContactByTypeQuery query) throws Exception {
		UserContact userContact = repository.getUserContact(query.getUserId());
		if (userContact == null)
			throw new Exception("User does not exist.");
		return userContact.getContactByType().get(query.getContactType());
	}

	public Set<Address> handle(AddressByRegionQuery query) throws Exception {
		UserAddress userAddress = repository.getUserAddress(query.getUserId());
		if (userAddress == null)
			throw new Exception("User does not exist.");
		return userAddress.getAddressByRegion().get(query.getState());
	}
}
