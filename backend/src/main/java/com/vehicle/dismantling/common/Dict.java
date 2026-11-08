package com.vehicle.dismantling.common;

import java.util.List;

/** 报废汽车拆解厂业务字典。 */
public final class Dict {

    private Dict() {
    }

    public static final List<String> SOURCE_TYPES = List.of("个人", "单位", "保险", "执法");
    public static final List<String> VEHICLE_STATUS = List.of("已进场", "拆解中", "已拆解", "已注销");
    public static final List<String> ASSEMBLY_TYPES = List.of("发动机", "变速器", "方向机", "前后桥", "车架");
    public static final List<String> WASTE_TYPES = List.of("废机油", "废电瓶", "废三元催化", "废冷媒");
    public static final List<String> CANCELLATION_STATUS = List.of("待办理", "办理中", "已注销");

    public static boolean contains(List<String> values, String value) {
        return value != null && values.contains(value);
    }
}
