package use_case.info_collection;

import entity.Allergy;
import entity.CommonUser;
import entity.CommonUserFactory;
import data_access.InMemoryUserDataAccessObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import entity.User;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InfoCollectionInteractorTest {
    private InMemoryUserDataAccessObject userDataAccessObject;
    private InfoCollectionInteractor interactor;
    private Set<Allergy> allergies;
    private TestSuccessPresenter successPresenter;
    private TestFailurePresenter failurePresenter;

    // Create presenter classes rather than anonymous classes to handle all methods
    private static class TestSuccessPresenter implements InfoCollectionOutputBoundary {
        private boolean successCalled = false;
        private InfoCollectionOutputData outputData;

        @Override
        public void prepareSuccessView(InfoCollectionOutputData data) {
            this.successCalled = true;
            this.outputData = data;
        }

        @Override
        public void prepareFailView(String error) {
            fail("Use case failure is unexpected: " + error);
        }

        public boolean isSuccessCalled() {
            return successCalled;
        }

        public InfoCollectionOutputData getOutputData() {
            return outputData;
        }
    }

    private static class TestFailurePresenter implements InfoCollectionOutputBoundary {
        private boolean failureCalled = false;
        private String errorMessage;
        private final String expectedError;

        public TestFailurePresenter(String expectedError) {
            this.expectedError = expectedError;
        }

        @Override
        public void prepareSuccessView(InfoCollectionOutputData data) {
            fail("Expected failure but got success");
        }

        @Override
        public void prepareFailView(String error) {
            this.failureCalled = true;
            this.errorMessage = error;
            assertEquals(expectedError, error);
        }

        public boolean isFailureCalled() {
            return failureCalled;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    @BeforeEach
    void setUp() {
        userDataAccessObject = new InMemoryUserDataAccessObject();
        allergies = new HashSet<>();
        successPresenter = new TestSuccessPresenter();
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            successPresenter,
            new CommonUserFactory()
        );
    }

    @Test
    void testSuccessfulInfoCollection() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String username = "testUser";
        String password = "password123";
        String gender = "Male";
        int weight = 70;
        int height = 175;
        double activityMultiplier = 1.2;

        InfoCollectionInputData inputData = new InfoCollectionInputData(
            username, password, birthDate, gender, weight, height, activityMultiplier, allergies
        );

        interactor.execute(inputData);

        assertTrue(successPresenter.isSuccessCalled());
        InfoCollectionOutputData outputData = successPresenter.getOutputData();
        assertNotNull(outputData);

        // Verify user was saved in DAO
        assertTrue(userDataAccessObject.existsByName(username));
        CommonUser savedUser = (CommonUser) userDataAccessObject.get(username);

        // Verify all user data
        assertEquals(birthDate, savedUser.getBirthDate());
        assertEquals(gender, savedUser.getGender());
        assertEquals(weight, savedUser.getWeight());
        assertEquals(height, savedUser.getHeight());
        assertEquals(activityMultiplier, savedUser.getActivityMultiplier(), 0.001);

        // Verify output data
        assertEquals(username, outputData.getUsername());
//        assertTrue(outputData.isUseCaseSuccess());
        assertTrue(outputData.getCalculatedBMR() > 0);
        assertTrue(outputData.getCalculatedTDEE() > 0);
    }


    @Test
    void testExistingUserWithWrongPassword() {
        // First create a user
        String username = "existingUser";
        String correctPassword = "correctpass";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);

        InfoCollectionInputData createData = new InfoCollectionInputData(
            username, correctPassword, birthDate, "Male", 70, 175, 1.2, allergies
        );
        interactor.execute(createData);
        assertTrue(userDataAccessObject.existsByName(username));

        // Try to modify with wrong password
        failurePresenter = new TestFailurePresenter("Incorrect password for existing user.");
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            failurePresenter,
            new CommonUserFactory()
        );

        InfoCollectionInputData wrongPasswordData = new InfoCollectionInputData(
            username, "wrongpass", birthDate, "Male", 75, 175, 1.2, allergies
        );

        interactor.execute(wrongPasswordData);

        assertTrue(failurePresenter.isFailureCalled());

        // Verify original user data wasn't changed
        CommonUser user = (CommonUser) userDataAccessObject.get(username);
        assertEquals(70, user.getWeight());
    }

    @Test
    void testHandlesDatabaseException() {
        // Set up DAO to throw an exception
        userDataAccessObject = new InMemoryUserDataAccessObject() {
            @Override
            public void save(User user) {
                throw new RuntimeException("Database error");
            }
        };

        failurePresenter = new TestFailurePresenter("Database error");
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            failurePresenter,
            new CommonUserFactory()
        );

        InfoCollectionInputData inputData = new InfoCollectionInputData(
            "testUser", "password", LocalDate.of(1990, 1, 1),
            "Male", 70, 175, 1.2, allergies
        );

        interactor.execute(inputData);
        assertTrue(failurePresenter.isFailureCalled());
        assertEquals("Database error", failurePresenter.getErrorMessage());
    }

    private void testValidation(InfoCollectionInputData inputData, boolean shouldSucceed) {
        TestSuccessPresenter successPresenter = new TestSuccessPresenter();
        TestFailurePresenter failurePresenter = new TestFailurePresenter(null);

        InfoCollectionInteractor testInteractor = new InfoCollectionInteractor(
            userDataAccessObject,
            shouldSucceed ? successPresenter : failurePresenter,
            new CommonUserFactory()
        );

        testInteractor.execute(inputData);

        if (shouldSucceed) {
            assertTrue(successPresenter.isSuccessCalled());
        } else {
            assertTrue(failurePresenter.isFailureCalled());
        }
    }

    private InfoCollectionInputData createInputData(int height, int weight) {
        return new InfoCollectionInputData(
            "testUser", "password", LocalDate.of(1990, 1, 1),
            "Male", weight, height, 1.2, allergies
        );
    }

    @Test
    void testAgeExactlyThirteen() {
        LocalDate thirteenYearsOld = LocalDate.now().minusYears(13);
        InfoCollectionInputData inputData = new InfoCollectionInputData(
            "testUser", "password", thirteenYearsOld,
            "Male", 70, 175, 1.2, allergies
        );

        interactor.execute(inputData);
        assertTrue(successPresenter.isSuccessCalled());
    }

    @Test
    void testAgeExactly120() {
        LocalDate oneHundredTwentyYearsOld = LocalDate.now().minusYears(120);
        InfoCollectionInputData inputData = new InfoCollectionInputData(
            "testUser", "password", oneHundredTwentyYearsOld,
            "Male", 70, 175, 1.2, allergies
        );

        interactor.execute(inputData);
        assertTrue(successPresenter.isSuccessCalled());
    }

    @Test
    void testWeightExactly30() {
        InfoCollectionInputData inputData = new InfoCollectionInputData(
            "testUser", "password", LocalDate.of(1990, 1, 1),
            "Male", 30, 175, 1.2, allergies
        );

        interactor.execute(inputData);
        assertTrue(successPresenter.isSuccessCalled());
    }

    @Test
    void testWeightExactly300() {
        InfoCollectionInputData inputData = new InfoCollectionInputData(
            "testUser", "password", LocalDate.of(1990, 1, 1),
            "Male", 300, 175, 1.2, allergies
        );

        interactor.execute(inputData);
        assertTrue(successPresenter.isSuccessCalled());
    }

    @Test
    void testHeightExactly100() {
        InfoCollectionInputData inputData = new InfoCollectionInputData(
            "testUser", "password", LocalDate.of(1990, 1, 1),
            "Male", 70, 100, 1.2, allergies
        );

        interactor.execute(inputData);
        assertTrue(successPresenter.isSuccessCalled());
    }

    @Test
    void testHeightExactly250() {
        InfoCollectionInputData inputData = new InfoCollectionInputData(
            "testUser", "password", LocalDate.of(1990, 1, 1),
            "Male", 70, 250, 1.2, allergies
        );

        interactor.execute(inputData);
        assertTrue(successPresenter.isSuccessCalled());
    }

    @Test
    void testInvalidAgeRanges() {
        // Test too young
        LocalDate tooYoung = LocalDate.now().minusYears(12);
        InfoCollectionInputData youngData = new InfoCollectionInputData(
            "testUser", "password", tooYoung,
            "Male", 70, 175, 1.2, allergies
        );

        failurePresenter = new TestFailurePresenter("Invalid age. Must be between 13 and 120 years old.");
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            failurePresenter,
            new CommonUserFactory()
        );

        interactor.execute(youngData);
        assertTrue(failurePresenter.isFailureCalled());

        // Test too old
        LocalDate tooOld = LocalDate.now().minusYears(121);
        InfoCollectionInputData oldData = new InfoCollectionInputData(
            "testUser", "password", tooOld,
            "Male", 70, 175, 1.2, allergies
        );

        failurePresenter = new TestFailurePresenter("Invalid age. Must be between 13 and 120 years old.");
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            failurePresenter,
            new CommonUserFactory()
        );

        interactor.execute(oldData);
        assertTrue(failurePresenter.isFailureCalled());
    }

    @Test
    void testInvalidWeightRanges() {
        // Test too light
        InfoCollectionInputData lightData = new InfoCollectionInputData(
            "testUser", "password", LocalDate.of(1990, 1, 1),
            "Male", 29, 175, 1.2, allergies
        );

        failurePresenter = new TestFailurePresenter("Invalid weight. Must be between 30 and 300 kg.");
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            failurePresenter,
            new CommonUserFactory()
        );

        interactor.execute(lightData);
        assertTrue(failurePresenter.isFailureCalled());

        // Test too heavy
        InfoCollectionInputData heavyData = new InfoCollectionInputData(
            "testUser", "password", LocalDate.of(1990, 1, 1),
            "Male", 301, 175, 1.2, allergies
        );

        failurePresenter = new TestFailurePresenter("Invalid weight. Must be between 30 and 300 kg.");
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            failurePresenter,
            new CommonUserFactory()
        );

        interactor.execute(heavyData);
        assertTrue(failurePresenter.isFailureCalled());
    }

    @Test
    void testInvalidHeightRanges() {
        InfoCollectionInputData shortData = new InfoCollectionInputData(
            "testUser", "password", LocalDate.of(1990, 1, 1),
            "Male", 70, 99, 1.2, allergies
        );

        failurePresenter = new TestFailurePresenter("Invalid height. Must be between 100 and 250 cm.");
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            failurePresenter,
            new CommonUserFactory()
        );

        interactor.execute(shortData);
        assertTrue(failurePresenter.isFailureCalled());

        InfoCollectionInputData tallData = new InfoCollectionInputData(
            "testUser", "password", LocalDate.of(1990, 1, 1),
            "Male", 70, 251, 1.2, allergies
        );

        failurePresenter = new TestFailurePresenter("Invalid height. Must be between 100 and 250 cm.");
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            failurePresenter,
            new CommonUserFactory()
        );

        interactor.execute(tallData);
        assertTrue(failurePresenter.isFailureCalled());
    }


    @Test
    void testInvalidGender() {
        InfoCollectionInputData invalidGenderData = new InfoCollectionInputData(
            "testUser", "password", LocalDate.of(1990, 1, 1),
            "NonBinary", 70, 175, 1.2, allergies // Changed from "Other" to any non-Male/Female value
        );

        failurePresenter = new TestFailurePresenter("Gender must be either 'Male' or 'Female'.");
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            failurePresenter,
            new CommonUserFactory()
        );

        interactor.execute(invalidGenderData);
        assertTrue(failurePresenter.isFailureCalled());
    }

    @Test
    void testExistingUserPasswordNull() {
        // Create test user with null password
        User testUser = new CommonUser("testUser", null,
            LocalDate.of(1990, 1, 1), "Male", 70, 175, 1.2, new HashSet<>());
        userDataAccessObject.save(testUser);

        InfoCollectionInputData inputData = new InfoCollectionInputData(
            "testUser", "password",
            LocalDate.of(1990, 1, 1),
            "Male", 70, 175, 1.2, allergies
        );

        failurePresenter = new TestFailurePresenter(
            "Cannot invoke \"String.equals(Object)\" because the return value of \"entity.User.getPassword()\" is null"
        );
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            failurePresenter,
            new CommonUserFactory()
        );

        interactor.execute(inputData);
        assertTrue(failurePresenter.isFailureCalled());
    }

    @Test
    void testExistingUserWithNullPassword() {
        userDataAccessObject = new InMemoryUserDataAccessObject() {
            @Override
            public User get(String username) {
                return new User() {
                    @Override
                    public String getName() {
                        return "testUser";
                    }

                    @Override
                    public String getPassword() {
                        return null;
                    }
                };
            }

            @Override
            public boolean existsByName(String identifier) {
                return true;
            }
        };

        InfoCollectionInputData inputData = new InfoCollectionInputData(
            "testUser", "password",
            LocalDate.of(1990, 1, 1),
            "Male", 70, 175, 1.2, allergies
        );

        failurePresenter = new TestFailurePresenter(
            "Cannot invoke \"String.equals(Object)\" because the return value of \"entity.User.getPassword()\" is null"
        );
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            failurePresenter,
            new CommonUserFactory()
        );

        interactor.execute(inputData);
        assertTrue(failurePresenter.isFailureCalled());
    }

    @Test
    void testExistingUserWithMatchingPassword() {
        User testUser = new CommonUser("testUser", "correctPassword",
            LocalDate.of(1990, 1, 1), "Male", 70, 175, 1.2, new HashSet<>());
        userDataAccessObject.save(testUser);

        InfoCollectionInputData inputData = new InfoCollectionInputData(
            "testUser", "correctPassword",
            LocalDate.of(1990, 1, 1),
            "Male", 70, 175, 1.2, allergies
        );

        successPresenter = new TestSuccessPresenter();
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            successPresenter,
            new CommonUserFactory()
        );

        interactor.execute(inputData);
        assertTrue(successPresenter.isSuccessCalled());
    }

    @Test
    void testValidGenderValues() {
        InfoCollectionInputData maleData = new InfoCollectionInputData(
            "testUser", "password",
            LocalDate.of(1990, 1, 1),
            "Male", 70, 175, 1.2, allergies
        );

        successPresenter = new TestSuccessPresenter();
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            successPresenter,
            new CommonUserFactory()
        );

        interactor.execute(maleData);
        assertTrue(successPresenter.isSuccessCalled());

        InfoCollectionInputData femaleData = new InfoCollectionInputData(
            "testUser2", "password",
            LocalDate.of(1990, 1, 1),
            "Female", 70, 175, 1.2, allergies
        );

        successPresenter = new TestSuccessPresenter();
        interactor = new InfoCollectionInteractor(
            userDataAccessObject,
            successPresenter,
            new CommonUserFactory()
        );

        interactor.execute(femaleData);
        assertTrue(successPresenter.isSuccessCalled());
    }

    @Test
    void testExistingUserIsNullButExistsInDAO() {
        InfoCollectionUserDataAccessInterface testUserDAO = new InfoCollectionUserDataAccessInterface() {
            @Override
            public boolean existsByName(String identifier) {
                return true;
            }

            @Override
            public User get(String username) {
                return null;
            }

            @Override
            public void save(User user) {
            }
        };

        InfoCollectionInputData inputData = new InfoCollectionInputData(
            "testUser", "password",
            LocalDate.of(1990, 1, 1),
            "Male", 70, 175, 1.2, allergies
        );

        failurePresenter = new TestFailurePresenter("User data error");

        interactor = new InfoCollectionInteractor(
            testUserDAO,
            failurePresenter,
            new CommonUserFactory()
        );

        interactor.execute(inputData);
        assertTrue(failurePresenter.isFailureCalled());
    }
}

