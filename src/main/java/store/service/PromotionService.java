package store.service;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Product;
import store.domain.Products;
import store.model.PurchaseResult;
import store.view.InputView;

public class PromotionService {
    Products products;
    InputView inputView;

    public PromotionService(Products products) {
        this.products = products;
        this.inputView = new InputView();
    }

    public void askServeExtraProduct(PurchaseResult purchaseResult, Product promoteProduct) {
        int extraProductAmount = ExtraProductAmount(purchaseResult.getAmount(), promoteProduct);
        if ( extraProductAmount > 0 ) {
            inputView.notifyExtraProducts(promoteProduct.getName(), extraProductAmount);
            String input = Console.readLine();

            if ( input.equals("Y") ) {
                purchaseResult.addAmount(extraProductAmount);
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

    public void promotionQuantityCheck(Product promoteProduct, PurchaseResult purchaseResult) {
        if ( purchaseResult.getAmount() > promoteProduct.getQuantity() ) {
            notifyLackPromotionQuantity(promoteProduct, purchaseResult);
        }
    }

    private void notifyLackPromotionQuantity(Product promoteProduct, PurchaseResult purchaseResult) {
        int lackAmount = getLackAmount(promoteProduct, purchaseResult.getAmount());
        inputView.notifyPromotionQuantity(promoteProduct.getName(), lackAmount);
        String input = Console.readLine();

        if (input.equals("N")) {
            purchaseResult.subtractAmount(lackAmount);
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
}
