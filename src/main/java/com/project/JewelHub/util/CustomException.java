package com.project.JewelHub.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private final int status;
    public CustomException(String error, int status) {
        super(error);
        this.status = status;
    }
}
