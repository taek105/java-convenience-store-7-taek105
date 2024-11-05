package store.constant;

public enum Constants {
    PRODUCT_NAME_INDEX(0),
    PRODUCT_PRICE_INDEX(1),
    PRODUCT_QUANTITY_INDEX(2),
    PRODUCT_PROMOTION_INDEX(3),
    PROMOTION_NAME_INDEX(0),
    PROMOTION_BUY_INDEX(1),
    PROMOTION_GET_INDEX(2),
    PROMOTION_START_DATE_INDEX(3),
    PROMOTION_END_DATE_INDEX(4),
    YEAR_INDEX(0),
    MONTH_INDEX(1),
    DAY_INDEX(2);

    private final int value;

    Constants(int constant) {
        this.value = constant;
    }

    public int getValue() {
        return value;
    }
}
