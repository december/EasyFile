/**
 * 提供了BorderLayout
 */

package swing2swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
/**
 * 提供了BorderLayout
 * @author Eternal_Answer
 *
 */
public class BorderLayout extends AWTLayout {
  public final static String CENTER = "Center";
  public final static String EAST = "East";
  public final static String NORTH = "North";
  public final static String SOUTH = "South";
  public final static String WEST = "West";

  //-----------------------

  private int hgap, vgap;
  private int H, W;
  private Control centerChild, eastChild, northChild, southChild, westChild;
  /**
   * 构造函数
   */
  public BorderLayout() {
    super();
    H = W = -1;
  }
  /**
   * 构造函数
   * @param hgap 横向间隔
   * @param vgap 纵向间隔
   */
  public BorderLayout(int hgap, int vgap) {
    this.hgap = hgap;
    this.vgap = vgap;
    H = W = -1;
  }
  /**
   * 构造函数
   * @param hgap 横向间隔
   * @param vgap 纵向间隔
   * @param _W 宽度
   * @param _H 高度
   */
  public BorderLayout(int hgap, int vgap, int _W, int _H) {
	    this.hgap = hgap;
	    this.vgap = vgap;
	    H = _H;
	    W = _W;
	  }
  
  protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
    if (H != -1) return new Point(W, H);
    
    readLayoutData(composite);
    Point size = new Point(0, 0);

    Point preferredSize;
    if (northChild != null) {
      preferredSize = getPreferredSize(northChild, wHint, SWT.DEFAULT, flushCache);
      size.y += preferredSize.y + vgap;
    }
    if (southChild != null) {
      preferredSize = getPreferredSize(southChild, wHint, SWT.DEFAULT, flushCache);
      size.y += preferredSize.y + vgap;
    }
    if (westChild != null) {
      preferredSize = getPreferredSize(westChild, SWT.DEFAULT, hHint, flushCache);
      System.out.println(preferredSize);
      size.x += preferredSize.x + hgap;
    }
    if (eastChild != null) {
      preferredSize = getPreferredSize(eastChild, SWT.DEFAULT, hHint, flushCache);
      size.x += preferredSize.x + hgap;
    }
    if (centerChild != null) {
      preferredSize = getPreferredSize(centerChild, wHint, hHint, flushCache);
      size.x += preferredSize.x;
      size.y += preferredSize.y;
    }
    return size;
  }

  protected void layout(Composite composite, boolean flushCache) {
    readLayoutData(composite);
    Rectangle clientArea = composite.getClientArea();
    int top = clientArea.y;
    int bottom = clientArea.y + clientArea.height;
    int left = clientArea.x;
    int right = clientArea.x + clientArea.width;

    Point preferredSize;
    if (northChild != null) {
      preferredSize =
        getPreferredSize(northChild, clientArea.width, SWT.DEFAULT, flushCache);
      northChild.setBounds(left, top, right - left, preferredSize.y);
      top += preferredSize.y + vgap;
    }
    if (southChild != null) {
      preferredSize =
        getPreferredSize(southChild, clientArea.width, SWT.DEFAULT, flushCache);
      southChild.setBounds(
        left,
        bottom - preferredSize.y,
        right - left,
        preferredSize.y);
      bottom -= preferredSize.y + vgap;
    }
    if (westChild != null) {
      preferredSize =
        getPreferredSize(westChild, SWT.DEFAULT, bottom - top, flushCache);
      westChild.setBounds(left, top, preferredSize.x, bottom - top);
      left += preferredSize.x + hgap;
    }
    if (eastChild != null) {
      preferredSize =
        getPreferredSize(eastChild, SWT.DEFAULT, bottom - top, flushCache);
      eastChild.setBounds(
        right - preferredSize.x,
        top,
        preferredSize.x,
        bottom - top);
      right -= preferredSize.x + hgap;
    }
    if (centerChild != null) {
      centerChild.setBounds(left, top, right - left, bottom - top);
    }
  }

  /**
   * Read the layout data of the children of a composite.
   * @param composite the parent composite
   */
  private void readLayoutData(Composite composite) {
    northChild = southChild = eastChild = westChild = centerChild = null;
    Control[] children = composite.getChildren();
    for (int i = 0; i < children.length; i++) {
      //if (!children[i].isVisible())
      //  continue;
      Object layoutData = children[i].getLayoutData();
      if (NORTH.equals(layoutData))
        northChild = children[i];
      else if (SOUTH.equals(layoutData))
        southChild = children[i];
      else if (EAST.equals(layoutData))
        eastChild = children[i];
      else if (WEST.equals(layoutData))
        westChild = children[i];
      else
        centerChild = children[i];
    }
  }
/**
 * @return Returns the hgap.
 */
public int getHgap() {
	return hgap;
}
/**
 * @param hgap The hgap to set.
 */
public void setHgap(int hgap) {
	this.hgap = hgap;
}
/**
 * @return Returns the vgap.
 */
public int getVgap() {
	return vgap;
}
/**
 * @param vgap The vgap to set.
 */
public void setVgap(int vgap) {
	this.vgap = vgap;
}
}
