package me.smartstore.project.exception;

import me.smartstore.project.util.ErrorMessage;

public class InputEmptyException extends Exception {

    public InputEmptyException() {
        super(ErrorMessage.ERR_MSG_INVALID_INPUT_EMPTY);
    }

    public InputEmptyException(String message) {
        super(message);
    }
}
