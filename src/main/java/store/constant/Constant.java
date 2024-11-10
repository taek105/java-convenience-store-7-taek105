package store.constant;

public enum Constant {
    //nullProduct, nullPromotion 이름은 "null"
    PRODUCT_NAME_INDEX(0),
    PRODUCT_PRICE_INDEX(1),
    PRODUCT_QUANTITY_INDEX(2),
    PRODUCT_PROMOTION_INDEX(3),
    EMPTY_PRODUCT_QUANTITY(0),
    EMPTY_PRODUCT_PRICE(-1),
    PROMOTION_NAME_INDEX(0),
    PROMOTION_BUY_INDEX(1),
    PROMOTION_GET_INDEX(2),
    PROMOTION_START_DATE_INDEX(3),
    PROMOTION_END_DATE_INDEX(4),
    PROMOTION_NULL_BUY(0),
    PROMOTION_NULL_GET(0),
    YEAR_INDEX(0),
    MONTH_INDEX(1),
    DAY_INDEX(2),
    MEMBERSHIP_LIMIT(8000),
    MEMBERSHIP_RATE(30),
    INPUT_FORMAT_INDEX(1),
    INPUT_FORMAT_LENGTH(2);

    private final int value;

    Constant(int constant) {
        this.value = constant;
    }

    public int getValue() {
        return value;
    }
}
