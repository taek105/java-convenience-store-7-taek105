package store.constant;

public enum FilePath {
    PRODUCT_MD("src/main/resources/products.md"),
    PROMOTION_MD("src/main/resources/promotions.md"),
    INIT_PRODUCT_MD("src/main/resources/initProducts.md");

    private final String value;

    FilePath(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
