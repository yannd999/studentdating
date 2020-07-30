package controller;

import domain.service.PersonService;

public class ControllerFactory {
	
    public RequestHandler getController(String key, PersonService model) {
        return createHandler(key, model);
    }
    
	private RequestHandler createHandler(String handlerName, PersonService model) {
		RequestHandler handler = null;
		System.out.println("Factory create handler: " + handlerName); //todo
		try {
			Class<?> handlerClass = Class.forName("controller.handler." + handlerName);
			Object handlerObject = handlerClass.getConstructor().newInstance();
			handler = (RequestHandler) handlerObject;
	    	handler.setModel(model);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("Deze pagina bestaat niet!!!!");
		}
		return handler;
	}
}
