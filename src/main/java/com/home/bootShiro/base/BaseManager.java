package com.home.bootShiro.base;

import com.home.bootShiro.builder.BaseConditionBuilder;
import com.home.bootShiro.base.BaseDao;
import com.home.bootShiro.base.BaseDO;
import com.home.bootShiro.enums.DelFlagEnum;
import com.home.bootShiro.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author guxc
 * @date 2020/3/1
 */
@Slf4j
public class BaseManager<T extends BaseDO, B extends BaseConditionBuilder> {

    @Autowired
    private BaseDao<T> dao;

    /**
     * 泛型实际类型
     */
    private Class clz;

    private Class bClz;


    public BaseManager() {
        Type[] clzs = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments();
        if(null == clzs || clzs.length < 2){
            throw new RuntimeException("泛型参数传入异常");
        }
        clz = (Class) clzs[0];
        bClz = (Class) clzs[1];
    }

    /**
     * 保存
     *
     * @param model
     * @return
     */
    public int save(T model) {
        return null == assembleModel(model) ? 0 : dao.insertSelective(model);
    }

    private T assembleModel(T model){
        if(null == model){
            return null;
        }
        model.setDelFlag(DelFlagEnum.PERSISTENT.getCode());
        model.setCreateTime(new Date());
        try {
            if (StringUtils.isEmpty(model.getCreateUser())) {
                model.setCreateUser(UserUtil.getUserName());
            }
            if (StringUtils.isEmpty(model.getUpdateUser())) {
                model.setUpdateUser(model.getCreateUser());
            }
        } catch (Exception e) {
            log.info("设置创建人失败，预计是通过mq发起创建，设置创建人为Mq，错误信息: {}", e);
            model.setCreateUser("Mq");
            model.setUpdateUser("Mq");
        }
        model.setUpdateTime(new Date());
        return model;
    }

    /**
     * 保存并获取主键
     * @param model
     * @return
     */
    public int saveGenerateKey(T model){
        return null == assembleModel(model)
                ? 0
                : dao.insertUseGeneratedKeys(model);
    }

    /**
     * 批量保存
     *
     * @param models
     * @return
     */
    public int save(List<T> models) {
        if(CollectionUtils.isEmpty(models)){
            return 0;
        }
        for(T model : models) {
            model.setDelFlag(DelFlagEnum.PERSISTENT.getCode());
            model.setCreateTime(new Date());
            model.setCreateUser(UserUtil.getUserName());
            model.setUpdateUser(UserUtil.getUserName());
            model.setUpdateTime(new Date());
        }
        return dao.insertList(models);
    }

    /**
     * 有则更新，无则插入
     * @param model
     * @return
     */
    public int insertOrUpdate(T model){
        Long id = model.getId();
        if(null == id) {
            // 如果id为空，保存
            return save(model);
        }
        Condition condition = getConditionBuilder()
                .id(id)
                .delFlag(DelFlagEnum.PERSISTENT.getCode())
                .build();
        return insertOrUpdateByCondition(model, condition);
    }

    /**
     * 有则更新，无则插入
     * @param model
     * @param condition
     * @return
     */
    protected int insertOrUpdateByCondition(T model, Condition condition){
        if(null == model || null ==condition){
            throw new RuntimeException("ss");
        }
        List<T> existDOS = dao.selectByCondition(condition);
        if(CollectionUtils.isEmpty(existDOS)){
            return save(model);
        }
        return updateByConditionSelective(model, condition);
    }

    /**
     * 更新记录
     *
     * @param model
     * @return
     */
    public int update(T model) {
        if (null == model || null == model.getId())  {
            throw new RuntimeException("进行更新操作时字段id不能空");
        }
        String updateUser;
        try {
            updateUser = UserUtil.getUserName();
            model.setUpdateUser(updateUser);
        } catch (Exception e) {
            log.info("设置更新人失败，预计为mq消息发起的更新，请检查：{}", e);
            model.setUpdateUser("Mq");
        }
        model.setUpdateTime(new Date());
        Condition condition = getConditionBuilder()
                .id(model.getId())
                .build();
        return dao.updateByConditionSelective(model, condition);
    }

