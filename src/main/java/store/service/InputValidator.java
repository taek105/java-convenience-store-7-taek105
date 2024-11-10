package store.service;

import store.constant.Constant;
import store.constant.ErrorMessage;

import static store.service.InputHandler.subFormatting;

public class InputValidator {
    public static void purchaseInputValidate(String input) {
        if ( !input.startsWith("[") ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_INPUT.getMessages());
        }
        if ( !input.endsWith("]") ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_INPUT.getMessages());
        }

        input = subFormatting(input);
        String[] splitByDash = input.split("-");
        if ( splitByDash.length != Constant.INPUT_FORMAT_LENGTH.getValue() ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_INPUT.getMessages());
        }

        if ( !splitByDash[1].matches("[0-9]*") ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_INPUT.getMessages());
        }
    }

    public static void flagValidate(String input) {
        if ( !input.matches("[YN]") ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_INPUT.getMessages());
        }
    }
}
