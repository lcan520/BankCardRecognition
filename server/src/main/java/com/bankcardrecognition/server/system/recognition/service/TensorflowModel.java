package com.bankcardrecognition.server.system.recognition.service;

import org.apache.commons.io.IOUtils;
import org.tensorflow.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lizhilong
 * @Description:
 * @Date: Created in 1:03 2019/5/11
 * @Modified By:
 */
public class TensorflowModel {
//    public static void main(String[] args) throws IOException {
//        try (Graph graph = new Graph()) {
//            //导入图
//            byte[] graphBytes = IOUtils.toByteArray(new
//                    FileInputStream("./model.pb"));
//            graph.importGraphDef(graphBytes);
//
//            //根据图建立Session
//            try(Session session = new Session(graph)){
//                //相当于TensorFlow Python中的sess.run(z, feed_dict = {'x': 10.0})
//                Long[] i= new Long[2];
//                i[0] = 1L;
//                i[1] = 24*24*3L;
//                Tensor z = session.runner()
//                        .feed("Placeholder", Tensor.create(i))
//                        .fetch("model").run().get(0);
//                System.out.println("z = " + z);
//            }
//        }
//
//    }

    public static ArrayList<Map> predictNum(byte[][] numdata) throws IOException {
        //Map numCharMap = new HashMap<Float, Float>(16);
        ArrayList<Map> numCharMap= new ArrayList<>();
        try (Graph graph = new Graph()) {
            //导入图
            byte[] graphBytes = IOUtils.toByteArray(new
                    FileInputStream("./model.pb"));
            graph.importGraphDef(graphBytes);
            //根据图建立Session
            try (Session session = new Session(graph)) {
                //相当于TensorFlow Python中的sess.run(z, feed_dict = {'x': 10.0})
                float[][] floatData = byteArray2FloatArray(numdata);
                Tensor input = Tensor.create(floatData);
                Tensor predict = session.runner()
                        .feed("Placeholder", input)
                        .fetch("model").run().get(0);
                //System.out.println(predict.toString());
                float[][] floatResult = new float[floatData.length][10];
                predict.copyTo(floatResult);

                for (int i = 0; i < floatResult.length; i++) {
                    float maxp = 0;
                    int maxIndex = 0;
                    for (int j = 0; j < floatResult[j].length; j++) {
                        if (floatResult[i][j] > maxp) {
                            maxp = floatResult[i][j];
                            maxIndex = j;
                        }
                    }
                    Map map = new HashMap();
                    map.put(String.valueOf(maxIndex),String.valueOf(Math.floor(maxp*100)));
                    numCharMap.add(map);
                }
                return numCharMap;
            }
        }
    }

    private static float[][] byteArray2FloatArray(byte[][] data) {
        float[][] floatPixels = new float[data.length][24 * 24 * 3];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                float pixels= data[i][j] & 0xff;
                floatPixels[i][j] = pixels/255.0F;
            }
        }
        return floatPixels;
    }

}
