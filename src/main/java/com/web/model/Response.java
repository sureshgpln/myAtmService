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
public class Response {

    private List<Currency> responseBody;
    private String responseDesc;
    private String responseStatus;

    public Response(){
        this.responseBody = new ArrayList<>();
    }

    public void setResponseBody(List<Currency> currencies){
        this.responseBody.addAll(currencies);
    }

    public List<Currency> getResponseBody(){
        return responseBody;
    }
}
