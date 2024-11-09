package store.service;

import store.domain.Product;
import store.domain.Products;
import store.model.PurchaseDTO;
import store.view.InputView;

public class PromotionService {
    Products products;

    public PromotionService(Products products) {
        this.products = products;
    }

    public void promotionQuantityCheck(PurchaseDTO purchaseDTO) {
        if ( purchaseDTO.getAmount() > purchaseDTO.getPromoteProduct().getQuantity() ) {
            notifyLackPromotionQuantity(purchaseDTO);
        }
    }

    public void askServeExtraProduct(PurchaseDTO purchaseDTO) {
        int extraProductAmount = calculateExtraProductAmount(
                purchaseDTO.getAmount(), purchaseDTO.getPromoteProduct());
        if ( extraProductAmount > 0 ) {
            if ( InputView.notifyExtraProducts(
                    purchaseDTO.getPromoteProduct().getName(), extraProductAmount) ) {
                purchaseDTO.addAmount(extraProductAmount);
            }
        }
    }

    public void addExtraAmount(PurchaseDTO purchaseDTO) {
        if (isExtraPromote(purchaseDTO)) {
            int promotionGet = promotionGet(purchaseDTO);
            purchaseDTO.addPurchaseCount(promotionGet);
            purchaseDTO.addExtraAmount(promotionGet);
        }
    }

    private void notifyLackPromotionQuantity(PurchaseDTO purchaseDTO) {
        int lackAmount = getLackAmount(purchaseDTO.getPromoteProduct(), purchaseDTO.getAmount());

        if ( !InputView.notifyPromotionQuantity(
                purchaseDTO.getPromoteProduct().getName(), lackAmount) ) {
            purchaseDTO.subtractAmount(lackAmount);
        }
    }

    private int getLackAmount(Product promoteProduct, int amount) {
        int buy = promoteProduct.getPromotion().getBuy();
        int get = promoteProduct.getPromotion().getGet();
        int quantity = promoteProduct.getQuantity();

        if (buy + get > quantity) {
            return amount - quantity;
        }
        return amount - ((quantity/(buy+get)) * (buy+get));
    }

    private int calculateExtraProductAmount(int amount, Product promoteProduct) {
        int buyQuantity = promoteProduct.getPromotion().getBuy();
        int freeQuantity = promoteProduct.getPromotion().getGet();
        int totalQuantity = promoteProduct.getQuantity();

        while (buyQuantity <= amount && amount < totalQuantity) {
            int nextBuyStart = buyQuantity + freeQuantity;
            if (amount < nextBuyStart) {
                return Math.min(nextBuyStart - amount, totalQuantity - amount);
            }
            buyQuantity += nextBuyStart;
        }
        return 0;
    }

    private boolean isExtraPromote(PurchaseDTO purchaseDTO) {
        int purchaseCount = purchaseDTO.getPurchaseCount();
        int get = purchaseDTO.getPromotion().getGet();
        int buy = purchaseDTO.getPromotion().getBuy();

        return ((purchaseCount - buy)%(buy+get)) == 0;
    }

    private int promotionGet(PurchaseDTO purchaseDTO) {
        int promoteProductQuantity = purchaseDTO.getPromoteProduct().getQuantity();
        int promotionGet = purchaseDTO.getPromotion().getGet();
        int purchaseCount = purchaseDTO.getPurchaseCount();

        if (promotionGet + purchaseCount > purchaseDTO.getAmount()) {
            return purchaseDTO.getAmount() - purchaseCount;
        }
        if (promoteProductQuantity < promotionGet + purchaseCount) {
            // 프로모션 적용으로 얻을 수 있는 상품 개수는 프로모션 재고 이내일 수 있게
            return promoteProductQuantity - purchaseCount;
        }
        return promotionGet;
    }
}
