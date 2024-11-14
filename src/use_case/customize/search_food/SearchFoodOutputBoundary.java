package use_case.customize.search_food;

public interface SearchFoodOutputBoundary {

    void prepareSuccessView(SearchFoodOutputData outputData);

    void prepareFailView(String errorMessage);
}
