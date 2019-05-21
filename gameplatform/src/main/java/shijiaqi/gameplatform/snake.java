package shijiaqi.gameplatform;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.Timer;

import javax.swing.*;
import javax.swing.border.*;

class snake {
	private JFrame frame = new JFrame();
	private JPanel panel2 = new JPanel();
	private JLabel text = new JLabel("0");
	private JMenuBar menubar = new JMenuBar();
	private JMenu menu = new JMenu("选项");
	private JMenuItem item1 = new JMenuItem("设置倍速");
	private Border matte = new MatteBorder(2, 2, 2, 2, Color.BLACK);
	private static double speed;
	private Timer time = new Timer();
	private TimerTask task;
	private int[] fruit = new int[2];
	private int direction = 1;
	private int direction1 = 1;
	private int body = 5;
	private JPanel panel1 = new JPanel(new GridLayout(60, 90));
	private panel[][] panels = new panel[60][90];
	private int[][] position = new int[5400][2];
//使用panel数组来显示画面，使用一个更加方便的数组来做计算（一维存坐标，二维表示自然数列）

	private snake() {
		this(1);
	}

	private snake(double speed) {
		this.speed = speed;
		UIManager.put("Label.font", new Font(null, 0, 25));
		UIManager.put("Button.font", new Font(null, 0, 25));
		text.setFont(new Font(null, 0, 138));
		ActionListener listenitem = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				time.cancel();
				double input = Double.parseDouble(JOptionPane.showInputDialog("请设置您的倍速"));
				frame.dispose();
				framestart.start(new snake(input).frame, 1280, 720, "snake");
			}
		};
		item1.addActionListener(listenitem);
		item1.setFont(new Font(null, 0, 25));
		menu.setFont(new Font(null, 0, 25));
		menu.add(item1);
		menubar.add(menu);
//菜单设置完毕
		for (int n = 0; n < 60; n++) {
			for (int m = 0; m < 90; m++) {
				panels[n][m] = new panel();
				panel1.add(panels[n][m]);
			}
		}
		position[0][0] = 4;
		position[0][1] = 20;
		position[1][0] = 3;
		position[1][1] = 20;
		position[2][0] = 2;
		position[2][1] = 20;
		position[3][0] = 1;
		position[3][1] = 20;
		position[4][0] = 0;
		position[4][1] = 20;
		fruit = randomfruit();
		panels[position[0][1]][position[0][0]].setBackground(Color.red);
		panels[position[1][1]][position[1][0]].setBackground(Color.black);
		panels[position[2][1]][position[2][0]].setBackground(Color.black);
		panels[position[3][1]][position[3][0]].setBackground(Color.black);
		panels[position[4][1]][position[4][0]].setBackground(Color.black);
		panels[fruit[1]][fruit[0]].setBackground(Color.green.darker());
//初始化完成
		KeyAdapter keyboard = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if (code == KeyEvent.VK_UP) {
					if (direction != 2 && direction != 4) {
						direction1 = 4;
					}
				}
				if (code == KeyEvent.VK_DOWN) {
					if (direction != 2 && direction != 4) {
						direction1 = 2;
					}
				}
				if (code == KeyEvent.VK_RIGHT) {
					if (direction != 1 && direction != 3) {
						direction1 = 1;
					}
				}
				if (code == KeyEvent.VK_LEFT) {
					if (direction != 1 && direction != 3) {
						direction1 = 3;
					}
				}
			}
		};
