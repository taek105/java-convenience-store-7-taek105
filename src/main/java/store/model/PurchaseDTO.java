package store.model;

import store.constant.ErrorMessage;
import store.domain.Product;
import store.domain.Promotion;

public class PurchaseDTO {
    Product promoteProduct;
    Product justProduct;
    private int amount;
    private int promotedPrice = 0;
    private int purchaseCount = 0;
    private int extraAmount = 0;

    public PurchaseDTO(Product promoteProduct, Product justProduct, int amount) {
        this.promoteProduct = promoteProduct;
        this.justProduct = justProduct;
        this.amount = amount;
        validate(amount, promoteProduct, justProduct);
    }

    private static void validate(int amount, Product promoteProduct, Product justProduct) {
        if ( promoteProduct.isEmpty() && justProduct.isEmpty() ) {
            throw new IllegalArgumentException(ErrorMessage.NOT_EXISTENCE_PRODUCT.getMessages());
        }
        if ( amount > promoteProduct.getQuantity() + justProduct.getQuantity() ) {
            throw new IllegalArgumentException(ErrorMessage.EXCEED_QUANTITY.getMessages());
        }
    }

    public Product getPromoteProduct() {
        return promoteProduct;
    }

    public Product getJustProduct() {
        return justProduct;
    }

    public Promotion getPromotion() {
        return this.promoteProduct.getPromotion();
    }
    public int getPromotedPrice() {
        return promotedPrice;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public int getAmount() {
        return amount;
    }

    public int getExtraAmount() {
        return extraAmount;
    }

    public void incrementPurchaseCount() {
        this.purchaseCount++;
    }

    public void addPayAmount(int amount) {
        this.promotedPrice += amount;
    }

    public void addPurchaseCount(int amount) {
        this.purchaseCount += amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void addExtraAmount(int amount) {
        this.extraAmount += amount;
    }

    public void subtractAmount(int amount) {
        this.amount -= amount;
    }
}
