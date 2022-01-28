package com.azat.myretro.utils;


import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Class for generic application exceptions
 *
 */
public class AppException extends Throwable {

	/** error code string, typically used as keys to lookup error messages from resource files */
    @JsonProperty
	private String errCode;
    
	/** optional error message is default language */
    @JsonProperty
    private String errMsg;

	/** optional http status code for the error */
    @JsonProperty
    private int httpCode = -1;

    
    /**  underlying exception, if there is one*/
   @JsonIgnore
   @JsonProperty
   private Throwable exception;


    public AppException(String errCode) {
    	super(errCode);
    	this.errCode = errCode;
    }

    public AppException(String errCode, String errMsg) {
    	super(errMsg);
    	this.errCode = errCode;
    	this.errMsg = errMsg;
    }

    public AppException(String errCode, String errMsg, int httpCode) {
    	super(errMsg);
    	this.errCode = errCode;
    	this.errMsg = errMsg;
    	this.httpCode = httpCode;
    }
    
    public AppException(String errCode, Throwable t) {
    	super(errCode, t);
    	this.errCode = errCode;
    	this.exception = t;
    }

    public AppException(String errCode, String errMsg, Throwable t) {
    	super(errMsg, t);
    	this.errCode = errCode;
    	this.errMsg = errMsg;
    	this.exception = t;
    }

    public AppException(String errCode, String errMsg, Throwable t, int httpCode) {
    	super(errMsg, t);
    	this.errCode = errCode;
    	this.errMsg = errMsg;
    	this.exception = t;
    	this.httpCode = httpCode;
    }
    
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public Throwable getException() {
		return exception;
	}

    public String getFullErrorMsg(boolean addExceptionMsg) {
    	String err = this.errCode;
    	if(this.errMsg!=null) err += ": " + this.errMsg;
    	if(addExceptionMsg && this.exception!=null) err += ": " + this.exception.getMessage();
    	return err;
    }
}

