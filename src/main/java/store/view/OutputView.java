package store.view;

import store.domain.Product;
import store.domain.Products;
import store.model.PurchaseResult;
import store.model.Receipt;

public class OutputView {
    public static void printWelcome() {
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
    }

    public static void printProducts(Products products) {
        for ( Product product : products.getProductsList() ) {
            StringBuilder sb = new StringBuilder("- ");
            sb.append(product.getName()).append(" ").
                    append(product.getPrice()).append("원 ").
                    append(product.getQuantity()).append("개 ");

            if ( product.isPromotion() ) {
                sb.append(product.getPromotion().getName());
            }
            System.out.println(sb);
        }
    }

    public static void printReceipt(Receipt receipt) {
        int totalAmount = 0;
        StringBuilder sb = new StringBuilder("=============W 편의점===============\n");
        sb.append("상품명 / 수량 / 금액\n");
        for (PurchaseResult purchaseResult : receipt.getPurchaseResults() ) {
            totalAmount += purchaseResult.getAmount();
            sb.append(purchaseResult.getName()).append(" / ");
            sb.append(purchaseResult.getAmount()).append(" / ");
            sb.append(purchaseResult.getPromotedPrice()).append('\n');
        }

        sb.append("=============증   정================\n");
        for (PurchaseResult purchaseResult : receipt.getPurchaseResults() ) {
            if ( purchaseResult.getExtraAmount() > 0 ) {
                sb.append(purchaseResult.getName()).append(" / ");
                sb.append(purchaseResult.getExtraAmount()).append('\n');
            }
        }

        sb.append("====================================\n");
        sb.append("총구매액 / ").append(totalAmount).append(" / ").append(receipt.getPromotedPrice()+receipt.getPromotionKeep()).append('\n');
        sb.append("행사할인 / -").append(receipt.getPromotionKeep()).append('\n');
        sb.append("멤버십할인 / -").append(receipt.getMembershipKeep()).append('\n');
        sb.append("내실돈 / ").append(receipt.getPromotedPrice()-receipt.getMembershipKeep()).append('\n');


        System.out.println(sb);
    }
}
