package use_case.meal_planner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import entity.CommonUser;
import entity.Food;
import entity.User;
import entity.MealType;
import entity.Allergy;

import java.time.LocalDate;
import java.util.*;

class MealPlannerInteractorTest {
    private TestMealPlannerPresenter presenter;
    private TestMealPlannerData mealPlannerData;
    private TestMealStorageData mealStorageData;
    private MealPlannerInteractor interactor;
    private CommonUser testUser;
    private Food testFood;

    @BeforeEach
    void init() {
        presenter = new TestMealPlannerPresenter();
        mealPlannerData = new TestMealPlannerData();
        mealStorageData = new TestMealStorageData();
        interactor = new MealPlannerInteractor(
                mealStorageData,
                mealPlannerData,
                presenter
        );

        // Create test user
        testUser = new CommonUser(
                "testUser",
                "password",
                LocalDate.now(),
                "Male",
                70,
                170,
                1.2,
                new HashSet<>()
        );

        // Create test food
        Map<String, Double> nutrients = new HashMap<>();
        nutrients.put("ENERC_KCAL", 200.0);
        nutrients.put("PROCNT", 20.0);
        nutrients.put("CHOCDF", 30.0);
        nutrients.put("FAT", 10.0);
        testFood = new Food("test-id", "Test Food", "Test Category", nutrients);
    }

    @Test
    void executeWhenUserNotFound() {
        String username = "nonexistent";
        Set<String> preferences = new HashSet<>();
        MealPlannerInputData input = new MealPlannerInputData(preferences, username);

        interactor.execute(input);

        assertTrue(presenter.failureCalled);
        assertEquals("User not found.", presenter.failureMessage);
        assertFalse(presenter.successCalled);
    }

    @Test
    void executeWhenUserExists() {
        String username = "testUser";
        Set<String> preferences = new HashSet<>(Arrays.asList("Mediterranean", "High-Protein"));
        MealPlannerInputData input = new MealPlannerInputData(preferences, username);
        mealStorageData.addUser(username);
        mealStorageData.setTestUser(testUser);

        Map<MealType, List<Food>> mealPlan = new EnumMap<>(MealType.class);
        mealPlan.put(MealType.BREAKFAST, Arrays.asList(testFood));
        mealPlan.put(MealType.LUNCH, Arrays.asList(testFood));
        mealPlan.put(MealType.DINNER, Arrays.asList(testFood));
        mealPlannerData.setTestMealPlan(mealPlan);

        interactor.execute(input);

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failureCalled);
        MealPlannerOutputData output = presenter.outputData;
        assertNotNull(output);
        assertEquals(username, output.getUsername());
        assertEquals(Arrays.asList(testFood), output.getBreakfastOptions());
        assertEquals(Arrays.asList(testFood), output.getLunchOptions());
        assertEquals(Arrays.asList(testFood), output.getDinnerOptions());
    }

    @Test
    void executeWithAPIError() {
        String username = "testUser";
        Set<String> preferences = new HashSet<>();
        MealPlannerInputData input = new MealPlannerInputData(preferences, username);
        mealStorageData.addUser(username);
        mealStorageData.setTestUser(testUser);
        mealPlannerData.shouldError = true;

        interactor.execute(input);

        assertTrue(presenter.failureCalled);
        assertEquals("API Error", presenter.failureMessage);
        assertFalse(presenter.successCalled);
    }

    @Test
    void executeWithNullCurrentUsername() {
        String username = "testUser";
        Set<String> preferences = new HashSet<>();
        MealPlannerInputData input = new MealPlannerInputData(preferences, username);
        mealStorageData.setCurrentUsername(null);
        mealStorageData.forceUserNotFound = true;

        interactor.execute(input);

        assertTrue(presenter.failureCalled);
        assertEquals("User not found.", presenter.failureMessage);
        assertFalse(presenter.successCalled);
    }

    @Test
    void executeWithEmptyDietaryPreferences() {
        String username = "testUser";
        Set<String> preferences = new HashSet<>();
        MealPlannerInputData input = new MealPlannerInputData(preferences, username);
        mealStorageData.addUser(username);
        mealStorageData.setTestUser(testUser);

        Map<MealType, List<Food>> emptyMealPlan = new EnumMap<>(MealType.class);
        for (MealType type : MealType.values()) {
            emptyMealPlan.put(type, new ArrayList<>());
        }
        mealPlannerData.setTestMealPlan(emptyMealPlan);

        interactor.execute(input);

        assertTrue(presenter.successCalled);
        assertNotNull(presenter.outputData);
        assertTrue(presenter.outputData.getBreakfastOptions().isEmpty());
    }

    @Test
    void testAddMealToUser() {
        String username = "testUser";
        mealStorageData.addUser(username);

        interactor.addMealToUser(username, "BREAKFAST", testFood);

        assertTrue(mealStorageData.mealAdded);
        assertEquals(testFood, mealStorageData.lastAddedFood);
        assertEquals("BREAKFAST", mealStorageData.lastAddedMealType);
    }

    @Test
    void testAddMealToUserWithError() {
        String username = "testUser";
        mealStorageData.shouldThrowError = true;

        interactor.addMealToUser(username, "BREAKFAST", testFood);

        assertTrue(presenter.failureCalled);
        assertEquals("Error adding meal", presenter.failureMessage);
    }

    @Test
    void testNavigateHome() {
        interactor.goHome();
        assertTrue(presenter.homeNavigationCalled);
    }

    @Test
    void executeWhenUserExistsButNotFoundInStorage() {
        String username = "testUser";
        Set<String> preferences = new HashSet<>();
        MealPlannerInputData input = new MealPlannerInputData(preferences, username);
        mealStorageData.addUser(username);
        mealStorageData.setTestUser(testUser);
        mealStorageData.forceUserNotFound = true; // Force user not found in storage

        interactor.execute(input);

        assertTrue(presenter.failureCalled);
        assertEquals("User not found.", presenter.failureMessage);
        assertFalse(presenter.successCalled);
    }
}

