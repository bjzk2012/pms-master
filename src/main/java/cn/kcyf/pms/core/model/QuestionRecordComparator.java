package cn.kcyf.pms.core.model;

import cn.kcyf.pms.modular.business.entity.QuestionRecord;

import java.util.Comparator;

public class QuestionRecordComparator implements Comparator<QuestionRecord> {
    public int compare(QuestionRecord o1, QuestionRecord o2) {
        return o2.getCreateTime().compareTo(o1.getCreateTime());
    }
}
