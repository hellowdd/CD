package com.bocom.web.controller.widget;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bocom.business.SpaceManageBusiness;
import com.bocom.util.ResponseVo;

/*****
 * 类名称：SpaceManageController
 * 类描述：空间管理控制类
 * 创建人：donghongguang
 * 创建时间：2017年4月12日 下午5:31:27
 * 修改人：
 * 修改时间：
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manager/space")
public class SpaceManageController
{
    @Autowired
    private SpaceManageBusiness spaceManageBusiness;
    
    /*****
     * 功能：获取空间信息
     * 创建人：donghongguang
     * 创建时间：2017年3月22日 下午3:10:24
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/getSpaceInfo")
    @ResponseBody
    public ResponseVo getSpaceInfo()
    {
        return spaceManageBusiness.getSpaceInfo(null);
    }
    
    /*****
     * 功能：申请空间信息
     * 创建人：donghongguang
     * 创建时间：2017年3月22日 下午3:10:24
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/applySpace")
    @ResponseBody
    public ResponseVo applySpace(@RequestParam(value="spaceSize",required = true) Long spaceSize,@RequestParam(value="spaceUnit",required = true) String spaceUnit, @RequestParam("applyReason") String applyReason )
    {
        Map<String, Object> map = new HashMap<>();
        map.put("spaceSize", spaceSize);
        map.put("spaceUnit", spaceUnit);
        map.put("applyReason", applyReason);
        return spaceManageBusiness.applySpace(map);

    }
    
    public ResponseVo applyInfoList()
    {
        ResponseVo vo = new ResponseVo();
        return vo;
    }
    
    /*****
     * 功能：审批空间信息
     * 创建人：donghongguang
     * 创建时间：2017年3月22日 下午3:10:24
     * @param
     * @return
     * @version 1.0.0
     */
    @RequestMapping("/auditApply")
    @ResponseBody
    public ResponseVo auditApply(@RequestParam(value="applyId",required = true) Integer applyId,@RequestParam(value="applyResult",required = true) String applyResult, @RequestParam("content") String content )
    {
        Map<String,Object> map = new HashMap<>();
        map.put("applyId", applyId);
        map.put("applyResult", applyResult);
        map.put("content", content);
        return spaceManageBusiness.auditSpace(map);
    }
    
}
