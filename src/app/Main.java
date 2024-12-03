package app;

import javax.swing.JFrame;
import java.awt.*;

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
                .addSignupView()
                .addLoginView()
                .addInfoCollectionView()
                .addDashboardView()
                .addLoggedInView()
                .addCustomizeView()

                .addSignupUseCase()
                .addLoginUseCase()
                .addInfoCollectionUseCase()
                .addDashboardUseCase()
                .addCustomizeUseCase()
//                .addChangePasswordUseCase()
                .addLogoutUseCase()
                .build();

        application.setMinimumSize(new Dimension(800, 600));
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
