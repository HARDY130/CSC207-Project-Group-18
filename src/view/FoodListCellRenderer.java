package view;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import entity.Food;
import java.awt.Component;

//class FoodListCellRenderer extends JLabel implements ListCellRenderer<String> {
//    public FoodListCellRenderer() {
//        setOpaque(true);
//        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//    }
//
//    @Override
//    public Component getListCellRendererComponent(JList<? extends String> list,
//                                                  String value, int index, boolean isSelected, boolean cellHasFocus) {
//        setText("<html>" + value.replace("\n", "<br/>") + "</html>");
//
//        if (isSelected) {
//            setBackground(list.getSelectionBackground());
//            setForeground(list.getSelectionForeground());
//        } else {
//            setBackground(list.getBackground());
//            setForeground(list.getForeground());
//        }
//
//        return this;
//    }
//}

class FoodListCellRenderer extends JLabel implements ListCellRenderer<Food> {
    public FoodListCellRenderer() {
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Food> list,
                                                  Food food, int index, boolean isSelected, boolean cellHasFocus) {
        // Format the food item display
        String displayText =
            String.format("<html>%s<br/>Calories: %.0f kcal | Protein: %.1fg | Carbs: %.1fg | Fat: %.1fg</html>",
                food.getLabel(),
                food.getNutrients().getOrDefault("ENERC_KCAL", 0.0),
                food.getNutrients().getOrDefault("PROCNT", 0.0),
                food.getNutrients().getOrDefault("CHOCDF", 0.0),
                food.getNutrients().getOrDefault("FAT", 0.0));

        setText(displayText);

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}