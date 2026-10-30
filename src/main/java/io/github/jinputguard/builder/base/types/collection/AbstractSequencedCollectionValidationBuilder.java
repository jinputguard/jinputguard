package io.github.jinputguard.builder.base.types.collection;

import io.github.jinputguard.InputGuardBuilder;
import io.github.jinputguard.builder.base.AbstractValidationBuilder;
import io.github.jinputguard.failure.BaseValidationErrors;
import java.util.SequencedCollection;

public class AbstractSequencedCollectionValidationBuilder<IN, C extends SequencedCollection<T>, T, B extends InputGuardBuilder<IN, C, B>, SELF extends AbstractSequencedCollectionValidationBuilder<IN, C, T, B, SELF>>
	extends AbstractValidationBuilder<IN, C, B, SELF> {

	protected AbstractSequencedCollectionValidationBuilder(B builder) {
		super(builder);
	}

	/**
	 * Validates that the collection is not empty.
	 * 
	 * @see String#isEmpty()
	 */
	public SELF isNotEmpty() {
		builder = notNullValueBuilder().validate(
			(value, path) -> value.isEmpty()
				? BaseValidationErrors.COLLECTION_MUST_NOT_BE_EMPTY.toFailure(path)
				: null
		);
		return cast();
	}

	/**
	 * Validates that the collection has the given size.
	 * 
	 * @param size the expected size
	 */
	public SELF hasSize(int size) {
		builder = notNullValueBuilder().validate(
			(value, path) -> value.size() == size
				? null
				: BaseValidationErrors.COLLECTION_SIZE_EXACT.toFailure(path, size)
		);
		return cast();
	}

}
