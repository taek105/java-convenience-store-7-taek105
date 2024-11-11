package store.constant;

public enum PromotionConstant {
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
    NULL_PROMOTION_NAME("null"),
    NULL_PROMOTION_DATE("0-1-1");

    private final int value;
    private final String stringValue;

    PromotionConstant(int value) {
        this.value = value;
        this.stringValue = "null";
    }

    PromotionConstant(String stringValue) {
        this.value = 0;
        this.stringValue = stringValue;
    }

    public int getValue() {
        return value;
    }

    public String getStringValue() {
        return stringValue;
    }
}
