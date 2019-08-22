package cn.kcyf.pms.core.enumerate;

public enum AuthType {
    REGISTER_AUTHCODE("注册验证码", "您的验证码为%s（3分钟有效），用于用户注册，平台工作人员与合作商户都不会问您索要验证码，请勿提供给任何人，以免对您的财产或资金造成损失。"),
    QUESTION_AUTHCODE("问题反馈验证码", "您的验证码为%s（3分钟有效），用于问题反馈，平台工作人员与合作商户都不会问您索要验证码，请勿提供给任何人，以免对您的财产或资金造成损失。"),
    RESTPASSWORD_AUTHCODE("重置密码验证码", "您好%s,您的验证码为%s（3分钟有效），用于重置密码，平台工作人员与合作商户都不会问您索要验证码，请勿提供给任何人，以免对您的财产或资金造成损失。");

    AuthType(String message, String template) {
        this.message = message;
        this.template = template;
    }

    private String message;

    private String template;

    public String getMessage() {
        return message;
    }

    public String getTemplate() {
        return template;
    }
}
