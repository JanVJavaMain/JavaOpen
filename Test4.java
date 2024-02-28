import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CubeRotation3D3 extends JPanel {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int SIZE = 200;

    public CubeRotation3D3() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
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
        int[] zPoints = {SIZE / 2, SIZE / 2, SIZE / 2, SIZE / 2, -SIZE / 2, -SIZE / 2, -SIZE / 2, -SIZE / 2};

        int[][] edges = {
            {0, 1}, {1, 2}, {2, 3}, {3, 0}, // Top face
            {4, 5}, {5, 6}, {6, 7}, {7, 4}, // Bottom face
            {0, 4}, {1, 5}, {2, 6}, {3, 7}  // Vertical edges
        };

        for (int[] edge : edges) {
            int x1 = xPoints[edge[0]];
            int y1 = yPoints[edge[0]];
            int z1 = zPoints[edge[0]];
            int x2 = xPoints[edge[1]];
            int y2 = yPoints[edge[1]];
            int z2 = zPoints[edge[1]];

            int screenX1 = centerX + x1;
            int screenY1 = centerY + y1;
            int screenX2 = centerX + x2;
            int screenY2 = centerY + y2;

            g2d.drawLine(screenX1, screenY1, screenX2, screenY2);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cube Rotation 3D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new CubeRotation3D());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}