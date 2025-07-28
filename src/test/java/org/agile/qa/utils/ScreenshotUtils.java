package org.agile.qa.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

public class ScreenshotUtils {

    private WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);

    // Store screenshots in the project root "screenshots"
    private static final String SCREENSHOT_DIR = "./screenshots";
    private String datePattern = "yyyyMMdd_HHmm";

    public ScreenshotUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void takeScreenshot(String name) {
        String dir = SCREENSHOT_DIR;      // default root directory
        String safeName = name;

        if (name.contains("/")) {
            // Handle subdirectory
            int slashIdx = name.lastIndexOf('/');
            dir = SCREENSHOT_DIR + "/" + name.substring(0, slashIdx).trim();
            safeName = name.substring(slashIdx + 1);
        }

        // Replace whitespace in filename only (not in directory part)
        safeName = safeName.trim().replaceAll("\\s+", "_");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(datePattern));
        String fileName = safeName + "_" + timestamp + ".png";

        // Ensure directory exists
        File directory = new File(dir);
        if (!directory.exists()) {
            directory.mkdirs();
            logger.info("Created screenshot directory: {}", directory.getAbsolutePath());
        }

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path destPath = new File(directory, fileName).toPath();

        try {
            Files.copy(screenshot.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Screenshot saved at: {}", destPath.toAbsolutePath());
        } catch (IOException ex) {
            logger.error("Failed to save screenshot: {}", destPath, ex);
        }
    }
    
    public void takeElementScreenshot(WebElement element, String name) {
        String dir = SCREENSHOT_DIR;
        String safeName = name;
        if (name.contains("/")) {
            int slashIdx = name.lastIndexOf('/');
            dir = SCREENSHOT_DIR + "/" + name.substring(0, slashIdx).trim();
            safeName = name.substring(slashIdx + 1);
        }
        safeName = safeName.trim().replaceAll("\\s+", "_");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(datePattern));
        String fileName = safeName + "_" + timestamp + ".png";

        File directory = new File(dir);
        if (!directory.exists()) {
            directory.mkdirs();
            logger.info("Created screenshot directory: {}", directory.getAbsolutePath());
        }

        if (element != null) {
            try {
                File screenshot = element.getScreenshotAs(OutputType.FILE);
                Path destPath = new File(directory, fileName).toPath();
                Files.copy(screenshot.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
                logger.info("Element screenshot saved at: {}", destPath.toAbsolutePath());
            } catch (Exception ex) {
                logger.error("Failed to save element screenshot: {}", fileName, ex);
                // Optionally, fallback if desired
            }
        } else {
            // Element not present; take full-page screenshot as fallback
            logger.warn("Element not found for screenshot: '{}' -- taking full page screenshot instead.", safeName);
            takeScreenshot(name + "_fallback_fullpage");
        }
    }

    public void takeContextualElementScreenshot(WebElement element, String name, int padding) {
        try {
            // Take full page screenshot
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage fullImg = ImageIO.read(screenshotFile);

            // Get element location and size
            Point point = element.getLocation();
            Dimension size = element.getSize();

            // Calculate expanded rectangle with padding, ensuring boundaries within full image
            int x = Math.max(point.getX() - padding, 0);
            int y = Math.max(point.getY() - padding, 0);
            int width = Math.min(size.getWidth() + 2 * padding, fullImg.getWidth() - x);
            int height = Math.min(size.getHeight() + 2 * padding, fullImg.getHeight() - y);

            // Crop the image
            BufferedImage elementScreenshot = fullImg.getSubimage(x, y, width, height);

            // Save cropped image (reuse your naming, directory logic here)
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(datePattern));
            File output = new File("./screenshots/" + name.replaceAll("\\s+","_") + "_" + timestamp + ".png");
            ImageIO.write(elementScreenshot, "png", output);

            logger.info("Contextual screenshot saved at: {}", output.getAbsolutePath());

        } catch (Exception e) {
            logger.error("Failed to capture contextual element screenshot: {}", name, e);
            // fallback, e.g., call takeScreenshot(name)
            takeScreenshot(name + "_fallback_fullpage");
        }
    }

}
