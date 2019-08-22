package cn.kcyf.pms.core.enumerate;

public enum WorkStatus {
    DRAFT("草稿", "保存", "未提交"),
    SUBMIT("已提交", "提交", "待审核"),
    FINISH("已完成", "通过", "已通过"),
    REFUSE("已退回", "拒绝", "已拒绝");
    WorkStatus(String message, String action, String status){
        this.message = message;
        this.action = action;
        this.status = status;
    }
    private String message;
    private String action;
    private String status;

    public String getMessage() {
        return message;
    }

    public String getAction() {
        return action;
    }

    public String getStatus() {
        return status;
    }
}
