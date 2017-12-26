package com.bocom.service.impl;

import com.bocom.dao.ApplyShareDao;
import com.bocom.dao.WidgetExtendInfoDao;
import com.bocom.dao.WidgetTypeDao;
import com.bocom.domain.ApplyShare;
import com.bocom.domain.WidgetExtendInfo;
import com.bocom.domain.WidgetType;
import com.bocom.service.WidgetTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*****
 * 类名称：WidgetTypeServiceImpl
 * 类描述：空间分类服务处理类
 * 创建人：donghongguang
 * 创建时间：2017年5月16日 上午10:04:50
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
@Service
public class WidgetTypeServiceImpl implements WidgetTypeService
{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WidgetTypeDao widgetTypeDao;
    @Autowired
    private WidgetExtendInfoDao widgetExtendInfoDao;
    @Autowired
    private ApplyShareDao applyShareDao;

    @Override
    public List<WidgetType> getAll(WidgetType widgetType)
    {
        return null;
    }

    /**** 
     * @see com.bocom.service.WidgetTypeService#addWidgetType(com.bocom.domain.WidgetType)   
     * 增加一条类型
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午3:41:25
     * @version 1.0.0
     */
    @Override
    public int addWidgetType(WidgetType widgetType)
    {
        logger.info("WidgetTypeServiceImpl addWidgetType begin ...");
        /**
         * 1,插入一条类型信息，符合性在业务层进行处理
         */
        try
        {
            widgetTypeDao.insert(widgetType);
            //取得控件类型id
            logger.info("WidgetTypeServiceImpl addWidgetType exit ...");
            return widgetType.getId();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("WidgetTypeServiceImpl addWidgetType error ...", e);
        }
        logger.info("WidgetTypeServiceImpl addWidgetType exit ...");
        return 0;
    }

    /**** 
     * @see com.bocom.service.WidgetTypeService#updateWidgetType(com.bocom.domain.WidgetType)
     * 更新一条类型   
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午3:41:38
     * @version 1.0.0
     */
    @Override
    public int updateWidgetType(WidgetType widgetType)
    {
        return widgetTypeDao.updateWidgetType(widgetType);
    }

    /**** 
     * @see com.bocom.service.WidgetTypeService#removeWidgetType(com.bocom.domain.WidgetType)
     * 删除一条类型   
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午3:41:51
     * @version 1.0.0
     */
    @Override
    public int removeWidgetType(WidgetType widgetType)
    {
        /**
         * 1.把控件类型属于此类型的控件修改为“4，其他控件”
         * 2.删除控件
         */
        //待控件功能完成后完成此快内容
        WidgetExtendInfo widgetExtendInfo = new WidgetExtendInfo();
        widgetExtendInfo.setUploadUserid(widgetType.getCreateUserid());
        widgetExtendInfo.setWidgetTypeId(widgetType.getId());
        if(widgetExtendInfoDao.updateWidgetType(widgetExtendInfo) >= 0){
            return widgetTypeDao.deleteWidgetType(widgetType);
        }
        else return 0;
    }

    /**** 
     * @see com.bocom.service.WidgetTypeService#getWidgetTypeInfoByUser(com.bocom.domain.WidgetType)
     * 获取用户下的类型   
     * 创建人：donghongguang
     * 创建时间：2017年5月15日 下午3:42:02
     * @version 1.0.0
     */
    @Override
    public List<WidgetType> getWidgetTypeInfoByUser(WidgetType widgetType)
    {
        return widgetTypeDao.selectWidgetTypeInfoByUser(widgetType);
    }

    /**** 
     * @see com.bocom.service.WidgetTypeService#getWidgetTypeInfoById(com.bocom.domain.WidgetType)
     * 获取某一类型信息   
     * 创建人：donghongguang24900
     * 创建时间：2017年5月15日 下午3:42:19
     * @version 1.0.0
     */
    @Override
    public WidgetType getWidgetTypeInfoById(WidgetType widgetType)
    {
        return widgetTypeDao.selectWidgetTypeInfoById(widgetType);
    }
	
    /*****
     * 功能：添加一条权限记录
     * 创建人：donghongguang
     * 创建时间：2017年6月29日 下午1:56:41
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int addWidgetShare(ApplyShare applyShare){
        return applyShareDao.insert(applyShare);
    }
    
    /** 删除权限 */
    public int removeWidgetShare(ApplyShare applyShare){
        return applyShareDao.deleteWidgetShare(applyShare);
    }
    
    /** 查找某控件赋予权限的部门 */
    public List<ApplyShare> getAllByWidget(ApplyShare applyShare){
        return applyShareDao.selectAllByWidget(applyShare);
    }
   
}
