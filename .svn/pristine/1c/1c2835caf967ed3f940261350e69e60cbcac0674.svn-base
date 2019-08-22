package cn.kcyf.pms.core.model;

import cn.kcyf.pms.core.enumerate.Status;
import cn.kcyf.pms.core.enumerate.YesOrNo;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;

@Data
@ApiModel("菜单树形节点")
public class MenuNode {
    /**
     * 编号
     */
    private Long id;
    /**
     * 菜单编号
     */
    private String code;
    /**
     * 菜单父编号
     */
    private Long parentId;
    public Long getPId(){
        if (parentId != null) {
            return parentId;
        }
        return 0L;
    }
    /**
     * 菜单父编号
     */
    private String pcode;
    /**
     * 当前菜单的所有父菜单编号
     */
    private String pcodes;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单图标
     */
    @JSONField(serialize = false)
    private String icon;
    public String iconSkin(){
        if (!StringUtils.isEmpty(icon)){
            return icon;
        }
        return "";
    }
    /**
     * url地址
     */
    private String url;
    /**
     * 菜单排序号
     */
    private Integer sort;
    /**
     * 菜单层级
     */
    private Integer levels;
    /**
     * 是否是菜单
     */
    private YesOrNo menuFlag;
    /**
     * 备注
     */
    private String description;
    /**
     * 菜单状态
     */
    private Status status;
    /**
     * 是否打开新页面的标识
     */
    private YesOrNo newPageFlag;
    /**
     * 是否打开
     */
    private YesOrNo openFlag;
    public Boolean getOpen(){
        if (openFlag != null){
            return YesOrNo.YES.equals(openFlag);
        }
        return true;
    }
    /**
     * 子节点
     */
    private List<MenuNode> children;
}
