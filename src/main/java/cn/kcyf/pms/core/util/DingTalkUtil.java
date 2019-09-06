package cn.kcyf.pms.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DingTalkUtil {
    @Value("${dingtalk.service}")
    private String service = "https://oapi.dingtalk.com";
    @Value("${dingtalk.appid}")
    private String appid = "dingoaugcl42xapwtntrkh";
    @Value("${dingtalk.appsecret}")
    private String appsecret = "ahTZfAxGtKUrSprLS2eeRbbaEIjtiRRhrdWD_epV7GDcRDbaDVraxn-SOQ5tNCiN";
    @Value("${dingtalk.getuserinfo_bycode}")
    private String getuserinfo_bycode = "/sns/getuserinfo_bycode";

    public JSONObject getUserInfoByCode(String code) {
        OapiSnsGetuserinfoBycodeResponse response = null;
        try {
            DefaultDingTalkClient  client = new DefaultDingTalkClient(service + getuserinfo_bycode);
            OapiSnsGetuserinfoBycodeRequest request = new OapiSnsGetuserinfoBycodeRequest();
            request.setTmpAuthCode(code);
            response = client.execute(request, appid, appsecret);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return JSON.parseObject(response.getBody());
    }
}
