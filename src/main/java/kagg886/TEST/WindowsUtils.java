package kagg886.TEST;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

public class WindowsUtils {

    private static final Kernel32 KERNEL = Kernel32.INSTANCE;

    private static final User32 USER = User32.INSTANCE;


    /**
     * 通过窗口名称获取句柄
     * @param windowName    窗口名称
     * @return  句柄
     */
    public static HANDLE getHandleByWindowName(String windowName) {
        HWND hwnd = USER.FindWindow(null, windowName);
        IntByReference pidReference = new IntByReference();
        USER.GetWindowThreadProcessId(hwnd, pidReference);
        int pid = pidReference.getValue();

        return KERNEL.OpenProcess(WinNT.PROCESS_ALL_ACCESS, false, pid);
    }


    /**
     * 通过pid获取句柄
     * @param pid    pid
     * @return  句柄
     */
    public static HANDLE getHandleByPid(int pid) {
        return KERNEL.OpenProcess(WinNT.PROCESS_ALL_ACCESS, false, pid);
    }


    /**
     * 将int值写入到指定地址
     * @param handle    程序句柄
     * @param address   地址
     * @param value     值
     * @return  写入多少 -1为失败
     */
    public static int writeIntToAddress(HANDLE handle, int address, int value) {
        Memory memory = new Memory(4);
        memory.setInt(0, value);
        IntByReference readByteNumber = new IntByReference();
        KERNEL.WriteProcessMemory(handle, Pointer.createConstant(address), memory, 4, readByteNumber);
        return readByteNumber.getValue();
    }

    /**
     * 读取指定地址的int值
     * @param handle    程序句柄
     * @param address   地址
     * @return  值
     */
    public static int readIntOfAddress(HANDLE handle, int address) {
        Memory memory = new Memory(4);
        IntByReference readByteNumber = new IntByReference();
        KERNEL.ReadProcessMemory(handle, Pointer.createConstant(address), memory, 4, readByteNumber);
        return memory.getInt(0);
    }


    /**
     * 读取指定地址的int值
     * @param handle    程序句柄
     * @param pointer   地址
     * @return  值
     */
    public static int readIntOfPointer(HANDLE handle, Pointer pointer) {
        Memory memory = new Memory(4);
        IntByReference readByteNumber = new IntByReference();
        KERNEL.ReadProcessMemory(handle, pointer, memory, 4, readByteNumber);
        return memory.getInt(0);
    }

}
