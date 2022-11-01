package me.smartstore.project.exception;

import me.smartstore.project.util.ErrorMessage;

public class InputFormatException extends Exception {
    public InputFormatException() {
        super(ErrorMessage.ERR_MSG_INVALID_INPUT_FORMAT);
    }

    public InputFormatException(String message) {
        super(message);
    }
}
