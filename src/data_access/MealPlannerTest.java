//package data_access;
////
////import data_access.MealPlannerDataAccessObject;
////import entity.Allergy;
////import entity.CommonUser;
////import entity.Food;
////import entity.MealType;
////
////import java.time.LocalDate;
////import java.util.*;
////
////public class MealPlannerTest {
////    public static void main(String[] args) {
////        // Create test user with some sample data
////        CommonUser testUser = createTestUser();
////
////        // Create instance of DAO
////        MealPlannerDataAccessObject mealPlannerDAO = new MealPlannerDataAccessObject();
////
////        // Test diet preferences
////        List<String> selectedDiets = Arrays.asList("balanced", "high-protein");
////
////        try {
////            // Generate meal plan
////            Map<MealType, List<Food>> mealPlan = mealPlannerDAO.generateMealPlan(testUser, selectedDiets);
////
////            // Print results
////            System.out.println("=== Generated Meal Plan ===\n");
////
////            for (MealType mealType : MealType.values()) {
////                System.out.println("--- " + mealType + " Options ---");
////                List<Food> foods = mealPlan.get(mealType);
////                if (foods != null) {
////                    for (Food food : foods) {
////                        printFoodDetails(food);
////                    }
////                }
////                System.out.println();
////            }
////
////        } catch (Exception e) {
////            System.err.println("Error generating meal plan: " + e.getMessage());
////            e.printStackTrace();
////        }
////    }
////
////    private static CommonUser createTestUser() {
////        // Create user with some sample data
////        Set<Allergy> allergies = new HashSet<>();
////        allergies.add(Allergy.PEANUT);
////        allergies.add(Allergy.DAIRY);
////
////        return new CommonUser(
////                "testUser",           // name
////                "password",          // password
////                LocalDate.of(1990, 1, 1), // birthDate
////                "Male",              // gender
////                70,                  // weight in kg
////                175,                // height in cm
////                CommonUser.MODERATELY_ACTIVE, // activity level
////                allergies           // allergies
////        );
////    }
////
////    private static void printFoodDetails(Food food) {
////        System.out.println("\nFood: " + food.getLabel());
////        System.out.println("Category: " + food.getCategory());
////        System.out.println("Nutrients:");
////
////        Map<String, Double> nutrients = food.getNutrients();
////        System.out.printf("  Calories: %.1f kcal\n", nutrients.get("ENERC_KCAL"));
////        System.out.printf("  Protein: %.1f g\n", nutrients.get("PROCNT"));
////        System.out.printf("  Fat: %.1f g\n", nutrients.get("FAT"));
////        System.out.printf("  Carbs: %.1f g\n", nutrients.get("CHOCDF"));
////
////        if (nutrients.containsKey("FIBTG")) {
////            System.out.printf("  Fiber: %.1f g\n", nutrients.get("FIBTG"));
////        }
////
////        System.out.println("------------------------");
////    }
////}
//
////import data_access.MealPlannerDataAccessObject;
////import entity.*;
////import java.time.LocalDate;
////import java.util.*;
////
////public class MealPlannerTest {
////    public static void main(String[] args) {
////        try {
////            // Create test user
////            CommonUser user = createTestUser();
////
////            // Create DAO
////            MealPlannerDataAccessObject mealPlannerDAO = new MealPlannerDataAccessObject();
////
////            // Test with some diet preferences
////            List<String> selectedDiets = Arrays.asList("balanced", "high-protein");
////
////            System.out.println("Generating meal plan for test user...");
////            System.out.println("User TDEE: " + user.calculateTDEE());
////            System.out.println("Selected diets: " + selectedDiets);
////            System.out.println("Allergies: " + user.getAllergies());
////
////            Map<MealType, List<Food>> mealPlan = mealPlannerDAO.generateMealPlan(user, selectedDiets);
////
////            // Print results
////            System.out.println("\n=== Generated Meal Plan ===\n");
////            for (MealType type : MealType.values()) {
////                System.out.println("=== " + type + " ===");
////                List<Food> foods = mealPlan.get(type);
////                if (foods != null) {
////                    for (Food food : foods) {
////                        printFoodDetails(food);
////                    }
////                }
////                System.out.println();
////            }
////
////        } catch (Exception e) {
////            System.err.println("Error: " + e.getMessage());
////            e.printStackTrace();
////        }
////    }
////
////    private static CommonUser createTestUser() {
////        Set<Allergy> allergies = new HashSet<>();
////        allergies.add(Allergy.PEANUT);
////        allergies.add(Allergy.DAIRY);
////
////        return new CommonUser(
////                "testUser",
////                "password",
////                LocalDate.of(1990, 1, 1),
////                "Male",
////                70,  // 70kg
////                175, // 175cm
////                CommonUser.MODERATELY_ACTIVE,
////                allergies
////        );
////    }
////
////    private static void printFoodDetails(Food food) {
////        System.out.println("\nFood: " + food.getLabel());
////        System.out.println("Category: " + food.getCategory());
////
////        Map<String, Double> nutrients = food.getNutrients();
////        System.out.printf("Calories: %.1f kcal\n", nutrients.getOrDefault("ENERC_KCAL", 0.0));
////        System.out.printf("Protein: %.1f g\n", nutrients.getOrDefault("PROCNT", 0.0));
////        System.out.printf("Fat: %.1f g\n", nutrients.getOrDefault("FAT", 0.0));
////        System.out.printf("Carbs: %.1f g\n", nutrients.getOrDefault("CHOCDF", 0.0));
////        System.out.println("------------------------");
////    }
////}
//
//
////package data_access;
//
//import entity.*;
//import java.time.LocalDate;
//import java.util.*;
//
//public class MealPlannerTest {
//    public static void main(String[] args) {
//        try {
//            // Create test user
//            CommonUser user = createTestUser();
//
//            // Create DAO
//            MealPlannerDataAccessObject mealPlannerDAO = new MealPlannerDataAccessObject();
//
//            // Test with some diet preferences (using proper format from documentation)
//            List<String> selectedDiets = Arrays.asList("MEDITERRANEAN", "HIGH_PROTEIN");
//
//            System.out.println("=== Test Configuration ===");
//            System.out.println("User TDEE: " + user.calculateTDEE());
//            System.out.println("Selected diets: " + selectedDiets);
//            System.out.println("Allergies: " + user.getAllergies());
//            System.out.println("\nGenerating meal plan...\n");
//
//            Map<MealType, List<Food>> mealPlan = mealPlannerDAO.generateMealPlan(user, selectedDiets);
//
//            // Print results
//            System.out.println("\n=== Generated Meal Plan ===\n");
//            for (MealType type : MealType.values()) {
//                System.out.println("=== " + type + " ===");
//                List<Food> foods = mealPlan.get(type);
//                if (foods != null && !foods.isEmpty()) {
//                    for (Food food : foods) {
//                        printFoodDetails(food);
//                    }
//                } else {
//                    System.out.println("No meals generated for this type");
//                }
//                System.out.println();
//            }
//
//        } catch (Exception e) {
//            System.err.println("Error: " + e.getMessage());
//            System.err.println("\nStack trace:");
//            e.printStackTrace();
//        }
//    }
//
//    private static CommonUser createTestUser() {
//        Set<Allergy> allergies = new HashSet<>();
//        allergies.add(Allergy.PEANUT);
//        allergies.add(Allergy.DAIRY);
//
//        return new CommonUser(
//                "testUser",
//                "password",
//                LocalDate.of(1990, 1, 1),
//                "Male",
//                70,  // 70kg
//                175, // 175cm
//                CommonUser.MODERATELY_ACTIVE,
//                allergies
//        );
//    }
//
//    private static void printFoodDetails(Food food) {
//        System.out.println("\nFood: " + food.getLabel());
//        System.out.println("Category: " + food.getCategory());
//
//        Map<String, Double> nutrients = food.getNutrients();
//        System.out.printf("Calories: %.1f kcal\n", nutrients.getOrDefault("ENERC_KCAL", 0.0));
//        System.out.printf("Protein: %.1f g\n", nutrients.getOrDefault("PROCNT", 0.0));
//        System.out.printf("Fat: %.1f g\n", nutrients.getOrDefault("FAT", 0.0));
//        System.out.printf("Carbs: %.1f g\n", nutrients.getOrDefault("CHOCDF", 0.0));
//        System.out.println("------------------------");
//    }
//}