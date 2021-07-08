package com.phoosop.springbootmultidatasource.exception;

import com.phoosop.springbootmultidatasource.model.StatusConstants;
import lombok.Getter;

import static com.phoosop.springbootmultidatasource.model.StatusConstants.*;


@Getter
public class ServiceException extends RuntimeException {

    private final HttpConstants status;
    private String text;

    public ServiceException(HttpConstants status) {
        super(status.getDesc(), null);
        this.status = status;
    }

    public ServiceException(String text, HttpConstants status) {
        super(status.getDesc(), null);
        this.status = status;
        this.text = text;
    }

}
