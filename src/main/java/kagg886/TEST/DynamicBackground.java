package kagg886.TEST;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;

public class DynamicBackground extends JFrame implements ActionListener {
	public static DynamicBackground instance;
	
	
	private static final long serialVersionUID = 1L;
	JMenuBar bar;
	JMenu menus;
	JMenuItem[] items;
	String[] picName = {"正常","疮痍","中弹","奖残","危险"};
	String[] picName2 = {"normal","gameover","hit","extend","zero"};
	JComponent p3;
	JLabel imgLabel;
	Image bg, bg2;
 
	public DynamicBackground(String title) {
		bar = new JMenuBar();
		menus = new JMenu("更换背景");
		items = new JMenuItem[5];
		bar.add(menus);
		for (int i = 0; i < 5; i++) {
			items[i] = new JMenuItem(picName[i]);
			menus.add(items[i]);
			items[i].addActionListener(this);
		}
		this.setJMenuBar(bar);
		p3 = (JComponent) getLayeredPane();
		p3.setLayout(null);
		((JComponent) getContentPane()).setOpaque(false);
		this.setUndecorated(false);
		this.setResizable(false);
		JTextArea input = new JTextArea();
		input.setOpaque(false);
		input.setForeground(Color.GREEN);
		add(input);
		setTitle("美好蛾子拯救疮痍,当前游戏:" + title + "---Power By kagg886");
		setSize(600, 480);
		setVisible(true);
	}
 
	public static void createUI(String title) {
		instance = new DynamicBackground(title);
	}
	
 
	public void changePhoto(String picName) {
		System.out.println("change photo :"+picName);
		URL imgUrl = DynamicBackground.class.getClassLoader().getResource(picName + ".jpg");
		bg = Toolkit.getDefaultToolkit().getImage(imgUrl);
		bg2 = bg.getScaledInstance(600, 480, Image.SCALE_DEFAULT);
		/*
		 * JLabel只new一次，保证所有的点击操作使用同一个JLabel，唯一不同的是它的icon
		 * 动态修改图片，通过修正setIcon完成，它会自动刷新背景的
		 */
		if(imgLabel==null){
			imgLabel = new JLabel(new ImageIcon(bg2));
			imgLabel.setOpaque(false);
			imgLabel.setBounds(0, 0, 600, 480);
			p3.add(imgLabel, new Integer(-30001));
		}else{
			imgLabel.setIcon(new ImageIcon(bg2));
		}
	}
 
	public void actionPerformed(ActionEvent e) {
		
		for (int i = 0; i < items.length; i++) {
			if (e.getSource() == items[i]) {
				this.changePhoto(picName2[i]);
				break;
			}
			
		}
	}
 
}