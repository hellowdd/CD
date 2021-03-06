package com.bocom.service.impl;

import com.bocom.dao.SpaceManageDao;
import com.bocom.dao.WidgetExtendInfoDao;
import com.bocom.dao.WidgetInfoDao;
import com.bocom.domain.SpaceManage;
import com.bocom.domain.WidgetExtendInfo;
import com.bocom.domain.WidgetInfo;
import com.bocom.dto.session.SessionUserInfo;
import com.bocom.service.WidgetInfoService;
import com.bocom.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*****
 * <pre>
 * 类名称：WidgetInfoServiceImpl
 * 类描述：控件服务
 * 创建人：donghongguang
 * 创建时间：2017年3月23日 上午11:02:38
 * 修改人：
 * 修改时间：
 * </pre>
 * @version 1.0.0
 */
@Service
public class WidgetInfoServiceImpl extends BaseSerivceImpl implements WidgetInfoService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WidgetInfoDao widgetInfoDao;
    @Autowired
    private WidgetExtendInfoDao widgetExtendInfoDao;
    @Autowired
    private SpaceManageDao spaceManageDao;
    @Autowired
    private HttpServletRequest request;


    /*****
     * 功能：获取控件列表
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午9:39:41
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public List<Map> getWidgetInfoList(Map request) {
        logger.info("WidgetInfoServiceImpl getWidgetInfoList begin ...");
        List<Map> widgetInfoList = null;
        try {
            //request.put("beginPlanDelivery", GeneralUtils.string2Date(request.getBeginPlanDelivery(), "yyyy-MM-dd
            // HH:mm:ss"));

            widgetInfoList = widgetInfoDao.queryWidgetInfoList(request);
        } catch (Exception e) {
            logger.error("WidgetInfoServiceImpl getWidgetInfoList error ...", e);
            throw e;
        }
        logger.info("WidgetInfoServiceImpl getWidgetInfoList exit ...");
        return widgetInfoList;
    }

    /**** 
     * @see com.bocom.service.WidgetInfoService#getWidgetInfoListCount(java.util.Map)
     * 功能：查询上传的控件列表总数
     * 创建人：donghongguang
     * 创建时间：2017年4月7日 上午9:30:54
     * @version 1.0.0
     */
    @Override
    public int getWidgetInfoListCount(Map map) {

        return widgetInfoDao.queryWidgetInfoListCount(map);
    }

    /*****
     * 功能：插入上传控件信息
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午9:39:45
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public int addWidgetInfo(WidgetInfo widgetInfo) {
        logger.info("WidgetInfoServiceImpl addWidgetInfo begin ...");
        /**
         * 1,插入一条控件信息，返回控件id
         * 2.根据控件id，插入一条控件扩展信息记录
         * 3.更新空间信息
         */
//        try
//        {
        widgetInfo.setIsDelete(0);
        widgetInfo.setIsDeletePhysically(0);
        widgetInfoDao.insert(widgetInfo);
        //取得控件id
        if (widgetInfo.getId() != null) {
            WidgetExtendInfo widgetExtendInfo = new WidgetExtendInfo();
            widgetExtendInfo.setWidgetId(widgetInfo.getId());
            widgetExtendInfo.setDirectoryId(widgetInfo.getDirectoryId());
            widgetExtendInfo.setDownloadTimes(0);
            widgetExtendInfo.setIsShare("0");
            widgetExtendInfo.setWidgetTypeId(widgetInfo.getWidgetTypeId());
            widgetExtendInfo.setAppType(widgetInfo.getAppType());
            widgetExtendInfoDao.insert(widgetExtendInfo);
            //更新空间信息
            SpaceManage spaceManage = new SpaceManage();
            spaceManage.setUserType("0");
            spaceManage.setUserId(widgetInfo.getUploadUserid());

            spaceManage = spaceManageDao.selectSpaceMangeInfo(spaceManage);
            if (spaceManage.getId() != null) {
                //计算空间信息
                Long spaceUse = spaceManage.getSpaceUse();
                Long spaceRest = spaceManage.getSpaceRest();
                Long applySapce = new Long(widgetInfo.getWidgetSize());

                spaceUse = FormatUtils.add(spaceUse, applySapce);
                spaceRest = FormatUtils.subtract(spaceRest, applySapce);
                //更新空间表
                spaceManage.setSpaceRest(spaceRest);
                spaceManage.setSpaceUse(spaceUse);
                int resultSpace = spaceManageDao.updateSpaceManage(spaceManage);
                if (resultSpace > 0) {
                    logger.info("WidgetInfoServiceImpl addWidgetInfo exit ...");
                    return widgetInfo.getId();
                } else {
                    return 0;
                }

            }

        }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            logger.error("WidgetInfoServiceImpl addWidgetInfo error ...", e);
