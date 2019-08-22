package cn.kcyf.pms.core.enumerate;

public enum ReadType {
    UNREAD("未读"),
    READ("已读");

    ReadType(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }
}
