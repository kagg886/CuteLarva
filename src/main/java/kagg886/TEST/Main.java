package kagg886.TEST;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.sun.jna.platform.win32.WinNT.HANDLE;

public class Main {
	public static void main(String[] args) throws IOException {
		Process o = Runtime.getRuntime().exec("tasklist");
		InputStream stream = o.getInputStream();
		ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
		int bte = 0;
		while ((bte = stream.read()) != -1) {
			stream2.write(bte);
		}
		bte = 0;
		
		String e = new String(stream2.toByteArray(),"GBK");
		for (String string : e.split("\r\n")) {
			if (string.matches("th[0-9]+.exe.*")) {
				startLarva(string);
				bte = 1;
			}
		}
		
		if (bte == 0) {
			JOptionPane.showMessageDialog(null,"你确定你开游戏了?");
			System.exit(0);
		}
	}
	
	static void startLarva(String string) {
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(string);
		while (matcher.find()) {
			if (matcher.group().length() == 4) {
				int pid = Integer.parseInt(matcher.group());
				ControlThread thread = new ControlThread(pid,string.substring(0,4));
				thread.start();
				break;
			}
		}
		pattern = null;
		matcher = null;
	}
}
