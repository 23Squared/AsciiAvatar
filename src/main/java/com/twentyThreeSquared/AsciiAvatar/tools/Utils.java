package com.twentyThreeSquared.AsciiAvatar.tools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Utils {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String SPACE = " ";

    public static BufferedImage loadImage(String imageURI) throws IOException {

        File imageFile = new File(imageURI);

        if(!imageFile.exists()) {
            throw new FileNotFoundException("Failed to find file " + imageURI);
        }

        BufferedImage image = ImageIO.read(imageFile);

        if(image == null) {
            throw new IllegalArgumentException("Failed to load " + imageURI + ". Not a valid image file format");
        }

        return image;
    }
}
