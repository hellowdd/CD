package com.bocom.dao;

import java.util.List;

import com.bocom.domain.DirectoryWidget;


/*****
 * <pre>
 * 类名称：DirectoryWidgetDao
 * 类描述：控件目录数据访问层
 * 创建人：donghongguang
 * 创建时间：2017年3月23日 下午2:15:20
 * 修改人：
 * 修改时间：
 * </pre>
 * @version 1.0.0
 */
public interface DirectoryWidgetDao
{

    public List<DirectoryWidget> selectAll(DirectoryWidget directoryWidget);
    
    public int insert(DirectoryWidget directoryWidget);
    /** 修改目录 移动目录 */
    public int updateDirectory(DirectoryWidget directoryWidget);
    
    /** 删除目录 */
    public int deleteDirecotry(DirectoryWidget directoryWidget);
    /** 查找目录下的子目录 */
    public List<DirectoryWidget> selectSubDirectory(DirectoryWidget directoryWidget);
    
    /** 查找某用户下的根目录 */
    public List<DirectoryWidget> selectDirectoryInfoByUser(DirectoryWidget directoryWidget);
    /** 查找目录 */
    public DirectoryWidget selectDirectoryInfoById(DirectoryWidget directoryWidget);
}
