package store.model;

public class PurchaseDTO {
    private int amount;
    private int promotedPrice;
    private int purchaseCount;
    private int extraAmount;

    public PurchaseDTO(int amount) {
        this.amount = amount;
        this.promotedPrice = 0;
        this.purchaseCount = 0;
        this.extraAmount = 0;
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
