package store.model;

public class readProductDTO {
    private final String name;
    private final int amount;

    public readProductDTO(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }
}
