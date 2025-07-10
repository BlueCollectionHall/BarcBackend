package com.miaoyu.barc.utils.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValueLabelDto {
    private String value;
    private String label;

    public ValueLabelDto(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