//        }
        logger.info("WidgetInfoServiceImpl addWidgetInfo exit ...");
        return 0;
    }

    /*****
     * 功能：获取控件信息
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 上午11:31:40
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public WidgetInfo getWidgetInfo(WidgetInfo widgetInfo) {
        logger.info("WidgetInfoServiceImpl getWidgetInfo begin ...");
        WidgetInfo WidgetInfoResult = null;
//        try
//        {
        WidgetInfoResult = widgetInfoDao.selectWidgetInfo(widgetInfo);
//        }
//        catch (Exception e)
//        {
//            logger.error("WidgetInfoServiceImpl getWidgetInfo error ...", e);
//        }
        logger.info("WidgetInfoServiceImpl getWidgetInfo exit ...");
        return WidgetInfoResult;
    }

    /*****
     * 功能：获取控件扩展信息
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 上午11:31:40
     * @param
     * @return
     * @version 1.0.0
     */
    public WidgetExtendInfo getWidgetExtendInfo(WidgetExtendInfo widgetExtendInfo) {
        logger.info("WidgetInfoServiceImpl getWidgetExtendInfo begin ...");
        WidgetExtendInfo widgetExtendInfoResult = null;
        try {
            widgetExtendInfoResult = widgetExtendInfoDao.selectWidgetExtendInfo(widgetExtendInfo);
        } catch (Exception e) {
            logger.error("WidgetInfoServiceImpl getWidgetExtendInfo error ...", e);
        }
        logger.info("WidgetInfoServiceImpl getWidgetExtendInfo exit ...");
        return widgetExtendInfoResult;
    }

    /*****
     * 功能：下载次数加一
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 上午11:31:40
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public int updatetWidgetDownloadTimes(WidgetExtendInfo widgetExtendInfo) {
        logger.info("WidgetInfoServiceImpl addWidgetInfo begin ...");
        /**
         * 1,插入一条控件信息，返回控件id
         * 2.根据控件id，插入一条控件扩展信息记录
         */
        try {
            widgetExtendInfoDao.updateWidgetDownloadTimes(widgetExtendInfo);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("WidgetInfoServiceImpl addWidgetInfo error ...", e);
        }
        logger.info("WidgetInfoServiceImpl addWidgetInfo exit ...");
        return 0;
    }

    /*****
     * 功能：更新控件信息
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午9:39:45
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public int updateWidgetInfo(WidgetInfo widgetInfo) {
        logger.info("WidgetInfoServiceImpl addWidgetInfo begin ...");
        /**
         * 1,更新一条控件信息
         */
        try {
            return widgetInfoDao.updateWidgetInfo(widgetInfo);
        } catch (Exception e) {
            logger.error("WidgetInfoServiceImpl addWidgetInfo error ...", e);
        }
        logger.info("WidgetInfoServiceImpl addWidgetInfo exit ...");
        return 0;
    }


    /*****
     * 功能：修改控件扩展记录
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午9:39:45
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public int updateWidgetExtendInfo(WidgetExtendInfo widgetExtendInfo) {
        logger.info("WidgetInfoServiceImpl addWidgetInfo begin ...");
        /**
         * 1,更新一条控件扩展信息
         */
        try {
            return widgetExtendInfoDao.updateWidgetExtendInfo(widgetExtendInfo);
        } catch (Exception e) {
            logger.error("WidgetInfoServiceImpl addWidgetInfo error ...", e);
        }
        logger.info("WidgetInfoServiceImpl addWidgetInfo exit ...");
        return 0;
    }

    /*****
     * 功能：更新控件基本信息
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午9:39:45
     * @param
     * @return
     * @version 1.0.0
     */
    public int updateWidgetInfoBase(WidgetInfo widgetInfo, WidgetExtendInfo widgetExtendInfo) {
        /**
         * 1,更新控件信息，
         * 2.更新一条控件扩展信息
         */
        int result = widgetInfoDao.updateWidgetInfo(widgetInfo);
        if (result > 0) {
            WidgetExtendInfo widgetExtendInfoOld = widgetExtendInfoDao.selectWidgetExtendInfo(widgetExtendInfo);
            widgetExtendInfoOld.setDirectoryId(widgetExtendInfo.getDirectoryId());
            widgetExtendInfoOld.setWidgetTypeId(widgetExtendInfo.getWidgetTypeId());
            result = widgetExtendInfoDao.updateWidgetExtendInfo(widgetExtendInfoOld);
            return result;
        }
        return 0;
    }

    /*****
     * 功能：发布次数加一
     * 创建人：donghongguang
     * 创建时间：2017年5月16日 上午11:31:40
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public int updatetWidgetReleaseTimes(WidgetExtendInfo widgetExtendInfo) {
        logger.info("WidgetInfoServiceImpl updatetWidgetReleaseTimes begin ...");
        try {
            widgetExtendInfoDao.updateWidgetReleaseTimes(widgetExtendInfo);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("WidgetInfoServiceImpl updatetWidgetReleaseTimes error ...", e);
        }
        logger.info("WidgetInfoServiceImpl updatetWidgetReleaseTimes exit ...");
        return 0;
    }

    /*****
     * 功能：企业上传控件统计排名
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public List<Map> queryWidgetNum(Map request) {
        return widgetInfoDao.queryWidgetNum(request);
    }

    /*****
     * 功能：企业控件总发布次数统计排名 
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public List<Map> queryWidgetAllReleaseNum(Map request) {
        return widgetInfoDao.queryWidgetAllReleaseNum(request);
    }

    /*****
     * 功能：企业控件发布下载次数统计排名
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public List<Map> queryWidgetReleaseNum(Map request) {
        return widgetInfoDao.queryWidgetReleaseNum(request);
    }

    @Override
    public int queryWidgetReleaseNumCount(Map map) {

        return widgetInfoDao.queryWidgetReleaseNumCount(map);
    }

    /*****
     * 功能：按类型统计 控件量 
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param
     * @return
     * @version 1.0.0
     */
    @Override
    public List<Map> queryWidgetNumByType(Map request) {
        return widgetInfoDao.queryWidgetNumByType(request);
    }

    /**
     * 查询总共的上传文件总量
     *
     * @return
     */
    @Override
    public int queryCountWidget() {
        return widgetInfoDao.queryCountWidget();
    }

    /**
     * 查询总共的上传文件总量的大小
     *
     * @return
     */
    @Override
    public Long queryWidgetSize() {
        return widgetInfoDao.queryWidgetSize();
    }

    /**
     * 查询各个文件类型的总量，各个类型的文件占用空间大小
     *
     * @return
     */
    @Override
    public List<Map> queryWidgetType() {
        return widgetInfoDao.queryWidgetType();
    }

    @Override
    public ResponseVo uploadFile(Map map) throws Exception {
        logger.info("进入service");
        FastDfsUtil fastDfsUtil = new FastDfsUtil();
        HttpSession session = request.getSession();
        SessionUserInfo userInfo = (SessionUserInfo) session.getAttribute("sessionUserInfo");
        File file = (File) map.get("file");
//        CommonsMultipartFile cf = (CommonsMultipartFile) file;
//        DiskFileItem fi = (DiskFileItem) cf.getFileItem();

        SpaceManage spaceManage = new SpaceManage();
        spaceManage.setUserId(userInfo.getUserId());
        //获取该用户的剩余空间
        spaceManage = spaceManageDao.selectSpaceMangeInfo(spaceManage);
        logger.info(spaceManage + "获取到的用户的剩余空间");
        Long spaceRest = spaceManage.getSpaceRest();
        //说明还有剩余空间
        if (spaceRest < file.length()) {
            return ResponseVoUtil.success("剩余空间不足,请申请空间");
        }
        //获取file的md5值
        String md5 = (String) map.get("fileMd5");
        //检验该md5是否存在，判断该文件是否已经存在
        logger.info("获取到的文件的md5值：   " + md5);
        Map widgetMap = new HashMap();
        widgetMap.put("MD5", md5);
        String path = "";
        List<Map> list = widgetInfoDao.queryMD5(widgetMap);
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.size() > 0) {
                //再根据userId判断是否存在
                widgetMap.put("uploadUserid", userInfo.getUserId());
                List<Map> list1 = widgetInfoDao.queryMD5(widgetMap);
                if (CollectionUtils.isNotEmpty(list1) && list1.size() > 0) {
                    return ResponseVoUtil.success(list.get(0).get("storage_path"));
                } else {
                    //说明有重复文件但是该用户没有该文件
                    path = (String) list.get(0).get("storage_path");
                }
            }
        }

        //直接上传文件
        //path没有值说明不存在重复文件上传到fastdfs的路径
        if (path.equals("")) {
            path = fastDfsUtil.uploadFile(file.getAbsolutePath());
            logger.info("上传到fastdfs之后的路径为================>" + path);
        }
        WidgetInfo widgetInfo = new WidgetInfo();
        widgetInfo.setWidgetName((String) map.get("fileName"));
        // 上传人信息
        widgetInfo.setUploadUserid(userInfo.getUserId());
        widgetInfo.setUploadUsername(userInfo.getOrgName()); //修改为上传者为企业名称
        widgetInfo.setMd5(md5);
        widgetInfo.setWidgetSize((int) file.length());
        widgetInfo.setWidgetExtension(FileUtil.getFileSuffix((String) map.get("fileName")));
        widgetInfo.setStoragePath(path);
        widgetInfo.setRemarks((String) map.get("remarks"));
        //插入t_widget_info表中
        widgetInfoDao.insertWidget(widgetInfo);
        int id = widgetInfo.getId();
        //将信息插入扩展表
        WidgetExtendInfo widgetExtendInfo = new WidgetExtendInfo();
        widgetExtendInfo.setDirectoryId((Integer.valueOf((String) map.get("directoryId"))));
        widgetExtendInfo.setWidgetId(id);
        widgetExtendInfo.setUploadUserid(userInfo.getUserId());
        widgetExtendInfoDao.insertSelective(widgetExtendInfo);
        //修改空间信息表
        Long l = spaceManage.getSpaceUse() + file.length();
        spaceManage.setSpaceUse(l);
        spaceManage.setSpaceRest(spaceManage.getSpaceRest() - file.length());
        spaceManageDao.updateSpaceManage(spaceManage);
        file.delete();
        super.saveLog("上传文件", "上传文件名：" + (String) map.get("fileName"), userInfo);
        return ResponseVoUtil.success(path);
    }
}
