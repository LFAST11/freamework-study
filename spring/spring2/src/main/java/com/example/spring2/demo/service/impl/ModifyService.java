package com.example.spring2.demo.service.impl;

import com.example.spring2.demo.service.IModifyService;
import com.example.spring2.formwork.annotation.DiyService;

/**
 * 增删改业务
 * @author Tom
 *
 */
@DiyService
public class ModifyService implements IModifyService {

	/**
	 * 增加
	 */
	public String add(String name,String addr) throws Exception {
		throw  new Exception("1111");
	}

	/**
	 * 修改
	 */
	public String edit(Integer id,String name) {
		return "modifyService edit,id=" + id + ",name=" + name;
	}

	/**
	 * 删除
	 */
	public String remove(Integer id) {
		return "modifyService id=" + id;
	}

}
