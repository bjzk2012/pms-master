package cn.kcyf.pms.core.enumerate;

public enum DayType {
    WORKDAY("工作日"),
    PLAYDAY("休息日"),
    HOLIDAY("节假日");

    DayType(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public static DayType valueOfOrdinal(int ordinal) {
        if (ordinal < 0 || ordinal > values().length) {
            return null;
        }
        return values()[ordinal];
    }
}
