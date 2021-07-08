package com.phoosop.springbootmultidatasource.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class StatusConstants {

    @Getter
    @AllArgsConstructor
    public enum HttpConstants {

        SUCCESS("0", "Success"),
        FAILED_TO_CONVERT_VALUE_TO_ENUM("31329", "Failed to convert value to enum"),

        NOT_FOUND("TEMP31997", "Not found"),
        BAD_REQUEST("TEMP31998", "Bad request"),
        INTERNAL_SERVER_ERROR("TEMP31999", "Internal server error");

        private final String code;
        private final String desc;

    }

}
