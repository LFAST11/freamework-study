package com.example.spring2.demo;

import com.example.spring2.demo.service.IModifyService;
import com.example.spring2.demo.service.IQueryService;
import com.example.spring2.formwork.annotation.DiyAutowired;
import com.example.spring2.formwork.annotation.DiyController;
import com.example.spring2.formwork.annotation.DiyRequestMapper;
import com.example.spring2.formwork.annotation.DiyRequestParam;
import com.example.spring2.formwork.webmvc.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 公布接口url
 * @author Tom
 *
 */
@DiyController
@DiyRequestMapper("/web")
public class MyAction {

	@DiyAutowired IQueryService queryService;
	@DiyAutowired IModifyService modifyService;

	@DiyRequestMapper("/query.json")
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response,
							  @DiyRequestParam("name") String name){
		String result = queryService.query(name);
		return out(response,result);
	}

	@DiyRequestMapper("/add*.json")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response,
                    @DiyRequestParam("name") String name, @DiyRequestParam("addr") String addr){
		try {
			String result = modifyService.add(name,addr);
			return out(response,result);
		} catch (Exception e) {
			Map<String,Object> model = new HashMap();
			model.put("detail",e.getMessage());
			model.put("stackTrace",e.getStackTrace());
			return new ModelAndView("500",model);

		}
	}

	@DiyRequestMapper("/remove.json")
	public ModelAndView remove(HttpServletRequest request, HttpServletResponse response,
                       @DiyRequestParam("id") Integer id){
		String result = modifyService.remove(id);
		return out(response,result);
	}

	@DiyRequestMapper("/edit.json")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response,
                     @DiyRequestParam("id") Integer id,
                     @DiyRequestParam("name") String name){
		String result = modifyService.edit(id,name);
		return out(response,result);
	}



	private ModelAndView out(HttpServletResponse resp, String str){
		try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
