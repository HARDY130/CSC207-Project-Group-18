package use_case.dashboard;

import data_access.InMemoryUserDataAccessObject;
import entity.Allergy;
import entity.CommonUser;
import entity.CommonUserFactory;
import entity.Food;
import entity.MealType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DashboardInteractorTest {
    private InMemoryUserDataAccessObject userDataAccessObject;
    private DashboardInteractor interactor;
    private TestDashboardPresenter presenter;

    private static class TestDashboardPresenter implements DashboardOutputBoundary {
        private boolean successViewCalled = false;
        private boolean failViewCalled = false;
        private boolean switchToInfoCollectionCalled = false;
        private boolean switchToMealPlannerCalled = false;
        private boolean switchToCustomizeCalled = false;
        private DashboardOutputData outputData;
        private String errorMessage;

        @Override
        public void prepareSuccessView(DashboardOutputData data) {
            this.successViewCalled = true;
            this.outputData = data;
        }

        @Override
        public void prepareFailView(String error) {
            this.failViewCalled = true;
            this.errorMessage = error;
        }

        @Override
        public void prepareSwitchToInfoCollection() {
            this.switchToInfoCollectionCalled = true;
        }

        @Override
        public void prepareSwitchToMealPlanner(DashboardOutputData outputData) {
            this.switchToMealPlannerCalled = true;
            this.outputData = outputData;
        }

        @Override
        public void prepareSwitchToCustomize() {
            this.switchToCustomizeCalled = true;
        }
    }

    @BeforeEach
    void setUp() {
        userDataAccessObject = new InMemoryUserDataAccessObject();
        presenter = new TestDashboardPresenter();
        interactor = new DashboardInteractor(userDataAccessObject, presenter);

        // Create and save a test user
        CommonUserFactory userFactory = new CommonUserFactory();
        Set<Allergy> allergies = new HashSet<>();
        CommonUser testUser = (CommonUser) userFactory.createWithInfo(
            "testUser",
            "password",
            LocalDate.of(1990, 1, 1),
            "Male",
            70, // weight
            175, // height
            1.2, // activity multiplier
            allergies
        );
        userDataAccessObject.save(testUser);
    }

    @Test
    void testSuccessfulDashboardLoad() {
        String username = "testUser";
        DashboardInputData inputData = new DashboardInputData(username);

        interactor.execute(inputData);

        assertTrue(presenter.successViewCalled);
        assertNotNull(presenter.outputData);
        assertEquals(username, presenter.outputData.getUsername());

        // Verify calculated values
        assertTrue(presenter.outputData.getBmr() > 0);
        assertTrue(presenter.outputData.getTdee() > 0);
        assertTrue(presenter.outputData.getCarbsGoal() > 0);
        assertTrue(presenter.outputData.getProteinGoal() > 0);
        assertTrue(presenter.outputData.getFatGoal() > 0);
    }

    @Test
    void testFailWithNonexistentUser() {
        DashboardInputData inputData = new DashboardInputData("nonexistentUser");

        interactor.execute(inputData);

        assertTrue(presenter.failViewCalled);
        assertEquals("User not found", presenter.errorMessage);
        assertFalse(presenter.successViewCalled);
    }

    @Test
    void testSwitchToUpdateProfile() {
        interactor.switchToUpdateProfile();

        assertTrue(presenter.switchToInfoCollectionCalled);
    }

    @Test
    void testSwitchToMealPlanner() {
        String username = "testUser";
        interactor.switchToMealPlanner(username);

        assertTrue(presenter.switchToMealPlannerCalled);
        assertNotNull(presenter.outputData);
        assertEquals(username, presenter.outputData.getUsername());
    }

    @Test
    void testSwitchToMealPlannerWithInvalidUser() {
        interactor.switchToMealPlanner("nonexistentUser");

        assertTrue(presenter.failViewCalled);
        assertEquals("User not found.", presenter.errorMessage);
        assertFalse(presenter.switchToMealPlannerCalled);
    }

    @Test
    void testSwitchToCustomize() {
        interactor.switchToCustomize();

        assertTrue(presenter.switchToCustomizeCalled);
    }

    @Test
    void testNutritionCalculationsWithMeals() {
        String username = "testUser";
        CommonUser user = (CommonUser) userDataAccessObject.get(username);

        // Add some test meals
        Map<String, Double> nutrients = new HashMap<>();
        nutrients.put("ENERC_KCAL", 500.0);
        nutrients.put("CHOCDF", 50.0);
        nutrients.put("PROCNT", 25.0);
        nutrients.put("FAT", 20.0);

        Food testFood = new Food("1", "Test Food", "Test Category", nutrients);
        user.addMeal(MealType.BREAKFAST, "Breakfast", testFood);

        // Execute dashboard update
        DashboardInputData inputData = new DashboardInputData(username);
        interactor.execute(inputData);

        // Verify nutrition values
        assertTrue(presenter.successViewCalled);
        assertEquals(500.0, presenter.outputData.getConsumedCalories());
        assertEquals(50.0, presenter.outputData.getConsumedCarbs());
        assertEquals(25.0, presenter.outputData.getConsumedProtein());
        assertEquals(20.0, presenter.outputData.getConsumedFat());
    }

    @Test
    void testUserWithEmptyMeals() {
        String username = "testUser";
        DashboardInputData inputData = new DashboardInputData(username);

        interactor.execute(inputData);

        assertTrue(presenter.successViewCalled);
        assertEquals(0.0, presenter.outputData.getConsumedCalories());
        assertEquals(0.0, presenter.outputData.getConsumedCarbs());
        assertEquals(0.0, presenter.outputData.getConsumedProtein());
        assertEquals(0.0, presenter.outputData.getConsumedFat());
    }

    @Test
    void testActivityLevelDisplay() {
        String username = "testUser";
        DashboardInputData inputData = new DashboardInputData(username);

        interactor.execute(inputData);

        assertTrue(presenter.successViewCalled);
        assertNotNull(presenter.outputData.getActivityLevel());
        assertTrue(presenter.outputData.getActivityLevel().contains("Sedentary"));
    }


}