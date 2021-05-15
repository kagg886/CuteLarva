package kagg886.TEST;

import com.sun.jna.platform.win32.WinNT.HANDLE;

public class TouhouLife {
	public static int lifeGet(int ProgressID,int offset) {
		HANDLE h = WindowsUtils.getHandleByPid(ProgressID);
		int count = WindowsUtils.readIntOfAddress(h,offset);
		return count;
	}
}
