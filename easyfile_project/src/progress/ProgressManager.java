package progress;

import display.MainWindow;
import adapter.*;
import basic.*;

import org.eclipse.jface.dialogs.MessageDialog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import org.eclipse.swt.widgets.Display;

public class ProgressManager {
	MainWindow window;
	int init = 1;
	public List<FileStatus> fsList = new ArrayList<FileStatus>();
	/**
	 * init
	 * @param w
	 */
	public ProgressManager(MainWindow w) {
		window = w;
		Timer timer = new Timer();
		timer.schedule(new MyTask(this), 1000, 1000);
	}
	/**
	 * when clicked
	 * @param index
	 */
	public void clicked(int index) {

		int cnt = -1;
		for (FileStatus fs : fsList) {
			if (fs.progress < 0.999999) {
				cnt++;
				index--;
			} else {
				cnt++;
			}

			System.out.println(cnt);
			if (index == -1)
				break;
		}
		System.out.println(cnt);
		FileStatus fs = fsList.get(cnt);
		if (fs.status == -2) { // stop uploading
			FTPAdapter.getInstance().cutProcess(fs.remoteNameList,
					fs.localNameList);
			fs.status = -1;
		} else if (fs.status == 2) { // stop downloading
			FTPAdapter.getInstance().cutProcess(fs.remoteNameList,
					fs.localNameList);
			fs.status = 1;
		} else if (fs.status == -1) { // start uploading
			if (fs.progress < 0.999999) {
				FTPAdapter.getInstance().newUploadProcess(fs.remoteNameList,
						fs.localNameList, fs.wp);
				fs.status = -2;
			}
		} else if (fs.status == 1) { // start downloading
			if (fs.progress < 0.999999) {
				FTPAdapter.getInstance().newDownloadProcess(fs.remoteNameList,
						fs.localNameList, fs.curMode);
				fs.status = 2;
			}
		}
		saveToFile();
	}
	/**
	 * delete a progess
	 * @param index
	 * @param done
	 */
	public void deleteProgress(int index, int done) {
		int cnt = -1;

		System.out.println(index);
		for (FileStatus fs : fsList) {
			if (fs.progress < 0.999999) {
				cnt++;
				if (done == 0)
					index--;
			} else {
				cnt++;
				if (done == 1)
					index--;
			}

			if (index == -1)
				break;
		}
		System.out.println(cnt);
		fsList.remove(cnt);
		saveToFile();
	}
	/**
	 * read from file
	 */
	public void readFromFile() {
		fsList.clear();
		BufferedReader br;

		try {
			File fp = new File("./res/progress.txt");
			if (!fp.exists()) {
				System.out.println("creating progress.txt");
				fp.createNewFile();
			}
		} catch (Exception e) {

		}
		try {
			// System.out.println("in try");
			br = new BufferedReader(new FileReader("./res/progress.txt"));

			String data = br.readLine();
			if (data == null || !data.equals("fuckxj")) {
			} else {
				data = br.readLine();
				while (data != null) {

					FileStatus fs = new FileStatus();
					fs.localNameList = new ArrayList<String>();
					fs.remoteNameList = new ArrayList<String>();
					int ls, rs;

					ls = Integer.parseInt(data);
					for (int j = 0; j < ls; j++)
						fs.localNameList.add(br.readLine());

					rs = Integer.parseInt(br.readLine());
					for (int j = 0; j < rs; j++)
						fs.remoteNameList.add(br.readLine());

					fs.progress = Double.parseDouble(br.readLine());
					;
					fs.status = Integer.parseInt(br.readLine());
					fs.wp = Integer.parseInt(br.readLine());
					if (init == 1 && fs.status == -2)
						fs.status = -1;
					if (init == 1 && fs.status == 2)
						fs.status = 1;
					fsList.add(fs);

					data = br.readLine();
				}
			}
			init = 0;
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * save to file
	 */
	public void saveToFile() {
		try {
			FileOutputStream out = new FileOutputStream("./res/progress.txt");
			PrintStream p = new PrintStream(out);
			p.println("fuckxj");
			for (FileStatus fs : fsList) {
				p.println(fs.localNameList.size());
				for (String s : fs.localNameList) {
					p.println(s);
				}
				p.println(fs.remoteNameList.size());
				for (String s : fs.remoteNameList) {
					p.println(s);
				}
				p.println(fs.progress);
				p.println(fs.status);
				p.println(fs.wp);
			}
			p.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * update all progress
	 */
	public void updateProgress() {
		if (FTPAdapter.getInstance().isConnection()
				&& FTPAdapter.getInstance().haveLoggedIn()) {
			for (FileStatus fs : fsList) {
				if (fs.status == -2 || fs.status == 2) {
					fs.progress = FTPAdapter.getInstance().getAccomplishRate(
							fs.remoteNameList, fs.localNameList, fs.curMode);
					if (fs.progress > 0.999999) {
						if (fs.status == 2)
							fs.status = 1;
						if (fs.status == -2)
							fs.status = -1;
						window.refresh();
						if (fs.status == -1)
							MessageDialog.openInformation(Display.getCurrent()
									.getActiveShell(), "Uploading ",
									fs.localNameList + " Done");
						else if (fs.status == 1)
							MessageDialog.openInformation(Display.getCurrent()
									.getActiveShell(), "Downloading ",
									fs.localNameList + " Done");
					}
				}
			}
		}
	}
}
/**
 * the timer task
 * @author nikifor40
 *
 */
class MyTask extends TimerTask {
	ProgressManager pm;

	public MyTask(ProgressManager pm) {
		this.pm = pm;
	}

	@Override
	public void run() {
		Runnable runnable = new Runnable() {
			public void run() {
				pm.readFromFile();
				pm.updateProgress();
				pm.window.updateProgressTable();
				pm.saveToFile();
			}
		};
		Display.getDefault().syncExec(runnable);

	}
}