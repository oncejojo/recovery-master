package com.jojo.recovery.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class ImageTrimUtil {

    public static void main(String[] args) throws IOException {
        // 选择图片
        File file1 = new File("E:/result.png");
        BufferedImage image1 = ImageIO.read(file1);
        // 输出图片(相同即覆盖)
        File outFile = new File("E:/result.png");
        int trimLeft = trimBroadsideWhite(image1, 0);
        int trimRight = trimBroadsideWhite(image1, 1);
        // 顶部空白高度
        int trimTop = trimUpDownSpace(image1, 0);
        // 底部空白高度
        int trimBottom = trimUpDownSpace(image1, 1);
        log.info("top:" + trimTop + "bottom:" + trimBottom);
        //上下
        image1 = image1.getSubimage(0, trimTop, image1.getWidth(), image1.getHeight() - trimTop - trimBottom);
        // 上下左右
//        image1 = image1.getSubimage(trimLeft, trimTop, image1.getWidth() - trimLeft - trimRight, image1.getHeight() - trimTop - trimBottom);
        ImageIO.write(image1, "png", outFile);
    }

    /**
     * @param image
     * @param type  0上1下
     * @return
     * @desc 如果该X轴上所有的点都是白色，就判断这一行需要截取，直到没有白色为止
     */
    public static int trimUpDownSpace(BufferedImage image, int type) {
        int trimHeight = 0;
        if (type == 0) {
            for (int y = 0; y < image.getHeight(); y++) {
                boolean flag = true;
                for (int x = 0; x < image.getWidth(); x++) {
                    if (image.getRGB(x, y) != Color.WHITE.getRGB()) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    trimHeight = trimHeight + 1;
                } else {
                    break;
                }
            }
        } else {
            for (int y = image.getHeight() - 1; y >= 0; y--) {
                boolean flag = false;
                for (int x = 0; x < image.getWidth(); x++) {
                    if (image.getRGB(x, y) != Color.WHITE.getRGB()) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    trimHeight = trimHeight + 1;
                } else {
                    break;
                }
            }
        }

        return trimHeight;
    }

    /**
     * @param image
     * @param type
     * @return
     * @desc 如果该Y轴上所有的点都是白色，就判断这一行需要截取，直到没有白色为止
     */
    public static int trimBroadsideWhite(BufferedImage image, int type) {
        int trimWidth = 0;
        if (type == 0) {
            for (int x = 0; x < image.getWidth(); x++) {
                boolean flag = true;
                for (int y = 0; y < image.getHeight(); y++) {
                    if (image.getRGB(x, y) != Color.WHITE.getRGB()) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    trimWidth = trimWidth + 1;
                } else {
                    break;
                }
            }
        } else {
            for (int x = image.getWidth() - 1; x >= 0; x--) {
                boolean flag = true;
                for (int y = 0; y < image.getHeight(); y++) {
                    if (image.getRGB(x, y) != Color.white.getRGB()) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    trimWidth = trimWidth + 1;
                } else {
                    break;
                }
            }
        }

        return trimWidth;
    }

    /**
     * @desc 获取图片任意一边留白尺寸用于去除
     * @param image
     * @param position
     * @return
     */
    public static int trimWhite(BufferedImage image, String position) {
        int trimSize = 0;
        if (position.equals("top")){
            for (int y = 0; y < image.getHeight(); y++) {
                boolean flag = true;
                for (int x = 0; x < image.getWidth(); x++) {
                    if (image.getRGB(x,y) != Color.WHITE.getRGB()) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    trimSize = trimSize + 1;
                } else {
                    break;
                }
            }
        } else if (position.equals("bottom")) {
            for (int y = image.getHeight() - 1; y >= 0; y--) {
                boolean flag= true;
                for (int x = 0; x < image.getWidth(); x++) {
                    if (image.getRGB(x,y) != Color.WHITE.getRGB()) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    trimSize = trimSize + 1;
                } else {
                    break;
                }
            }
        } else if (position.equals("left")){
            for (int x = 0; x < image.getWidth(); x++) {
                boolean flag = true;
                for (int y = 0; x < image.getHeight(); y++) {
                    if (image.getRGB(x,y) != Color.WHITE.getRGB()) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    trimSize = trimSize + 1;
                } else {
                    break;
                }
            }
        } else if (position.equals("right")) {
            for (int x = image.getWidth() - 1; x >= 0; x--) {
                boolean flag = true;
                for (int y = 0; y < image.getHeight(); y++) {
                    if (image.getRGB(x,y) != Color.WHITE.getRGB()) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    trimSize = trimSize + 1;
                } else {
                    break;
                }
            }
        }

        return trimSize;
    }


}
