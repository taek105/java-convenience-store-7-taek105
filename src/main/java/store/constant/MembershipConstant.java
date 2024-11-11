package store.constant;

public enum MembershipConstant {
    MEMBERSHIP_LIMIT(8000),
    MEMBERSHIP_RATE(30);

    private final int value;

    MembershipConstant(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
