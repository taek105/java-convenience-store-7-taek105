package store.domain;

import store.constant.ErrorMessage;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    public Product(String name, String price, String stock, Promotion promotion) {
        this.name = name;
        this.price = Integer.parseInt(price);
        this.quantity = Integer.parseInt(stock);
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public boolean isSellable(int quantity) {
        return this.quantity >= quantity;
    }

    public void sold(int quantity) {
        if ( !isSellable(quantity) ) {
            throw new IllegalArgumentException(ErrorMessage.EXCEED_QUANTITY.getMessages());
        }
        this.quantity -= quantity;
    }
}
