/**
 *标签列表的LabelProvider
 */
package logic.provider;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import basic.LGKLabel;

/**
 * 标签列表的LabelProvider
 * 
 * @author Eternal_Answer
 *
 */
public class TagColumnLabelProvider extends ColumnLabelProvider {
	int index;

	public TagColumnLabelProvider(int _index) {
		super();
		index = _index;
	}

	public String getText(Object element) {
		if (index == 0) {
			return ((LGKLabel) element).getKey();
		}
		if (index == 1) {
			return "";
		}
		if (index == 2) {
			return ((LGKLabel) element).getValue();
		}
		return "";
	}
}
