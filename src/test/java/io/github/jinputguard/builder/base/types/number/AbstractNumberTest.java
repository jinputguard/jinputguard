package io.github.jinputguard.builder.base.types.number;

abstract class AbstractNumberTest<T extends Number> {

	abstract <B extends AbstractNumberInputGuardBuilder<T, T, B>> AbstractNumberInputGuardBuilder<T, T, B> initiateGuard();

	abstract NumberProvider<T> getNumberProvider();

}
