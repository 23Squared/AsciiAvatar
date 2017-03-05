package com.twentyThreeSquared.AsciiAvatar;

import com.twentyThreeSquared.AsciiAvatar.processors.ImageConverter;
import com.twentyThreeSquared.AsciiAvatar.processors.ImageResizer;
import com.twentyThreeSquared.AsciiAvatar.tools.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class AsciiAvatar {

    private static final int MAX_WIDTH_CHARS = 150;
    private static final int MAX_HEIGHT_CHARS = 100;

    private static final Logger log =
            LoggerFactory.getLogger(AsciiAvatar.class);

    public static void main(String[] args) {

        try {
            String fileURI = validateCommandLineArgs(args);
            log.info(Utils.ANSI_CYAN + "Using file: " + fileURI + " to generate ASCII art" + Utils.ANSI_RESET);

            log.info(Utils.ANSI_CYAN + "Loading image..." + Utils.ANSI_RESET);
            BufferedImage image = Utils.loadImage(fileURI);

            // Create ImageResizer with max height and width bounds and constrain image
            ImageResizer imageResizer = new ImageResizer(MAX_WIDTH_CHARS, MAX_HEIGHT_CHARS);
            image = imageResizer.constrainImage(image);

            // Convert to greyscale as it provides a better result
            log.info(Utils.ANSI_CYAN + "Converting to grayscale..." + Utils.ANSI_RESET);
            ImageConverter imageConverter = new ImageConverter();
            image = imageConverter.convertToGrayscale(image);

            // Generate and output to console
            log.info(Utils.ANSI_CYAN + "Generating ASCII image..." + Utils.ANSI_RESET);
            System.out.println(imageConverter.convertToAscii(image));

            log.info(Utils.ANSI_GREEN + "Done!" + Utils.ANSI_RESET);

        } catch (IOException | IllegalArgumentException e) {
            log.error(Utils.ANSI_RED + e.getMessage() + Utils.ANSI_RESET);
        }
    }

    public static String validateCommandLineArgs(String[] args)
            throws IllegalArgumentException {

        // We're expecting to receive one arg (name of original image file)
        if(args.length != 1) {
            throw new IllegalArgumentException(
                    Utils.ANSI_RED + "Simon says you've used the application incorrectly.\n" + Utils.ANSI_RESET +
                    Utils.ANSI_CYAN + "Usage: AsciiAvatar [name of image]" + Utils.ANSI_RESET);
        }

        return args[0];
    }

}
