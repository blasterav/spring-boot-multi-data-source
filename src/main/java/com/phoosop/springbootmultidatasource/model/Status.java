package com.phoosop.springbootmultidatasource.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.phoosop.springbootmultidatasource.model.StatusConstants.*;


@Getter
@AllArgsConstructor
public class Status {

    private String code;

    private String message;

    public Status(HttpConstants httpConstantsExpect) {
        this.code = httpConstantsExpect.getCode();
        this.message = httpConstantsExpect.getDesc();
    }

}
