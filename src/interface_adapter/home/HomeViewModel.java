package interface_adapter.home;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HomeViewModel extends ViewModel {

    public static final String MealPlanner_Button = "Offer me";
    public static final String FoodSearch_Button = "Customize";
    public static final String Update_Button = "Update";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private HomeState state = new HomeState();

    public HomeViewModel() {
        super("Home");
    }

    public void setState(HomeState state) {
        this.state = state;
    }

    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public HomeState getState() {
        return state;
    }

}
