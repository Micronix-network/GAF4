package it.micronixnetwork.gaf.struts2.action;

import it.micronixnetwork.gaf.exception.ActionException;
import it.micronixnetwork.gaf.exception.ApplicationException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ShellAction extends CardAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String path;

    private String shell_out = "";

    private String shell_err = "";

    @Override
    public String exe() throws ApplicationException {

        try {
            String line;

            final Map<String, String> env = new HashMap<String, String>(System.getenv());
            env.put("Path", env.get("Path") + ";foo");
            final String[] strings = mapToStringArray(env);

            Process p = Runtime.getRuntime().exec(path,strings);
            BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            debug("-- SHELL OUT --");
            while ((line = bri.readLine()) != null) {
                debug(line);
                shell_out += line + "\n";
            }
            bri.close();
            debug("-- SHELL ERROR --");
            while ((line = bre.readLine()) != null) {
                debug(line);
                shell_err += line + "\n";
            }
            bre.close();
            p.waitFor();
            debug("Done.");
        } catch (Exception err) {
            throw new ActionException("Execution exception", err);
        }
        return SUCCESS;
    }

    private String[] mapToStringArray(Map<String, String> map) {
        final String[] strings = new String[map.size()];
        int i = 0;
        for (Map.Entry<String, String> e : map.entrySet()) {
            strings[i] = e.getKey() + '=' + e.getValue();
            debug(e.getKey()+"="+e.getValue());
            i++;
        }
        return strings;
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
