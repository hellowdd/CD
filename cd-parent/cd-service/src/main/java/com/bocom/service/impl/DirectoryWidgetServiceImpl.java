package com.bocom.service.impl;

import com.bocom.dao.DirectoryWidgetDao;
import com.bocom.domain.DirectoryWidget;
import com.bocom.service.DirectoryWidgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*****
 * 类名称：DirectoryWidgetServiceImpl
 * 类描述：控件目录
 * 创建人：donghongguang
 * 创建时间：2017年3月24日 上午11:42:27
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
@Service
public class DirectoryWidgetServiceImpl implements DirectoryWidgetService
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DirectoryWidgetDao directoryWidgetDao;

    
    @Override
    public List<DirectoryWidget> getAll(DirectoryWidget directoryWidget)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**** 
     * @see com.bocom.service.DirectoryWidgetService#addDirectoryWidget(com.bocom.domain.DirectoryWidget)   
     * <pre>增加一条目录信息
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 上午11:45:50
     * </pre>
     * @version 1.0.0
     */
    @Override
    public int addDirectoryWidget(DirectoryWidget directoryWidget)
    {
        logger.info("DirectoryWidgetServiceImpl addDirectoryWidget begin ...");
        /**
         * 1,插入一条目录信息，目录符合性在业务层进行处理
         */
        try
        {
            directoryWidgetDao.insert(directoryWidget);
            //取得控件id
            logger.info("DirectoryWidgetServiceImpl addDirectoryWidget exit ...");
            return directoryWidget.getId();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("DirectoryWidgetServiceImpl addDirectoryWidget error ...", e);
        }
        logger.info("DirectoryWidgetServiceImpl addDirectoryWidget exit ...");
        return 0;
    }

    /**** 
     * @see com.bocom.service.DirectoryWidgetService#updateDirectory(com.bocom.domain.DirectoryWidget)   
     * <pre>修改目录
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 下午1:35:35
     * </pre>
     * @version 1.0.0
     */
    @Override
    public int updateDirectory(DirectoryWidget directoryWidget)
    {
        return directoryWidgetDao.updateDirectory(directoryWidget);
    }

    /**** 
     * @see com.bocom.service.DirectoryWidgetService#removeDirecotry(com.bocom.domain.DirectoryWidget)   
     * <pre>移除目录
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 下午1:35:38
     * </pre>
     * @version 1.0.0
     */
    @Override
    public int removeDirecotry(DirectoryWidget directoryWidget)
    {
        return directoryWidgetDao.deleteDirecotry(directoryWidget);
    }

    /**** 
     * @see com.bocom.service.DirectoryWidgetService#getSubDirectory(com.bocom.domain.DirectoryWidget)   
     * <pre>获得子目录
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 下午1:35:41
     * </pre>
     * @version 1.0.0
     */
    @Override
    public List<DirectoryWidget> getSubDirectory(DirectoryWidget directoryWidget)
    {
        return directoryWidgetDao.selectSubDirectory(directoryWidget);
    }

    /**** 
     * @see com.bocom.service.DirectoryWidgetService#getDirectoryInfoByUser(com.bocom.domain.DirectoryWidget)   
     * <pre>查找用户下的根目录
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 下午3:30:09
     * </pre>
     * @version 1.0.0
     */
    @Override
    public List<DirectoryWidget> getDirectoryInfoByUser(DirectoryWidget directoryWidget)
    {
        return directoryWidgetDao.selectDirectoryInfoByUser(directoryWidget);
    }

    /**** 
     * @see com.bocom.service.DirectoryWidgetService#getDirectoryInfoById(com.bocom.domain.DirectoryWidget)   
     * <pre>查找目录
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 下午3:36:50
     * </pre>
     * @version 1.0.0
     */
    @Override
    public DirectoryWidget getDirectoryInfoById(DirectoryWidget directoryWidget)
    {
        return directoryWidgetDao.selectDirectoryInfoById(directoryWidget);
    }
    
}
