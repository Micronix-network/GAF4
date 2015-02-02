package it.micronixnetwork.gaf.struts2.gui.component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Writer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;

@StrutsTag(name = "xslt", tldTagClass = "it.micronixnetwork.gaf.struts2.gui.jsp.XsltTag", description = "Transform xml by xsl")
public class Xslt extends GAFComponent {

    private String var;
    private String xslFileName;

    protected HttpServletResponse res;
    protected HttpServletRequest req;

    public Xslt(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
	super(stack);
	this.req = req;
	this.res = res;
    }

    public void setVar(String var) {
	this.var = var;
    }

    public void setXslFileName(String xslFileName) {
	this.xslFileName = xslFileName;
    }

    public boolean end(Writer writer, String body) {
	String path = req.getContextPath();
	File xslFile = null;

	ServletContext context = req.getSession().getServletContext();

	xslFile = new File(context.getRealPath(xslFileName));

	String xml = (String) stack.findValue(var);

	ByteArrayInputStream xmlStream = new ByteArrayInputStream(xml.getBytes());
	try {
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer = tFactory.newTransformer(new StreamSource(xslFile));
	    transformer.transform(new StreamSource(xmlStream), new StreamResult(writer));
	} catch (TransformerException tx) {
	    error("XslTransformTag: " + tx.getMessage(), tx);
	}
	return true;
    }

    @Override
    public boolean usesBody() {
	return false;
    }
}
