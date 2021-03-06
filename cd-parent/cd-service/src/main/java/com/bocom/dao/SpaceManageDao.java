package com.bocom.dao;

import com.bocom.domain.SpaceManage;

import java.util.Map;

/*****
 * 类名称：SapceManageDao
 * 类描述：数据访问
 * 创建人：donghongguang
 * 创建时间：2017年4月10日 上午11:12:09
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
public interface SpaceManageDao
{

    /*****
     * 功能：获取某条空间信息
     * 创建人：donghongguang
     * 创建时间：2017年4月10日 下午2:50:09
     * @param 
     * @return 
     * @version 1.0.0
     */
    public SpaceManage selectSpaceMangeInfo(SpaceManage spaceManage);
    
    /*****
     * 功能：插入空间管理信息
     * 创建人：donghongguang
     * 创建时间：2017年4月10日 下午3:01:32
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int insert(SpaceManage spaceManage);
    
    /*****
     * 功能：更新空间管理信息
     * 创建人：donghongguang
     * 创建时间：2017年4月10日 下午3:01:32
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int updateSpaceManage(SpaceManage spaceManage);


    /**
     * 查询用户量
     * @param map
     * @return
     */
    public int selectUserCount(Map map);
    
}
