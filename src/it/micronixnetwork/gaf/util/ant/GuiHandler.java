package it.micronixnetwork.gaf.util.ant;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.input.InputHandler;
import org.apache.tools.ant.input.InputRequest;
import org.apache.tools.ant.input.MultipleChoiceInputRequest;

public class GuiHandler implements InputHandler {

    /**
     * Default non-arg constructor
     */
    public GuiHandler() {
    }

    /**
     * Prompts and requests input. The user enters the input in a JPasswordField
     * where the input is masked.
     * 
     * @param request
     *            the request to handle
     * @throws BuildException
     *             if not possible to read from console
     */
    public void handleInput(final InputRequest request) {
	String prompt = getPrompt(request);
	final JDialog dlgPassword = new JDialog();
	dlgPassword.setTitle("Enter Password");
	dlgPassword.setModal(true);
	final JPasswordField fldPassword = new JPasswordField();
	fldPassword.setPreferredSize(new Dimension(150, 18));
	JLabel lblPassword = new JLabel(prompt);
	JButton btnOk = new JButton("OK");
	btnOk.setPreferredSize(new Dimension(120, 20));
	JPanel pan = new JPanel();
	BoxLayout layout1 = new BoxLayout(pan, BoxLayout.X_AXIS);
	pan.setLayout(layout1);

	pan.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
	pan.add(lblPassword);
	pan.add(Box.createHorizontalGlue());
	pan.add(fldPassword);
	BoxLayout layout2 = new BoxLayout(dlgPassword.getContentPane(), BoxLayout.Y_AXIS);

	dlgPassword.getContentPane().setLayout(layout2);
	dlgPassword.getContentPane().add(pan);
	JPanel pan2 = new JPanel();
	BoxLayout layout3 = new BoxLayout(pan2, BoxLayout.X_AXIS);
	pan2.setLayout(layout3);
	pan2.add(btnOk);

	dlgPassword.getContentPane().add(Box.createVerticalStrut(5));
	dlgPassword.getContentPane().add(pan2);
	dlgPassword.pack();
	btnOk.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		String input = fldPassword.getText();
		request.setInput(input);
		dlgPassword.setVisible(true);
		dlgPassword.dispose();
	    }
	});
	dlgPassword.setVisible(true);
    }

    /**
     * Constructs user prompt from a request.
     * 
     * <p>
     * This implementation adds (choice1,choice2,choice3,...) to the prompt for
     * <code>MultipleChoiceInputRequest</code>s.
     * </p>
     * 
     * @param request
     *            the request to construct the prompt for. Must not be
     *            <code>null</code>.
     * @return the prompt to ask the user
     */
    protected String getPrompt(InputRequest request) {
	String prompt = request.getPrompt();
	if (request instanceof MultipleChoiceInputRequest) {
	    StringBuffer sb = new StringBuffer(prompt);
	    sb.append("(");
	    Enumeration e = ((MultipleChoiceInputRequest) request).getChoices().elements();
	    boolean first = true;
	    while (e.hasMoreElements()) {
		if (!first) {
		    sb.append(",");
		}
		sb.append(e.nextElement());
		first = false;
	    }
	    sb.append(")");
	    prompt = sb.toString();
	}
	return prompt;
    }
}
