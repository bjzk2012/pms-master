package cn.kcyf.pms.core.constant;

import cn.kcyf.pms.core.enumerate.WorkStatus;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constant {
    /**
     * 默认密码
     */
    public final static String DEFAULT_PWD = "111111";

    /**
     * 默认图像
     */
    public final static String DEFAULT_HEAD= "/assets/common/images/head.png";
    /**
     * 默认图片
     */
    public final static String DEFAULT_IMG= "/assets/common/images/default.png";

    /**
     * 默认验证码SESSION_KEY
     */
    public final static String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

    /**
     * 注册验证码SESSION_KEY
     */
    public final static String KAPTCHA_REGISTER_SESSION_KEY = "KAPTCHA_REGISTER_SESSION_KEY";

    /**
     * 问题反馈验证码SESSION_KEY
     */
    public final static String KAPTCHA_QUESTION_SESSION_KEY = "KAPTCHA_QUESTION_SESSION_KEY";

    /**
     * 验证码登录验证码SESSION_KEY
     */
    public final static String KAPTCHA_PHONEVCODE_SESSION_KEY = "KAPTCHA_PHONEVCODE_SESSION_KEY";

    public final static String SMS_QUESTION_FORMAT = "您本次操作验证码为：{0}，有效期为{1}分钟，请尽快验证。";

    // 比较的基础
    // 优先级是 REFUSE>DRAFT>SUBMIT>FINISH
    public final static Map<WorkStatus, Integer> workCompareMap = Collections.unmodifiableMap(new HashMap<WorkStatus, Integer>() {
        {
            put(WorkStatus.REFUSE, 1);
            put(WorkStatus.DRAFT, 2);
            put(WorkStatus.SUBMIT, 3);
            put(WorkStatus.FINISH, 4);
        }
    });

}
