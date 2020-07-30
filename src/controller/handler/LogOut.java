package controller.handler;

import controller.SyncHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOut extends SyncHandler {
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		//System.out.println("Logout"); //todo
		return "index.jsp";
	}
}
