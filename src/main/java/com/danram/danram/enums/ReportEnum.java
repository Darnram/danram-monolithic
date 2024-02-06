package com.danram.danram.enums;

import lombok.Getter;

@Getter
public enum ReportEnum {
    광고(0L),
    욕설(1L),
    도배(2L),
    사칭(3L),
    정치(4L),
    음란물(5L);

    private Long value;

    ReportEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static ReportEnum findByValue(Long value) {
        for (ReportEnum category : ReportEnum.values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }
        return null;
    }
}
