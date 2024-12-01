package interface_adapter.customize;

import entity.Food;
import entity.MealType;
import interface_adapter.ViewModel;

import java.util.List;

public class CustomizeViewModel extends ViewModel<CustomizeState> {
    // View labels and constants
    public static final String TITLE_LABEL = "Customize Meals";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String ADD_BUTTON_LABEL = "Add to Meal";
    public static final String RETURN_BUTTON_LABEL = "Return to Dashboard";
    public static final String SEARCH_PROMPT = "Enter food name...";
    public static final String LOADING_MESSAGE = "Searching for foods...";
    public static final String NO_RESULTS_MESSAGE = "No foods found";
    public static final String MEAL_TYPE_LABEL = "Select meal type:";

    public CustomizeViewModel() {
        super("customize");
        setState(new CustomizeState());
    }

    // Helper method to handle loading state
    public void setLoading(boolean loading) {
        CustomizeState currentState = getState();
        currentState.setLoading(loading);
        setState(currentState);
        firePropertyChanged();
    }

    // Helper method to update search results
    public void setSearchResults(List<Food> foods) {
        CustomizeState currentState = getState();
        currentState.setSearchResults(foods);
        currentState.setLoading(false);
        setState(currentState);
        firePropertyChanged();
    }

    // Helper method to set error
    public void setError(String error) {
        CustomizeState currentState = getState();
        currentState.setError(error);
        currentState.setLoading(false);
        setState(currentState);
        firePropertyChanged();
    }

    // Helper method to update selected meal type
    public void setSelectedMealType(MealType mealType) {
        CustomizeState currentState = getState();
        currentState.setSelectedMealType(mealType);
        setState(currentState);
        firePropertyChanged();
    }

    // Helper method to clear state
    public void clear() {
        setState(new CustomizeState());
        firePropertyChanged();
    }

    // Helper method to format numbers for display
    public String formatNutrientValue(double value) {
        return String.format("%.1f", value);
    }

    // Helper method to get appropriate message based on state
    public String getStatusMessage() {
        CustomizeState state = getState();
        if (state.isLoading()) {
            return LOADING_MESSAGE;
        }
        if (state.getError() != null) {
            return state.getError();
        }
        if (state.getSearchResults().isEmpty() && state.getSearchQuery() != null) {
            return NO_RESULTS_MESSAGE;
        }
        return "";
    }
}