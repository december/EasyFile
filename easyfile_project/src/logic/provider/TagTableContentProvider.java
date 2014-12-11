/**
 * 标签列表的ContentProvider
 */
package logic.provider;

import adapter.*;
import java.util.ArrayList;

import org.eclipse.jface.viewers.*;

import basic.*;

/**
 * 标签列表的ContentProvider
 * 
 * @author Eternal_Answer
 *
 */
public class TagTableContentProvider implements IStructuredContentProvider {
	boolean first;
	Object[] kids;

	public TagTableContentProvider() {
		first = true;
	}

	/**
	 * 若elemnt为"1"，则重新获取标签列表；
	 */
	public Object[] getElements(Object element) {
		if (((String) element).equals("1")) {
			first = true;
		}
		if (!first)
			return kids;
		first = false;
		ArrayList<LGKLabel> Tags = FTPAdapter.getInstance().showAllKey();
		kids = new Object[Tags.size()];
		for (int i = 0; i < Tags.size(); ++i) {
			kids[i] = new LGKLabel(Tags.get(i).getKey(), Tags.get(i).getType(),
					Tags.get(i).getValue());
			ArrayList<String> valuelist = new ArrayList<String>();
			for (int j = 0; j < Tags.get(i).getValueList().size(); ++j) {
				valuelist.add(new String(Tags.get(i).getValueList().get(j)));

			}
			((LGKLabel) kids[i]).setValueList(valuelist);
		}
		return kids;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object old_object, Object new_object) {
	}
}
