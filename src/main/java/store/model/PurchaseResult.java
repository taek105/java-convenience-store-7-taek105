package store.model;

public class PurchaseResult {
    private final String name;
    private final int amount;
    private final int extraAmount;
    private final int promotedPrice;

    public PurchaseResult(String name, int amount, int extraAmount, int promotedPrice) {
        this.name = name;
        this.amount = amount;
        this.extraAmount = extraAmount;
        this.promotedPrice = promotedPrice;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getExtraAmount() {
        return extraAmount;
    }

    public int getPromotedPrice() {
        return promotedPrice;
    }

    public int getPrice() {
        return promotedPrice/(amount-extraAmount);
    }

    public int getPromotionKeep() {
        return getPrice()*extraAmount;
    }
}
