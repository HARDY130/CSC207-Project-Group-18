package view;

import javax.swing.*;
import java.awt.*;

public class NutritionGraphPanel extends JPanel {
    private int carbsProgress = 30; // Percentage of carbs consumed
    private int proteinsProgress = 60; // Percentage of proteins consumed
    private int fatsProgress = 40; // Percentage of fats consumed

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = 100;

        drawProgressArc(g2d, centerX, centerY, radius, Color.GREEN, carbsProgress);
        drawProgressArc(g2d, centerX, centerY, radius - 20, Color.BLUE, proteinsProgress);
        drawProgressArc(g2d, centerX, centerY, radius - 40, Color.YELLOW, fatsProgress);

        g2d.setColor(Color.BLACK);
        g2d.drawString("Carbs (g): " + carbsProgress + " / 100", 10, 20);
        g2d.drawString("Proteins (g): " + proteinsProgress + " / 100", 10, 40);
        g2d.drawString("Fats (g): " + fatsProgress + " / 100", 10, 60);
    }

    private void drawProgressArc(Graphics2D g2d, int x, int y, int radius, Color color, int progress) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(10));
        g2d.drawArc(x - radius, y - radius, 2 * radius, 2 * radius, 90, -progress * 360 / 100);
    }
}
