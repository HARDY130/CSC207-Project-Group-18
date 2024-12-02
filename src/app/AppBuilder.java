package app;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.FoodDatabaseAccessObject;
import data_access.InMemoryUserDataAccessObject;
import data_access.MealPlannerDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.customize.CustomizeController;
import interface_adapter.customize.CustomizePresenter;
import interface_adapter.customize.CustomizeViewModel;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardPresenter;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.info_collection.InfoCollectionController;
import interface_adapter.info_collection.InfoCollectionPresenter;
import interface_adapter.info_collection.InfoCollectionViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.meal_planner.MealPlannerController;
import interface_adapter.meal_planner.MealPlannerPresenter;
import interface_adapter.meal_planner.MealPlannerViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import java.awt.CardLayout;
import java.awt.Dimension;
import use_case.customize.CustomizeInputBoundary;
import use_case.customize.CustomizeInteractor;
import use_case.customize.CustomizeOutputBoundary;
import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInteractor;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.info_collection.InfoCollectionInputBoundary;
import use_case.info_collection.InfoCollectionInteractor;
import use_case.info_collection.InfoCollectionOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.meal_planner.MealPlannerInputBoundary;
import use_case.meal_planner.MealPlannerInteractor;
import use_case.meal_planner.MealPlannerOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.CustomizeView;
import view.DashboardView;
import view.InfoCollectionView;
import view.LoggedInView;
import view.LoginView;
import view.MealPlannerView;
import view.SignupView;
import view.ViewManager;

;


/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();
    private final MealPlannerDataAccessObject mealPlannerDataAccessObject;
    private final FoodDatabaseAccessObject foodDatabaseAccessObject;

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private InfoCollectionView infoCollectionView;
    private InfoCollectionViewModel infoCollectionViewModel;
    private DashboardView dashboardView;
    private DashboardViewModel dashboardViewModel;
    private DashboardController dashboardController;
    private CustomizeView customizeView;
    private CustomizeViewModel customizeViewModel;
    private MealPlannerView mealPlannerView;
    private MealPlannerViewModel mealPlannerViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        mealPlannerDataAccessObject = new MealPlannerDataAccessObject(userDataAccessObject);
        foodDatabaseAccessObject = new FoodDatabaseAccessObject();
        customizeViewModel = new CustomizeViewModel();
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel, viewManagerModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addInfoCollectionView() {
        infoCollectionViewModel = new InfoCollectionViewModel();
        dashboardViewModel = new DashboardViewModel();
        infoCollectionView = new InfoCollectionView(infoCollectionViewModel, viewManagerModel);
        cardPanel.add(infoCollectionView, infoCollectionView.getViewName());
        return this;
    }

    public AppBuilder addDashboardView() {
        dashboardViewModel = new DashboardViewModel();

        DashboardOutputBoundary outputBoundary = new DashboardPresenter(
            viewManagerModel,
            dashboardViewModel,
            infoCollectionViewModel,
            customizeViewModel,
            mealPlannerViewModel
        );

        DashboardInputBoundary interactor = new DashboardInteractor(
            userDataAccessObject,
            outputBoundary
        );

        dashboardController = new DashboardController(interactor);

        dashboardView = new DashboardView(dashboardViewModel);
        dashboardView.setDashboardController(dashboardController);
        cardPanel.add(dashboardView, dashboardView.getViewName());

        return this;
    }

    public AppBuilder addCustomizeView() {
        customizeViewModel = new CustomizeViewModel();
        CustomizeController customizeController = createCustomizeUseCase();
        customizeView = new CustomizeView(customizeViewModel, customizeController, viewManagerModel);
        cardPanel.add(customizeView, customizeView.getViewName());
        return this;
    }

    private CustomizeController createCustomizeUseCase() {
        CustomizeOutputBoundary customizePresenter = new CustomizePresenter(
            customizeViewModel,
            viewManagerModel,
            dashboardViewModel,
            dashboardController
        );

        CustomizeInputBoundary customizeInteractor = new CustomizeInteractor(
            foodDatabaseAccessObject,
            customizePresenter,
            userDataAccessObject
        );

        return new CustomizeController(customizeInteractor);
    }

    public AppBuilder addMealPlannerView() {
        mealPlannerViewModel = new MealPlannerViewModel();
        mealPlannerView = new MealPlannerView(mealPlannerViewModel);
        cardPanel.add(mealPlannerView, mealPlannerView.getViewName());
        return this;
    }

    public AppBuilder addDashboardUseCase() {
        DashboardOutputBoundary outputBoundary = new DashboardPresenter(
            viewManagerModel,
            dashboardViewModel,
            infoCollectionViewModel,
            customizeViewModel,
            mealPlannerViewModel
        );

        DashboardInputBoundary interactor = new DashboardInteractor(
            userDataAccessObject,
            outputBoundary
        );

        DashboardController controller = new DashboardController(interactor);
        dashboardView.setDashboardController(controller);
        return this;
    }

    public AppBuilder addMealPlannerUseCase() {
        MealPlannerOutputBoundary outputBoundary = new MealPlannerPresenter(
            mealPlannerViewModel,
            viewManagerModel,
            dashboardViewModel
        );

        MealPlannerInputBoundary interactor = new MealPlannerInteractor(
            userDataAccessObject,
            mealPlannerDataAccessObject,
            outputBoundary
        );

        MealPlannerController controller = new MealPlannerController(interactor);
        mealPlannerView.setMealPlannerController(controller);
        return this;
    }

    public AppBuilder addSignupUseCase() {
        SignupOutputBoundary signupOutputBoundary = new SignupPresenter(
            viewManagerModel,
            signupViewModel,
            loginViewModel,
            infoCollectionViewModel
        );

        SignupInputBoundary userSignupInteractor = new SignupInteractor(
            userDataAccessObject,
            signupOutputBoundary,
            userFactory
        );

        SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(
            viewManagerModel,
            loggedInViewModel,
            loginViewModel,
            infoCollectionViewModel,
            dashboardViewModel
        );

        LoginInputBoundary loginInteractor = new LoginInteractor(
            userDataAccessObject,
            loginOutputBoundary
        );

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addInfoCollectionUseCase() {
        InfoCollectionOutputBoundary outputBoundary = new InfoCollectionPresenter(
            viewManagerModel,
            infoCollectionViewModel,
            dashboardViewModel
        );

        InfoCollectionInputBoundary infoCollectionInteractor = new InfoCollectionInteractor(
            userDataAccessObject,
            outputBoundary,
            userFactory
        );

        InfoCollectionController controller = new InfoCollectionController(infoCollectionInteractor);
        infoCollectionView.setInfoCollectionController(controller);
        return this;
    }

    public AppBuilder addLogoutUseCase() {
        LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(
            viewManagerModel,
            loggedInViewModel,
            loginViewModel
        );

        LogoutInputBoundary logoutInteractor = new LogoutInteractor(
            userDataAccessObject,
            logoutOutputBoundary
        );

        LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        dashboardView.setLogoutController(logoutController);

        return this;
    }

    public JFrame build() {
        JFrame application = new JFrame("Food Planner");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        application.setMinimumSize(new Dimension(800, 600));

        viewManagerModel.setActiveView(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}