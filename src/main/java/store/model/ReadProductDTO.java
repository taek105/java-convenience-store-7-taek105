package store.model;

public class ReadProductDTO {
    private final String name;
    private final int amount;

    public ReadProductDTO(String name, int amount) {
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
