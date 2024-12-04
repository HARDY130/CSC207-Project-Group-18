package use_case.customize;

public class CustomizeInputData {
    private final String username;
    private final String searchQuery;

    public CustomizeInputData(String username, String searchQuery) {
        this.username = username;
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery() {
        return searchQuery;
    }
}