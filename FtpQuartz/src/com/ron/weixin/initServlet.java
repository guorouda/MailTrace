package com.ron.weixin;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.ron.weixin.pereference.SystemGlobals;

public class initServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(initServlet.class);
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);

		try {
			log.debug("initiating ...");
			SystemGlobals.initGlobals();
		}catch (Exception e) {
	//		throw new WeixinStartupException("Error while starting JForum", e);
		}
	}

}
