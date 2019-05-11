package com.bankcardrecognition.server.result;

/**
 * @Author: lizhilong
 * @Description:
 * @Date: Created in 17:14 2019/1/15
 * @Modified By:
 */
public class CodeMsg {
    private final String msg;
    private final int code;

    CodeMsg(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
    /**-------------------------- 系统管理相关信息 -----------------------------*/
    public interface SystemCodeMsg {
        CodeMsg SUCCESS = new CodeMsg("成功", 200);
        CodeMsg ERROR = new CodeMsg("系统失败", 500);
        /**---------------- 500700~500999 ----------------*/
        CodeMsg USER_EXIST = new CodeMsg("当前手机号已被注册", 500700);
        CodeMsg USER_NON = new CodeMsg("当前输入用户为空", 500701);
        CodeMsg USER_NON_REGISTER = new CodeMsg("当前用户未被注册", 500702);
        CodeMsg USER_PASS_NOT_EXACTLY = new CodeMsg("用户密码输入错误", 500703);
        CodeMsg USER_NOT_LOGIN = new CodeMsg("尚未登录", 500704);
        CodeMsg PHONE_EXISTS = new CodeMsg("用户手机号已存在", 500705);
    }
    /**-------------------------- 动态SQL语句相关错误信息 -----------------------------*/
    /**-------------------------- 500800 ~ 500899 -----------------------------*/
    public interface DynamicSQLCodeMsg {
        CodeMsg CONDITION = new CodeMsg("当前手机号已被注册", 500805);
        CodeMsg PROPERTY  = new CodeMsg("当前手机号已被注册", 500806);
        CodeMsg PRIMARYKEY_MULTIPLE = new CodeMsg("主键查询只自持唯一主键",500807);
        CodeMsg PRIMARYKEY_NULL = new CodeMsg("主键不存在",500808);;
    }

    /**-------------------------- 模型出错 -----------------------------*/
    /**-------------------------- 500900 ~ 500999 -----------------------------*/
    public interface ModelCodeMsg {
        CodeMsg CONDITION = new CodeMsg("模型加载出错", 500805);

    }
}
