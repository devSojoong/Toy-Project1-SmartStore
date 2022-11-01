package me.smartstore.project.exception;

import me.smartstore.project.util.ErrorMessage;

public class InputRangeException extends Exception {

    public InputRangeException() {
        super(ErrorMessage.ERR_MSG_INVALID_INPUT_RANGE);
    }

    public InputRangeException(String message) {
        super(message);
    }
}
