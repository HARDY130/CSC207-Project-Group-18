package app;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.customize.CustomizeWindowViewModel;
import interface_adapter.customize.SearchFoodController;
import interface_adapter.customize.SearchFoodPresenter;
import interface_adapter.customize.SearchFoodViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.customize.SearchFoodInputBoundary;
import use_case.customize.SearchFoodInterator;
import use_case.customize.SearchFoodOutputBoundary;
import use_case.dashboard.DashboardDataAccessInterface;
import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInteractor;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.*;
import interface_adapter.info_collection.*;
import interface_adapter.dashboard.*;
import use_case.info_collection.*;
import view.FoodSearchView.customize_view.CustomizeWindowView;
import view.FoodSearchView.search_foods_view.SearchFoodView;


/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();

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
    private SearchFoodViewModel searchFoodViewModel;
    private SearchFoodView searchFoodView;
    private CustomizeWindowView customizeWindowView;
    private CustomizeWindowViewModel customizeWindowViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel, viewManagerModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel, infoCollectionViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);


        SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
//    public AppBuilder addLoginUseCase() {
//        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
//                loggedInViewModel, loginViewModel, infoCollectionViewModel);
//
//        final LoginInputBoundary loginInteractor = new LoginInteractor(
//                userDataAccessObject, loginOutputBoundary);
//
//        final LoginController loginController = new LoginController(loginInteractor);
//        loginView.setLoginController(loginController);
//        return this;
//    }
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(
                viewManagerModel,
                loggedInViewModel,
                loginViewModel,
                infoCollectionViewModel,
                dashboardViewModel  // Add this parameter
        );

        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject,
                loginOutputBoundary
        );

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Change Password Use Case to the application.
     * @return this builder
     */
//    public AppBuilder addChangePasswordUseCase() {
//        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
//                new ChangePasswordPresenter(loggedInViewModel);
//
//        final ChangePasswordInputBoundary changePasswordInteractor =
//                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);
//
//        final ChangePasswordController changePasswordController =
//                new ChangePasswordController(changePasswordInteractor);
//        loggedInView.setChangePasswordController(changePasswordController);
//        return this;
//    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        LogoutInputBoundary logoutInteractor = new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        dashboardView.setLogoutController(logoutController); // Add this line to connect logout to dashboard

        return this;
    }

    public AppBuilder addInfoCollectionView() {
        infoCollectionViewModel = new InfoCollectionViewModel();
        dashboardViewModel = new DashboardViewModel();  // Initialize dashboard view model
        infoCollectionView = new InfoCollectionView(infoCollectionViewModel, viewManagerModel);
        cardPanel.add(infoCollectionView, infoCollectionView.getViewName());
        return this;
    }

    public AppBuilder addCustomizeWindowView() {
        customizeWindowViewModel = new CustomizeWindowViewModel();
        dashboardViewModel = new DashboardViewModel();
        customizeWindowView = new CustomizeWindowView();
        cardPanel.add(customizeWindowView, customizeWindowView.getViewName());
        return this;
    }

//    public AppBuilder addCustomizeUseCase() {
//        SearchFoodInputBoundary interactor = new SearchFoodInterator(searchFoodViewModel);
//        SearchFoodController searchFoodController = new SearchFoodController(interactor);
//        searchFoodView.setSearchFoodController(searchFoodController);
//        return this;
//    }

//    public AppBuilder addSearchFoodView() {
//        searchFoodViewModel = new SearchFoodViewModel();
//        dashboardViewModel = new DashboardViewModel();
//        searchFoodView = new SearchFoodView(searchFoodViewModel);
//        cardPanel.add(searchFoodView, searchFoodView.getViewName());
//        return this;
//    }
//
//    public AppBuilder addSearchFoodUseCase() {
//        SearchFoodOutputBoundary outputBoundary = new SearchFoodPresenter(
//                searchFoodViewModel
//        );
//
//        SearchFoodInputBoundary interactor = new SearchFoodInterator(
//
//        )
//
//    }


    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
//    public JFrame build() {
//        final JFrame application = new JFrame("Login Example");
//        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//        application.add(cardPanel);
//
//        viewManagerModel.setState(signupView.getViewName());
//        viewManagerModel.firePropertyChanged();
//
//        return application;
//    }

//    public JFrame build() {
//        final JFrame application = new JFrame("Meal Planner");
//        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        application.add(cardPanel);
//        application.setMinimumSize(new Dimension(800, 600));  // Set minimum window size
//        viewManagerModel.setActiveView(signupView.getViewName());
//        viewManagerModel.firePropertyChanged();
//        return application;
//    }

    public AppBuilder addDashboardView() {
        dashboardViewModel = new DashboardViewModel();
        dashboardView = new DashboardView(dashboardViewModel);
        cardPanel.add(dashboardView, dashboardView.getViewName());
        return this;
    }

    public AppBuilder addDashboardUseCase() {
        DashboardOutputBoundary outputBoundary = new DashboardPresenter(
                viewManagerModel,
                dashboardViewModel,
                infoCollectionViewModel,
                customizeWindowViewModel
        );

        DashboardDataAccessInterface userDataAccessObject =
                (DashboardDataAccessInterface) this.userDataAccessObject;

        DashboardInputBoundary interactor = new DashboardInteractor(
                userDataAccessObject,
                outputBoundary
        );

        dashboardController = new DashboardController(interactor);
        dashboardView.setDashboardController(dashboardController);
        return this;
    }

    // Make sure InfoCollectionView can transition to Dashboard
    public AppBuilder addInfoCollectionUseCase() {
        InfoCollectionOutputBoundary outputBoundary = new InfoCollectionPresenter(
                viewManagerModel,
                infoCollectionViewModel,
                dashboardViewModel// Pass dashboardViewModel
        );

        InfoCollectionInputBoundary infoCollectionInteractor = new InfoCollectionInteractor(
                (InfoCollectionUserDataAccessInterface) userDataAccessObject,
                outputBoundary,
                userFactory
        );

        InfoCollectionController controller = new InfoCollectionController(infoCollectionInteractor);
        infoCollectionView.setInfoCollectionController(controller);
        return this;
    }


    // Update the build method to support the new sizing
    public JFrame build() {
        JFrame application = new JFrame("Food Planner");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        // Set minimum size to accommodate the dashboard
        application.setMinimumSize(new Dimension(800, 600));

        viewManagerModel.setActiveView(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
