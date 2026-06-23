package io.github.jinputguard.result.errors;

import java.io.Serializable;

/**
 * Represents an error message that can be used in validation or error reporting.
 */
public interface ErrorMessage extends Serializable {

	/**
	 * A message that will be concatenated after the path.
	 * As an example, if the path is "user.name" and the message is "must not be empty", the final message will be "user.name must not be empty".
	 * 
	 * @return	message that will be concatenated after the path.
	 */
	String getMessage();

}
