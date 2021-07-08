package com.phoosop.springbootmultidatasource.controller;

import com.phoosop.springbootmultidatasource.model.Response;
import com.phoosop.springbootmultidatasource.model.Status;

import static com.phoosop.springbootmultidatasource.model.StatusConstants.HttpConstants;

public interface BaseController {

    default <T> Response<T> success() {
        return new Response<>(new Status(HttpConstants.SUCCESS), null);
    }

    default <T> Response<T> success(T data) {
        return new Response<>(new Status(HttpConstants.SUCCESS), data);
    }

}
