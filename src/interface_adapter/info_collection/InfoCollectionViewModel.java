package interface_adapter.info_collection;

import interface_adapter.ViewModel;

public class InfoCollectionViewModel extends ViewModel<InfoCollectionState> {
    public static final String TITLE_LABEL = "Complete Your Profile";
    public static final String BIRTH_YEAR_LABEL = "Year of Birth:";
    public static final String GENDER_LABEL = "Gender:";
    public static final String WEIGHT_LABEL = "Weight (kg):";
    public static final String HEIGHT_LABEL = "Height (cm):";
    public static final String ACTIVITY_LABEL = "Activity Level:";
    public static final String ALLERGIES_LABEL = "Allergies & Preferences";

    public static final String SAVE_BUTTON_LABEL = "Save";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public InfoCollectionViewModel() {
        super("info collection");
        setState(new InfoCollectionState());
    }
}