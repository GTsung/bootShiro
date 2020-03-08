package com.home.bootShiro.builder;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @date 2018/10/12
 */
public class ConditionBuilderHelper {

    private ConditionBuilderHelper() {

    }

    /**
     * 构造查询条件
     *
     * @param clz
     * @param equalMap
     * @param inMap
     * @param ascField
     * @param descField
     * @return
     */
    public static final Condition build(Class clz,
                                        Map<String, Object> equalMap,
                                        Map<String, List> inMap,
                                        String ascField, String descField) {
        return build(clz, equalMap, inMap, null, ascField, descField);
    }

    /**
     * 构造查询条件
     *
     * @param clz
     * @param equalMap
     * @param inMap
     * @param likeMap
     * @param ascField
     * @param descField
     * @return
     */
    public static final Condition build(Class clz,
                                        Map<String, Object> equalMap,
                                        Map<String, List> inMap,
                                        Map<String, String> likeMap,
                                        String ascField, String descField) {
        return build(clz, equalMap, inMap, likeMap, null, ascField, descField);
    }

    /**
     * 构造查询条件
     *
     * @param clz
     * @param equalMap   条件为 = 的map
     * @param inMap      条件为 in 的map
     * @param likeMap    条件为 like 的map
     * @param betweenMap 条件为 between 的map
     * @param ascField   升序字段
     * @param descField  降序字段
     * @return
     */
    public static final Condition build(Class clz,
                                        Map<String, Object> equalMap,
                                        Map<String, List> inMap,
                                        Map<String, String> likeMap,
                                        Map<String, List<Object>> betweenMap,
                                        String ascField, String descField) {
        Condition condition = new Condition(clz);
        Example.Criteria criteria = condition.and();
        //and
        if (!CollectionUtils.isEmpty(equalMap)) {
            for (Map.Entry<String, Object> entry : equalMap.entrySet()) {
                criteria.andEqualTo(entry.getKey(), entry.getValue());
            }
        }
        //in
        if (!CollectionUtils.isEmpty(inMap)) {
            for (Map.Entry<String, List> entry : inMap.entrySet()) {
                criteria.andIn(entry.getKey(), entry.getValue());
            }
        }
        // like
        if (!CollectionUtils.isEmpty(likeMap)) {
            for (Map.Entry<String, String> entry : likeMap.entrySet()) {
                criteria.andLike(entry.getKey(), entry.getValue());
            }
        }
        // between
        if (!CollectionUtils.isEmpty(betweenMap)) {
            for (Map.Entry<String, List<Object>> entry : betweenMap.entrySet()) {
                if (CollectionUtils.isEmpty(entry.getValue()) || entry.getValue().size() != 2) {
                    // between条件参数必须为2
                    throw new RuntimeException(String.format("between条件中字段%s对应的属性值为空或者传入数量不为2: %s",
                            entry.getKey(), entry.getValue()));
                }
                criteria.andBetween(entry.getKey(), entry.getValue().get(0), entry.getValue().get(1));
            }
        }
        if (!StringUtils.isEmpty(ascField)) {
            condition.orderBy(ascField).asc();
        }
        if (!StringUtils.isEmpty(descField)) {
            condition.orderBy(descField).desc();
        }

        return condition;
    }

}
