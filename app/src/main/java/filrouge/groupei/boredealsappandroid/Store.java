package filrouge.groupei.boredealsappandroid;

public class Store {
    private String name;
    private String description;

    public Store(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
