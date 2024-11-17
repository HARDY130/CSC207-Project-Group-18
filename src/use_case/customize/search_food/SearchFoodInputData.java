package use_case.customize.search_food;

public class SearchFoodInputData {
    private final String foodname;

    public SearchFoodInputData(String foodName) {
        this.foodname = foodName;
    }

    public String getfoodname() {
        return foodname;
    }
}