//键盘监听完成(注意新的direction1变量，因为在一个时刻只监听两边方向)
		task = new TimerTask() {
			public void run() {
				int x = position[body - 1][0];
				int y = position[body - 1][1];
				boolean eat = false;
				int direction0 = direction1;
				direction = direction0;
				if (direction0 == 1) {
					for (int n = body - 1; n > 0; n--) {
						position[n][0] = position[n - 1][0];
						position[n][1] = position[n - 1][1];
					}
					position[0][0]++;
					if (position[0][0] > 89) {
						fail(snake.speed);
					}
				}
				if (direction0 == 2) {
					for (int n = body - 1; n > 0; n--) {
						position[n][0] = position[n - 1][0];
						position[n][1] = position[n - 1][1];
					}
					position[0][1]++;
					if (position[0][1] > 59) {
						fail(snake.speed);
					}
				}
				if (direction0 == 3) {
					for (int n = body - 1; n > 0; n--) {
						position[n][0] = position[n - 1][0];
						position[n][1] = position[n - 1][1];
					}
					position[0][0]--;
					if (position[0][0] < 0) {
						fail(snake.speed);
					}
				}
				if (direction0 == 4) {
					for (int n = body - 1; n > 0; n--) {
						position[n][0] = position[n - 1][0];
						position[n][1] = position[n - 1][1];
					}
					position[0][1]--;
					if (position[0][1] < 0) {
						fail(snake.speed);
					}
				}
//蛇的四向移动和碰壁判定，注意direction0的设置，防止BUG
				for (int n = 3; n < body; n++) {
					if (position[0][1] == position[n][1] && position[0][0] == position[n][0]) {
						fail(snake.speed);
						break;
					}
				}
//蛇判定是否撞到自己的身体
				if (position[0][0] == fruit[0] && position[0][1] == fruit[1]) {
					eat = true;
					position[body][0] = x;
					position[body][1] = y;
					body++;
					text.setText("" + (int) ((body - 5) * snake.speed));
					fruit = randomfruit();
					panels[fruit[1]][fruit[0]].setBackground(Color.green.darker());
				}
//蛇吃到果实并刷新果实
				panels[position[0][1]][position[0][0]].setBackground(Color.red);
				panels[position[1][1]][position[1][0]].setBackground(Color.black);
				if (!eat)
					panels[y][x].setBackground(Color.white);
			}
		};
		panel2.setPreferredSize(new Dimension(300, 600));
		panel2.setBackground(Color.LIGHT_GRAY);
		panel2.setBorder(matte);
		panel2.add(text);
		panel1.setBorder(matte);
		panel1.setBackground(Color.LIGHT_GRAY);
		frame.setJMenuBar(menubar);
		frame.add(BorderLayout.EAST, panel2);
		frame.add(panel1);
		frame.addKeyListener(keyboard);
		framestart.start(frame, 1280, 720, "snake");
		Object[] options = { "开始", "退出" };
		int val = JOptionPane.showOptionDialog(null, "准备好了吗？", "提醒", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		if (val != JOptionPane.OK_OPTION) {
			frame.dispose();
		} else {
			time.schedule(task, 0, (long) (100 / speed));
		}
	}

	int[] randomfruit() {
		int x;
		int y;
		label: while (true) {
			x = (int) (Math.random() * 90);
			y = (int) (Math.random() * 60);
			for (int n = 0; n < body; n++) {
				if (position[n][0] == x && position[n][1] == y) {
					continue label;
				}
			}
			break;
		}
		int[] a = { x, y };
		return a;
	}
//随机一个不在蛇身体里面的果实

	void fail(double speed) {
		time.cancel();
		JOptionPane.showMessageDialog(null, "你吃到了" + (body - 5) + "颗果子！乘上速度系数，总共是" + (int) ((body - 5) * speed) + "分！",
				"结束", JOptionPane.PLAIN_MESSAGE);
		try {
			cjdbc jdbc = new cjdbc();
			Connection conn = jdbc.conn;
			Statement stmt = jdbc.stmt;
			stmt.executeUpdate("insert into snake (score) values (" + (int) ((body - 5) * speed) + ");");
			stmt.close();
			conn.close();
		} catch (SQLException e1) {
		}
//连接数据库
		frame.dispose();
		framestart.start(new snake(speed).frame, 1280, 720, "snake");
	}

//撞到之后调用的函数
	static void start() {
		new snake();

	}

	class panel extends JPanel {
		panel() {
			setBackground(Color.white);
		}
	}
}