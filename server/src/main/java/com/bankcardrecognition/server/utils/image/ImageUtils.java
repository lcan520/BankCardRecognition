package com.bankcardrecognition.server.utils.image;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.opencv.imgcodecs.Imgcodecs.imencode;

/**
 * @Author: lizhilong
 * @Description:
 * @Date: Created in 21:16 2019/5/7
 * @Modified By:
 */
public class ImageUtils {
    /**
     * 将Mat类型转化成BufferedImage类型
     * 
     * @param  mat 对象
     * @param fileExtension 文件扩展名
     * @return
     */
    public static BufferedImage Mat2Img(Mat mat, String fileExtension) {
        MatOfByte mob = new MatOfByte();


        imencode(fileExtension, mat, mob);
        // convert the "matrix of bytes" into a byte array
        byte[] byteArray = mob.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImage;
    }

    /**
     * 将BufferedImage类型转换成Mat类型 
     * @param bfImg
     * @param imgType bufferedImage的类型 如 BufferedImage.TYPE_3BYTE_BGR
     * @param matType 转换成mat的type 如 CvType.CV_8UC3
     * @return
     */
    public static Mat Img2Mat(BufferedImage bfImg, int imgType, int matType) {
        BufferedImage original = bfImg;
        int itype = imgType;
        int mtype = matType;

        if (original == null) {
            throw new IllegalArgumentException("original == null");
        }

        if (original.getType() != itype) {
            BufferedImage image = new BufferedImage(original.getWidth(),                                                        original.getHeight(), itype);

            Graphics2D g = image.createGraphics();
            try {
                g.setComposite(AlphaComposite.Src);
                g.drawImage(original, 0, 0, null);
            } finally {
                g.dispose();
            }
        }

        byte[] pixels = ((DataBufferByte) original.getRaster().getDataBuffer()).getData();
        Mat mat = Mat.eye(original.getHeight(), original.getWidth(), mtype);
        mat.put(0, 0, pixels);

        return mat;
    }
}
