package store.service;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Product;
import store.domain.Products;
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
        InputView.notifyPromotionQuantity(promoteProduct.getName(), lackAmount);
        String input = Console.readLine();

        if (input.equals("N")) {
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
        int extraProductAmount = ExtraProductAmount(purchaseDTO.getAmount(), promoteProduct);
        if ( extraProductAmount > 0 ) {
            InputView.notifyExtraProducts(promoteProduct.getName(), extraProductAmount);
            String input = Console.readLine();

            if ( input.equals("Y") ) {
                purchaseDTO.addAmount(extraProductAmount);
            }
        }
    }

    private int ExtraProductAmount(int amount, Product promoteProduct) {
        for (int i = promoteProduct.getPromotion().getBuy(); i <= promoteProduct.getQuantity();
             i += (promoteProduct.getPromotion().getBuy() + promoteProduct.getPromotion().getGet()) ) {
            for ( int j = i; j < i+promoteProduct.getPromotion().getGet(); j ++ ) {
                if ( amount == j ) {
                    return Math.min(i+promoteProduct.getPromotion().getGet()-j, promoteProduct.getQuantity()-j);
                }
            }
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
