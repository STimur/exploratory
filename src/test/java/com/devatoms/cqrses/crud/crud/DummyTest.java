package com.devatoms.cqrses.crud.crud;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DummyTest {

	@Test
	public void dummy() {
		assertThat(1 + 1).isEqualTo(2);
	}

	@Test(expected = NullPointerException.class)
	public void foreach() {
		List<Integer> ints = null;
		for (int i : ints)
			i++;
	}
}
