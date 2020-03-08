package com.home.bootShiro.builder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;

import java.util.*;


public abstract class BaseConditionBuilder<T extends BaseConditionBuilder> {

    /**
     * 条件为equal的map
     */
    protected Map<String, Object> equalMap;

    /**
     * 条件为in的条件map
     */
    protected Map<String, List> inMap;

    /**
     * 条件为like的map
     * 只会在末尾 + "%"
     */
    protected Map<String, String> likeMap;

    /**
     * 条件为between的map
     */
    protected Map<String, List<Object>> betweenMap;

    /**
     * 条件为 null 的字段
     */
    protected List<String> nullList;

    /**
     * 查询字段
     */
    protected String[] properties;

    /**
     * 是否唯一
     */
    protected boolean distinct;

    protected Class clz;

    /**
     * 升序字段
     */
    protected String ascField;

    /**
     * 降序字段
     */
    protected String descField;

    /**
     * 初始化
     *
     * @param clz
     * @return
     */
    public T init(Class clz) {
        initBase(clz);
        return (T) this;
    }

    ;

    /**
     * 初始化各个map
     *
     * @param clz
     */
    protected void initBase(Class clz) {
        this.clz = clz;
        this.equalMap = new HashMap();
        this.inMap = new HashMap();
        this.likeMap = new HashMap();
        this.betweenMap = new HashMap();
        this.nullList = new ArrayList<String>();
    }

    public BaseConditionBuilder() {

    }

    /**
     * like条件
     * 如果传入为空不会加入条件
     *
     * @param prop  字段名
     * @param value 值，最终会在值后面加上 "%"，例如：传入value为 "test"，最终查询条件为"test%"
     * @return
     */
    public T andLike(String prop, String value) {
        if (!StringUtils.isEmpty(value)) {
            likeMap.put(prop, value + "%");
        }
        return (T) this;
    }

    /**
     * 相等
     * @param prop
     * @param value
     * @return
     */
    public T andEqualTo(String prop, Object value) {
        if (null != value) {
            equalMap.put(prop, value);
        }
        return (T) this;
    }

    public T andIn(String prop, List value) {
        if (!CollectionUtils.isEmpty(value)) {
            inMap.put(prop, value);
        }
        return (T) this;
    }

    public T andIsNull(String prop) {
        if(StringUtils.isNotEmpty(prop) && !nullList.contains(prop)) {
            nullList.add(prop);
        }
        return (T)this;
    }

    /**
     * between查询条件构造
     *
     * @param prop
     * @param start
     * @param end
     * @return
     */
    public T between(String prop, Object start, Object end) {
        if(StringUtils.isBlank(prop)){
            throw new RuntimeException("between字段不能为空");
        }
        if (null != start || null != end) {
            // 如果存在一个条件不为空，两个条件均不能为空
            if(null == start){
                throw new RuntimeException("between开始条件（start）不能为空");
            }
            if(null == end){
                throw new RuntimeException("between结束条件不能为空");
            }
            betweenMap.put(prop, Arrays.asList(start, end));
        }
        return (T) this;
    }

    public T id(Long id) {
        if (null != id) {
            equalMap.put("id", id);
        }
        return (T) this;
    }

    public T idList(List<Long> idList) {
        if (!CollectionUtils.isEmpty(idList)) {
            inMap.put("id", idList);
        }
        return (T) this;
    }

    public T stageList(List<Integer> stages) {
        if (!CollectionUtils.isEmpty(stages)) {
            inMap.put("stage", stages);
        }
        return (T) this;
    }

    public T delFlag(Integer delFlag) {
        if (null != delFlag) {
            equalMap.put("delFlag", delFlag);
        }
        return (T) this;
    }

    public T createTime(Date createTime) {
        if (null != createTime) {
            equalMap.put("createTime", createTime);
        }
        return (T) this;
    }

    public T createUser(String createUser) {
        if (!StringUtils.isEmpty(createUser)) {
            equalMap.put("createUser", createUser);
        }
        return (T) this;
    }

    public T updateTime(Date updateTime) {
        if (null != updateTime) {
            equalMap.put("updateTime", updateTime);
        }
        return (T) this;
    }

    public T updateUser(String updateUser) {
        if (!StringUtils.isEmpty(updateUser)) {
            equalMap.put("updateUser", updateUser);
        }
        return (T) this;
    }

    public T asc(String ascField) {
        if (!StringUtils.isEmpty(ascField)) {
            this.ascField = ascField;
        }
        return (T) this;
    }

    public T desc(String descField) {
        if (!StringUtils.isEmpty(descField)) {
            this.descField = descField;
        }
        return (T) this;
    }

    public T properties(String... properties) {
        if (null != properties && properties.length > 0) {
            this.properties = properties;
        }
        return (T) this;
    }

    public T distinct() {
        this.distinct = true;
        return (T) this;
    }

    public Condition build() {
        Condition condition = ConditionBuilderHelper.build(clz, equalMap, inMap, likeMap, betweenMap, ascField, descField);
        // 需要选择的属性
        if (null != properties && properties.length > 0) {
            condition.selectProperties(properties);
        }
        // 是否去重
        condition.setDistinct(distinct);
        // 增加为空的条件
        nullList.forEach(p ->
                condition.and()
                        .andIsNull(p)
        );
        return condition;
    }
}
