// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   WaitingFrame.java

package lattice.gui.tooltask;

import java.awt.*;
import java.awt.event.WindowEvent;
import javax.swing.JProgressBar;
import lattice.gui.Console;
import lattice.gui.ConsoleFrame;

// Referenced classes of package lattice.gui.tooltask:
//            JobObserver, JobObservable

public class WaitingFrame extends ConsoleFrame
    implements JobObserver
{

    private boolean goodJob;
    private JobObservable work;
    private JProgressBar progressBar;
    private Thread theThread;

    public WaitingFrame(JobObservable theWork, Console outputConsole)
    {
        super(0.90000000000000002D);
        goodJob = false;
        setSize(400, 300);
        setLocation(200, 200);
        setTitle(theWork.getDescription());
        bottomPanel.removeAll();
        progressBar = new JProgressBar(0, 100);
        setBottomPanel(progressBar);
        work = theWork;
        work.setJobObserver(this);
        console = outputConsole;
        show();
    }

    public void sendMessage(String aMess)
    {
        console.addMessage(aMess);
    }

    public void setPercentageOfWork(int val)
    {
        progressBar.setValue(val);
        validate();
    }

    public void jobEnd(boolean isGood)
    {
        goodJob = isGood;
        dispose();
    }

    public void setJobObservable(JobObservable jO)
    {
        work = jO;
    }

    public void Start()
    {
        progressBar.setValue(0);
        theThread = new Thread(work);
        theThread.start();
    }

    public JobObservable getJob()
    {
        return work;
    }

    public boolean goodWork()
    {
        return goodJob;
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowClosed(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent windowevent)
    {
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowOpened(WindowEvent windowevent)
    {
    }
}
