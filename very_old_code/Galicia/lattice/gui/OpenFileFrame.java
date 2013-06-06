// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   OpenFileFrame.java

package lattice.gui;

import java.awt.Frame;
import java.io.*;
import javax.swing.*;
import lattice.gui.filter.AbstractFilter;
import lattice.gui.filter.Bin_Filter;
import lattice.gui.filter.IBM_Filter;
import lattice.gui.filter.MVC_Filter;
import lattice.gui.filter.RCF_Filter;
import lattice.gui.filter.SLF_Filter;
import lattice.gui.filter.XML_Filter;
import lattice.io.*;
import lattice.io.task.ReadingTask;
import lattice.io.task.WritingTask;
import lattice.util.RelationalContext;

// Referenced classes of package lattice.gui:
//            ConsoleFrame

public abstract class OpenFileFrame extends ConsoleFrame {

	private static File lastDirectory = null;

	public static int BINARY_DATA = 0;

	public static int MULTI_VALUE_DATA = 1;

	public static int FAMILLY_DATA = 2;

	public static int LATTICE_DATA = 3;

	private static Bin_Filter binFilter = new Bin_Filter();

	private static RCF_Filter rcfFilter = new RCF_Filter();

	private static IBM_Filter ibmFilter = new IBM_Filter();

	private static SLF_Filter slfFilter = new SLF_Filter();

	private static XML_Filter xmlBinFilter = new XML_Filter(".bin");

	private static XML_Filter xmlMvcFilter = new XML_Filter(".mvc");

	private static XML_Filter xmlRcfFilter = new XML_Filter(".rcf");

	private static XML_Filter xmlLatFilter = new XML_Filter(".lat");

	private static MVC_Filter mvcFilter = new MVC_Filter();

	protected ReadingTask readTask;

	protected WritingTask writeTask;

	public OpenFileFrame() {
		readTask = null;
		writeTask = null;
	}

	public OpenFileFrame(String title) {
		readTask = null;
		writeTask = null;
		setTitle(title);
	}

	public OpenFileFrame(double div_loc) {
		super(div_loc);
		readTask = null;
		writeTask = null;
	}

	private JFileChooser getFileChooser(int chooserAction) {
		JFileChooser fileChooser = null;
		if (lastDirectory == null)
			fileChooser = new JFileChooser();
		else
			fileChooser = new JFileChooser(lastDirectory);
		fileChooser.setFileSelectionMode(0);
		fileChooser.setDialogType(chooserAction);
		fileChooser.setMultiSelectionEnabled(false);
		UIManager.installLookAndFeel(UIManager.getSystemLookAndFeelClassName(),
				"JFileChooser");
		return fileChooser;
	}

	private File selectedFile(JFileChooser fileChooser) {
		File fich = null;
		int result = fileChooser.showOpenDialog(this);
		if (result == 1 || fileChooser.getSelectedFile() == null)
			return null;
		fich = fileChooser.getSelectedFile();
		lastDirectory = fileChooser.getCurrentDirectory();
		if (!fich.getName().endsWith(
				((AbstractFilter) fileChooser.getFileFilter())
						.getFileExtension()))
			return fich = new File(lastDirectory, fich.getName()
					+ ((AbstractFilter) fileChooser.getFileFilter())
							.getFileExtension());
		else
			return fich;
	}

	protected File getFileFor(int chooserAction, int dataType) {
		JFileChooser fileChooser = getFileChooser(chooserAction);
		switch (dataType) {
		case 0: // '\0'
			fileChooser.addChoosableFileFilter(slfFilter);
			fileChooser.addChoosableFileFilter(ibmFilter);
			fileChooser.addChoosableFileFilter(xmlBinFilter);
			fileChooser.removeChoosableFileFilter(fileChooser
					.getChoosableFileFilters()[0]);
			fileChooser.setFileFilter(fileChooser.getChoosableFileFilters()[0]);
			break;

		case 1: // '\001'
			fileChooser.addChoosableFileFilter(mvcFilter);
			fileChooser.addChoosableFileFilter(xmlMvcFilter);
			fileChooser.removeChoosableFileFilter(fileChooser
					.getChoosableFileFilters()[0]);
			fileChooser.setFileFilter(fileChooser.getChoosableFileFilters()[0]);
			break;

		case 2: // '\002'
			// by cjj 2005.1.10 加入01文件格式
			fileChooser.addChoosableFileFilter(binFilter);
			fileChooser.addChoosableFileFilter(rcfFilter);
			fileChooser.addChoosableFileFilter(xmlRcfFilter);
			fileChooser.removeChoosableFileFilter(fileChooser
					.getChoosableFileFilters()[0]);
			fileChooser.setFileFilter(fileChooser.getChoosableFileFilters()[0]);
			break;

		case 3: // '\003'
			fileChooser.addChoosableFileFilter(xmlLatFilter);
			fileChooser.removeChoosableFileFilter(fileChooser
					.getChoosableFileFilters()[0]);
			fileChooser.setFileFilter(fileChooser.getChoosableFileFilters()[0]);
			break;
		}
		// 返回一个file类型
		return selectedFile(fileChooser);
	}

