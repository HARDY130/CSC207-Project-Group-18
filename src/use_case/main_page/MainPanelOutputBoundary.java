package use_case.main_page;

public interface MainPanelOutputBoundary {
    void prepareSuccessView(MainPanelOutputData outputData);

    void prepareFailView(MainPanelOutputData outputData);
}
