package store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Receipt {
    private final List<PurchaseResult> purchaseResults;
    private int promotedPrice;
    private boolean membership;

    public Receipt() {
        purchaseResults = new ArrayList<>();
        this.promotedPrice = 0;
        this.membership = false;
    }

    public void membership() {
        this.membership = true;
    }

    public void add(PurchaseResult purchaseResult) {
        purchaseResults.add(purchaseResult);
        promotedPrice += purchaseResult.getPromotedPrice();
    }

    public List<PurchaseResult> getPurchaseResults() {
        return Collections.unmodifiableList(purchaseResults);
    }

    public int getPromotedPrice() {
        return promotedPrice;
    }

    public double getMembershipKeep() {
        double keep = 0;
        if ( this.membership ) {
            keep = promotedPrice * 0.3;
        }
        if ( keep > 8000 ) {
            keep = 8000;
        }
        return keep;
    }

    public double getPromotionKeep() {
        double promotionKeep = 0;
        for ( PurchaseResult purchaseResult : purchaseResults ) {
            promotionKeep += purchaseResult.getPromotionKeep();
        }
        return promotionKeep;
    }
}
