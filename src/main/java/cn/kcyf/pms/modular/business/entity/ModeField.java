package cn.kcyf.pms.modular.business.entity;

import cn.kcyf.orm.jpa.entity.TableDomain;
import cn.kcyf.pms.core.enumerate.FieldType;
import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.pms.modular.system.entity.Dict;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "c_mode_field")
@ApiModel("内容模型属性")
public class ModeField extends TableDomain {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mode_id")
    @JSONField(serialize = false, deserialize = false)
    private Mode mode;

    public Long getModeId(){
        if (mode != null){
            return mode.getId();
        }
        return null;
    }
    /**
     * 字段
     */
    @Column(name = "field", nullable = false)
    private String field;
    /**
     * 标题
     */
    @Column(name = "label", nullable = false)
    private String label;
    /**
     * 默认值
     */
    @Column(name = "def_value")
    private String defValue;
    /**
     * 是否自定义
     */
    @Column(name = "custom", nullable = false)
    private Boolean custom = false;
    /**
     * 可选项键
     */
    @Column(name = "opt_keys")
    private String optKeys;
    /**
     * 可选项值
     */
    @Column(name = "opt_values")
    private String optValues;

    @ManyToOne
    @JoinColumn(name = "dict_id")
    @JSONField(serialize = false, deserialize = false)
    private Dict dict;

    public Long getDictId(){
        if (dict != null){
            return dict.getId();
        }
        return null;
    }

    public Map<String, String> getMap(){
        Map<String, String> map = new HashMap<String, String>();
        if (this.custom){
            if (!StringUtils.isEmpty(this.optKeys) && !StringUtils.isEmpty(this.optValues)){
                String[] keys = this.optKeys.split(",");
                String[] values = this.optValues.split(",");
                for (int i = 0; i < keys.length; i++){
                    map.put(keys[i], values[i]);
                }
            }
        } else {
            if (this.dict != null) {
                Set<Dict> dicts = this.dict.getDicts();
                if (dicts != null && !dicts.isEmpty()) {
                    for (Dict dict : dicts) {
                        map.put(dict.getCode(), dict.getName());
                    }
                }
            }
        }
        return map;
    }
    /**
     * 最小值
     */
    @Column(name = "min")
    private Integer min = 1;
    /**
     * 最大值
     */
    @Column(name = "max")
    private Integer max = 255;
    /**
     * 最小长度
     */
    @Column(name = "min_lenght")
    private Integer minLenght = 1;
    /**
     * 最大长度
     */
    @Column(name = "max_lenght")
    private Integer maxLenght = 255;
    /**
     * 文本行数
     */
    @Column(name = "rows")
    private Integer rows = 4;
    /**
     * 文本列数
     */
    @Column(name = "cols")
    private Integer cols = 12;
    /**
     * 帮助文本
     */
    @Column(name = "help")
    private String help;
    /**
     * 类型
     */
    @Column(name = "type", nullable = false)
    @Enumerated
    private FieldType type;

    public String getTypeMessage(){
        return type.getMessage();
    }
    /**
     * 是否必填
     */
    @Column(name = "required", nullable = false)
    private Boolean required;
    /**
     * 是否独占一行
     */
    @Column(name = "single", nullable = false)
    private Boolean single;
    /**
     * 是否显示
     */
    @Column(name = "display", nullable = false)
    private Boolean display;
    /**
     * 宽度
     */
    @Column(name = "width")
    private Integer width;
    /**
     * 高度
     */
    @Column(name = "height")
    private Integer height;
    /**
     * 状态
     */
    @Column(name = "status")
    private Status status = Status.ENABLE;
    public String getStatusMessage(){
        return status.getMessage();
    }
    /**
     * 排序
     */
    @Column(name = "sort", nullable = false)
    private Integer sort = 0;

}
