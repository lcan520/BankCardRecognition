package com.bankcardrecognition.server.system.recognition.service;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;

/**
 * @Author: lizhilong
 * @Description:
 * @Date: Created in 20:42 2019/5/7
 * @Modified By:
 */
public class OpencvDLL {

    public interface OpencvJnaLib extends Library {

        OpencvJnaLib INSTANCE = (OpencvJnaLib) Native.load("test_opencv.dll", OpencvJnaLib.class);
        int add1(int first, int second);
        int substract1(int a,int b) ;
        int correctImage(byte[] imageArray, byte[] result, int width, int height);
        void printHello();
    }


//    public static void main(String[] args) {
//        int sum = TestJnaLib.INSTANCE.add1(13,234);
//        TestJnaLib.INSTANCE.printHello();
//        int[] i = {1,2,3,4};
//        int a = TestJnaLib.INSTANCE.correctImage(i,i.length);
//        System.out.println(sum);
//        System.out.println(a);
////        Mat mat = Imgcodecs.imread("E:\\SegCard\\sample\\0.jpg");
////        System.out.println(mat);
//    }
}
