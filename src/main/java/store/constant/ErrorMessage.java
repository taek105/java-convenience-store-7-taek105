package store.constant;

public enum ErrorMessage {
    NOT_VALID_INPUT("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NOT_EXISTENCE_PRODUCT("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    NOT_EXISTENCE_PROMOTION("[ERROR] 존재하지 않는 프로모션입니다. 다시 입력해 주세요."),
    EXCEED_QUANTITY("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    FAULT_INPUT("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."),
    NOT_VALID_FILEROUTE("[ERROR] 파일 경로가 잘못됐습니다. Promotions 참고하세용");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessages() {
        return message;
    }
}
