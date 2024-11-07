package store.model;

public class PurchaseResult {
    private int payAmount;
    private int purchasedAmount;

    public PurchaseResult() {
        this.payAmount = 0;
        this.purchasedAmount = 0;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public int getPurchasedAmount() {
        return purchasedAmount;
    }

    public void plusPurchasedAmount() {
        this.purchasedAmount ++;
    }

    public void addPayAmount(int amount) {
        this.payAmount += amount;
    }

    public void addPurchaseAmount(int amount) {
        this.purchasedAmount += amount;
    }
}
