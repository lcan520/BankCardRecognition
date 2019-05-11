package com.bankcardrecognition.server.system.recognition.controller;

import com.alibaba.fastjson.JSONObject;
import com.bankcardrecognition.server.result.CodeMsg;
import com.bankcardrecognition.server.result.JsonResult;
import com.bankcardrecognition.server.system.recognition.service.RecognitionService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: lizhilong
 * @Description:
 * @Date: Created in 17:45 2019/5/7
 * @Modified By:
 */
@Controller
@Slf4j
@RestController
public class RecognitionController {

    @Autowired
    private RecognitionService recognitionService;

    /**
     * 识别银行卡
     *
     * @param imageFile
     * @return java.lang.String
     */
    @RequestMapping(value = "/recognition")
    @ResponseBody
    public String recognition(@RequestParam("file") MultipartFile imageFile) {
        String fileName = imageFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 用当前时间作为文件名，防止生成的临时文件重复
        try {
            File file = File.createTempFile(System.currentTimeMillis() + "", prefix);

            imageFile.transferTo(file);

            Mat imageMat = recognitionService.convertFile2Mat(file);

            Mat correctMat = recognitionService.correctImage(imageMat);
            ArrayList<Map> result = recognitionService.recognitionCharImg(correctMat);
            return JSONObject.toJSONString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JsonResult.failure(CodeMsg.ModelCodeMsg.CONDITION).getMessage();
    }


}