class TestMealPlannerPresenter implements MealPlannerOutputBoundary {
    boolean successCalled = false;
    boolean failureCalled = false;
    boolean homeNavigationCalled = false;
    String failureMessage;
    MealPlannerOutputData outputData;

    @Override
    public void prepareSuccessView(MealPlannerOutputData data) {
        successCalled = true;
        this.outputData = data;
    }

    @Override
    public void prepareFailView(String error) {
        failureCalled = true;
        failureMessage = error;
    }

    @Override
    public void backToDashboard() {
        homeNavigationCalled = true;
    }
}

class TestMealPlannerData implements MealPlannerDataAccessInterface {
    boolean shouldError = false;
    private Map<MealType, List<Food>> testMealPlan = new EnumMap<>(MealType.class);

    void setTestMealPlan(Map<MealType, List<Food>> mealPlan) {
        this.testMealPlan = mealPlan;
    }

    @Override
    public Map<MealType, List<Food>> generateMealPlan(CommonUser user, List<String> selectedDiets) throws Exception {
        if (shouldError) {
            throw new Exception("API Error");
        }
        return testMealPlan;
    }

    @Override
    public List<Food> generateMealOptions(Set<String> dietaryPreferences, String mealType) {
        return testMealPlan.getOrDefault(MealType.valueOf(mealType), new ArrayList<>());
    }

    @Override
    public boolean existsByName(String username) {
        return true;
    }
}

class TestMealStorageData implements MealStorageDataAccessInterface {
    private final Set<String> users = new HashSet<>();
    private CommonUser testUser;
    boolean mealAdded = false;
    boolean shouldThrowError = false;
    boolean forceUserNotFound = false;
    Food lastAddedFood;
    String lastAddedMealType;
    private String currentUsername;

    void addUser(String username) {
        users.add(username);
    }

    void setTestUser(CommonUser user) {
        this.testUser = user;
    }

    void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    @Override
    public void save(User user) {}

    @Override
    public User get(String username) {
        return testUser;
    }

    @Override
    public boolean existsByName(String username) {
        if (forceUserNotFound) {
            return false;
        }
        return users.contains(username);
    }

    @Override
    public void addMealToUser(String username, String mealType, Food food) {
        if (shouldThrowError) {
            throw new RuntimeException("Error adding meal");
        }
        mealAdded = true;
        lastAddedFood = food;
        lastAddedMealType = mealType;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }
}