package cn.kcyf.pms.core.enumerate;

public enum FieldType {
    TEXT("文本框"),
    PASSWORD("密码框"),
    NUMBER("数字框"),
    COMBOBOX("下拉框"),
    CHECKBOX("复选框"),
    SWITCH("开关"),
    REDIO("单选框"),
    TEXTAREA("文本域"),
    RICHTEXT("富文本"),
    MACKDOWN("Mackdown"),
    TREE("树形选择框"),
    IMAGE("图片"),
    FILE("文件"),
    FILES("多文件");

    FieldType(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }
}
