package it.micronixnetwork.gaf.struts2.gui.component;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

public class MainMenu extends GAFGuiComponent {

    private it.micronixnetwork.gaf.domain.Menu mainMenu;

    public MainMenu(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack, req, res);
    }

    @Override
    public boolean end(Writer writer, String body) {
	try {
	    mainMenu = (it.micronixnetwork.gaf.domain.Menu) stack.findValue("mainMenu");
	    if (mainMenu != null) {
		templateDir = "gaf/tag";
		templateName = "main_menu.ftl";
		renderTemplate(writer,body);
	    }
	} catch (Exception ioe) {
	    error("Errore nel rendering del tag di menu", ioe);
	}
	return super.end(writer, body);
    }
    
    public it.micronixnetwork.gaf.domain.Menu getMainMenu() {
	return mainMenu;
    }

}
