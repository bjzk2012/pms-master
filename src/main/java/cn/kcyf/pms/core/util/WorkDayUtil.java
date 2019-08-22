package cn.kcyf.pms.core.util;

import cn.kcyf.pms.core.enumerate.DayType;
import cn.kcyf.commons.http.SimpleClient;
import cn.kcyf.commons.http.TApi;
import cn.kcyf.commons.http.enumerate.DataType;
import cn.kcyf.commons.http.enumerate.RequestMethod;
import cn.kcyf.commons.utils.DateUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WorkDayUtil {
    private static final String HOLIDAY_URL = "http://api.goseek.cn/Tools/holiday";

    public static DayType request(Date time) {
        TApi tApi = new TApi(HOLIDAY_URL, RequestMethod.GET, DataType.JSON, "");
        SimpleClient client = new SimpleClient();
        Map<String, String> params = new HashMap<String, String>();
        String date = DateUtils.format(time, "yyyyMMdd");
        params.put("date", date);
        JSONObject result = JSON.parseObject(client.send(tApi, params));
        return DayType.valueOfOrdinal(result.getInteger("data"));
    }

    public static Date get(Date time, int day){
        int i = 1;
        for(int k = 0; k < 15; k++){
            Date t = DateUtils.addTime(time, Calendar.DAY_OF_YEAR, -k-1);
            if (WorkDayUtil.request(t).equals(DayType.WORKDAY)){
                i++;
            }
            if (i > day){
                return t;
            }
        }
        return null;
    }

}
