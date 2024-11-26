package interface_adapter.home;

import use_case.home.HomeInputBoundary;
import use_case.home.HomeInputData;

public class HomeController {
    private HomeInputBoundary homeUseCaseInteractor;

    public HomeController(HomeInputBoundary homeUseCaseInteractor) {
        this.homeUseCaseInteractor = homeUseCaseInteractor;
    }



}
