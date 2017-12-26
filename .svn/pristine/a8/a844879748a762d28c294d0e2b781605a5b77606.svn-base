package com.bocom.service;

import java.util.List;

import com.bocom.domain.DirectoryWidget;

/*****
 * 类名称：DirectoryWidgetService
 * 类描述：控件目录管理类
 * 创建人：donghongguang
 * 创建时间：2017年3月24日 上午11:40:11
 * 修改人：
 * 修改时间：
 * 
 * @version 1.0.0
 */
public interface DirectoryWidgetService
{
    public List<DirectoryWidget> getAll(DirectoryWidget directoryWidget);
    
    public int addDirectoryWidget(DirectoryWidget directoryWidget);
    
    /** 修改目录 移动目录 */
    public int updateDirectory(DirectoryWidget directoryWidget);
    
    /** 删除目录 */
    public int removeDirecotry(DirectoryWidget directoryWidget);
    
    /** 查找目录下的子目录 */
    public List<DirectoryWidget> getSubDirectory(DirectoryWidget directoryWidget);
    
    /** 查找用户下的根目录 */
    public List<DirectoryWidget> getDirectoryInfoByUser(DirectoryWidget directoryWidget);
    
    /** 查找目录 */
    public DirectoryWidget getDirectoryInfoById(DirectoryWidget directoryWidget);
}
