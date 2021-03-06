package com.bocom.service.impl;

import com.bocom.dao.SpaceManageDao;
import com.bocom.domain.SpaceManage;
import com.bocom.service.SpaceManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



/*****
 * 类名称：SpaceMangeServiceImpl
 * 类描述：空间管理
 * 创建人：donghongguang
 * 创建时间：2017年4月10日 上午11:34:25
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
@Service
public class SpaceManageServiceImpl implements SpaceManageService
{

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

	 @Autowired
	 private SpaceManageDao   spaceManageDao;

    @Override
    public int addSpaceMange(SpaceManage spaceManage)
    {
        return spaceManageDao.insert(spaceManage);
    }

    @Override
    public int delete(SpaceManage spaceManage)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public SpaceManage getSpaceMange(SpaceManage spaceManage)
    {
        return spaceManageDao.selectSpaceMangeInfo(spaceManage);
    }

    @Override
    public List<SpaceManage> getSpaceMangeList(SpaceManage spaceManage)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int updateSpaceMange(SpaceManage spaceManage)
    {
        // TODO Auto-generated method stub
        return spaceManageDao.updateSpaceManage(spaceManage);
    }

    @Override
    public int selectUserCount(Map map) {
        return spaceManageDao.selectUserCount(map);
    }
}
