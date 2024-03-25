package filrouge.groupei.boredealsappandroid;

public class Store {
    private String name;
    private String description;
    private int discountPercentage;

    public Store(String name, String description, int discountPercentage) {
        this.name = name;
        this.description = description;
        this.discountPercentage = discountPercentage;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }
}

