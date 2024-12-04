package use_case.customize;

public class CustomizeInputData {
    private final String searchQuery;

    public CustomizeInputData(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery() {
        return searchQuery;
    }
}