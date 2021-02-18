package com.devatoms.cqrses.cqrses;

import com.devatoms.cqrses.cqrs.commands.CreateUserCommand;
import com.devatoms.cqrses.cqrs.commands.UpdateUserCommand;
import com.devatoms.cqrses.cqrs.projections.UserProjection;
import com.devatoms.cqrses.cqrs.queries.AddressByRegionQuery;
import com.devatoms.cqrses.cqrs.queries.ContactByTypeQuery;
import com.devatoms.cqrses.cqrs.repositories.UserReadRepository;
import com.devatoms.cqrses.cqrses.aggregates.UserAggregate;
import com.devatoms.cqrses.cqrses.projectors.UserProjector;
import com.devatoms.cqrses.domain.Address;
import com.devatoms.cqrses.domain.Contact;
import com.devatoms.cqrses.es.events.Event;
import com.devatoms.cqrses.es.repository.EventStore;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ApplicationUnitTest {

	private EventStore writeRepository;
	private UserReadRepository readRepository;
	private UserProjector projector;
	private UserAggregate userAggregate;
	private UserProjection userProjection;

	@Before
	public void setUp() {
		writeRepository = new EventStore();
		readRepository = new UserReadRepository();
		projector = new UserProjector(readRepository);
		userAggregate = new UserAggregate(writeRepository);
		userProjection = new UserProjection(readRepository);
	}

	@Test
	public void givenCQRSApplication_whenCommandRun_thenQueryShouldReturnResult() throws Exception {
		String userId = UUID.randomUUID()
				.toString();
		List<Event> events = null;
		CreateUserCommand createUserCommand = new CreateUserCommand(userId, "Kumar", "Chandrakant");
		events = userAggregate.handleCreateUserCommand(createUserCommand);

		projector.project(userId, events);

		UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId,
				Stream.of(new Address("New York", "NY", "10001"), new Address("Los Angeles", "CA", "90001"))
				.collect(Collectors.toSet()),
				Stream.of(new Contact("EMAIL", "tom.sawyer@gmail.com"), new Contact("EMAIL", "tom.sawyer@rediff.com"))
						.collect(Collectors.toSet()));
		events = userAggregate.handleUpdateUserCommand(updateUserCommand);
		projector.project(userId, events);

		updateUserCommand = new UpdateUserCommand(userId,
				Stream.of(new Address("New York", "NY", "10001"), new Address("Housten", "TX", "77001"))
				.collect(Collectors.toSet()),
				Stream.of(new Contact("EMAIL", "tom.sawyer@gmail.com"), new Contact("PHONE", "700-000-0001"))
						.collect(Collectors.toSet()));
		events = userAggregate.handleUpdateUserCommand(updateUserCommand);
		projector.project(userId, events);

		ContactByTypeQuery contactByTypeQuery = new ContactByTypeQuery(userId, "EMAIL");
		assertEquals(Stream.of(new Contact("EMAIL", "tom.sawyer@gmail.com"))
				.collect(Collectors.toSet()), userProjection.handle(contactByTypeQuery));
		AddressByRegionQuery addressByRegionQuery = new AddressByRegionQuery(userId, "NY");
		assertEquals(Stream.of(new Address("New York", "NY", "10001"))
				.collect(Collectors.toSet()), userProjection.handle(addressByRegionQuery));

	}

}