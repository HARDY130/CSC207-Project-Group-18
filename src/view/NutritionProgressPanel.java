package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

public class NutritionProgressPanel extends JPanel {
    private final ProgressRing calorieRing;
    private final ProgressRing carbsRing;
    private final ProgressRing proteinRing;
    private final ProgressRing fatRing;

    public NutritionProgressPanel() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(800, 400));

        // Initialize progress rings with different colors
        calorieRing = new ProgressRing(new Color(64, 169, 255), 180);  // Blue
        carbsRing = new ProgressRing(new Color(82, 196, 26), 120);    // Green
        proteinRing = new ProgressRing(new Color(250, 173, 20), 120); // Orange
        fatRing = new ProgressRing(new Color(245, 34, 45), 120);      // Red

        layoutComponents();
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);

        // Place large calorie ring at top
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(calorieRing, gbc);

        // Place macro rings in a row below
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(30, 20, 10, 20);  // Add more space between rows

        gbc.gridx = 0;
        add(carbsRing, gbc);

        gbc.gridx = 1;
        add(proteinRing, gbc);

        gbc.gridx = 2;
        add(fatRing, gbc);
    }

    public void updateProgress(int caloriePercent, int carbsPercent,
                               int proteinPercent, int fatPercent,
                               String calorieText, String carbsText,
                               String proteinText, String fatText) {
        calorieRing.updateProgress(caloriePercent, "Calories", calorieText);
        carbsRing.updateProgress(carbsPercent, "Carbs", carbsText);
        proteinRing.updateProgress(proteinPercent, "Protein", proteinText);
        fatRing.updateProgress(fatPercent, "Fat", fatText);
        repaint();
    }

    private static class ProgressRing extends JPanel {
        private final Color ringColor;
        private final Timer animationTimer;
        private int currentProgress = 0;
        private int targetProgress = 0;
        private String label = "";
        private String value = "";
        private final int size;
        private static final int ANIMATION_STEPS = 20;
        private static final int ANIMATION_DELAY = 20;

        public ProgressRing(Color color, int size) {
            this.ringColor = color;
            this.size = size;
            setPreferredSize(new Dimension(size, size));
            setOpaque(false);

            animationTimer = new Timer(ANIMATION_DELAY, e -> {
                if (currentProgress != targetProgress) {
                    if (Math.abs(currentProgress - targetProgress) <= ANIMATION_STEPS) {
                        currentProgress = targetProgress;
                    } else {
                        currentProgress += (targetProgress > currentProgress) ?
                                ANIMATION_STEPS : -ANIMATION_STEPS;
                    }
                    repaint();
                }
                if (currentProgress == targetProgress) {
                    ((Timer)e.getSource()).stop();
                }
            });
        }

        public void updateProgress(int newProgress, String newLabel, String newValue) {
            this.targetProgress = newProgress;
            this.label = newLabel;
            this.value = newValue;

            if (!animationTimer.isRunning()) {
                animationTimer.start();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            configureGraphics(g2d);

            // Calculate dimensions
            int padding = 10;
            int diameter = Math.min(getWidth(), getHeight()) - (padding * 2);
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;
            int strokeWidth = diameter / 10;

            // Draw shadow
            drawShadow(g2d, x, y, diameter);

            // Draw background circle
            drawBackgroundCircle(g2d, x, y, diameter, strokeWidth);

            // Draw progress arc
            drawProgressArc(g2d, x, y, diameter, strokeWidth);

            // Draw center circle
            drawCenterCircle(g2d, x, y, diameter);

            // Draw text
            drawText(g2d, x, y, diameter);
        }

        private void configureGraphics(Graphics2D g2d) {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        }

        private void drawShadow(Graphics2D g2d, int x, int y, int diameter) {
            int shadowSize = 4;
            for (int i = shadowSize; i > 0; i--) {
                float alpha = 0.2f / i;
                g2d.setColor(new Color(0f, 0f, 0f, alpha));
                g2d.fill(new Ellipse2D.Double(x - i, y - i, diameter + (2 * i), diameter + (2 * i)));
            }
        }

        private void drawBackgroundCircle(Graphics2D g2d, int x, int y, int diameter, int strokeWidth) {
            g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setColor(new Color(230, 230, 230));
            g2d.draw(new Arc2D.Double(
                    x + strokeWidth/2,
                    y + strokeWidth/2,
                    diameter - strokeWidth,
                    diameter - strokeWidth,
                    0, 360, Arc2D.OPEN
            ));
        }

        private void drawProgressArc(Graphics2D g2d, int x, int y, int diameter, int strokeWidth) {
            g2d.setColor(ringColor);
            float arcAngle = -360f * currentProgress / 100f;
            g2d.draw(new Arc2D.Double(
                    x + strokeWidth/2,
                    y + strokeWidth/2,
                    diameter - strokeWidth,
                    diameter - strokeWidth,
                    90, arcAngle, Arc2D.OPEN
            ));
        }

        private void drawCenterCircle(Graphics2D g2d, int x, int y, int diameter) {
            int innerDiameter = diameter - (diameter / 4);
            int innerX = x + (diameter - innerDiameter) / 2;
            int innerY = y + (diameter - innerDiameter) / 2;

            // Create gradient for center circle
            GradientPaint gradient = new GradientPaint(
                    innerX, innerY, Color.WHITE,
                    innerX + innerDiameter, innerY + innerDiameter,
                    new Color(245, 245, 245)
            );
            g2d.setPaint(gradient);
            g2d.fill(new Ellipse2D.Double(innerX, innerY, innerDiameter, innerDiameter));
        }

        private void drawText(Graphics2D g2d, int x, int y, int diameter) {
            g2d.setColor(Color.DARK_GRAY);

            // Draw label
            Font labelFont = new Font("Segoe UI", Font.BOLD, diameter/10);
            g2d.setFont(labelFont);
            FontMetrics labelMetrics = g2d.getFontMetrics();
            g2d.drawString(label,
                    x + (diameter - labelMetrics.stringWidth(label))/2,
                    y + diameter/2 - diameter/8);

            // Draw value
            Font valueFont = new Font("Segoe UI", Font.PLAIN, diameter/12);
            g2d.setFont(valueFont);
            FontMetrics valueMetrics = g2d.getFontMetrics();
            g2d.drawString(value,
                    x + (diameter - valueMetrics.stringWidth(value))/2,
                    y + diameter/2 + diameter/8);

            // Draw percentage
            Font percentFont = new Font("Segoe UI", Font.BOLD, diameter/8);
            g2d.setFont(percentFont);
            String percentText = currentProgress + "%";
            FontMetrics percentMetrics = g2d.getFontMetrics();
            g2d.drawString(percentText,
                    x + (diameter - percentMetrics.stringWidth(percentText))/2,
                    y + diameter/2);
        }
    }
}