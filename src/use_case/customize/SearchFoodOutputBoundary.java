package use_case.customize;

public interface SearchFoodOutputBoundary {

    void prepareSuccessView(SearchFoodOutputData outputData);

    void prepareFailView(String errorMessage);
}
