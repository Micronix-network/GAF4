package it.micronixnetwork.gaf.util.catalina;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationListener implements ServletContextListener {

	  private static Map<String, ServletContext> contexts = 
	    new HashMap<String,ServletContext>();


	@Override
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
	    if (context.getContextPath().length() > 0)
	      contexts.remove(context.getContextPath());
	    System.out.println("Application Listener: "+context.getContextPath()+" removed");
	    context.setAttribute("myapps", contexts);
		
	}


	@Override
	public void contextInitialized(ServletContextEvent event) {
	    ServletContext context = event.getServletContext();
	    if (context.getContextPath().length() > 0)
	      contexts.put(context.getContextPath(), context);
	    System.out.println("Application Listener: "+context.getContextPath()+" added");
	    context.setAttribute("myapps", contexts);
	  }

	}