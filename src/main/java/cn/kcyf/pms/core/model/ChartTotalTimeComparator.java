package cn.kcyf.pms.core.model;

import com.alibaba.fastjson.JSONObject;

import java.util.Comparator;

public class ChartTotalTimeComparator implements Comparator<JSONObject> {
    public int compare(JSONObject o1, JSONObject o2) {
        return 0 - o1.getString("time").compareTo(o2.getString("time"));
    }
}
