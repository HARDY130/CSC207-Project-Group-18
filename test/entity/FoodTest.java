package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    private Food food;
    private Map<String, Double> nutrients;

    @BeforeEach
    void setUp() {
        nutrients = new HashMap<>();
        nutrients.put("ENERC_KCAL", 52.0);
        nutrients.put("CHOCDF", 14.0);
        nutrients.put("PROCNT", 0.3);
        nutrients.put("FAT", 0.2);

        food = new Food("F123", "Apple", "Fruit", nutrients);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("F123", food.getFoodId());
        assertEquals("Apple", food.getLabel());
        assertEquals("Fruit", food.getCategory());
        assertEquals(nutrients, food.getNutrients());
    }

    @Test
    void testSetters() {
        food.setFoodId("F124");
        food.setLabel("Banana");
        food.setCategory("Snack");

        Map<String, Double> newNutrients = new HashMap<>();
        newNutrients.put("ENERC_KCAL", 89.0);
        newNutrients.put("CHOCDF", 22.8);
        newNutrients.put("PROCNT", 1.1);
        newNutrients.put("FAT", 0.3);
        food.setNutrients(newNutrients);

        assertEquals("F124", food.getFoodId());
        assertEquals("Banana", food.getLabel());
        assertEquals("Snack", food.getCategory());
        assertEquals(newNutrients, food.getNutrients());
    }

    @Test
    void testToString() {
        String expectedString =
                "Food{foodId='F123', label='Apple', category='Fruit', " +
                        "nutrients={PROCNT=0.3, ENERC_KCAL=52.0, FAT=0.2, CHOCDF=14.0}}";
        assertEquals(expectedString, food.toString());
    }
}
