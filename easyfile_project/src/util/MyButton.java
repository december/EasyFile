package util;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class MyButton {
	private Image normalImage;
	private Image mouseOverImage;
	private Image mouseDownImage;
	private Image disabledImage;

	private Cursor normalCursor;
	private Cursor handCursor = new Cursor(null, SWT.CURSOR_HAND);

	private Label label;

	private boolean isDisabled = false;

	private List<OnClickListener> listeners = new ArrayList<OnClickListener>();

	public MyButton(Composite parent, Image normalImage, Image mouseOverImage,
			Image mouseDownImage) {
		this(parent, normalImage, mouseOverImage, mouseDownImage, null);
	}

	public MyButton(final Composite parent, Image normalImage,
			Image mouseOverImage, Image mouseDownImage, Image disabledImage) {
		label = new Label(parent, SWT.NONE);
		this.normalImage = normalImage;
		this.mouseOverImage = mouseOverImage;
		this.mouseDownImage = mouseDownImage;
		this.disabledImage = disabledImage;
		normalCursor = parent.getCursor();
		label.setImage(normalImage);
		label.addListener(SWT.MouseEnter, new Listener() {
			public void handleEvent(Event event) {
				if (!isDisabled && MyButton.this.mouseOverImage != null) {
					label.setImage(MyButton.this.mouseOverImage);
					parent.getShell().setCursor(handCursor);
				}
			}
		});
		label.addListener(SWT.MouseExit, new Listener() {
			public void handleEvent(Event event) {
				if (!isDisabled && MyButton.this.normalImage != null) {
					label.setImage(MyButton.this.normalImage);
					parent.getShell().setCursor(normalCursor);
				}
			}
		});
		label.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				if (!isDisabled && MyButton.this.mouseDownImage != null) {
					label.setImage(MyButton.this.mouseDownImage);
				}
			}
		});
		label.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				if (!isDisabled) {
					if (event.widget.equals(label)) {
						if (MyButton.this.mouseOverImage != null) {
							label.setImage(MyButton.this.mouseOverImage);
						}
					}
					for (OnClickListener listener : listeners) {
						listener.onClick();
					}
				}
			}
		});
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
		if (isDisabled) {
			if (disabledImage != null) {
				label.setImage(disabledImage);
			}
		}
	}

	public void dispose() {
		if (normalImage != null && !normalImage.isDisposed()) {
			normalImage.dispose();
		}
		if (mouseOverImage != null && !mouseOverImage.isDisposed()) {
			mouseOverImage.dispose();
		}
		if (mouseDownImage != null && !mouseDownImage.isDisposed()) {
			mouseDownImage.dispose();
		}
		if (disabledImage != null && !disabledImage.isDisposed()) {
			disabledImage.dispose();
		}
	}

	public void addClickListener(OnClickListener listener) {
		listeners.add(listener);
	}

	public void removeClickListener(OnClickListener listener) {
		listeners.remove(listener);
	}

	public interface OnClickListener {
		public void onClick();
	}

	public void setLayoutData(Object btnData) {
		if (label != null && !label.isDisposed()) {
			label.setLayoutData(btnData);
		}
	}

	public Shell getShell() {
		if (label != null && !label.isDisposed()) {
			return label.getShell();
		}
		return null;
	}
}