	protected AbstractReader getReaderFromFile(File file)
			throws FileNotFoundException {
		if (rcfFilter.accept(file))
			return new RcfReader(new FileReader(file));
		if (ibmFilter.accept(file))
			return new IbmReader(new FileReader(file));
		if (slfFilter.accept(file))
			return new SlfReader(new FileReader(file));
		if (xmlBinFilter.accept(file) || xmlMvcFilter.accept(file)
				|| xmlRcfFilter.accept(file) || xmlLatFilter.accept(file))
			return new XmlReader(new FileReader(file));
		if (mvcFilter.accept(file))
			return new MvcReader(new FileReader(file));
		else
			return null;
	}

	protected AbstractWriter getWriterFromFile(File file) throws IOException {
		if (binFilter.accept(file))
			return new BinWriter(new FileWriter(file));
		if (rcfFilter.accept(file))
			return new RcfWriter(new FileWriter(file));
		if (ibmFilter.accept(file))
			return new IbmWriter(new FileWriter(file));
		if (slfFilter.accept(file))
			return new SlfWriter(new FileWriter(file));
		if (xmlBinFilter.accept(file) || xmlMvcFilter.accept(file)
				|| xmlRcfFilter.accept(file) || xmlLatFilter.accept(file))
			return new XmlWriter(new FileWriter(file));
		else
			return null;
	}

	protected void writeAction(Object data, String actionType, int writingType) {
		File fich = null;
		// 得到一个指定的文件
		fich = getFileFor(1, writingType);
		if (fich == null) {
			addMessage("File not selected ...");
			addMessage(actionType + " Cancelled !\n");
			return;
		}
		AbstractWriter writer = null;
		try {
			writer = getWriterFromFile(fich);
		} catch (FileNotFoundException fnfe) {
			addMessage("File Not Found !");
			addMessage("Writing Aborted ...\n");
			return;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			addMessage(ioe.getMessage());
			addMessage("Writing Aborted ...\n");
			return;
		}
		if (writer == null) {
			addMessage("Can not find any writer for the file !");
			addMessage("Writing Aborted ...\n");
		}
		// 抽象的writer对象中加载data数据,以便输出
		writer.setData(data);
		if (writer instanceof XmlWriter)
			((XmlWriter) writer).setTypeOfWriting(writingType);

		// writeTask为protect类型，是类变量,setDataWriter操作，是加载到writeTask中去具体操作
		writeTask.setDataWriter(writer);
		writeTask.exec();
	}

	protected void readAction(String actionType, int readingType) {
		File fich = null;
		fich = getFileFor(0, readingType);
		if (fich == null) {
			addMessage("File not selected ...");
			addMessage(actionType + " Cancelled !\n");
			return;
		}
		AbstractReader reader = null;
		try {
			reader = getReaderFromFile(fich);
			reader.setDefaultNameForData(fich.getName());
		} catch (FileNotFoundException fnfe) {
			addMessage("File Not Found !");
			addMessage("Reading Aborted ...\n");
			return;
		}
		if (reader instanceof XmlReader)
			((XmlReader) reader).setTypeOfReading(readingType);
		readTask.setDataReader(reader);
		readTask.exec();
	}

	protected void openData() {
		addMessage("Open ...");
		int readingType = whichData("Open data from :");
		if (readingType == -1) {
			addMessage("Open Cancelled !\n");
			return;
		} else {
			readAction("Open", readingType);
			return;
		}
	}

	protected void importData() {
		addMessage("Import ...");
		int readingType = whichData("Import data from :");
		if (readingType == -1) {
			addMessage("Import Cancelled !\n");
			return;
		} else {
			readAction("Import", readingType);
			return;
		}
	}

	protected void saveAllData(RelationalContext data) {
		addMessage("Save all ...");
		writeAction(data, "Save all", FAMILLY_DATA);
	}

	public int whichData(String action) {
		String action1 = action + " Relational Contexts Familly";
		String action2 = action + " Binary Relation";
		String action3 = action + " Multi-Valued Relation";
		String action4 = action + " Lattice";
		Object lesVals[] = { action1, action2, action3, action4 };
		String val = (String) JOptionPane.showInputDialog(this,
				"Choose a possible action :", "Choose a possible action :", 3,
				null, lesVals, action1);
		if (val == null)
			return -1;
		if (val.equals(action1))
			return FAMILLY_DATA;
		if (val.equals(action2))
			return BINARY_DATA;
		if (val.equals(action3))
			return MULTI_VALUE_DATA;
		if (val.equals(action4))
			return LATTICE_DATA;
		else
			return -1;
	}

	public File chooseAnyFile() {
		return selectedFile(getFileChooser(1));
	}

}
