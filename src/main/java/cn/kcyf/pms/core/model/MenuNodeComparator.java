package cn.kcyf.pms.core.model;

import java.util.Comparator;

public class MenuNodeComparator implements Comparator<MenuNode> {

    public int compare(MenuNode o1, MenuNode o2) {
        if (o1.getLevels() == o2.getLevels()){
            return o1.getSort().compareTo(o2.getSort());
        } else {
            return o1.getLevels().compareTo(o2.getLevels());
        }
    }
}
