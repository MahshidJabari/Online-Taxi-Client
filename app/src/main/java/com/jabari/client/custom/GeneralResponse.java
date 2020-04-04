package com.jabari.client.custom;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GeneralResponse {

    private Boolean success;
    private String Message;
    public GeneralResponse(JsonObject body) {
        setMessage(new Gson().fromJson(body.get("message"), String.class));
        setSuccess(new Gson().fromJson(body.get("success"), Boolean.class));
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
