package io.github.jinputguard.result;

import io.github.jinputguard.result.Path.RootPath;
import java.util.function.Consumer;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;

public class PathAssert extends AbstractObjectAssert<PathAssert, Path> {

	protected PathAssert(Path actual) {
		super(actual, PathAssert.class);
	}

	public static PathAssert assertThat(Path actual) {
		return new PathAssert(actual);
	}

	public PathAssert isRoot() {
		Assertions.assertThat(actual).isInstanceOf(RootPath.class);
		return myself;
	}

	public PathAssert formatTextSatisfies(Consumer<String> formatTextConsumer) {
		formatTextConsumer.accept(actual.format());
		return myself;
	}

	public PathAssert formatTextAssert(Consumer<AbstractStringAssert<?>> formatTextConsumer) {
		return formatTextSatisfies(formatText -> formatTextConsumer.accept(Assertions.assertThat(formatText)));
	}

	public PathAssert hasFormatText(String expected) {
		return formatTextAssert(assertor -> assertor.isEqualTo(expected));
	}

}
