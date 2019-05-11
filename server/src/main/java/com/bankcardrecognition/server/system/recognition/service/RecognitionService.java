package com.bankcardrecognition.server.system.recognition.service;

import com.bankcardrecognition.server.utils.image.ImageUtils;
import org.apache.tomcat.jni.Time;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.opencv.core.Size;


/**
 * @Author: lizhilong
 * @Description:
 * @Date: Created in 17:50 2019/5/7
 * @Modified By:
 */
@Service
public class RecognitionService {


    /**
     *  将图像转为Mat 对象
     *
     * @param file
     * @return org.opencv.core.Mat
     */
    public Mat convertFile2Mat(File file) {
        //读取图片文件

        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Mat image = ImageUtils.Img2Mat(bi,BufferedImage.TYPE_3BYTE_BGR, CvType.CV_8UC3);
        return image;
    }

    /**
     * 矫正图片，获取银行卡部分
     *
     * @param image
     * @return Mat
     */
    public Mat correctImage(Mat image) {
        Mat reshapeMat = image.reshape(1,1);
        byte[] imageArray = new byte[(int)reshapeMat.total()];
        byte[] result1 = new byte[(int)reshapeMat.total()];
        //配置参数
        reshapeMat.get(0,0,imageArray);
        int length = 0;
        //获取长度
        length = OpencvDLL.OpencvJnaLib.INSTANCE.correctImage(imageArray,result1,image.width(),image.height());

        OpencvDLL.OpencvJnaLib.INSTANCE.correctImage(imageArray,result1,image.width(),image.height());
        Mat correctMat = Mat.zeros(new Size(length*24,24),CvType.CV_8UC3);
        byte [] byteArray = new byte[length*24*24*3];
        for (int i = 0; i < length*24*24*3; i++) {
            byteArray[i] = result1[i];
        }
        correctMat.put(0,0,byteArray);
        Imgcodecs.imwrite("correctMat.jpg",correctMat);
        return correctMat;
    }

    /**
     * 截取字符串区域
     *
     * @param charLineImage
     * @return org.opencv.core.Mat
     */
    public ArrayList<Map> recognitionCharImg(Mat charLineImage) {

        byte[][] numArray = new byte[charLineImage.cols()/24][24*24*3];
        if(charLineImage.cols()>24){
            for(int i =0;i< charLineImage.cols()/24;i++){
                Rect rect = new Rect(i*24,0,24,24);
                Mat roi = new Mat(charLineImage,rect);
//                StringBuffer filename = new StringBuffer();
//                filename.append("imgNum/");
//                filename.append(System.currentTimeMillis());
//                filename.append(".jpg");
//                Imgcodecs.imwrite(filename.toString(),roi);
                roi.get(0,0,numArray[i]);

            }
            try {
                ArrayList<Map> charMap = TensorflowModel.predictNum(numArray);
                return charMap;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


}
