package cn.kcyf.pms.core.enumerate;

public enum QuestionRecordType {
    INITIATE("发起", QuestionStatus.DRAFT),
    CONFIRM("确认", QuestionStatus.ACTIVE),
    APPOINT("指派", QuestionStatus.ACTIVE),
    SOLVE("解决", QuestionStatus.SOLVED),
    EDIT("编辑", QuestionStatus.ACTIVE),
    CLOSE("关闭", QuestionStatus.CLOSED),
    ACTIVE("激活", QuestionStatus.ACTIVE);

    QuestionRecordType(String message, QuestionStatus status) {
        this.message = message;
        this.status = status;
    }

    private String message;

    private QuestionStatus status;

    public String getMessage() {
        return message;
    }

    public QuestionStatus getStatus() {
        return status;
    }
}
