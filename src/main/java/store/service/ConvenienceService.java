package store.service;

import store.constant.ErrorMessage;
import store.domain.Product;
import store.domain.Products;
import store.model.PurchaseResult;
import store.view.OutputView;

public class ConvenienceService {
    PromotionService promotionService;
    Products products;
    OutputView outputView;

    public ConvenienceService(Products products) {
        this.promotionService = new PromotionService(products);
        this.products = products;
        this.outputView = new OutputView();
    }

    public int purchase(String productName, int amount) {
        Product promoteProduct = products.getProduct(productName, true);
        Product justProduct = products.getProduct(productName, false);
        validate(amount, promoteProduct, justProduct);

        PurchaseResult purchaseResult = new PurchaseResult(amount);
        promotionService.promotionQuantityCheck(promoteProduct, purchaseResult);
        promotionService.askServeExtraProduct(purchaseResult, promoteProduct);

        promoteProductPurchase(promoteProduct, purchaseResult);
        justProductPurchase(justProduct, purchaseResult);

        return purchaseResult.getPayAmount();
    }

    private void promoteProductPurchase(Product promoteProduct, PurchaseResult purchaseResult) {
        while ( purchaseResult.getPurchaseCount() < promoteProduct.getQuantity()
                && purchaseResult.getPurchaseCount() < purchaseResult.getAmount()) {
            purchaseResult.incrementPurchaseCount();
            purchaseResult.addPayAmount(promoteProduct.getPrice());

            promotionCheck(promoteProduct, purchaseResult);
        }
        promoteProduct.sold(purchaseResult.getPurchaseCount());
    }

    private void promotionCheck(Product promoteProduct, PurchaseResult purchaseResult) {
        int purchaseCount = purchaseResult.getPurchaseCount();
        if ( purchaseCount % promoteProduct.getPromotion().getBuy() == 0 ){
            int promotionGet = promotionGet(promoteProduct, purchaseResult);

            purchaseResult.addPurchaseCount(promotionGet);
        }
    }

    private int promotionGet(Product promoteProduct, PurchaseResult purchaseResult) {
        int promoteProductQuantity = promoteProduct.getQuantity();
        int promotionGet = promoteProduct.getPromotion().getGet();
        int purchaseCount = purchaseResult.getPurchaseCount();

        if (promoteProductQuantity-purchaseResult.getPurchaseCount() < promotionGet) {
            // 프로모션 적용으로 얻을 수 있는 상품 개수는 프로모션 재고 이내일 수 있게
            return promoteProductQuantity - purchaseCount;
        }
        return purchaseCount - purchaseResult.getAmount();
    }

    private void justProductPurchase(Product justProduct, PurchaseResult purchaseResult) {
        int amount = 0;
        while ( amount < justProduct.getQuantity()
                && purchaseResult.getPurchaseCount() < purchaseResult.getAmount()) {
            amount++;
            purchaseResult.incrementPurchaseCount();
            purchaseResult.addPayAmount(justProduct.getPrice());
        }
        justProduct.sold(amount);
    }

    private static void validate(int amount, Product promoteProduct, Product justProduct) {
        if ( amount > promoteProduct.getQuantity() + justProduct.getQuantity() ) {
            throw new IllegalArgumentException(ErrorMessage.EXCEED_QUANTITY.getMessages());
        }
    }
}
