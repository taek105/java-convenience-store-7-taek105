package store.constant;

public enum InputConstant {
    INPUT_FORMAT_INDEX(1),
    INPUT_FORMAT_LENGTH(2);

    private final int value;

    InputConstant(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
