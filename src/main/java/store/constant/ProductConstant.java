package store.constant;

public enum ProductConstant {
    PRODUCT_NAME_INDEX(0),
    PRODUCT_PRICE_INDEX(1),
    PRODUCT_QUANTITY_INDEX(2),
    PRODUCT_PROMOTION_INDEX(3),
    EMPTY_PRODUCT_QUANTITY(0),
    EMPTY_PRODUCT_PRICE(-1),
    NULL_PRODUCT_NAME("null"),
    DUMMY_PRODUCT_QUANTITY(0);

    private final int value;
    private final String stringValue;

    ProductConstant(int value) {
        this.value = value;
        this.stringValue = "null";
    }

    ProductConstant(String stringValue) {
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
