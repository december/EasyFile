/**
 * 文件的标签值的列表的ContentProcider
 */
package logic.provider;

import java.util.ArrayList;

import org.eclipse.jface.viewers.*;

import basic.*;

/**
 * 文件的标签值的列表的LabelProvider
 * 
 * @author Eternal_Answer
 *
 */
public class ValueTableContentProvider implements IStructuredContentProvider {
	Object[] kids;
	/**
	 * 当前操作的标签信息
	 */
	public LGKLabel thisTag;

	// boolean first;
	/**
	 * 构造函数
	 * 
	 * @param Tag
	 *            需要显示的标签
	 */
	public ValueTableContentProvider(LGKLabel Tag) {
		thisTag = new LGKLabel("", 0, "");
		thisTag.setKey(new String(Tag.getKey()));
		thisTag.setType(Tag.getType());
		thisTag.setValue(new String(Tag.getValue()));
		//System.out.println("this tag = " + Tag.getValue());
		ArrayList<String> valuelist = new ArrayList<String>();
		for (int i = 0; i < Tag.getValueList().size(); ++i)
			valuelist.add(new String(Tag.getValueList().get(i)));
		thisTag.setValueList(valuelist);
	}

	/**
	 * 若element为"0"，则更新当前的标签类别；若element为"1",则在当前的标签值后加一个新值；否则保存当前的值
	 */
	public Object[] getElements(Object element) {
		if (((String) element).equals("0")) {
			if (thisTag.getType() == 0) {
				kids = new Object[1];
				kids[0] = new Sign(0, 0, thisTag.getValue());
			} else {
				ArrayList<String> values = thisTag.getValueList();
				kids = new Object[values.size()];
				for (int i = 0; i < values.size(); ++i)
					kids[i] = new Sign(i, 1, values.get(i));
			}
		} else if (((String) element).equals("1")) {
			Object[] newKids = new Object[kids.length + 1];
			for (int i = 0; i < kids.length; ++i)
				newKids[i] = kids[i];
			newKids[kids.length] = new Sign(kids.length, 1, "");
			thisTag.getValueList().add("");
			kids = newKids;
		} else {
			if (thisTag.getType() == 0)
				thisTag.setValue(((Sign) kids[0]).value);
			else {
				ArrayList<String> labellist = new ArrayList<String>();
				for (int i = 0; i < kids.length; ++i) {
					// System.out.println(((Sign)kids[i]).value);
					labellist.add(((Sign) kids[i]).value);
				}
				thisTag.setValueList(labellist);
			}
		}
		return kids;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object old_object, Object new_object) {
	}
}
