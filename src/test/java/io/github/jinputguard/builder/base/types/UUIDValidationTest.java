package io.github.jinputguard.builder.base.types;

import io.github.jinputguard.GuardResultAssert;
import io.github.jinputguard.InputGuard;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UUIDValidationTest {

	@Nested
	class IsNotNilUUID {

		private static InputGuard<UUID, UUID> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forUUID().validateThat().isNotNilUUID().then().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"8045c8c0-d4a7-11f1-a500-a3940c7a539f", // v1 -> Time-based UUID
				"3437ef8f-d4a8-21f1-bd00-b12f77154a97", // v2 -> DCE security UUID
				"64a4e8fa-ed1a-3aa0-bf8b-f0fc84938d25", // v3 -> Name-based, MD5 - from UUID.nameUUIDFromBytes("plop".getBytes())
				"dd2b94bf-b810-4d90-a05e-3f2bd5b6413b", // v4 Randomly generated UUID
				"fedb2fa3-8f5c-5189-80e6-f563dd1cb8f9", // v5 -> Name-based, SHA-1
				"1f1d4a7e-d6d0-6d50-abdf-2e9f804be071", // v6
				"01a18eae-5fd2-7690-8db8-16e674eda3de", // v7 Unix Epoch time-based UUID
				"2489e9ad-2ee2-8e00-8ec9-32d5f69181c0", // v8
			}
		)
		void when_is_not_nil_then_success(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isSuccess();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"00000000-0000-0000-0000-000000000000", // nil
			}
		)
		void when_is_nil_then_failure(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must not be nil UUID");
		}

	}

	@Nested
	class IsV1 {

		private static InputGuard<UUID, UUID> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forUUID().validateThat().isV1().then().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"8045c8c0-d4a7-11f1-a500-a3940c7a539f", // v1 -> Time-based UUID
			}
		)
		void when_correct_version_then_success(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isSuccess();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"00000000-0000-0000-0000-000000000000", // nil
				"3437ef8f-d4a8-21f1-bd00-b12f77154a97", // v2 -> DCE security UUID
				"64a4e8fa-ed1a-3aa0-bf8b-f0fc84938d25", // v3 -> Name-based, MD5 - from UUID.nameUUIDFromBytes("plop".getBytes())
				"dd2b94bf-b810-4d90-a05e-3f2bd5b6413b", // v4 Randomly generated UUID
				"fedb2fa3-8f5c-5189-80e6-f563dd1cb8f9", // v5 -> Name-based, SHA-1
				"1f1d4a7e-d6d0-6d50-abdf-2e9f804be071", // v6
				"01a18eae-5fd2-7690-8db8-16e674eda3de", // v7 Unix Epoch time-based UUID
				"2489e9ad-2ee2-8e00-8ec9-32d5f69181c0", // v8
			}
		)
		void when_wrong_version_then_failure(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must be UUID v1");
		}

	}

	@Nested
	class IsV2 {

		private static InputGuard<UUID, UUID> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forUUID().validateThat().isV2().then().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"3437ef8f-d4a8-21f1-bd00-b12f77154a97", // v2 -> DCE security UUID
			}
		)
		void when_correct_version_then_success(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isSuccess();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"00000000-0000-0000-0000-000000000000", // nil
				"8045c8c0-d4a7-11f1-a500-a3940c7a539f", // v1 -> Time-based UUID
				"64a4e8fa-ed1a-3aa0-bf8b-f0fc84938d25", // v3 -> Name-based, MD5 - from UUID.nameUUIDFromBytes("plop".getBytes())
				"dd2b94bf-b810-4d90-a05e-3f2bd5b6413b", // v4 Randomly generated UUID
				"fedb2fa3-8f5c-5189-80e6-f563dd1cb8f9", // v5 -> Name-based, SHA-1
				"1f1d4a7e-d6d0-6d50-abdf-2e9f804be071", // v6
				"01a18eae-5fd2-7690-8db8-16e674eda3de", // v7 Unix Epoch time-based UUID
				"2489e9ad-2ee2-8e00-8ec9-32d5f69181c0", // v8
			}
		)
		void when_wrong_version_then_failure(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must be UUID v2");
		}

	}

	@Nested
	class IsV3 {

		private static InputGuard<UUID, UUID> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forUUID().validateThat().isV3().then().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"64a4e8fa-ed1a-3aa0-bf8b-f0fc84938d25", // v3 -> Name-based, MD5 - from UUID.nameUUIDFromBytes("plop".getBytes())
			}
		)
		void when_correct_version_then_success(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isSuccess();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"00000000-0000-0000-0000-000000000000", // nil
				"8045c8c0-d4a7-11f1-a500-a3940c7a539f", // v1 -> Time-based UUID
				"3437ef8f-d4a8-21f1-bd00-b12f77154a97", // v2 -> DCE security UUID
				"dd2b94bf-b810-4d90-a05e-3f2bd5b6413b", // v4 Randomly generated UUID
				"fedb2fa3-8f5c-5189-80e6-f563dd1cb8f9", // v5 -> Name-based, SHA-1
				"1f1d4a7e-d6d0-6d50-abdf-2e9f804be071", // v6
				"01a18eae-5fd2-7690-8db8-16e674eda3de", // v7 Unix Epoch time-based UUID
				"2489e9ad-2ee2-8e00-8ec9-32d5f69181c0", // v8
			}
		)
		void when_wrong_version_then_failure(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must be UUID v3");
		}

	}

	@Nested
	class IsV4 {

		private static InputGuard<UUID, UUID> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forUUID().validateThat().isV4().then().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"dd2b94bf-b810-4d90-a05e-3f2bd5b6413b", // v4 Randomly generated UUID
			}
		)
		void when_correct_version_then_success(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isSuccess();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"00000000-0000-0000-0000-000000000000", // nil
				"8045c8c0-d4a7-11f1-a500-a3940c7a539f", // v1 -> Time-based UUID
				"3437ef8f-d4a8-21f1-bd00-b12f77154a97", // v2 -> DCE security UUID
				"64a4e8fa-ed1a-3aa0-bf8b-f0fc84938d25", // v3 -> Name-based, MD5 - from UUID.nameUUIDFromBytes("plop".getBytes())
				"fedb2fa3-8f5c-5189-80e6-f563dd1cb8f9", // v5 -> Name-based, SHA-1
				"1f1d4a7e-d6d0-6d50-abdf-2e9f804be071", // v6
				"01a18eae-5fd2-7690-8db8-16e674eda3de", // v7 Unix Epoch time-based UUID
				"2489e9ad-2ee2-8e00-8ec9-32d5f69181c0", // v8
			}
		)
		void when_wrong_version_then_failure(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must be UUID v4");
		}

	}

	@Nested
	class IsV5 {

		private static InputGuard<UUID, UUID> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forUUID().validateThat().isV5().then().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"fedb2fa3-8f5c-5189-80e6-f563dd1cb8f9", // v5 -> Name-based, SHA-1
			}
		)
		void when_correct_version_then_success(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isSuccess();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"00000000-0000-0000-0000-000000000000", // nil
				"8045c8c0-d4a7-11f1-a500-a3940c7a539f", // v1 -> Time-based UUID
				"3437ef8f-d4a8-21f1-bd00-b12f77154a97", // v2 -> DCE security UUID
				"64a4e8fa-ed1a-3aa0-bf8b-f0fc84938d25", // v3 -> Name-based, MD5 - from UUID.nameUUIDFromBytes("plop".getBytes())
				"dd2b94bf-b810-4d90-a05e-3f2bd5b6413b", // v4 Randomly generated UUID
				"1f1d4a7e-d6d0-6d50-abdf-2e9f804be071", // v6
				"01a18eae-5fd2-7690-8db8-16e674eda3de", // v7 Unix Epoch time-based UUID
				"2489e9ad-2ee2-8e00-8ec9-32d5f69181c0", // v8
			}
		)
		void when_wrong_version_then_failure(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must be UUID v5");
		}

	}

	@Nested
	class IsV6 {

		private static InputGuard<UUID, UUID> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forUUID().validateThat().isV6().then().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"1f1d4a7e-d6d0-6d50-abdf-2e9f804be071", // v6
			}
		)
		void when_correct_version_then_success(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isSuccess();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"00000000-0000-0000-0000-000000000000", // nil
				"8045c8c0-d4a7-11f1-a500-a3940c7a539f", // v1 -> Time-based UUID
				"3437ef8f-d4a8-21f1-bd00-b12f77154a97", // v2 -> DCE security UUID
				"64a4e8fa-ed1a-3aa0-bf8b-f0fc84938d25", // v3 -> Name-based, MD5 - from UUID.nameUUIDFromBytes("plop".getBytes())
				"dd2b94bf-b810-4d90-a05e-3f2bd5b6413b", // v4 Randomly generated UUID
				"fedb2fa3-8f5c-5189-80e6-f563dd1cb8f9", // v5 -> Name-based, SHA-1
				"01a18eae-5fd2-7690-8db8-16e674eda3de", // v7 Unix Epoch time-based UUID
				"2489e9ad-2ee2-8e00-8ec9-32d5f69181c0", // v8
			}
		)
		void when_wrong_version_then_failure(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must be UUID v6");
		}

	}

	@Nested
	class IsV7 {

		private static InputGuard<UUID, UUID> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forUUID().validateThat().isV7().then().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"01a18eae-5fd2-7690-8db8-16e674eda3de", // v7 Unix Epoch time-based UUID
			}
		)
		void when_correct_version_then_success(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isSuccess();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"00000000-0000-0000-0000-000000000000", // nil
				"8045c8c0-d4a7-11f1-a500-a3940c7a539f", // v1 -> Time-based UUID
				"3437ef8f-d4a8-21f1-bd00-b12f77154a97", // v2 -> DCE security UUID
				"64a4e8fa-ed1a-3aa0-bf8b-f0fc84938d25", // v3 -> Name-based, MD5 - from UUID.nameUUIDFromBytes("plop".getBytes())
				"dd2b94bf-b810-4d90-a05e-3f2bd5b6413b", // v4 Randomly generated UUID
				"fedb2fa3-8f5c-5189-80e6-f563dd1cb8f9", // v5 -> Name-based, SHA-1
				"1f1d4a7e-d6d0-6d50-abdf-2e9f804be071", // v6
				"2489e9ad-2ee2-8e00-8ec9-32d5f69181c0", // v8
			}
		)
		void when_wrong_version_then_failure(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must be UUID v7");
		}

	}

	@Nested
	class IsV8 {

		private static InputGuard<UUID, UUID> GUARD;

		@BeforeAll
		static void setup() {
			GUARD = InputGuard.builder().forUUID().validateThat().isV8().then().build();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"2489e9ad-2ee2-8e00-8ec9-32d5f69181c0", // v8
			}
		)
		void when_correct_version_then_success(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isSuccess();
		}

		@ParameterizedTest
		@ValueSource(
			strings = {
				"00000000-0000-0000-0000-000000000000", // nil
				"8045c8c0-d4a7-11f1-a500-a3940c7a539f", // v1 -> Time-based UUID
				"3437ef8f-d4a8-21f1-bd00-b12f77154a97", // v2 -> DCE security UUID
				"64a4e8fa-ed1a-3aa0-bf8b-f0fc84938d25", // v3 -> Name-based, MD5 - from UUID.nameUUIDFromBytes("plop".getBytes())
				"dd2b94bf-b810-4d90-a05e-3f2bd5b6413b", // v4 Randomly generated UUID
				"fedb2fa3-8f5c-5189-80e6-f563dd1cb8f9", // v5 -> Name-based, SHA-1
				"1f1d4a7e-d6d0-6d50-abdf-2e9f804be071", // v6
				"01a18eae-5fd2-7690-8db8-16e674eda3de", // v7 Unix Epoch time-based UUID
			}
		)
		void when_wrong_version_then_failure(String input) {
			var actual = GUARD.process(UUID.fromString(input), "myVal");
			GuardResultAssert.assertThat(actual).isFailure()
				.hasMessage("myVal must be UUID v8");
		}

	}

}