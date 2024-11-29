package entity;

public enum MealType {
    BREAKFAST("B"),
    LUNCH("L"),
    DINNER("D");

    private final String code;

    MealType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}