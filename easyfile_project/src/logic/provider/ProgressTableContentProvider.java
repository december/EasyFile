/**
 * Manage the content of progress table
 */
package logic.provider;

import org.eclipse.jface.viewers.*;

import progress.*;

public class ProgressTableContentProvider implements IStructuredContentProvider {
	ProgressManager pm;

	public Object[] getElements(Object element) {
		int cnt = 0;
		for (int i = 0; i < pm.fsList.size(); i++) {
			if (pm.fsList.get(i).progress < 0.999999)
				cnt++;
		}
		Object[] kids = new Object[cnt];
		cnt = 0;
		for (int i = 0; i < pm.fsList.size(); i++) {
			if (pm.fsList.get(i).progress < 0.999999)
				kids[cnt++] = pm.fsList.get(i);
		}

		// System.out.println("... done");
		return kids;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object old_object, Object new_object) {
	}

	public ProgressTableContentProvider(ProgressManager pm) {
		this.pm = pm;
	}
}
