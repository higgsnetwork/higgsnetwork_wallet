package com.higgs.network.wallet.common.dao;

import com.higgs.network.wallet.common.domain.BaseModel;

import java.util.List;


/**
 * 该接口提供业务逻辑最基本的服务，所有的业逻辑类都必须实现此接口，这样该业务逻辑类对应
 * 的action就免去了写基本selectList、insert、update、toEdit、deletes麻烦s
 *
 * @author :
 * @date :
 */
public interface DaoManager<E extends BaseModel> extends SqlMapper {
    /**
     * 添加
     *
     * @param e the e
     * @return int
     */
    int insert(E e);

    /**
     * 删除
     *
     * @param e the e
     * @return int
     */
    int delete(E e);

    /**
     * 删除
     *
     * @param e
     * @return int
     */
    int deleteAll();

    /**
     * 修改
     *
     * @param e the e
     * @return int
     */
    int update(E e);

    /**
     * 修改一条记录
     *
     * @param e the e
     * @return int
     */
    int updateByPrimaryKey(E e);

    /**
     * 修改一条记录
     *
     * @param e the e
     * @return int
     */
    int updateByPrimaryKeySelective(E e);

    /**
     * 查询一条记录
     *
     * @param e the e
     * @return e
     */
    E selectOne(E e);

    /**
     * 分页查询
     *
     * @param e the e
     * @return list
     */
    List<E> selectPageList(E e);

    /**
     * 根据条件查询所有
     *
     * @param e the e
     * @return list
     */
    List<E> selectList(E e);

    /**
     * Select page count int.
     *
     * @param e the e
     * @return the int
     */
    int selectPageCount(E e);

    /**
     * 根据ID来删除一条记录
     *
     * @param id the id
     * @return the int
     */
    int deleteByPrimaryKey(int id);

    /**
     * 根据ID查询一条记录
     *
     * @param id the id
     * @return e
     */
    E selectByPrimaryKey(Long id);


    /**
     * Select by selected list.
     *
     * @param e the e
     * @return the list
     */
    List<E> selectBySelected(E e);
}
