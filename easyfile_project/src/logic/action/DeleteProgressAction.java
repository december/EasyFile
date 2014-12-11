/**
 * �ļ�ɾ������
 */
package logic.action;
/*
 * ����java��׼��
 */
import java.io.File;

import org.apache.commons.net.ftp.FTPFile;
/*
 * ����swt, jface��
 */
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;



import util.EasyFileUtil;
/*
 * �����Լ��İ�
 */
import display.MainWindow;
import basic.*;
import progress.*;
public class DeleteProgressAction extends Action implements MouseListener
{
	MainWindow window;
	int index, done;
	ProgressManager pm;
	public DeleteProgressAction(MainWindow w, int index, int done, ProgressManager pm)
	{
		window = w;
		this.index = index;
		this.done = done;
		this.pm = pm;
		setText("Delete");
		setToolTipText("");
		setImageDescriptor(ImageDescriptor.createFromURL(EasyFileUtil.newURL( "file:icons/delete.bmp" )));
	}
	
	public void run()
	{
		pm.deleteProgress(index, done);
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * �����ʱˢ��
	 */
	@Override
	public void mouseDown(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
