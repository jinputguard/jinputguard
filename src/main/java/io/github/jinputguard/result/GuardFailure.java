package io.github.jinputguard.result;

import io.github.jinputguard.InputGuard;
import io.github.jinputguard.InputGuardFailureException;

/**
 * Represents a failure that occurred during the process of an input by an {@link InputGuard}.
 * It contains information about the path where the failure happened, the failure message, and an optional cause.
 * 
 * @see InputGuard
 * @see InputGuardFailureException
 */
public sealed interface GuardFailure permits DefaultGuardFailure, MultiGuardFailure, SubElementGuardFailure {

}
