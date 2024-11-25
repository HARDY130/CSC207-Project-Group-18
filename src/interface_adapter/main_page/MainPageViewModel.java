package interface_adapter.main_page;

import interface_adapter.ViewModel;

import java.util.ArrayList;

public class MainPageViewModel extends ViewModel<MainPageState> {

    public MainPageViewModel() {
        super("Main Page");
        setState(new MainPageState(new String(), new String(), new String[], new String[], new String[]);
    }

}
