package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class ImageHandler {

    public static void saveImageFromURL(String imageUrl, String destinationFile) throws Exception {
        URL url = new URL(imageUrl);

        try (InputStream in = url.openStream()) {
            BufferedImage image = ImageIO.read(in);

            File outputFile = new File(destinationFile);

            ImageIO.write(image, "jpg", outputFile);

            System.out.println("Image successfully saved in: " + destinationFile);
        }
    }
}