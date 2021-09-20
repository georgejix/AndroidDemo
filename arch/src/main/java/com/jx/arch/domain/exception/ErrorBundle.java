package com.jx.arch.domain.exception;

/**
 * Interface to represent a wrapper around an {@link Exception} to manage errors.
 */
public interface ErrorBundle
{
    String getErrorStatus();

    String getErrorMessage();

    Object getObjectBean();
}
