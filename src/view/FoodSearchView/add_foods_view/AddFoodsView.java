package view.FoodSearchView.add_foods_view;

import entity.Food;
import interface_adapter.customize.SearchFoodState;
import use_case.customize.Constant;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class AddFoodsView extends JPanel implements PropertyChangeListener {
    private final JTextField textField;
    private final JTextArea textArea1;
    private final JTextArea textArea2;
    private final JTextArea textArea3;
    private Food[] food_in_box;
    private cacheMap added;

    public AddFoodsView() {
        this.food_in_box = new Food[Constant.LENGTHOFFOODS];
        this.added = new cacheMap();

        JLabel label = new JLabel("Type (B/L/S)");
        label.setSize(60,20);
        this.textField = new JTextField(20); //argument required
        JLabel label1 =new JLabel("Menu");
        label1.setSize(this.getWidth(), 20);
        this.textArea1 = new JTextArea(10, 20);
        textArea1.setText("Food1: Cheese \nTime: 2024-11-17");
        textArea1.setEditable(false);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        JScrollPane scrollPane1 = new JScrollPane(textArea1);
        JButton button1 = new JButton("ADD");
        this.textArea2 = new JTextArea(10, 20);
        textArea2.setText("Food2: The King of Cheese \nTime: 2024-11-17");
        textArea2.setEditable(false);
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        JButton button2 = new JButton("ADD");
        this.textArea3 = new JTextArea(10, 20);
        textArea3.setText("Food3: Mum's Cheese \nTime: 2024-11-17");
        textArea3.setEditable(false);
        textArea3.setLineWrap(true);
        textArea3.setWrapStyleWord(true);
        JScrollPane scrollPane3 = new JScrollPane(textArea3);
        JButton button3 = new JButton("ADD");
        JButton button4 = new JButton("Ready to go");

        button1.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                addintolist(food_in_box[0]);
            }
        });

        button2.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                addintolist(food_in_box[1]);
            }
        });

        button3.addActionListener(new ActionListener() {

            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                addintolist(food_in_box[2]);
            }
        });

        button4.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(added.getMap().toString());
                added = new cacheMap();
            }
        });

        this.add(label);
        this.add(textField);
        this.add(label1);
        this.add(scrollPane1);
        this.add(button1);
        this.add(scrollPane2);
        this.add(button2);
        this.add(scrollPane3);
        this.add(button3);
        this.add(button4);
    }

    public String getTextFromTextField() {
        return this.textField.getText();
    }

    public void addintolist(Food food) {
        String mealtype = getTextFromTextField();
        if (mealtype.equals("B")) {
            added.addFoodinB(food);
        }
        else if (mealtype.equals("L")) {
            added.addFoodinL(food);
        }
        else if (mealtype.equals("S")) {
            added.addFoodinS(food);
        }
    }

    public void setTextArea(String text, int idx) {
        if (idx == 0) {
            this.textArea1.setText(text);
        }
        else if (idx == 1) {
            this.textArea2.setText(text);
        }
        else if (idx == 2) {
            this.textArea3.setText(text);
        }
    }


    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SearchFoodState searchFoodState = (SearchFoodState) evt.getNewValue();
        final Food[] foods = searchFoodState.getFoods();
        this.food_in_box = foods;
        System.out.println(Arrays.toString(foods));
        for (int i = 0; i < Constant.LENGTHOFFOODS; i++) {
            if (foods[i] != null) {
                String food = "1. Your option is " + foods[i].getLabel() + "." + "\n" + "2. Its category: "
                        + foods[i].getCategory() + "." + "\n" + "3. Its nutrients are " + foods[i].getNutrients() + ".";
                setTextArea(food, i);
            }
        }
    }
}

class cacheMap {

    private Map<String, List<Food>> map;

    public cacheMap() {
        this.map = new HashMap<>();
        map.put("B", new ArrayList<>());
        map.put("L", new ArrayList<>());
        map.put("S", new ArrayList<>());
    }

    public void addFoodinB (Food f) {
        if (f != null) {
            map.get("B").add(f);
        }
    }

    public void addFoodinL (Food f) {
        if (f != null) {
            map.get("L").add(f);
        }
    }

    public void addFoodinS (Food f) {
        if (f != null) {
            map.get("S").add(f);
        }
    }

    public Map<String, List<Food>> getMap() {
        return map;
    }
}
