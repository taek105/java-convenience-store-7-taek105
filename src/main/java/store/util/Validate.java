package store.util;

import store.constant.Constant;
import store.constant.ErrorMessage;

public class Validate {
    public static void parseProduct(String[] split) {
        if ( split.length != Constant.PRODUCT_PROMOTION_INDEX.getValue()+1 ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_FILE_FORMAT.getMessages());
        }
    }

    public static void parsePromotion(String[] split) {
        if ( split.length != Constant.PROMOTION_END_DATE_INDEX.getValue()+1 ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_VALID_FILE_FORMAT.getMessages());
        }
    }
}
