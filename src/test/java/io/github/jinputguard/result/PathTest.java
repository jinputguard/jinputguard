package io.github.jinputguard.result;

import io.github.jinputguard.result.Path;
import io.github.jinputguard.result.Path.IndexPath;
import io.github.jinputguard.result.Path.PropertyPath;
import io.github.jinputguard.result.Path.RootPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PathTest {

	@Nested
	class StaticFactories {

		@Test
		void instances() {
			Assertions.assertThat(Path.root())
				.isInstanceOf(RootPath.class);
			Assertions.assertThat(Path.createPropertyPath("plop"))
				.isInstanceOf(PropertyPath.class);
			Assertions.assertThat(Path.createIndexPath(0))
				.isInstanceOf(IndexPath.class);
		}

	}

	@Test
	void path_multiple() {
		var path = Path.createPropertyPath("val1").atProperty("val2").atProperty("val3");
		Assertions.assertThat(path.format()).isEqualTo("val3.val2.val1");
	}

	@Nested
	class RootPathTest {

		private static final Path ROOT_PATH = Path.root();

		@Test
		void staticFactory() {
			Assertions.assertThat(ROOT_PATH)
				.isInstanceOf(RootPath.class);
		}

		@Test
		void format_withNoParent() {
			Assertions.assertThat(ROOT_PATH.format()).isEqualTo("value");
		}

		@Test
		void when_atPath_with_anyRootPath_return_anyPath() {
			var anyPath = Path.root();
			Assertions.assertThat(ROOT_PATH.atPath(anyPath)).isSameAs(anyPath);
		}

		@Test
		void when_atPath_with_anyPropertyPath_return_anyPath() {
			var anyPath = Path.createPropertyPath("plop");
			Assertions.assertThat(ROOT_PATH.atPath(anyPath)).isSameAs(anyPath);
		}

		@Test
		void when_atPath_with_anyIndexPath_return_anyPath() {
			var anyPath = Path.createIndexPath(0);
			Assertions.assertThat(ROOT_PATH.atPath(anyPath)).isSameAs(anyPath);
		}

	}

	@Nested
	class PropertyPathTest {

		private static final Path PROPERTY_PATH = Path.createPropertyPath("plop");

		@Test
		void staticFactory() {
			Assertions.assertThat(PROPERTY_PATH).isInstanceOf(PropertyPath.class);
			Assertions.assertThat((PropertyPath) PROPERTY_PATH)
				.extracting(PropertyPath::parent, PropertyPath::property)
				.satisfiesExactly(
					parent -> Assertions.assertThat(parent).isInstanceOf(RootPath.class),
					property -> Assertions.assertThat(property).isEqualTo("plop")
				);
		}

		@Test
		void nullParent_forbidden() {
			Assertions.assertThatNullPointerException()
				.isThrownBy(() -> new PropertyPath(null, "plop"))
				.withMessage("parent path cannot be null");
		}

		@Test
		void nullProperty_forbidden() {
			Assertions.assertThatNullPointerException()
				.isThrownBy(() -> new PropertyPath(Path.root(), null))
				.withMessage("property path cannot be null");
		}

		@Test
		void format_withNoParent() {
			Assertions.assertThat(PROPERTY_PATH.format()).isEqualTo("plop");
		}

		@Test
		void format_withParent() {
			var newPath = PROPERTY_PATH.atProperty("midProp").atProperty("topProp");
			Assertions.assertThat(newPath.format()).isEqualTo("topProp.midProp.plop");
		}

	}

	@Nested
	class IndexPathTest {

		private static final Path INDEX_PATH = Path.createIndexPath(123);

		@Test
		void staticFactory() {
			Assertions.assertThat(INDEX_PATH).isInstanceOf(IndexPath.class);
			Assertions.assertThat((IndexPath) INDEX_PATH)
				.extracting(IndexPath::parent, IndexPath::index)
				.satisfiesExactly(
					parent -> Assertions.assertThat(parent).isInstanceOf(RootPath.class),
					index -> Assertions.assertThat(index).isEqualTo(123)
				);
		}

		@Test
		void nullParent_forbidden() {
			Assertions.assertThatNullPointerException()
				.isThrownBy(() -> new IndexPath(null, 0))
				.withMessage("parent path cannot be null");
		}

		@Test
		void negativeIndex_forbidden() {
			Assertions.assertThatIllegalArgumentException()
				.isThrownBy(() -> new IndexPath(Path.root(), -1))
				.withMessage("index path cannot be negative: -1");
		}

		@Test
		void format_withNoParent() {
			Assertions.assertThat(INDEX_PATH.format()).isEqualTo("index [123]");
		}

		@Test
		void format_withParent() {
			var newPath = INDEX_PATH.atIndex(456).atIndex(789);
			Assertions.assertThat(newPath.format()).isEqualTo("index [789][456][123]");
		}

	}

}
