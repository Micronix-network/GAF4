package it.micronixnetwork.gaf.struts2.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import it.micronixnetwork.gaf.domain.RoledUser;
import it.micronixnetwork.gaf.exception.ApplicationException;
import it.micronixnetwork.gaf.service.exception.NotActiveUserException;
import it.micronixnetwork.gaf.service.exception.UserExpiredException;

public class CheckInAction extends WebAppAction {
    
    String username;
    
    String password;
    
    public void setUsername(String username) {
	this.username = username;
    }
    
    public void setPassword(String password) {
	this.password = password;
    }
    
    @Override
    protected String exe() throws ApplicationException {
	RoledUser user=null;
	try{
        user=accountService.checkIn(username, password);
	}catch(UserExpiredException ex){
	    info("User: "+username+" expired");
	    message="login.user.expired";
	    return "no_checkin";
	}catch(NotActiveUserException ex){
	    info("User: "+username+" unactiveted");
	    message="login.user.not.active";
	    return "no_checkin";
	}
	message="login.form.accessError";
        if(user==null) return "no_checkin";
        
        //Viene inserito in sessione il tiket
        
        ActionContext context = ActionContext.getContext();
	if (context == null)
	    return "no_checkin";
	Map session = context.getSession();
        session.put(RoledUser.USER_TICKET, user);
        
        for (String role : user.getRoles()) {
	    if(role.equals(RoledUser.SUPER_ADMIN_ROLE))
		session.put(RoledUser.SUPER_TICKET, "ok");
	}
        
        

        return SUCCESS;
    }

}
