package io.github.jinputguard.result;

public sealed interface SubElementGuardFailure extends GuardFailure permits IndexedElementGuardFailure, NonIndexedElementGuardFailure {

	String getContainerPath();

	GuardFailure getElementFailure();

}
