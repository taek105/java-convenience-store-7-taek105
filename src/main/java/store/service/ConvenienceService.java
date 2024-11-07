package store.service;

import camp.nextstep.edu.missionutils.Console;
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
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public int purchase(String productName, int amount) {
        Product promoteProduct = products.getProduct(productName, true);
        Product justProduct = products.getProduct(productName, false);
        validate(amount, promoteProduct, justProduct);

        PurchaseResult purchaseResult = new PurchaseResult(amount);

        promotionQuantityCheck(promoteProduct, purchaseResult);
//        if ( amount % )

        promoteProductPurchase(promoteProduct, purchaseResult);
        justProductPurchase(justProduct, purchaseResult);

        return purchaseResult.getPayAmount();
    }

    private void promotionQuantityCheck(Product promoteProduct, PurchaseResult purchaseResult) {
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
//            boolean promotionApply = notifyPromotion(promoteProduct, purchaseResult);
            int promotionGet = promotionGet(promoteProduct, purchaseResult, true);

            purchaseResult.addPurchaseCount(promotionGet);
        }
    }

    private int promotionGet(Product promoteProduct, PurchaseResult purchaseResult, boolean promotionApply) {
        int promoteProductQuantity = promoteProduct.getQuantity();
        int promotionGet = promoteProduct.getPromotion().getGet();
        int purchaseCount = purchaseResult.getPurchaseCount();

        if ( promotionApply ){
            if (promoteProductQuantity-purchaseResult.getPurchaseCount() < promotionGet) {
                // 프로모션 적용으로 얻을 수 있는 상품 개수는 프로모션 재고 이내일 수 있게
                return promoteProductQuantity - purchaseCount;
            }
            return promotionGet;
        }
        if (promoteProductQuantity-purchaseResult.getPurchaseCount() < promotionGet) {
            // 프로모션 적용으로 얻을 수 있는 상품 개수는 프로모션 재고 이내일 수 있게
            return promoteProductQuantity - purchaseCount;
        }
        return purchaseCount - purchaseResult.getAmount();
    }

    private boolean notifyPromotion(Product promoteProduct, PurchaseResult purchaseResult) {
        if ( purchaseResult.getAmount()
                < (purchaseResult.getPurchaseCount() + promoteProduct.getPromotion().getGet()) ) {
            inputView.notifyPromotion(promoteProduct.getName(), promoteProduct.getPromotion().getGet());
            String input = Console.readLine();

            if ( input.equals("Y") ) {
                return true;
            }
        }
        return false;
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
