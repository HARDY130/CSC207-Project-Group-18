package use_case.customize.search_food;

public class SearchFoodInputData {
    private final String foodname;

    public SearchFoodInputData(String foodname) {
        this.foodname = foodname;
    }

    public String getfoodname() {
        return foodname;
    }
}
