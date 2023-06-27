package com.gato.images;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class Sobel3x3 {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        // Reading the image
        System.out.println("Beginning...");
        File file = null;
        BufferedImage img = null;
        try {
            file = new File("C:\\Users\\Ivan\\Desktop\\imagen\\gato.jpg");
            img = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println(e);
        }

        // Creating a copy of the image
        BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);

        // Sobel kernel 3x3
        System.out.print("Cantidad de filas y columnas (x): ");
        // Número de filas
        int filas = scanner.nextInt();
        // Número de columnas
        int columnas = filas;
        int[][] sobel_X = new int[filas][columnas]; // Creación del sobel_X
        int[][] sobel_Y = new int[filas][columnas]; // Creación del sobel_Y
        System.out.println("Ingresar valores del sobel_X "+filas+"x"+columnas+": ");
        // Asignar valores al sobel_X
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print("Ingrese el valor para la posición [" + i + "][" + j + "] de sobel_X: ");
                sobel_X[i][j] = scanner.nextInt();
            }
        }System.out.println("Ingresar valores del sobel_Y "+filas+"x"+columnas+": ");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print("Ingrese el valor para la posición [" + i + "][" + j + "] de sobel_Y: ");
                sobel_Y[i][j] = scanner.nextInt();
            }
        }


        // Convolución
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                // Convolución para x
                int pixelX = (sobel_X[filas -1][columnas]  * getGrayValue(img.getRGB(x, y)));
                // Convolución para y
                int pixelY = (sobel_Y[filas -1][columnas]  * getGrayValue(img.getRGB(x, y)));

                // Calcular magnitud del gradiente
                int gradient = (int) Math.sqrt(pixelX * pixelX + pixelY * pixelY);

                // Establecer nuevo valor de píxel
                Color color = new Color(gradient, gradient, gradient);
                output.setRGB(x, y, color.getRGB());
            }
        }

        // Saving the modified image
        try {
            file = new File("C:\\Users\\Ivan\\Desktop\\imagen\\gato_modificado.jpg");
            ImageIO.write(output, "jpg", file);
            System.out.println("Done...");

            // Obtener el histograma de la imagen modificada
            int[] histogram = calcularHistograma(output);

            // Imprimir el histograma
            for (int i = 0; i < 256; i++) {
                System.out.println("Valor de gris: " + i + ", Frecuencia: " + histogram[i]);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // Auxiliar para obtener el valor de gris de RGB
    private static int getGrayValue(int rgb) {
        Color color = new Color(rgb);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        return (red + green + blue) / 3;
    }

    // Función para calcular el histograma de una imagen
    private static int[] calcularHistograma(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    int[] histograma = new int[256];

    // Inicializar el histograma con ceros
    for (int i = 0; i < 256; i++) {
        histograma[i] = 0;
    }

    // Calcular el histograma
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            int pixel = image.getRGB(x, y);
            Color color = new Color(pixel);
            int grayValue = color.getRed();  // Usamos el componente rojo para una imagen en escala de grises
            histograma[grayValue]++;
        }
    }

    return histograma;
}
}