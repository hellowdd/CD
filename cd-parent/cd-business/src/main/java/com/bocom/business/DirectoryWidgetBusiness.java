package com.bocom.business;

import java.util.List;
import java.util.Map;

import com.bocom.domain.DirectoryWidget;
import com.bocom.util.ResponseVo;

/*****
 * 类名称：DirectoryWidgetBusiness
 * 类描述：控件目录业务处理接口
 * 创建人：donghongguang
 * 创建时间：2017年3月24日 下午1:42:54
 * 修改人：
 * 修改时间：
 * 
 * @version 1.0.0
 */
public interface DirectoryWidgetBusiness
{
    public List<DirectoryWidget> getAll(DirectoryWidget directoryWidget);
    
    /*****
     * 功能：创建目录
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 下午1:50:43
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo creatDirectoryInfo(DirectoryWidget directoryWidget);
    
    /*****
     * 功能：修改目录 移动目录
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 下午1:51:00
     * @param 
     * @return 
     * @version 1.0.0
     */
    public ResponseVo updateDirectory(DirectoryWidget directoryWidget);
    
    /*****
     * 功能：删除目录
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 下午1:51:52
     * @param 
     * @return 
     * @version 1.0.0
     */
    public Map removeDirecotry(DirectoryWidget directoryWidget);
    
    /*****
     * 功能：查找目录下的子目录 
     * 创建人：donghongguang
     * 创建时间：2017年3月24日 下午1:52:02
     * @param 
     * @return 
     * @version 1.0.0
     */
    public List<DirectoryWidget> getSubDirectory(DirectoryWidget directoryWidget);
}
