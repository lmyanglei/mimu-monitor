package cn.mimukeji.common.bean;

import cn.mimukeji.common.type.ResultType;

public class InterfaceResult {
    
    private String code;

    private String message;

    private Object data;

    public InterfaceResult(){
        this.failure();
    }
    
    private InterfaceResult setResultType(ResultType resultType) {
        this.message = resultType.getName();
        this.code = resultType.getCode();
        return this;
    }
    
    public InterfaceResult failure(ResultType resultType) {
        this.setResultType(resultType);
        return this;
    }
    
    public InterfaceResult success(ResultType resultType) {
        this.setResultType(resultType);
        return this;
    }
    
    public InterfaceResult success(Object data) {
        this.setResultType(ResultType.SUCCESS);
        this.data = data;
        return this;
    }
    
    public InterfaceResult failure() {
        this.setResultType(ResultType.ERROR_SYSTEM_ERROR);
        return this;
    }
    
    public InterfaceResult success() {
        this.setResultType(ResultType.SUCCESS);
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
}
