package com.bocom.service;

import com.bocom.domain.SpaceManage;

import java.util.List;
import java.util.Map;


/*****
 * 类名称：SpaceMangeService
 * 类描述：空间管理服务
 * 创建人：donghongguang
 * 创建时间：2017年4月10日 上午11:34:37
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
public interface SpaceManageService
{
    /**
     * 添加
     * 
     * @param spaceMange
     * @return
     */
    public int addSpaceMange(SpaceManage spaceManage);

    /**
     * 删除
     * 
     * @param spaceMange
     * @return
     * @author donghongguang
     * @date 2014年12月17日
     */
    public int delete(SpaceManage spaceMange);

    /**
     * 获取单个
     * 
     * @param spaceMange
     * @return
     */
    public SpaceManage getSpaceMange(SpaceManage spaceManage);

    /**
     * 获取列表
     * 
     * @param spaceMange
     * @return
     */
    public List<SpaceManage> getSpaceMangeList(SpaceManage spaceManage);

    /**
     * 更新
     * 
     * @param spaceMange
     * @return
     */
    public int updateSpaceMange(SpaceManage spaceManage);

    /**
     * 查询用户量
     * @param map
     * @return
     */
    public int selectUserCount(Map map);
   
}
