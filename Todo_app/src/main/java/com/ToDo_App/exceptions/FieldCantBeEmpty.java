package com.ToDo_App.exceptions;

public class FieldCantBeEmpty extends RuntimeException {
    public FieldCantBeEmpty(String message) {
        super(message);
    }
}
