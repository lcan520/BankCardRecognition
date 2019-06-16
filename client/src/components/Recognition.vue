<template>
  <div>
    <Upload
      ref="upload"
      :show-upload-list="true"
      :on-success="handleSuccess"
      :format="['jpg','jpeg','png']"
      :max-size="2048"
      :on-format-error="handleFormatError"
      :on-exceeded-size="handleMaxSize"
      :before-upload="handleBeforeUpload"
      type="drag"
      action="/API/recognition"
      style="display: inline-block;width:300px;"
    >
      <div style="padding: 20px 0">
        <Icon type="ios-camera" size="20"></Icon>
        <p>拖拽图片到此处</p>
      </div>
    </Upload>
    <div style="margin-top:5px;margin-bottom:5px">
      <img :src="preSrc.value" 
           v-if="preSrc.value" 
           style="display: inline-flex;"
      >
    </div>
    <div>
      <div class v-for="num in numList" style="display: inline-flex; margin-right: 4.5px;">
        <div class v-for="(cahr,pro) in num">
          <Button type="default" size="small">
            <span>{{pro}}</span>
          </Button>
        </div>
      </div>
    </div>

    <Modal title="View Image" v-model="visible">
      <img
        :src="'https://o5wwk8baw.qnssl.com/' + imgName + '/large'"
        v-if="visible"
        style="width: 100%"
      >
    </Modal>
  </div>
</template>

<script>
import * as tf from "@tensorflow/tfjs";
import getPixels from "get-pixels";

export default {
  name: "Recognition",
  data() {
    return {
      numList: [],
      imgName: "",
      visible: false,
      uploadList: [],
      preSrc: {
        value: ""
      }
    };
  },
  methods: {
    handleView(name) {
      this.imgName = name;
      this.visible = true;
    },
    handleRemove(file) {
      const fileList = this.$refs.upload.fileList;
      this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
    },
    handleSuccess(res, file) {
      if (res.success) {
        this.numList = res.data.charList;
        this.$set(
          this.preSrc,
          "value",
          `http://localhost:8082/preview/${res.data.url}.jpg`
        );
      } else {
        this.$Notice.warning({
          title: "识别错误！",
          desc: "识别错误，未检测出银行卡图像"
        });
      }
    },
    handleFormatError(file) {
      this.$Notice.warning({
        title: "格式错误！",
        desc:
          "File format of " +
          file.name +
          `文件 ${file.name} 格式错误，支持 jpg、png.`
      });
    },
    handleMaxSize(file) {
      this.$Notice.warning({
        title: "文件过大！",
        desc:`文件${file.name}过大，支持2M一下文件`
      });
    },
    handleBeforeUpload(files) {
      const check = this.uploadList.length < 10;
      if (!check) {
        this.$Notice.warning({
          title: "连续上传文件超过10个"
        });
      }
      return check;
    }
  },
  mounted() {
    this.uploadList = this.$refs.upload.fileList;
  }
};
</script>

<style lang="" scoped>
.demo-upload-list {
  display: inline-block;
  width: 60px;
  height: 60px;
  text-align: center;
  line-height: 60px;
  border: 1px solid transparent;
  border-radius: 4px;
  overflow: hidden;
  background: #fff;
  position: relative;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.2);
  margin-right: 4px;
}
.demo-upload-list img {
  width: 100%;
  height: 100%;
}
.demo-upload-list-cover {
  display: none;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.6);
}
.demo-upload-list:hover .demo-upload-list-cover {
  display: block;
}
.demo-upload-list-cover i {
  color: #fff;
  font-size: 20px;
  cursor: pointer;
  margin: 0 2px;
}
</style>