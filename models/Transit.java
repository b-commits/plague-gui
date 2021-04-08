package models;


public class Transit {

    private Country from;
    private Country to;
    private String type;

    public Transit(Country from, Country to, String type) {
        this.from = from;
        this.to = to;
        this.type = type;
    }

    public Country getFrom() {
        return from;
    }

    public Country getTo() {
        return to;
    }

    public String getType() {
        return type;
    }
}





