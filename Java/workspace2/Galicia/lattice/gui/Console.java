// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Console.java

package lattice.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import lattice.command.Command;

// Referenced classes of package lattice.gui:
//            MessageWriter

public class Console extends JPanel
    implements MessageWriter, ActionListener
{

    protected Command cmd;
    protected String prompt;
    protected JTextArea output;
    protected JTextField input;

    public Console()
    {
        prompt = "> ";
        init1();
    }

    public Console(String inv)
    {
        this();
        prompt = inv;
    }

    public Console(Command c)
    {
        prompt = "> ";
        cmd = c;
        init2();
    }

    public Console(Command c, String inv)
    {
        prompt = "> ";
        cmd = c;
        prompt = inv;
        init2();
    }

    public void addMessage(String newMess)
    {
        output.append(newMess + "\n");
    }

    public void clear()
    {
        output.setText("");
    }

    public void init1()
    {
        setLayout(new BorderLayout());
        initOutput();
        add(new JScrollPane(output), "Center");
    }

    public void init2()
    {
        setLayout(new BorderLayout());
        initOutput();
        initInput();
        add(new JScrollPane(output), "Center");
        add(input, "South");
        input.addActionListener(this);
    }

    public void initOutput()
    {
        output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font("Geneva", 0, 11));
    }

    public void initInput()
    {
        input = new JTextField(prompt);
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        addMessage(s);
        input.setText(prompt);
        cmd.eval(s);
    }

    public String getText()
    {
        return output.getText();
    }
}
