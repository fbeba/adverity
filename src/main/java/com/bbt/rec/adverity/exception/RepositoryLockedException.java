package com.bbt.rec.adverity.exception;

public class RepositoryLockedException extends RuntimeException {

    public RepositoryLockedException() {
        super("Repository locked. Please try again later.");
    }
}
