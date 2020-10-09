package com.xiangshangkan.framtest.web.controller;

import com.xiangshangkan.framtest.auto.dao.WikiDetailMapper;
import com.xiangshangkan.framtest.auto.entity.WikiDetail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @Description: ${description}
 * @Author: Zohar
 * @Date: 2020/9/30 10:20
 * @Version: 1.0
 */
@Controller
public class DemoController {

    private WikiDetailMapper wikiDetailMapper;

    @RequestMapping("/demo")
    public ModelAndView demo(){
        ModelAndView mov = new ModelAndView();
        mov.setViewName("/demo/utf8-jsp/demo");
        return mov;
    }

    @RequestMapping("/submit")
    public Integer submit(String hml) {
        WikiDetail detail = new WikiDetail();
        detail.setHtm(hml);
        return wikiDetailMapper.insert(detail) == 0 ? 0 :1;
    }

    @RequestMapping("/wikiDetail")
    public ModelAndView wikiDetail(Long id) {
        ModelAndView mov = new ModelAndView();
        WikiDetail detail = wikiDetailMapper.selectByPrimaryKey(id);
        mov.setViewName("/demo/detail");
        mov.addObject("detail",detail);
        return mov;
    }


}
