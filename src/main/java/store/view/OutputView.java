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
        for ( Product product : products.getProductsList() ) {
            StringBuilder sb = new StringBuilder("- ").append(product.getName()).append(" ");
            
            appendPrice(product, sb);
            appendQuantity(product, sb);
            appendPromotion(product, sb);
            
            System.out.println(sb);
        }
    }

    public static void printReceipt(Receipt receipt) {
        StringBuilder sb = new StringBuilder();

        int totalAmount = appendPurchaseProduct(receipt, sb);
        appendExtraProduct(receipt, sb);
        appendReceipt(receipt, sb, totalAmount);

        System.out.println(sb);
    }

    private static void appendPrice(Product product, StringBuilder sb) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.KOREA);

        sb.append(formatter.format(product.getPrice())).append("원 ");
    }

    private static void appendQuantity(Product product, StringBuilder sb) {
        if ( product.getQuantity() > 0 ) {
            sb.append(product.getQuantity()).append("개 ");
        }
        else {
            sb.append("재고 없음");
        }
    }

    private static void appendPromotion(Product product, StringBuilder sb) {
        if ( product.isPromotion() ) {
            sb.append(product.getPromotion().getName());
        }
    }

    private static int appendPurchaseProduct(Receipt receipt, StringBuilder sb) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.KOREA);

        int totalAmount = 0;
        sb.append("==============W 편의점================\n");
        sb.append(String.format("%-10s\t%4s\t%10s\n", "상품명", "수량", "금액"));
        for (PurchaseResult purchaseResult : receipt.getPurchaseResults()) {
            totalAmount += purchaseResult.getAmount();
            sb.append(String.format("%-10s\t%4d\t%10s\n",
                    purchaseResult.getName(),
                    purchaseResult.getAmount(),
                    formatter.format(
                            purchaseResult.getPromotedPrice()+receipt.getPromotionKeep())
            ));
        }
        return totalAmount;
    }

    private static void appendReceipt(Receipt receipt, StringBuilder sb, int totalAmount) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.KOREA);

        sb.append("====================================\n");
        sb.append(String.format("%-10s\t%4d\t%10s\n", "총구매액", totalAmount,
                formatter.format(receipt.getPromotedPrice() + receipt.getPromotionKeep())));
        sb.append(String.format("%-10s\t\t-%10s\n", "행사할인",
                formatter.format(receipt.getPromotionKeep())));
        sb.append(String.format("%-10s\t\t-%10s\n", "멤버십할인",
                formatter.format(receipt.getMembershipKeep())));
        sb.append(String.format("%-10s\t\t %10s\n", "내실돈",
                formatter.format(receipt.getPromotedPrice() - receipt.getMembershipKeep())));
    }

    private static void appendExtraProduct(Receipt receipt, StringBuilder sb) {
        sb.append("=============증\t정===============\n");
        for (PurchaseResult purchaseResult : receipt.getPurchaseResults()) {
            if (purchaseResult.getExtraAmount() > 0) {
                sb.append(String.format("%-10s\t%4d\n",
                        purchaseResult.getName(),
                        purchaseResult.getExtraAmount()));
            }
        }
    }
}
