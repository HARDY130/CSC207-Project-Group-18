package interface_adapter.customize;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
/**
 * The view model for the customize page.
 */

public class CustomizeViewModel extends ViewModel<CustomizeState> {
    public static final String TITLE_LABEL = "Customize Meals";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String ADD_BUTTON_LABEL = "Add to Meal";
    public static final String RETURN_BUTTON_LABEL = "Return to Dashboard";

    public CustomizeViewModel() {
        super("customize");
        setState(new CustomizeState());
    }
}
