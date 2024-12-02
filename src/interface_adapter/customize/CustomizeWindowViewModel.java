package interface_adapter.customize;

import interface_adapter.ViewModel;
/**
 * The view model for the customize page.
 */

public class CustomizeWindowViewModel extends ViewModel<String> {

    public CustomizeWindowViewModel() {
        super("customize");
        setState("");
    }
}