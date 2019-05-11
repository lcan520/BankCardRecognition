package com.bankcardrecognition.server;

import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//注意程序运行的时候需要在VM option添加该行 指明opencv的dll文件所在路径
		//-Djava.library.path=$PROJECT_DIR$\opencv\x64
	}
	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
