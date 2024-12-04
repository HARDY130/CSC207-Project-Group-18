package use_case.customize;

public interface CustomizeOutputBoundary {
    void presentSearchResults(CustomizeOutputData outputData);
    void presentError(String error);
    void presentSuccess(String message);
    void presentDashboard();
}