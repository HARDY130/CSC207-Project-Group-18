package entity;

import java.util.Map;

public class Food {
    private String foodId;
    private String label;
    private String category;
    private Map<String, Double> nutrients;

    public Food(String foodId, String label, String category, Map<String, Double> nutrients) {
        this.foodId = foodId;
        this.label = label;
        this.category = category;
        this.nutrients = nutrients;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Map<String, Double> getNutrients() {
        return nutrients;
    }

    public void setNutrients(Map<String, Double> nutrients) {
        this.nutrients = nutrients;
    }

    // display food information
    @Override
    public String toString() {
        return "Food{" +
            "foodId='" + foodId + '\'' +
            ", label='" + label + '\'' +
            ", category='" + category + '\'' +
            ", nutrients=" + nutrients +
            '}';
    }
}
