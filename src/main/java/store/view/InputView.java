package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.model.readProductDTO;
import store.service.InputHandler;

import java.util.List;

public class InputView {
    public InputView() {}

    public static List<readProductDTO> readPurchaseProduct() {
        System.out.println("\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        while (true) {
            try {
                return InputHandler.parsePurchaseInput(Console.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean notifyExtraProducts(String productName, int promotionGet) {
        StringBuilder sb = new StringBuilder();
        sb.append("현재 ").append(productName).append("은(는) ").
                append(promotionGet).append("개를 무료로 받을 수 있습니다. 추가하시겠습니까? (Y/N)");
        System.out.println(sb);

        while (true) {
            try {
                return InputHandler.parseNotifyExtraProducts(Console.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean notifyPromotionQuantity(String productName, int res) {
        StringBuilder sb = new StringBuilder();
        sb.append("현재 ").append(productName).append(' ').append(res)
                .append("개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)");
        System.out.println(sb);

        while (true) {
            try {
                return InputHandler.parseNotifyPromotionQunatity(Console.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean membership() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");

        while (true) {
            try {
                return InputHandler.parseMembershipFlag(Console.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static boolean nextPurchase() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");

        while (true) {
            try {
                return InputHandler.parseNextPurchase(Console.readLine());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
