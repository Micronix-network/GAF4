package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellAction extends WebAppAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String path;
	
	private String shell_out="";
	
	private String shell_err="";
	
	@Override
	public String exe() throws ApplicationException {
		
		try {
		      String line;
		      Process p = Runtime.getRuntime().exec(path);
		      BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
		      BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		      while ((line = bri.readLine()) != null) {
		        debug(line);
		        shell_out+=line+"\n";
		      }
		      bri.close();
		      while ((line = bre.readLine()) != null) {
		        debug(line);
		        shell_err+=line+"\n";
		      }
		      bre.close();
		      p.waitFor();
		      debug("Done.");
		    }
		    catch (Exception err) {
		     throw new ActionException("Execution exception",err);
		    }
		return SUCCESS;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getShell_out() {
		return shell_out;
	}

	public void setShell_out(String shell_out) {
		this.shell_out = shell_out;
	}

	public String getShell_err() {
		return shell_err;
	}

	public void setShell_err(String shell_err) {
		this.shell_err = shell_err;
	}
	
	

}
