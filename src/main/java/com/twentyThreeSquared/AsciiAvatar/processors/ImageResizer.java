package com.twentyThreeSquared.AsciiAvatar.processors;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ImageResizer {

    private static final String IMAGE_WIDTH = "imageWidth";
    private static final String IMAGE_HEIGHT = "imageHeight";

    private int maxImageWidth;
    private int maxImageHeight;

    public ImageResizer(int charsWidth, int charsHeight) {

        // Divide by two to take into account the space we're
        // adding in to maintain aspect ratio on output
        maxImageWidth = charsWidth / 2;
        maxImageHeight = charsHeight;
    }

    public BufferedImage constrainImage(BufferedImage image) {

        Map<String, Integer> dimensions = calculateNewDimensions(image.getWidth(), image.getHeight());
        int newWidth = dimensions.get(IMAGE_WIDTH);
        int newHeight = dimensions.get(IMAGE_HEIGHT);

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D graphics = resizedImage.createGraphics();

        // Draw image from 'image' using (0,0) as start coord with a
        // null observer object (observer would normally be notified
        // as more of image is drawn)
        graphics.drawImage(image, 0, 0, newWidth, newHeight, null);
        graphics.dispose();

        // Add rendering hints to improve the quality of the output image
        graphics.setComposite(AlphaComposite.Src);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    private Map<String, Integer> calculateNewDimensions(int width, int height) {

        // Ensure float arithmetic
        float aspectRatio = (float)width / (float)height;

        int currentWidth = width;
        int currentHeight = height;

        // Correct the width first
        if(currentWidth > maxImageWidth) {
            currentWidth = maxImageWidth;
            currentHeight = (int)((float)maxImageWidth/aspectRatio);
        }

        // If the height is still out of bounds correct this too.
        // This occurs in the sitation when there is a narrow, tall
        // image --> height >> width. In most situations, correcting
        // the width will also correct the height
        if(currentHeight > maxImageHeight) {
            currentHeight = maxImageHeight;
            currentWidth =  (int)(aspectRatio * (float)maxImageHeight);
        }

        Map<String, Integer> dimensions = new HashMap();
        dimensions.put(IMAGE_WIDTH, currentWidth);
        dimensions.put(IMAGE_HEIGHT, currentHeight);

        return dimensions;
    }
}
