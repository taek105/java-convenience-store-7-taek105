package store.service;

import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.model.PurchaseDTO;
import store.view.InputView;

public class PromotionService {
    Products products;

    public PromotionService(Products products) {
        this.products = products;
    }

    public void promotionQuantityCheck(Product promoteProduct, PurchaseDTO purchaseDTO) {
        if ( purchaseDTO.getAmount() > promoteProduct.getQuantity() ) {
            notifyLackPromotionQuantity(promoteProduct, purchaseDTO);
        }
    }

    private void notifyLackPromotionQuantity(Product promoteProduct, PurchaseDTO purchaseDTO) {
        int lackAmount = getLackAmount(promoteProduct, purchaseDTO.getAmount());

        if ( !InputView.notifyPromotionQuantity(promoteProduct.getName(), lackAmount) ) {
            purchaseDTO.subtractAmount(lackAmount);
        }
    }

    private int getLackAmount(Product promoteProduct, int amount) {
        int buy = promoteProduct.getPromotion().getBuy();
        int get = promoteProduct.getPromotion().getGet();
        int lackAmount;
        if (buy + get > promoteProduct.getQuantity()) {
            lackAmount = amount - promoteProduct.getQuantity();
        } else {
            lackAmount = amount - ((promoteProduct.getQuantity() / (buy + get)) * (buy + get));
        }
        return lackAmount;
    }

    public void askServeExtraProduct(Product promoteProduct, PurchaseDTO purchaseDTO) {
        int extraProductAmount = calculateExtraProductAmount(purchaseDTO.getAmount(), promoteProduct);
        if ( extraProductAmount > 0 ) {
            if ( InputView.notifyExtraProducts(
                    promoteProduct.getName(), extraProductAmount) ) {
                purchaseDTO.addAmount(extraProductAmount);
            }
        }
    }

    private int calculateExtraProductAmount(int amount, Product promoteProduct) {
        Promotion promotion = promoteProduct.getPromotion();
        int buyQuantity = promotion.getBuy();
        int freeQuantity = promotion.getGet();
        int totalQuantity = promoteProduct.getQuantity();

        while (amount >= buyQuantity && amount < totalQuantity) {
            int nextBuyStart = buyQuantity + freeQuantity;
            if (amount < nextBuyStart) {
                return Math.min(nextBuyStart - amount, totalQuantity - amount);
            }
            buyQuantity += nextBuyStart;
        }

        return 0;
    }


    public void promotionCheck(Product promoteProduct, PurchaseDTO purchaseDTO) {
        int purchaseCount = purchaseDTO.getPurchaseCount();

        if ( ((purchaseCount - promoteProduct.getPromotion().getBuy()) % 3) == 0 ) {
            int promotionGet = promotionGet(promoteProduct, purchaseDTO);
            purchaseDTO.addPurchaseCount(promotionGet);
            purchaseDTO.addExtraAmount(promotionGet);
        }
    }

    private int promotionGet(Product promoteProduct, PurchaseDTO purchaseDTO) {
        int promoteProductQuantity = promoteProduct.getQuantity();
        int promotionGet = promoteProduct.getPromotion().getGet();
        int purchaseCount = purchaseDTO.getPurchaseCount();

        if (promoteProductQuantity < promotionGet + purchaseCount) {
            // 프로모션 적용으로 얻을 수 있는 상품 개수는 프로모션 재고 이내일 수 있게
            return promoteProductQuantity - purchaseCount;
        }
        return promotionGet;
    }
}
