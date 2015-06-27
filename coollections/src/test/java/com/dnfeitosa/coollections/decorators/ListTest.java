package com.dnfeitosa.coollections.decorators;

import static com.dnfeitosa.coollections.Coollections.$;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.dnfeitosa.coollections.Filter;
import com.dnfeitosa.coollections.Function;
import com.dnfeitosa.coollections.Injector;

public class ListTest {

	@Test
	public void shouldMapTheListElementsApplyingTheFunction() {
		CoolList<String> result = $(asList("a", "b", "c")).map(new Function<String, String>() {
			@Override
			public String apply(String s) {
				return s.toUpperCase();
			}
		});

		assertThat(result.get(0), is("A"));
		assertThat(result.get(1), is("B"));
		assertThat(result.get(2), is("C"));
	}

	@Test
	public void shouldFilterTheListElementsBasedOnTheFunction() {
		Filter<Integer> evenNumbers = new Filter<Integer>() {
			@Override
			public Boolean matches(Integer number) {
				return number % 2 == 0;
			}
		};
		CoolList<Integer> result = $(asList(1, 2, 3, 100)).filter(evenNumbers);

		assertThat(result.size(), is(2));
		assertThat(result, hasItems(2, 100));
	}

	@Test
	public void shouldFindASingleValue() {
		CoolList<String> list = $(asList("a", "b", "c"));

		assertThat(list.find(lowerCaseValueOf("A")), is("a"));
		assertNull(list.find(lowerCaseValueOf("D")));
	}

	@Test
	public void shouldInjectAValueAndModifyIt() {
		CoolList<String> list = $(asList("a", "b", "c"));

		Map<String, String> result = list.inject(new HashMap<String, String>(), mapUpperByLower());

		assertThat(result.get("a"), is("A"));
		assertThat(result.get("b"), is("B"));
		assertThat(result.get("c"), is("C"));
	}

	@Test
	public void shouldMapByStringSize() {
		Map<Integer, String> map = $(asList("a", "aa", "aaa")).mapBy(new Function<String, Integer>() {
			@Override
			public Integer apply(String s) {
				return s.length();
			}
		});

		assertThat(map.size(), is(3));
		assertThat(map.get(1), is("a"));
		assertThat(map.get(2), is("aa"));
		assertThat(map.get(3), is("aaa"));
	}

	private Injector<Map<String, String>, String> mapUpperByLower() {
		return new Injector<Map<String, String>, String>() {
			@Override
			public Map<String, String> apply(Map<String, String> map, String value) {
				map.put(value, value.toUpperCase());
				return map;
			}
		};
	}

	private Filter<String> lowerCaseValueOf(final String value) {
		return new Filter<String>() {
			@Override
			public Boolean matches(String s) {
				return s.equalsIgnoreCase(value.toLowerCase());
			}
		};
	}
}
