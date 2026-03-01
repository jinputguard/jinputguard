package io.github.jinputguard.result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PathTest {

	@Test
	void create_shouldCreateRootPropertyPath() {
		Path path = Path.create("lastName");

		assertEquals("lastName", path.format());
		assertEquals("lastName", path.toString());
	}

	@Test
	void in_shouldAppendNestedProperty() {
		Path path = Path.create("user")
			.in("address")
			.in("street");

		assertEquals("user.address.street", path.format());
		assertEquals("user.address.street", path.toString());
	}

	@Test
	void atIndex_shouldAppendIndex() {
		Path path = Path.create("myList")
			.atIndex(0);

		assertEquals("myList[0]", path.format());
		assertEquals("myList[0]", path.toString());
	}

	@Test
	void atIndex_shouldWorkOnNestedProperty() {
		Path path = Path.create("user")
			.in("addresses")
			.atIndex(2)
			.in("street");

		assertEquals("user.addresses[2].street", path.format());
		assertEquals("user.addresses[2].street", path.toString());
	}

	@Test
	void atUndefinedIndex_shouldPrintQuestionMark() {
		Path path = Path.create("items")
			.atUndefinedIndex();

		assertEquals("items[?]", path.format());
		assertEquals("items[?]", path.toString());
	}

	@Test
	void atUndefinedIndex_shouldWorkInsideNestedStructure() {
		Path path = Path.create("order")
			.in("positions")
			.atUndefinedIndex()
			.in("price");

		assertEquals("order.positions[?].price", path.format());
	}

	@Test
	void chainingMixedOperations_shouldProduceCorrectFormat() {
		Path path = Path.create("root")
			.in("level1")
			.atIndex(5)
			.in("level2")
			.atUndefinedIndex()
			.in("value");

		assertEquals("root.level1[5].level2[?].value", path.format());
	}

	@Test
	void create_withNullProperty_shouldThrowException() {
		assertThrows(NullPointerException.class, () -> Path.create(null));
	}

}