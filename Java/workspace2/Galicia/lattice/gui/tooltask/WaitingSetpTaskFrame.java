// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   WaitingSetpTaskFrame.java

package lattice.gui.tooltask;

import java.awt.*;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import lattice.gui.ConsoleFrame;
import lattice.gui.MessageWriter;

// Referenced classes of package lattice.gui.tooltask:
//            StepTaskObserver, JobObservable, StepTaskObservable

public class WaitingSetpTaskFrame extends ConsoleFrame
    implements StepTaskObserver
{

    private boolean goodJob;
    private StepTaskObservable work;
    private JProgressBar progressBarTask;
    private JProgressBar progressBarJob;
    private Thread theThread;
    private MessageWriter outputMessage;
    private int maxStep;
    private int currentStep;

    public WaitingSetpTaskFrame()
    {
        goodJob = false;
        maxStep = 1;
        currentStep = 0;
    }

    public WaitingSetpTaskFrame(StepTaskObservable theWork, int maxStep, MessageWriter outputMessage)
    {
        super(0.90000000000000002D);
        goodJob = false;
        this.maxStep = 1;
        currentStep = 0;
        setSize(600, 300);
        setLocation(200, 200);
        setTitle(theWork.getDescription());
        addWindowListener(this);
        bottomPanel.removeAll();
        JPanel panelSud = new JPanel(new BorderLayout());
        progressBarTask = new JProgressBar(0, 100);
        progressBarJob = new JProgressBar(0, 100);
        if(maxStep == 1)
        {
            panelSud.add(progressBarJob, "Center");
        } else
        {
            panelSud.add(progressBarTask, "North");
            panelSud.add(progressBarJob, "South");
        }
        setBottomPanel(panelSud);
        work = theWork;
        work.setJobObserver(this);
        this.outputMessage = outputMessage;
        this.maxStep = maxStep;
        show();
    }

    public void setPercentageOfWork(int i)
    {
        progressBarJob.setValue(i);
    }

    public void setPercentageOfStep(int i)
    {
        progressBarTask.setValue(i);
    }

    public void setMaxStep(int maxStep)
    {
        this.maxStep = maxStep;
    }

    public void sendMessage(String aMess)
    {
        outputMessage.addMessage(aMess);
    }

    public void jobEnd(boolean isGood)
    {
        goodJob = isGood;
        currentStep++;
        setPercentageOfStep((currentStep * 100) / maxStep);
    }

    public void taskEnd()
    {
        dispose();
    }

    public void setJobObservable(JobObservable jobobservable)
    {
    }

    public void windowOpened(WindowEvent arg0)
    {
        progressBarJob.setValue(0);
        progressBarTask.setValue(0);
        theThread = new Thread(work);
        theThread.start();
    }

    public void windowClosing(WindowEvent arg0)
    {
        work.sendMessage(getTitle() + " ... CANCELED !\n");
        theThread.stop();
        dispose();
    }

    public void windowClosed(WindowEvent windowevent)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }
}
