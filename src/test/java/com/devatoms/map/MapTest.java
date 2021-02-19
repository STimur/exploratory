package com.devatoms.map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MapTest {
	private static final Logger LOG = LoggerFactory.getLogger(MapTest.class);

	@Test
	public void whenCallsEqualsOnCollision_thenCorrect() {
		Map<MyKey, String> map = new HashMap<>();
		MyKey k1 = new MyKey(1, "firstKey");
		MyKey k2 = new MyKey(2, "secondKey");
		MyKey k3 = new MyKey(2, "thirdKey");
		MyKey k4 = new MyKey(2, "fourthKey");

		LOG.debug("storing value for k1");
		map.put(k1, "firstValue");

		LOG.debug("storing value for k2");
		map.put(k2, "secondValue");

		LOG.debug("storing value for k3");
		map.put(k3, "thirdValue");

		LOG.debug("storing value for k4");
		map.put(k4, "fourthValue");

		LOG.debug("retrieving value for k1");
		String v1 = map.get(k1);

		LOG.debug("retrieving value for k2");
		String v2 = map.get(k2);

		LOG.debug("retrieving value for k3");
		String v3 = map.get(k3);

		LOG.debug("retrieving value for k4");
		String v4 = map.get(k4);

		assertEquals("firstValue", v1);
		assertEquals("secondValue", v2);
		assertEquals("thirdValue", v3);
		assertEquals("fourthValue", v4);
	}
}
