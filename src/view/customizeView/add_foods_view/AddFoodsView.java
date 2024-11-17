package view.customizeView.add_foods_view;

import javax.swing.*;
import java.awt.event.KeyAdapter;

public class AddFoodsView extends JPanel {
    private JTextField textField;

    public AddFoodsView() {
        JLabel label = new JLabel("Type (B/L/S)");
        label.setSize(60,20);
        JTextField textField = new JTextField(20); //argument required
        JLabel label1 =new JLabel("Menu");
        label1.setSize(this.getWidth(), 20);
        JTextArea textArea1 = new JTextArea(10, 20);
        textArea1.setText("Food1: Cheese \nTime: 2024-11-17");
        textArea1.setEditable(false);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        JScrollPane scrollPane1 = new JScrollPane(textArea1);
        JButton button1 = new JButton("ADD");
        JTextArea textArea2 = new JTextArea(10, 20);
        textArea2.setText("Food2: The King of Cheese \nTime: 2024-11-17");
        textArea2.setEditable(false);
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        JButton button2 = new JButton("ADD");
        JTextArea textArea3 = new JTextArea(10, 20);
        textArea3.setText("Food3: Mum's Cheese \nTime: 2024-11-17");
        textArea3.setEditable(false);
        textArea3.setLineWrap(true);
        textArea3.setWrapStyleWord(true);
        JScrollPane scrollPane3 = new JScrollPane(textArea3);
        JButton button3 = new JButton("ADD");
        JButton button4 = new JButton("Ready to go");

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
}
