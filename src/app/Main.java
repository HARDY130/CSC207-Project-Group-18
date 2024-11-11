package app;

import data_access.MealPlannerDataAccessObject;

import javax.swing.JFrame;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                                            .addLoginView()
                                            .addSignupView()
                                            .addLoggedInView()
                                            .addSignupUseCase()
                                            .addLoginUseCase()
                                            .addChangePasswordUseCase()
                                            .addLogoutUseCase()
                                            .build();

        application.pack();
        application.setVisible(true);

//        MealPlannerDataAccessObject mealPlannerDataAccessObject = new MealPlannerDataAccessObject();
//        try{
//            System.out.println(mealPlannerDataAccessObject.searchFood(1));
//        }
//        catch (Exception e){
//            System.out.println(e);
//        }
    }
}
