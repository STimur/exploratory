package com.devatoms.cqrses.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
public class UserAddresses {

	private Map<String, Set<Address>> addressesByRegion = new HashMap<>();
}
