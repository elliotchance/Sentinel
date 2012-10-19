package org.sentinel;

/**
 * This runtime exception can be thrown during JUnit tests to notify the method that it it time to
 * stop infinite loops. Methods that manually catch this should always rethrow it.
 */
public class SentinelJUnitStopException extends RuntimeException
{
}
