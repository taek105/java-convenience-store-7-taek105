package store.model;

public class PurchaseResult {
    private int amount;
    private int payAmount;
    private int purchaseCount;

    public PurchaseResult(int amount) {
        this.amount = amount;
        this.payAmount = 0;
        this.purchaseCount = 0;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public int getAmount() {
        return amount;
    }

    public void incrementPurchaseCount() {
        this.purchaseCount++;
    }

    public void addPayAmount(int amount) {
        this.payAmount += amount;
    }

    public void addPurchaseCount(int amount) {
        this.purchaseCount += amount;
    }

    public void subtractAmount(int amount) {
        this.amount -= amount;
    }
}
