package com.twentyThreeSquared.AsciiAvatar.processors;

import com.twentyThreeSquared.AsciiAvatar.tools.Utils;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;

public class ImageConverter {

    private BufferedImageOp grayscaleOperation;

    // Darkest to lightest to match the 0 - 255 darkest to lightest range in RGB images
    private static final char[] DRAWING_SET = {'@', '%', '#', '*', '+', '=', '-', ':', '.', ' '};

    public ImageConverter() {

        grayscaleOperation =
                new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
    }

    public BufferedImage convertToGrayscale(BufferedImage image) {
        // This is the fastest method of greyscaling that preserves
        // image transparency.
        // null - indicates creation of new image required
        return grayscaleOperation.filter(image, null);
    }

    public String convertToAscii(BufferedImage image) {

        StringBuilder imageBuilder = new StringBuilder();

        for(int y=0; y<image.getHeight(); y++) {

            for(int x=0; x<image.getWidth(); x++) {

                Color color = new Color(image.getRGB(x, y));
                int brightness = calculateBrightness(color);

                // Scale the brightness from 0 - 255 to 0 - 1
                float scaledBrightness = ((float)brightness / (float)256);

                // Move the value into the correct range for DRAWING_SET[]
                int drawingSetCharacterIndex = (int)((scaledBrightness * 10));

                imageBuilder.append(DRAWING_SET[drawingSetCharacterIndex]);

                // Append a space to maintain aspect ratio
                imageBuilder.append(Utils.SPACE);
            }

            imageBuilder.append("\n");
        }

        return imageBuilder.toString();
    }

    private int calculateBrightness(Color color) {

        // RGB values will all be the same for
        // greyscale but different for color
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        return (r+g+b) / 3;
    }
}
