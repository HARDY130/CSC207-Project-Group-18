package interface_adapter.customize;

import entity.Food;
import entity.MealType;
import java.util.ArrayList;
import java.util.List;

public class CustomizeState {
    private String username;
    private List<Food> searchResults;
    private MealType selectedMealType;
    private String error;
    private String successMessage;
    private String searchQuery;
    private boolean isLoading;

    public CustomizeState() {
        this.searchResults = new ArrayList<>();
        this.selectedMealType = MealType.BREAKFAST;
        this.isLoading = false;
    }

    public String getUsername() { return username; }

    public List<Food> getSearchResults() { return new ArrayList<>(searchResults); }

    public String getError() { return error; }

    public String getSuccessMessage() { return successMessage; }

    public void setUsername(String username) { this.username = username; }

    public void setSearchResults(List<Food> searchResults) {
        this.searchResults = new ArrayList<>(searchResults);
    }

    public void setError(String error) { this.error = error; }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public void setLoading(boolean loading) { isLoading = loading; }
}
