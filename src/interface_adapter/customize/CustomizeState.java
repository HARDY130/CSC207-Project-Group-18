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

    // Copy constructor
    public CustomizeState(CustomizeState copy) {
        this.username = copy.username;
        this.searchResults = copy.searchResults != null ? new ArrayList<>(copy.searchResults) : new ArrayList<>();
        this.selectedMealType = copy.selectedMealType;
        this.error = copy.error;
        this.successMessage = copy.successMessage;
        this.searchQuery = copy.searchQuery;
        this.isLoading = copy.isLoading;
    }

    // Default constructor
    public CustomizeState() {
        this.searchResults = new ArrayList<>();
        this.selectedMealType = MealType.BREAKFAST;
        this.isLoading = false;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public List<Food> getSearchResults() {
        return new ArrayList<>(searchResults);
    }

    public void setSearchResults(List<Food> searchResults) {
        this.searchResults = new ArrayList<>(searchResults);
    }

    public MealType getSelectedMealType() {
        return selectedMealType;
    }

    public void setSelectedMealType(MealType selectedMealType) {
        this.selectedMealType = selectedMealType;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
