/**
 * OpenAction��Ϊ��һ��ָ�����ļ�
 */
package logic.action;

import java.io.File;
import adapter.*;

import org.apache.commons.net.ftp.FTPFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.program.Program;

import display.MainWindow;
import basic.*;
import progress.*;
public class ProgressAction extends Action implements ISelectionChangedListener, IDoubleClickListener

{
	MainWindow window;
	ProgressManager pm;
	public ProgressAction(MainWindow w, ProgressManager pm)
	{
		window = w;
		this.pm = pm;
	}


	public void doubleClick( DoubleClickEvent event )
	{
		
		IStructuredSelection selection = (IStructuredSelection) window.getProgressTable().getSelection();
		
		if( selection.size() != 1 ){
			return;
		}
		pm.clicked(window.getProgressTable().getTable().getSelectionIndex());
	}


	@Override
	public void selectionChanged(SelectionChangedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
