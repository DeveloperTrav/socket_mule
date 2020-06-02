package core.models;

public class Response {
    private String name;
    private boolean hasSupplies;

    public Response(String name, boolean hasSupplies) {
        this.name = name;
        this.hasSupplies = hasSupplies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasSupplies() {
        return hasSupplies;
    }

    public void setHasSupplies(boolean hasSupplies) {
        this.hasSupplies = hasSupplies;
    }
}
