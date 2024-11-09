package store.view;

import store.domain.Product;
import store.domain.Products;
import store.model.PurchaseResult;
import store.domain.Receipt;

import java.text.NumberFormat;
import java.util.Locale;

public class OutputView {
    public static void printWelcome() {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
    }

    public static void printProducts(Products products) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.KOREA);

        for ( Product product : products.getProductsList() ) {
            StringBuilder sb = new StringBuilder("- ");
            sb.append(product.getName()).append(" ").
                    append(formatter.format(product.getPrice())).append("원 ");

            if ( product.getQuantity() > 0 ) {
                sb.append(product.getQuantity()).append("개 ");
            }
            else {
                sb.append("재고 없음");
            }

            if ( product.isPromotion() ) {
                sb.append(product.getPromotion().getName());
            }
            System.out.println(sb);
        }
    }

    public static void printReceipt(Receipt receipt) {
        int totalAmount = 0;
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.KOREA);

        StringBuilder sb = new StringBuilder("==============W 편의점================\n");
        sb.append(String.format("%-10s\t%4s\t%10s\n", "상품명", "수량", "금액"));
        for (PurchaseResult purchaseResult : receipt.getPurchaseResults()) {
            totalAmount += purchaseResult.getAmount();
            sb.append(String.format("%-10s\t%4d\t%10s\n",
                    purchaseResult.getName(),
                    purchaseResult.getAmount(),
                    formatter.format(
                            purchaseResult.getPromotedPrice()+receipt.getPromotionKeep() )));
        }

        sb.append("=============증\t정===============\n");
        for (PurchaseResult purchaseResult : receipt.getPurchaseResults()) {
            if (purchaseResult.getExtraAmount() > 0) {
                sb.append(String.format("%-10s\t%4d\n",
                        purchaseResult.getName(),
                        purchaseResult.getExtraAmount()));
            }
        }

        sb.append("====================================\n");
        sb.append(String.format("%-10s\t%4d\t%10s\n", "총구매액", totalAmount,
                formatter.format(receipt.getPromotedPrice() + receipt.getPromotionKeep())));
        sb.append(String.format("%-10s\t\t-%10s\n", "행사할인",
                formatter.format(receipt.getPromotionKeep())));
        sb.append(String.format("%-10s\t\t-%10s\n", "멤버십할인",
                formatter.format(receipt.getMembershipKeep())));
        sb.append(String.format("%-10s\t\t %10s\n", "내실돈",
                formatter.format(receipt.getPromotedPrice() - receipt.getMembershipKeep())));

        System.out.println(sb);
    }
}
