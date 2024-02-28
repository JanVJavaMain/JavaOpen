import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CubeRotation extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int SIZE = 200;
    private static final int DELAY = 10;

    private double angle = 0;

    public CubeRotation() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);

        Thread animationThread = new Thread(() -> {
            while (true) {
                angle += 0.01;
                repaint();
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        animationThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;

        int[] xPoints = {centerX - SIZE / 2, centerX + SIZE / 2, centerX + SIZE / 2, centerX - SIZE / 2, centerX - SIZE / 2, centerX + SIZE / 2, centerX + SIZE / 2, centerX - SIZE / 2};
        int[] yPoints = {centerY - SIZE / 2, centerY - SIZE / 2, centerY + SIZE / 2, centerY + SIZE / 2, centerY - SIZE / 2, centerY - SIZE / 2, centerY + SIZE / 2, centerY + SIZE / 2};

        for (int i = 0; i < xPoints.length; i++) {
            int x1 = xPoints[i];
            int y1 = yPoints[i];
            int x2 = xPoints[(i + 1) % xPoints.length];
            int y2 = yPoints[(i + 1) % yPoints.length];

            int rotatedX1 = (int) (centerX + (x1 - centerX) * Math.cos(angle) - (y1 - centerY) * Math.sin(angle));
            int rotatedY1 = (int) (centerY + (x1 - centerX) * Math.sin(angle) + (y1 - centerY) * Math.cos(angle));
            int rotatedX2 = (int) (centerX + (x2 - centerX) * Math.cos(angle) - (y2 - centerY) * Math.sin(angle));
            int rotatedY2 = (int) (centerY + (x2 - centerX) * Math.sin(angle) + (y2 - centerY) * Math.cos(angle));

            g2d.drawLine(rotatedX1, rotatedY1, rotatedX2, rotatedY2);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cube Rotation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new CubeRotation());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}