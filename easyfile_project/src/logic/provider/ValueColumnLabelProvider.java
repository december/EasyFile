/**
 *文件的标签值的列表的LabelProvider
 */
package logic.provider;

import org.eclipse.jface.viewers.ColumnLabelProvider;

/**
 * 文件的标签值的列表的LabelProvider
 * 
 * @author Eternal_Answer
 *
 */
public class ValueColumnLabelProvider extends ColumnLabelProvider {
	int index;

	public ValueColumnLabelProvider(int _index) {
		super();
		index = _index;
	}

	public String getText(Object element) {
		if (index == 0) {
			if (((Sign) element).type == 0)
				return "Defulat Value";
			else
				return "Value " + (((Sign) element).idx + 1);

		} else
			return ((Sign) element).value;
	}
}
