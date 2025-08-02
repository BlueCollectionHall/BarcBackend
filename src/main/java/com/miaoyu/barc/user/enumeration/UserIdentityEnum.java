package com.miaoyu.barc.user.enumeration;

import com.miaoyu.barc.api.work.enumeration.WorkStatusEnum;
import com.miaoyu.barc.utils.dto.ValueLabelDto;

import java.util.Arrays;
import java.util.List;

public enum UserIdentityEnum {
    USER("用户"),
    MANAGER("管理者");

    private static final List<ValueLabelDto>  CACHED_OPTIONS = Arrays.stream(WorkStatusEnum.values())
            .map(e -> new ValueLabelDto(e.name(), e.getName()))
            .toList();
    public static List<ValueLabelDto> getOptions() {
        return CACHED_OPTIONS;
    }

    private final String displayName;

    UserIdentityEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
