package com.jx.arch.data.exception;

import com.jx.arch.domain.exception.DefaultErrorBundle;

public class RepositoryErrorException extends Exception
{
    private DefaultErrorBundle defaultErrorBundle;

    public RepositoryErrorException(DefaultErrorBundle defaultErrorBundle) {
        super();
        this.defaultErrorBundle=defaultErrorBundle;
    }

    public DefaultErrorBundle getDefaultErrorBundle()
    {
        return defaultErrorBundle;
    }

    public void setDefaultErrorBundle(DefaultErrorBundle defaultErrorBundle)
    {
        this.defaultErrorBundle = defaultErrorBundle;
    }
}
