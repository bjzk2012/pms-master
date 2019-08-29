package cn.kcyf.pms.core.beetl;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ToolsSupport {
    public boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else {
            if (o instanceof String) {
                if (o.toString().trim().equals("")) {
                    return true;
                }
            } else if (o instanceof List) {
                if (((List)o).size() == 0) {
                    return true;
                }
            } else if (o instanceof Map) {
                if (((Map)o).size() == 0) {
                    return true;
                }
            } else if (o instanceof Set) {
                if (((Set)o).size() == 0) {
                    return true;
                }
            } else if (o instanceof Object[]) {
                if (((Object[])o).length == 0) {
                    return true;
                }
            } else if (o instanceof int[]) {
                if (((int[])o).length == 0) {
                    return true;
                }
            } else if (o instanceof long[] && ((long[])o).length == 0) {
                return true;
            }

            return false;
        }
    }

    public String[] split(String text, String regex){
        if (!isEmpty(text)){
            return text.split(regex);
        }
        return new String[0];
    }

}
