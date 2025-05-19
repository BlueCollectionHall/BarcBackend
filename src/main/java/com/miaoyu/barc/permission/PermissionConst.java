package com.miaoyu.barc.permission;

public class PermissionConst {
    public static final int USER = 1; // 用户 0000 0001

    public static final int JUNIOR_CARRIER = 1 << 1; // 初级搬运者 0000 0010
    public static final int INTERMEDIATE_CARRIER = 1 << 2; // 中级搬运者 0000 0100
    public static final int ADVANCED_CARRIER = 1 << 3; // 高级搬运者 0000 1000

    public static final int JUNIOR_CREATOR = 1 << 1; // 初级创作者 0000 0010
    public static final int INTERMEDIATE_CREATOR = 1 << 2; // 中级创作者 0000 0100
    public static final int ADVANCED_CREATOR = 1 << 3; // 高级创作者 0000 1000

    public static final int FIR_MAINTAINER = 1 << 4; // 一级维护者 0001 0000
    public static final int SEC_MAINTAINER = 1 << 5; // 二级维护者 0010 0000

    public static final int ADMINISTRATOR = 1 << 6; // 管理者 0100 0000
    public static final int ADVANCED_ADMINISTRATOR = 1 << 7; // 大馆长 1000 0000
}
