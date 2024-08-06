package app.enums;

public enum AccountType {
    CURRENT("CUR"), SAVING("SAV");
    private final String type;

    AccountType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
