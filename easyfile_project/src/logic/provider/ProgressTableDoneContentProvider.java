/**
 * Manage the content of progress table done
 */
package logic.provider;

import org.eclipse.jface.viewers.*;

import progress.*;

public class ProgressTableDoneContentProvider implements
		IStructuredContentProvider {
	ProgressManager pm;

	public Object[] getElements(Object element) {
		int cnt = 0;
		for (int i = 0; i < pm.fsList.size(); i++) {
			if (pm.fsList.get(i).progress > 0.999999)
				cnt++;
		}
		Object[] kids = new Object[cnt];
		cnt = 0;
		for (int i = 0; i < pm.fsList.size(); i++) {
			if (pm.fsList.get(i).progress > 0.999999)
				kids[cnt++] = pm.fsList.get(i);
		}
		return kids;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object old_object, Object new_object) {
	}

	public ProgressTableDoneContentProvider(ProgressManager pm) {
		this.pm = pm;
	}
}
