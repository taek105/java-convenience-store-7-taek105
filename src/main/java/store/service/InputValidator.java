package store.service;

import store.constant.ErrorMessage;

public class InputValidator {
    public static void purchaseInputValidate(String input) {
        if ( !input.startsWith("[") ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_INPUT.getMessages());
        }
        if ( !input.endsWith("]") ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_INPUT.getMessages());
        }

        input = input.substring(1, input.length() - 1);
        String[] splitByDash = input.split("-");
        if ( splitByDash.length != 2 ) {
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
