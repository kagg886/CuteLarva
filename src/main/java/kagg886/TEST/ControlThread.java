package kagg886.TEST;

import javax.swing.JOptionPane;

public class ControlThread extends Thread {
	private int pid;
	private int offset;

	public ControlThread(int pid,String gameName) {
		DynamicBackground.createUI(gameName);
		DynamicBackground.instance.changePhoto("normal");
		this.pid = pid;
		switch (gameName) {
		case "th10":
			this.offset = 0x00474C70;
			break;
		case "th11":
			this.offset = 0x004A5718;
			break;
		case "th12":
			this.offset = 0x004B0C98;
			break;
		case "th13":
			this.offset = 0x004BE7F4;
			break;
		case "th14":
			this.offset = 0x004F5864;
			break;
		case "th15":
			this.offset = 0x004E7450;
			break;
		case "th16":
			this.offset = 0x004A57F4;
			break;
		case "th17":
			this.offset = 0x004B5A40;
			break;
		case "th18":
			this.offset = 0x004CCD48;
			break;
		default:
			JOptionPane.showMessageDialog(null,"不支持的游戏:" + gameName + "\n点击确定让程序退出!");
			System.exit(0);
			break;
		}
	}

	@Override
	public void run() {
		int life = 0;
		int now = 0;
		while (true) {
			now = TouhouLife.lifeGet(pid, offset);
			if (now > life) { // 残机增加
				DynamicBackground.instance.changePhoto("extend");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
				}
				DynamicBackground.instance.changePhoto("normal");
				life = now;
			}
			if (now < life) { // 残机减少
				DynamicBackground.instance.changePhoto("hit");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e0) {
				}
				switch (now) {
				case 0:
					DynamicBackground.instance.changePhoto("zero");
					life = now;
					break;
				case -1:
					DynamicBackground.instance.changePhoto("gameover");
					life = now;
					break;
				default:
					DynamicBackground.instance.changePhoto("normal");
					life = now;
					break;
				}
			}
		}
	}
}
