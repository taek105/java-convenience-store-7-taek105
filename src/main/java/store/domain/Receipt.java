package store.domain;

import store.constant.Constant;
import store.model.PurchaseResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Receipt {
    private final List<PurchaseResult> purchaseResults;
    private int promotedPrice = 0;
    private boolean membership;

    public Receipt() {
        purchaseResults = new ArrayList<>();
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
            keep = promotedPrice * (Constant.MEMBERSHIP_RATE.getValue()/100.0);
        }
        if ( keep > Constant.MEMBERSHIP_LIMIT.getValue() ) {
            keep = Constant.MEMBERSHIP_LIMIT.getValue();
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
