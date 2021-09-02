package com.web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Setter
@Getter
@ToString
public class ResponseWrapper {

    private List<Currency> currencies;
    private String responseCode;
    private String responseDesc;
    private String responseStatus;

    public ResponseWrapper(){
        this.currencies = new ArrayList<>();
    }

    public void setResponseBody(List<Currency> currencies){
        this.currencies.addAll(currencies);
    }

    /*public void setResponseCode(String responseCode){
        this.responseCode = responseCode;
    }

    public void setResponseDesc(String responseDesc){
        this.responseDesc = responseDesc;
    }

    public void setResponseStatus(String responseStatus){
        this.responseStatus = responseStatus;
    }*/

    public List<Currency> getResponseBody(){
        return currencies;
    }

    /*public String getResponseCode(){
        return responseCode;
    }

    public String getResponseDesc(){
        return responseDesc;
    }

    public String getResponseStatus(){
        return responseStatus;
    }*/

    /*@Override
    public String toString(){
        return "ResponseWrapper:{" +
                " resultCode=" + getResponseCode() +
                " resultStatus=" + getResponseStatus() +
                " resultDesc=" + getResponseDesc() +
                " resultBody=" + getResponseBody() +
                " }";
    }*/
}
