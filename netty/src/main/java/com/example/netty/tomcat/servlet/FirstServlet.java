package com.example.netty.tomcat.servlet;


import com.example.netty.tomcat.http.Request;
import com.example.netty.tomcat.http.Response;
import com.example.netty.tomcat.http.Servlet;

public class FirstServlet extends Servlet {

	public void doGet(Request request, Response response) throws Exception {
		this.doPost(request, response);
	}

	public void doPost(Request request, Response response) throws Exception {
		response.write("This is First Serlvet");
	}

}
