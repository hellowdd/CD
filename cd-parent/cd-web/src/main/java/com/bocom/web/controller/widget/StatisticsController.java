package com.bocom.web.controller.widget;

import com.bocom.service.SpaceManageService;
import com.bocom.service.WidgetInfoService;
import com.bocom.util.ResponseVo;
import com.bocom.util.ResponseVoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager/Statistics")
public class StatisticsController {

    private static Logger logger = LoggerFactory
            .getLogger(StatisticsController.class);

    @Autowired
    private WidgetInfoService widgetInfoService;

    @Autowired
    private SpaceManageService spaceManageService;

    /**
     * 查询文件总量
     * @return
     */
    @RequestMapping("/queryCountWidget")
    @ResponseBody
    public ResponseVo queryCountWidget() {
        try {
            ResponseVo responseVo = new ResponseVo();
            int i = widgetInfoService.queryCountWidget();
            return ResponseVoUtil.success(i);
        } catch (Exception e) {
            logger.info("查询文件总量失败   "+e.getMessage());
            return ResponseVoUtil.fail("查询文件总量失败",e.getMessage());
        }
    }



    /**
     * 查询文件总量的大小。以兆为单位
     * @return
     */
    @RequestMapping("/queryWidgetSize")
    @ResponseBody
    public ResponseVo queryWidgetSize() {
        try {
            ResponseVo responseVo = new ResponseVo();
            Long l = widgetInfoService.queryWidgetSize();
            return ResponseVoUtil.success(l);
        } catch (Exception e) {
            logger.info("查询文件总量失败   "+e.getMessage());
            return ResponseVoUtil.fail("查询上传文件总共的空间大小失败",e.getMessage());
        }
    }


    /**
     * 查询各个文件类型的总量，各个类型的文件占用空间大小
     * @return
     */
    @RequestMapping("/queryWidgetType")
    @ResponseBody
    public ResponseVo queryWidgetType() {
        try {
            ResponseVo responseVo = new ResponseVo();
            List<Map> list = widgetInfoService.queryWidgetType();
            return ResponseVoUtil.success(list);
        } catch (Exception e) {
            logger.info("查询文件总量失败   "+e.getMessage());
            return ResponseVoUtil.fail("查询各个文件类型的数量与大小失败",e.getMessage());
        }
    }


    /**
     * 查询占用空间大于一个值的用户总量
     * @return
     */
    @RequestMapping("/selectUserCount")
    @ResponseBody
    public ResponseVo selectUserCount(double d) {
        try {
            ResponseVo responseVo = new ResponseVo();
            Map map =new HashMap();
            map.put("space",d);
            int i= spaceManageService.selectUserCount(map);
            return ResponseVoUtil.success(i);
        } catch (Exception e) {
            logger.info("查询文件总量失败   "+e.getMessage());
            return ResponseVoUtil.fail("查询用户量失败",e.getMessage());
        }
    }
}
