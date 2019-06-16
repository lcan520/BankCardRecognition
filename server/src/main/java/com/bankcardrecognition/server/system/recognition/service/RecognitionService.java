package com.bankcardrecognition.server.system.recognition.service;

import com.bankcardrecognition.server.utils.image.ImageUtils;
import com.bankcardrecognition.server.utils.image.OpencvDLL;
import com.bankcardrecognition.server.utils.image.TensorflowModel;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.opencv.core.*;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.opencv.core.CvType.CV_8UC3;


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
        Mat image = ImageUtils.Img2Mat(bi,BufferedImage.TYPE_3BYTE_BGR, CV_8UC3);
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
        Mat correctMat = Mat.zeros(new Size(length*24,24), CV_8UC3);
        byte [] byteArray = new byte[length*24*24*3];
        byte [] preByteArray = new byte[length*36*24*3];
        for (int i = 0; i < length*24*24*3; i++) {
            byteArray[i] = result1[i];
        }
        correctMat.put(0,0,byteArray);

        return correctMat;
    }

    /**
     * 截取字符串区域
     *
     * @param charLineImage
     * @return org.opencv.core.Mat
     */
    public Map recognitionCharImg(Mat charLineImage) {

        byte[][] numArray = new byte[charLineImage.cols()/24][24*24*3];
        List<Mat> preCharImg= new ArrayList<>();
        Mat interval = new Mat(new Size(3,24), CV_8UC3, new Scalar(new double[]{255, 255, 255}));
        int preWidth = charLineImage.cols()+(charLineImage.cols()/24-1)*3;
        Mat preCharLine = new Mat(new Size(preWidth,24),CV_8UC3);
        if(charLineImage.cols()>24){
            for(int i =0;i< charLineImage.cols()/24;i++){
                Rect rect = new Rect(i*24,0,24,24);
                Mat roi = new Mat(charLineImage,rect);

                preCharImg.add(roi);
                if(i< charLineImage.cols()/24-1){
                    preCharImg.add(interval);
                }

                roi.get(0,0,numArray[i]);
            }
            Core.hconcat(preCharImg,preCharLine);
            StringBuffer filename = new StringBuffer();
            filename.append("target/classes/static/preview/");
            Long curTime = System.currentTimeMillis();
            filename.append(curTime);
            filename.append(".jpg");
            Imgcodecs.imwrite(filename.toString(), preCharLine);
            try {
                ArrayList<Map> charMap = TensorflowModel.predictNum(numArray);
                Map result = new HashMap();
                result.put("url",curTime);
                result.put("charList",charMap);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


}
