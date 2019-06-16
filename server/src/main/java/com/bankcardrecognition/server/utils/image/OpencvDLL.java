package com.bankcardrecognition.server.utils.image;


import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
/**
 * @Author: lizhilong
 * @Description:
 * @Date: Created in 20:42 2019/5/7
 * @Modified By:
 */
public class OpencvDLL {
    /**
     * 调用C++DLL，切割字符串，内部接口静态调用
     */
    public interface OpencvJnaLib extends Library {

        OpencvJnaLib INSTANCE = (OpencvJnaLib) Native.load("opencv/test_opencv.dll", OpencvJnaLib.class);
        int add1(int first, int second);
        int substract(int a,int b) ;
        int correctImage(byte[] imageArray, byte[] result, int width, int height);
        void printHello();
    }
}
