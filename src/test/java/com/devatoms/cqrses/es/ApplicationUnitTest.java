package com.devatoms.cqrses.es;

import com.devatoms.cqrses.domain.Address;
import com.devatoms.cqrses.domain.Contact;
import com.devatoms.cqrses.es.repository.EventStore;
import com.devatoms.cqrses.es.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ApplicationUnitTest {

	private EventStore repository;
	private UserService service;

	@Before
	public void setUp() {
		repository = new EventStore();
		service = new UserService(repository);
	}

	@Test
	public void givenESApplication_whenDataCreated_thenDataCanBeFetched() throws Exception {
		String userId = UUID.randomUUID()
				.toString();

		service.createUser(userId, "Tom", "Sawyer");
		service.updateUser(userId, Stream.of(new Contact("EMAIL", "tom.sawyer@gmail.com"),
				new Contact("EMAIL", "tom.sawyer@rediff.com"), new Contact("PHONE", "700-000-0001"))
						.collect(Collectors.toSet()),
				Stream.of(new Address("New York", "NY", "10001"), new Address("Los Angeles", "CA", "90001"),
						new Address("Housten", "TX", "77001"))
						.collect(Collectors.toSet()));
		service.updateUser(userId, Stream.of(new Contact("EMAIL", "tom.sawyer@gmail.com"),
				new Contact("PHONE", "700-000-0001"))
						.collect(Collectors.toSet()),
				Stream.of(new Address("New York", "NY", "10001"), new Address("Housten", "TX", "77001"))
						.collect(Collectors.toSet()));

		assertEquals(Stream.of(new Contact("PHONE", "700-000-0001"))
				.collect(Collectors.toSet()), service.getContactByType(userId, "PHONE"));
		assertEquals(Stream.of(new Address("Housten", "TX", "77001"))
				.collect(Collectors.toSet()), service.getAddressByRegion(userId, "TX"));
	}
}