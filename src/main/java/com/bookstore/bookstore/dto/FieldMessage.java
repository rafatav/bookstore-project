package com.bookstore.bookstore.dto;

public class FieldMessage {

    private String nameField;
    private String message;

    public FieldMessage(String nameField, String messageField) {
        this.nameField = nameField;
        this.message = messageField;
    }

    public String getNameField() {
        return nameField;
    }

    public String getMessage() {
        return message;
    }
}
