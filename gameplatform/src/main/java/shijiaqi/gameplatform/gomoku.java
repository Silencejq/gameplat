package shijiaqi.gameplatform;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

class gomoku {
	private int round = 1;
	private int remaintime = 15;
	private boolean taskstart = true;
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel(new GridLayout(15, 15));
	private chess[][] chessman = new chess[17][17];
	private JLabel text = new JLabel("决策时间还剩15秒！");
	private Border matte = new MatteBorder(1, 1, 1, 1, Color.BLACK);
	private Timer time = new Timer();
	private TimerTask task;

	private gomoku() {
		task = new TimerTask() {
			public void run() {
				remaintime--;
				text.setText("决策时间还剩" + remaintime + "秒！");
				if (remaintime == 0) {
					if ((round & 1) == 1) {
						time.cancel();
						JOptionPane.showMessageDialog(null, "干啥呢???黑色玩家超时，白色玩家胜利！", "结束", JOptionPane.PLAIN_MESSAGE);
						gomoku.start();
						frame.dispose();
					} else {
						time.cancel();
						JOptionPane.showMessageDialog(null, "干啥呢???白色玩家超时，黑色玩家胜利！", "结束", JOptionPane.PLAIN_MESSAGE);
						gomoku.start();
						frame.dispose();
					}
				}
			}
		};
//限时15秒计时器任务
		ActionListener listen1 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remaintime = 15;
				if (taskstart) {
					time.scheduleAtFixedRate(task, 0, 1000);
					taskstart = false;
				}
				if ((round & 1) == 1) {
					chess n = (chess) e.getSource();
					n.removeActionListener(n.getActionListeners()[0]);
					n.setIcon(new inserticon().insert("heiqi.gif", n, 1, 1));
					n.color = 1;
					if (judgment(n)) {
						time.cancel();
						JOptionPane.showMessageDialog(null, "牛逼噢!黑色玩家赢了！", "结束", JOptionPane.PLAIN_MESSAGE);
						gomoku.start();
						frame.dispose();
					}
					round++;
				} else {
					chess n = (chess) e.getSource();
					n.removeActionListener(n.getActionListeners()[0]);
					n.setIcon(new inserticon().insert("baiqi.gif", n, 1, 1));
					n.color = 2;
					if (judgment(n)) {
						time.cancel();
						JOptionPane.showMessageDialog(null, "牛逼噢!白色玩家赢了！", "结束", JOptionPane.PLAIN_MESSAGE);
						gomoku.start();
						frame.dispose();
					}
					round++;
				}
			}
		};
//监听设置完成
		for (int n = 0; n < 17; n++) {
			for (int m = 0; m < 17; m++) {
				chessman[n][m] = new chess();
				chessman[n][m].x = m;
				chessman[n][m].y = n;
//为了简单化边界问题，这边扩大一圈棋盘，下面的才是真棋盘
				if (n > 0 && n < 16 && m > 0 && m < 16) {
					chessman[n][m].setBorder(matte);
					chessman[n][m].addActionListener(listen1);
					panel.add(chessman[n][m]);
				}
			}
		}
		UIManager.put("Label.font", new Font(null, 0, 25));
		UIManager.put("Button.font", new Font(null, 0, 25));
		text.setFont(new Font(null, 0, 40));
		frame.add(BorderLayout.NORTH, text);
		frame.add(panel);
		framestart.start(frame, 900, 1000, "gomoku", 500, 10);
		for (int n = 1; n < 16; n++) {
			for (int m = 1; m < 16; m++) {
				chessman[n][m].setIcon(new inserticon().insert("qipan.gif", chessman[1][1], 1, 1));
			}
		}
	}

	private boolean judgment(chess n) {
		int x = n.x - 1;
		int y = n.y;
		int number = 1;
		int direction = 0;
		while (true) {
			if (chessman[y][x].color == n.color) {
				if (direction == 0) {
					x--;
				} else {
					x++;
				}
				number++;
				if (number == 5) {
					return true;
				}
			} else {
				if (direction == 0) {
					direction++;
					x = n.x + 1;
				} else {
					break;
				}
			}
		}
//横向判断
		x = n.x;
		y = n.y - 1;
		number = 1;
		direction = 0;
		while (true) {
			if (chessman[y][x].color == n.color) {
				if (direction == 0) {
					y--;
				} else {
					y++;
				}
				number++;
				if (number == 5) {
					return true;
				}
			} else {
				if (direction == 0) {
					direction++;
					y = n.y + 1;
				} else {
					break;
				}
			}
		}
//纵向判断
		x = n.x - 1;
		y = n.y - 1;
		number = 1;
		direction = 0;
		while (true) {
			if (chessman[y][x].color == n.color) {
				if (direction == 0) {
					x--;
					y--;
				} else {
					x++;
					y++;
				}
				number++;
				if (number == 5) {
					return true;
				}
			} else {
				if (direction == 0) {
					direction++;
					x = n.x + 1;
					y = n.y + 1;
				} else {
					break;
				}
			}
		}
//左斜判断
		x = n.x + 1;
		y = n.y - 1;
		number = 1;
		direction = 0;
		while (true) {
			if (chessman[y][x].color == n.color) {
				if (direction == 0) {
					x++;
					y--;
				} else {
					x--;
					y++;
				}
				number++;
				if (number == 5) {
					return true;
				}
			} else {
				if (direction == 0) {
					direction++;
					x = n.x - 1;
					y = n.y + 1;
				} else {
					break;
				}
			}
		}
//右斜判断
		return false;
	}

	static void start() {
		new gomoku();
	}
}

class chess extends JButton {
	int color = 0;
	int x;
	int y;
}