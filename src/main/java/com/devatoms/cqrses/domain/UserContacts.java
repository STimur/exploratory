package com.devatoms.cqrses.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class UserContacts {

	private Map<String, Set<Contact>> contactsByType = new HashMap<>();
}
