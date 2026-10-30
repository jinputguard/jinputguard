package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.builder.base.AbstractValidationBuilder;
import io.github.jinputguard.failure.BaseValidationErrors;
import java.util.UUID;

public class UUIDValidationBuilder<IN> extends AbstractValidationBuilder<IN, UUID, UUIDInputGuardBuilder<IN>, UUIDValidationBuilder<IN>> {

	public UUIDValidationBuilder(UUIDInputGuardBuilder<IN> builder) {
		super(builder);
	}

	private UUIDValidationBuilder<IN> isVersion(int version) {
		builder = notNullValueBuilder().validate(
			(value, path) -> value.version() == version
				? null
				: BaseValidationErrors.UUID_MUST_BE_EXACT_VERSION.toFailure(path, version)
		);
		return cast();
	}

	/**
	 * Validate that the UUID is not the nil UUID as defined in RFC 9562: {@code 00000000-0000-0000-0000-000000000000}.
	 * 
	 * @return	this builder for chaining
	 * 
	 * @see <a href="https://www.rfc-editor.org/info/rfc4122/#section-4.1.7">RFC 4122, Section 4.1.7</a>
	 */
	public UUIDValidationBuilder<IN> isNotNilUUID() {
		builder = notNullValueBuilder().validate(
			(value, path) -> value.toString().equals("00000000-0000-0000-0000-000000000000")
				? BaseValidationErrors.UUID_MUST_NOT_BE_NIL.toFailure(path)
				: null
		);
		return cast();
	}

	/**
	 * Validate that the UUID is a version 1 UUID (time-based).
	 * 
	 * @return	this builder for chaining
	 */
	public UUIDValidationBuilder<IN> isV1() {
		return isVersion(1);
	}

	/**
	 * Validate that the UUID is a version 2 UUID (DCE security).
	 * 
	 * @return	this builder for chaining
	 */
	public UUIDValidationBuilder<IN> isV2() {
		return isVersion(2);
	}

	/**
	 * Validate that the UUID is a version 3 UUID (name-based, MD5).
	 * 
	 * @return	this builder for chaining
	 */
	public UUIDValidationBuilder<IN> isV3() {
		return isVersion(3);
	}

	/**
	 * Validate that the UUID is a version 4 UUID (random).
	 * 
	 * @return	this builder for chaining
	 */
	public UUIDValidationBuilder<IN> isV4() {
		return isVersion(4);
	}

	/**
	 * Validate that the UUID is a version 5 UUID (name-based, SHA-1).
	 * 
	 * @return	this builder for chaining
	 */
	public UUIDValidationBuilder<IN> isV5() {
		return isVersion(5);
	}

	/**
	 * Validate that the UUID is a version 6 UUID.
	 * 
	 * @return	this builder for chaining
	 */
	public UUIDValidationBuilder<IN> isV6() {
		return isVersion(6);
	}

	/**
	 * Validate that the UUID is a version 7 UUID (Unix Epoch time-based).
	 * 
	 * @return	this builder for chaining
	 */
	public UUIDValidationBuilder<IN> isV7() {
		return isVersion(7);
	}

	/**
	 * Validate that the UUID is a version 8 UUID.
	 * 
	 * @return	this builder for chaining
	 */
	public UUIDValidationBuilder<IN> isV8() {
		return isVersion(8);
	}

}
