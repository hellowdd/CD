package com.bocom.business;

import java.util.List;
import java.util.Map;

import com.bocom.dto.session.SessionUserInfo;
import com.bocom.util.ResponseVo;

/*****
 * <pre>
 * 类名称：WidgetInfoBusiness
 * 类描述：控件相关信息业务处理接口
 * 创建人：donghongguang
 * 创建时间：2017年3月22日 下午4:12:31
 * 修改人：
 * 修改时间：
 * </pre>
 * @version 1.0.0
 */
public interface WidgetInfoBusiness
{
    /*****
     * 功能：上传文件
     * 创建人：donghongguang
     * 创建时间：2017年3月22日 下午5:11:09
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo uploadWidgetInfoBySession(Map map);
    
    /*****
     * 功能：查询上传的控件列表
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午11:15:28
     * @param 
     * @return 
     * @version 1.0.0
     */
    public List<Map> getWidgetInfoList(Map map);
    
    /*****
     * 功能：查询上传的控件列表
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午11:15:28
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int getWidgetInfoListCount(Map map);
    
    /*****
     * 功能：获取共享控件列表
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午11:30:35
     * @param 
     * @return 
     * @version 1.0.0
     */
    public List<Map> getWidgetInfoShareList(Map map);
    
    /*****
     * 功能：查询共享的控件列表
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午11:15:28
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int getWidgetInfoShareListCount(Map map);
    
    /*****
     * 功能：控件共享
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午11:31:29
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo shareWidgetInfo(Map<String ,Object> map);
    /*****
     * 功能：取消控件共享
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 上午11:31:29
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo unshareWidgetInfo(Map<String ,Object> map);
    
    /*****
     * 功能：下载文件
     * 创建人：donghongguang
     * 创建时间：2017年3月23日 下午5:11:20
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo downloadWidgetInfo(Map map,SessionUserInfo sessionUserInfo);
    
     /*****
     * 功能：上传文件
     * 创建人：donghongguang
     * 创建时间：2017年3月29日 下午2:11:09
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo uploadWidgetInfo(Map map,SessionUserInfo sessionUserInfo);
    
    /*****
     * 功能：更新文件
     * 创建人：donghongguang
     * 创建时间：2017年3月29日 下午2:11:09
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo updateWidgetInfo(Map map,SessionUserInfo sessionUserInfo);
    
    /*****
     * 功能：更新文件
     * 创建人：donghongguang
     * 创建时间：2017年3月29日 下午2:11:09
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo updateWidgetInfoBase(Map map,SessionUserInfo sessionUserInfo);
    
    /*****
     * 功能：删除控件
     * 创建人：donghongguang
     * 创建时间：2017年4月5日 上午10:00:33
     * @param 
     * @return 
     * @version 1.0.0
     */
    public int deleteWidget(String widgetPath, String widgetId);
    
    /*****
     * 功能：发布调用 增加发布次数
     * 创建人：donghongguang
     * 创建时间：2017年5月16日 下午3:53:12
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo addRate(int widgetId);
    
    /*****
     * 功能：企业上传控件统计排名
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    public List<Map> getWidgetNum(Map map);
    
    /*****
     * 功能：企业控件总发布次数统计排名 
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    public List<Map> getWidgetAllReleaseNum(Map map);
    
    /*****
     * 功能：企业控件发布下载次数统计排名
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    public List<Map> getWidgetReleaseNum(Map map);
    public int getWidgetReleaseNumCount(Map map);
    
    /*****
     * 功能：按类型统计 控件量 
     * 创建人：donghongguang
     * 创建时间：2017年5月18日 下午1:25:40
     * @param 
     * @return 
     * @version 1.0.0
     */
    public List<Map> getWidgetNumByType(Map map);
    
}
