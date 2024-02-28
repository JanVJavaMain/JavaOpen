import java.util.Arrays;

public class Cube {
    static float A, B, C;
    static float cubeWidth = 20;
    static int width = 160, height = 44;
    static float[] zBuffer = new float[160 * 44];
    static char[] buffer = new char[160 * 44];
    static char backgroundASCIICode = '.'; // Hier war der Fehler: statt int steht nun char
    static int distanceFromCam = 100;
    static float horizontalOffset;
    static float K1 = 40;
    static float incrementSpeed = 0.6f;
    static float x, y, z;
    static float ooz;
    static int xp, yp;
    static int idx;

    public static void main(String[] args) {
        System.out.print("\033[2J");
        while (true) {
            Arrays.fill(buffer, backgroundASCIICode); // hat hier Problem gemeldet -> Methode hat keine
            //Möglichkeit, zwei unterschiedl. Datentypen gleich zu setzen 
            // (ganzes Array gleich einen best. Wert).
            Arrays.fill(zBuffer, 0);
            cubeWidth = 20;
            horizontalOffset = -2 * cubeWidth;
            // first cube
            for (float cubeX = -cubeWidth; cubeX < cubeWidth; cubeX += incrementSpeed) {
                for (float cubeY = -cubeWidth; cubeY < cubeWidth; cubeY += incrementSpeed) {
                    calculateForSurface(cubeX, cubeY, -cubeWidth, '@');
                    calculateForSurface(cubeWidth, cubeY, cubeX, '$');
                    calculateForSurface(-cubeWidth, cubeY, -cubeX, '~');
                    calculateForSurface(-cubeX, cubeY, cubeWidth, '#');
                    calculateForSurface(cubeX, -cubeWidth, -cubeY, ';');
                    calculateForSurface(cubeX, cubeWidth, cubeY, '+');
                }
            }
            cubeWidth = 10;
            horizontalOffset = 1 * cubeWidth;
            // second cube
            for (float cubeX = -cubeWidth; cubeX < cubeWidth; cubeX += incrementSpeed) {
                for (float cubeY = -cubeWidth; cubeY < cubeWidth; cubeY += incrementSpeed) {
                    calculateForSurface(cubeX, cubeY, -cubeWidth, '@');
                    calculateForSurface(cubeWidth, cubeY, cubeX, '$');
                    calculateForSurface(-cubeWidth, cubeY, -cubeX, '~');
                    calculateForSurface(-cubeX, cubeY, cubeWidth, '#');
                    calculateForSurface(cubeX, -cubeWidth, -cubeY, ';');
                    calculateForSurface(cubeX, cubeWidth, cubeY, '+');
                }
            }
            cubeWidth = 5;
            horizontalOffset = 8 * cubeWidth;
            // third cube
            for (float cubeX = -cubeWidth; cubeX < cubeWidth; cubeX += incrementSpeed) {
                for (float cubeY = -cubeWidth; cubeY < cubeWidth; cubeY += incrementSpeed) {
                    calculateForSurface(cubeX, cubeY, -cubeWidth, '@');
                    calculateForSurface(cubeWidth, cubeY, cubeX, '$');
                    calculateForSurface(-cubeWidth, cubeY, -cubeX, '~');
                    calculateForSurface(-cubeX, cubeY, cubeWidth, '#');
                    calculateForSurface(cubeX, -cubeWidth, -cubeY, ';');
                    calculateForSurface(cubeX, cubeWidth, cubeY, '+');
                }
            }
            System.out.print("\033[H");
            for (int k = 0; k < width * height; k++) {
                System.out.print(k % width == 0 ? "\n" : buffer[k]);
            }
            A += 0.05;
            B += 0.05;
            C += 0.01;
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void calculateForSurface(float cubeX, float cubeY, float cubeZ, char ch) {
        x = calculateX(cubeX, cubeY, cubeZ);
        y = calculateY(cubeX, cubeY, cubeZ);
        z = calculateZ(cubeX, cubeY, cubeZ) + distanceFromCam;
        ooz = 1 / z;
        xp = (int) (width / 2 + horizontalOffset + K1 * ooz * x * 2);
        yp = (int) (height / 2 + K1 * ooz * y);
        idx = xp + yp * width;
        if (idx >= 0 && idx < width * height) {
            if (ooz > zBuffer[idx]) {
                zBuffer[idx] = ooz;
                buffer[idx] = ch;
            }
        }
    }

    public static float calculateX(float i, float j, float k) {
        return j * (float) Math.sin(A) * (float) Math.sin(B) * (float) Math.cos(C) - k * (float) Math.cos(A) * (float) Math.sin(B) * (float) Math.cos(C) +
                j * (float) Math.cos(A) * (float) Math.sin(C) + k * (float) Math.sin(A) * (float) Math.sin(C) + i * (float) Math.cos(B) * (float) Math.cos(C);
    }

    public static float calculateY(float i, float j, float k) {
        return j * (float) Math.cos(A) * (float) Math.cos(C) + k * (float) Math.sin(A) * (float) Math.cos(C) -
                j * (float) Math.sin(A) * (float) Math.sin(B) * (float) Math.sin(C) + k * (float) Math.cos(A) * (float) Math.sin(B) * (float) Math.sin(C) -
                i * (float) Math.cos(B) * (float) Math.sin(C);
    }

    public static float calculateZ(float i, float j, float k) {
        return k * (float) Math.cos(A) * (float) Math.cos(B) - j * (float) Math.sin(A) * (float) Math.cos(B) + i * (float) Math.sin(B);
    }
}