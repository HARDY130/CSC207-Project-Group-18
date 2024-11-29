package interface_adapter.customize;

import interface_adapter.ViewModel;

public class CustomizeWindowViewModel extends ViewModel<String> {

    public CustomizeWindowViewModel() {
        super("customize");
        setState("");
    }
}
