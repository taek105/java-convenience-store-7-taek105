package store.view;

public class InputView {
    public InputView() {}

    public static void readPurchaseProduct() {
        System.out.println("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
    }

    public static void notifyPromotionQuantity(String productName, int res) {
        StringBuilder sb = new StringBuilder();

        sb.append("현재 ");
        sb.append(productName);
        sb.append(' ');
        sb.append(res);
        sb.append("개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");

        System.out.println(sb);
    }

    public static void notifyExtraProducts(String productName, int promotionGet) {
        StringBuilder sb = new StringBuilder();

        sb.append("현재 ");
        sb.append(productName);
        sb.append("은(는) ");
        sb.append(promotionGet);
        sb.append("개를 무료로 받을 수 있습니다. 추가하시겠습니까? (Y/N)");

        System.out.println(sb);
    }

    public static void membership() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
    }
}