    /**
     * 根据条件更新
     * @param model
     * @param condition
     * @return
     */
    public int updateByConditionSelective(T model, Condition condition){
        if(null==model || null==condition){
            throw  new RuntimeException();
        }

        model.setUpdateTime(new Date());
        try {
            // 这里把创建人置空，防止把创建人覆盖掉
            model.setCreateUser(null);
            model.setUpdateUser(Optional.ofNullable(model.getUpdateUser()).orElse(UserUtil.getUserName()));
        } catch (Exception e) {
            log.info("设置更新人失败，预计为mq消息发起的更新，请检查：{}", e);
        }
        return dao.updateByConditionSelective(model, condition);
    }

    /**
     * 删除（根据id）
     * @param model
     * @return
     */
    public int delete(T model){
        if(null == model){
            return 0;
        }

        if (null == model.getId()){
            throw new RuntimeException("删除时id字段不能为空");
        }
        Condition condition = getConditionBuilder()
                .id(model.getId())
                .build();
        return deleteByCondition(condition);
    }

    public List<T> selectAll() {
        Condition condition = getConditionBuilder()
                .delFlag(DelFlagEnum.PERSISTENT.getCode())
                .build();
        return dao.selectByCondition(condition);
    }

    public int delete(Long id){
        if(null == id){
            return 0;
        }
        Condition condition = getConditionBuilder().id(id).build();
        return deleteByCondition(condition);
    }

    /**
     * 根据条件删除
     * @param condition
     * @return
     */
    public int deleteByCondition(Condition condition){
        try {
            BaseDO model = (BaseDO) clz.newInstance();
            model.setDelFlag(DelFlagEnum.DELETED.getCode());
            return dao.updateByConditionSelective((T)model,condition);
        } catch (IllegalAccessException | InstantiationException e){
            log.error("删除失败：{}", e);
        }
        return 1;
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    public T selectById(Long id){
        if(null == id){
            log.warn("根据id查询信息时传入id为空");
            return null;
        }
        Condition condition = getConditionBuilder()
                .id(id)
                .delFlag(DelFlagEnum.PERSISTENT.getCode())
                .build();
        return selectOneByCondition(condition);
    }

    /**
     * 根据id批量查询
     * @param ids
     * @return
     */
    public List<T> selectById(List<Long> ids){
        if(CollectionUtils.isEmpty(ids)){
            log.warn("根据id查询信息时传入id为空");
            return new ArrayList<>();
        }
        Condition condition = getConditionBuilder()
                .idList(ids)
                .delFlag(DelFlagEnum.PERSISTENT.getCode())
                .build();
        return dao.selectByCondition(condition);
    }

    /**
     * 根据创建人和创建时间查询记录
     * @param createUser
     * @return
     */
    public T selectLatestByCreateUser(String createUser) {
        Condition condition = getConditionBuilder()
                .createUser(createUser)
                .delFlag(DelFlagEnum.PERSISTENT.getCode())
                .desc("createTime")
                .build();
        return dao.selectByCondition(condition)
                .stream()
                .findFirst()
                .orElse(null);
    }

    /**
     * 查询单条记录
     * 如果数据存在多条会报错
     * @param condition
     * @return
     */
    public T selectOneByCondition(Condition condition){
        List<T> doList = dao.selectByCondition(condition);
        if(!CollectionUtils.isEmpty(doList) && doList.size() != 1){
            log.error("数据结果不止一条，请检查数据和查询条件：### 查询结果 {}， 条件 {}", doList, condition.getOredCriteria());
            throw new RuntimeException("数据结果不止一条，请检查查询条件以及数据");
        }
        return CollectionUtils.isEmpty(doList) ? null : doList.get(0);
    }

    /**
     * 根据id删除数据
     * @param ids
     * @return
     */
    public int deleteByIds(List<Long> ids){
        if(CollectionUtils.isEmpty(ids)){
            return 0;
        }
        Condition condition = getConditionBuilder()
                .idList(ids)
                .delFlag(DelFlagEnum.PERSISTENT.getCode())
                .build();
        return deleteByCondition(condition);
    }

    /**
     * 获取condition
     *
     * @return
     */
    final protected B getConditionBuilder() {
        return getConditionBuilder(clz);
    }
    final protected B getConditionBuilder(Class clz) {
        B conditionBuilder = null;
        try {
            conditionBuilder = (B) bClz.newInstance();
            conditionBuilder = (B) bClz.getMethod("init", Class.class).invoke(conditionBuilder, clz);
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            log.error("初始化ConditionBuilder失败：{}", e);
        }
        if(null==conditionBuilder){
            throw new RuntimeException();
        }
        return conditionBuilder;
    }
}
