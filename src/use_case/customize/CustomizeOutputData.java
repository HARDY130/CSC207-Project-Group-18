package use_case.customize;

import entity.Food;
import java.util.List;

public class CustomizeOutputData {
    private final List<Food> searchResults;
    private final String error;
    private final String successMessage;

    public CustomizeOutputData(List<Food> searchResults, String error) {
        this(searchResults, error, null);
    }

    public CustomizeOutputData(List<Food> searchResults, String error, String successMessage) {
        this.searchResults = searchResults;
        this.error = error;
        this.successMessage = successMessage;
    }

    public List<Food> getSearchResults() {
        return searchResults;
    }

    public String getError() {
        return error;
    }

    public String getSuccessMessage() {
        return successMessage;
    }
}