package interface_adapter;

/**
 * Model for the View Manager. Its state is the name of the View which
 * is currently active. An initial state of "" is used.
 */
public class ViewManagerModel extends ViewModel<String> {

    public ViewManagerModel() {
        super("view manager");
        this.setState("");
    }
    // Alias setActiveView to setState for clarity
    public void setActiveView(String viewName) {
        this.setState(viewName);
    }
}
