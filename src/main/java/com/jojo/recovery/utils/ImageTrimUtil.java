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
        // 顶部空白高度
        int trimTop = trimUpDownSpace(image1, 0);
        // 底部空白高度
        int trimBottom = trimUpDownSpace(image1, 1);
        log.info("top:" + trimTop + "bottom:" + trimBottom);
        // x,y 为左上坐标 w,h 为宽高
        image1 = image1.getSubimage(0, trimTop, image1.getWidth(), image1.getHeight() - trimTop - trimBottom);
        ImageIO.write(image1, "png", outFile);
    }

    /**
     * @param image
     * @param type  0上1下
     * @return
     * @desc 去除图片上或下部的留白
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
     * @return
     * @desc 截去图片顶部白色区域
     */
    public static int trimTopWhite(BufferedImage image) {
        int trimHeight = 0;
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
        return trimHeight;
    }

    /**
     * @param image
     * @return
     * @desc 截去图片底部白色区域
     */
    public static int trimBottomWhite(BufferedImage image) {
        int trimHeight = 0;
        for (int y = image.getHeight() - 1; y >= 0; y++) {
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

        return trimHeight;
    }

}
