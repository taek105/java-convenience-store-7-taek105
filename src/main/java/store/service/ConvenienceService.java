package store.service;

import store.constant.ErrorMessage;
import store.domain.Product;
import store.domain.Products;
import store.model.PurchaseResult;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceService {
    Products products;
    InputView inputView;
    OutputView outputView;

    public ConvenienceService(Products products) {
        this.products = products;
    }

    public int purchase(String productName, int amount) {
        Product promoteProduct = products.getProduct(productName, true);
        Product justProduct = products.getProduct(productName, false);

        if ( amount > promoteProduct.getQuantity() + justProduct.getQuantity() ) {
            throw new IllegalArgumentException(ErrorMessage.EXCEED_QUANTITY.getMessages());
        }

        PurchaseResult purchaseResult = promoteProductPurchase(promoteProduct, amount);

        justProductPurchase(justProduct, amount, purchaseResult);

        return purchaseResult.getPayAmount();
    }

    private PurchaseResult promoteProductPurchase(Product promoteProduct, int amount) {
        PurchaseResult purchaseResult = new PurchaseResult();

        while ( purchaseResult.getPurchasedAmount() < promoteProduct.getQuantity()
                && purchaseResult.getPurchasedAmount() < amount) {
            purchaseResult.plusPurchasedAmount();
            purchaseResult.addPayAmount(promoteProduct.getPrice());

            promotionCheck(promoteProduct, purchaseResult);
        }

        promoteProduct.sold(purchaseResult.getPurchasedAmount());
        return purchaseResult;
    }

    private void promotionCheck(Product promoteProduct, PurchaseResult purchaseResult) {
        int purchaseAmount = purchaseResult.getPurchasedAmount();
        if ( purchaseAmount % promoteProduct.getPromotion().getBuy() == 0 ){
            purchaseResult.addPurchaseAmount(promotionGet(purchaseAmount, promoteProduct));
        }
    }

    private int promotionGet(int purchasedAmount, Product promoteProduct) {
        int promoteProductQuantity = promoteProduct.getQuantity();
        int promotionGet = promoteProduct.getPromotion().getGet();

        if ( promoteProductQuantity < promotionGet ) {
            // 프로모션 적용으로 얻을 수 있는 상품 개수는 프로모션 재고 이내일 수 있게
            return promoteProductQuantity - purchasedAmount;
        }
        return promotionGet;
    }

    private void justProductPurchase(Product justProduct, int amount, PurchaseResult purchaseResult) {
        int promotePurchaseAmount = purchaseResult.getPurchasedAmount();

        while ( purchaseResult.getPurchasedAmount() < justProduct.getQuantity()
                && purchaseResult.getPurchasedAmount() < amount) {
            purchaseResult.plusPurchasedAmount();
            purchaseResult.addPayAmount(justProduct.getPrice());
        }

        justProduct.sold((purchaseResult.getPurchasedAmount() - promotePurchaseAmount));
    }


}
