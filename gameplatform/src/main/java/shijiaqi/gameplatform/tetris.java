package shijiaqi.gameplatform;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.util.Timer;
import java.util.TimerTask;

class tetris {
	private JFrame frame = new JFrame();
	private JPanel panel2 = new JPanel(new BorderLayout());
	private JLabel label1 = new JLabel("下一个方块是：");
	private JPanel panel4 = new JPanel(new BorderLayout());
	private JLabel label2 = new JLabel("0");
	private JTextArea text = new JTextArea("操作指南：\r\n上键旋转方块\r\n下键加速坠落方块\r\n左右键平移方块\r\n填满一层则消除一层并加一分");
	private Border matte = new MatteBorder(10, 10, 10, 10, Color.lightGray);
	private Font font = new Font(null, 0, 35);
	private JMenuBar menubar = new JMenuBar();
	private JMenu menu = new JMenu("选项");
	private JMenuItem item1 = new JMenuItem("结束游戏");
	private Timer time = new Timer();
	private Timer timeset = new Timer();
	private int score = 0;
	private int key = 0;
	private TimerTask task;
	private TimerTask taskset;
	private int speed = 30;
	private int speed0 = 30;
	private JPanel panel1 = new JPanel(new GridLayout(20, 10));
	private grid[][] grid = new grid[20][10];
	private JPanel panel3 = new JPanel(new GridLayout(4, 4));
	private grid[][] grid2 = new grid[4][4];
	private int[][] position = new int[4][2];
	private int[][] position0 = new int[4][2];
//画面上能动的只有一组方块，所以只要处理他们的坐标列就行
	private Color color;
	private Color color0;
	private int[][] heap = new int[20][10];

	private tetris() {
		UIManager.put("Label.font", font);
		UIManager.put("Button.font", font);
		for (int n = 0; n < 20; n++) {
			for (int m = 0; m < 10; m++) {
				grid[n][m] = new grid();
				panel1.add(grid[n][m]);
			}
		}
		for (int n = 0; n < 4; n++) {
			for (int m = 0; m < 4; m++) {
				grid2[n][m] = new grid();
				panel3.add(grid2[n][m]);
			}
		}
		label1.setFont(font);
		label2.setFont(new Font(null, 0, 100));
		text.setFont(font);
		panel1.setBorder(new MatteBorder(5, 5, 5, 5, Color.black));
		panel2.setBorder(matte);
		label1.setBorder(matte);
		panel3.setBorder(matte);
		label2.setBorder(matte);
		text.setBorder(matte);
		text.setEnabled(false);
		text.setDisabledTextColor(Color.black);
		panel4.add(BorderLayout.NORTH, label2);
		panel4.add(BorderLayout.SOUTH, text);
		panel2.add(BorderLayout.NORTH, label1);
		panel2.add(panel3);
		panel2.add(BorderLayout.SOUTH, panel4);
		frame.add(panel1);
		frame.add(BorderLayout.EAST, panel2);
		ActionListener listenitem = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				time.cancel();
				timeset.cancel();
				frame.dispose();
			}
		};
		item1.addActionListener(listenitem);
		item1.setFont(new Font(null, 0, 25));
		menu.setFont(new Font(null, 0, 25));
		menu.add(item1);
		menubar.add(menu);
		frame.setJMenuBar(menubar);
		roll();
		position = position0;
		color = color0;
		draw(position, color);
		roll();
//初始化完成
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int input = e.getKeyCode();
				if (input == KeyEvent.VK_LEFT) {
					key = 3;
				}
				if (input == KeyEvent.VK_RIGHT) {
					key = 1;
				}
				if (input == KeyEvent.VK_DOWN) {
					key = 2;
				}
				if (input == KeyEvent.VK_UP) {
					key = 4;
				}
			}
		});
		taskset = new TimerTask() {
			public void run() {
				if (speed > 8) {
					speed -= 2;
				} else if (speed > 4) {
					speed--;
				}
			}
		};
//变速任务设置完成
		task = new TimerTask() {
			public void run() {
				if (key == 3) {
					boolean go = true;
					int[][] ffposition = new int[4][2];
					for (int n = 0; n < 4; n++) {
						ffposition[n][1] = position[n][1];
						ffposition[n][0] = position[n][0] - 1;
						if (ffposition[n][0] < 0) {
							go = false;
						} else if (heap[ffposition[n][1]][ffposition[n][0]] == 1) {
							go = false;
						}
					}
					if (go) {
						draw(position, Color.black);
						position = ffposition;
						draw(position, color);
					}
					key=0;
				}
				if (key == 1) {
					boolean go = true;
					int[][] ffposition = new int[4][2];
					for (int n = 0; n < 4; n++) {
						ffposition[n][1] = position[n][1];
						ffposition[n][0] = position[n][0] + 1;
						if (ffposition[n][0] > 9) {
							go = false;
						} else if (heap[ffposition[n][1]][ffposition[n][0]] == 1) {
							go = false;
						}
					}
					if (go) {
						draw(position, Color.black);
						position = ffposition;
						draw(position, color);
					}
					key = 0;
				}
//监听左右移动完成
				if (key == 4) {
					boolean go = true;
					int[][] ffposition = new int[4][2];
					for (int n = 0; n < 4; n++) {
						ffposition[n][0]=position[1][0]+(position[n][1]-position[1][1]);
						ffposition[n][1]=position[1][1]+(position[1][0]-position[n][0]);
						if ((ffposition[n][0] > 9)||(ffposition[n][0]<0)||(ffposition[n][1]<0)||(ffposition[n][1]>19)) {
							go = false;
						} else if (heap[ffposition[n][1]][ffposition[n][0]] == 1) {
							go = false;
						}
					}
					if (go) {
						draw(position, Color.black);
						position = ffposition;
						draw(position, color);
					}
					key = 0;
				}
//监听旋转完成：使用一个通用的几何中心
				if (key == 2) {
					speed0 = 1;
				}
				speed0--;
//speed0设置的原因：1.实现下落变速2.使按键响应和下落放在一个线程内，防止图案撕裂。而如果不设置speed0减速，响应速度将和下落一样慢，影响体验
				if (speed0 == 0) {
					speed0 = speed;
					boolean have = false;
					int[][] fposition = new int[4][2];
					for (int n = 0; n < 4; n++) {
						fposition[n][1] = position[n][1] + 1;
						fposition[n][0] = position[n][0];
					}
					for (int[] a : fposition) {
						if ((a[1] == 20) || (heap[a[1]][a[0]] == 1)) {
							if (a[1] == 1) {
								time.cancel();
								timeset.cancel();
								JOptionPane.showMessageDialog(null, "你得到了" + score + "分，恭喜您！", "结束",
										JOptionPane.PLAIN_MESSAGE);
								try {
									cjdbc jdbc = new cjdbc();
									Connection conn = jdbc.conn;
									Statement stmt = jdbc.stmt;
									stmt.executeUpdate("insert into tetris (score) values (" + score + ");");
									stmt.close();
									conn.close();
								} catch (SQLException e1) {
								}
//连接数据库
								frame.dispose();
								new tetris();
							}
							heap[position[0][1]][position[0][0]] = 1;
							heap[position[1][1]][position[1][0]] = 1;
							heap[position[2][1]][position[2][0]] = 1;
							heap[position[3][1]][position[3][0]] = 1;
							s1: for (int n = 0; n < 20; n++) {
								for (int m = 0; m < 10; m++) {
									if (heap[n][m] == 0) {
										continue s1;
									}
								}
								for (int q = n; q > 0; q--) {
									for (int w = 0; w < 10; w++) {
										heap[q][w] = heap[q - 1][w];
										grid[q][w].setBackground(grid[q - 1][w].getBackground());
									}
								}
								n--;
								score++;
								label2.setText("" + score);
							}
//若满一行则在待消除组中消除该行并更改图形显示并加一分
							position = position0;
							color = color0;
							key = 0;
							draw(position, color);
							have = true;
							roll();
							break;
						}
					}
					if (!have) {
						draw(position, Color.black);
						position = fposition;
						draw(position, color);
					}
				}
//若有一个方块碰到（所有方块都要循环），先判断是否已经输了，再则整个图形加入待消除组，然后带消除组判断是否能消层，且生成新的方块进来，再roll一个在预入区
//这里有很多循环和判断，position,position0,fposition,heap四个变量的位置要小心处理
			}
		};
		framestart.start(frame, 1000, 1025, "tetris", 400, 10);
		Object[] options = { "开始", "退出" };
		int val = JOptionPane.showOptionDialog(null, "准备好了吗？", "提醒", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		if (val != JOptionPane.OK_OPTION) {
			frame.dispose();
		} else {
			time.schedule(task, 0, 25);
			timeset.scheduleAtFixedRate(taskset, 0, 10000);
		}
	}

	private void draw(int[][] a, Color b) {
		for (int[] n : a) {
			grid[n[1]][n[0]].setBackground(b);
		}
	}

	private void J() {
		grid2[0][1].setBackground(Color.blue);
		grid2[1][1].setBackground(Color.blue);
		grid2[2][0].setBackground(Color.blue);
		grid2[2][1].setBackground(Color.blue);
		int[][] a = { { 5, 0 }, { 5, 1 }, { 4, 2 }, { 5, 2 } };
		position0 = a;
		color0 = Color.blue;
	}

	private void L() {
		grid2[0][0].setBackground(Color.cyan);
		grid2[1][0].setBackground(Color.cyan);
		grid2[2][0].setBackground(Color.cyan);
		grid2[2][1].setBackground(Color.cyan);
		int[][] a = { { 4, 0 }, { 4, 1 }, { 4, 2 }, { 5, 2 } };
		position0 = a;
		color0 = Color.cyan;
	}

	private void I() {
		grid2[0][0].setBackground(Color.gray);
		grid2[0][1].setBackground(Color.gray);
		grid2[0][2].setBackground(Color.gray);
		grid2[0][3].setBackground(Color.gray);
		int[][] a = { { 4, 0 }, { 6, 0 }, { 5, 0 }, { 7, 0 } };
		position0 = a;
		color0 = Color.gray;
	}

	private void O() {
		grid2[0][0].setBackground(Color.green);
		grid2[0][1].setBackground(Color.green);
		grid2[1][0].setBackground(Color.green);
		grid2[1][1].setBackground(Color.green);
		int[][] a = { { 4, 0 }, { 5, 0 }, { 4, 1 }, { 5, 1 } };
		position0 = a;
		color0 = Color.green;
	}

	private void T() {
		grid2[0][1].setBackground(Color.magenta);
		grid2[1][0].setBackground(Color.magenta);
		grid2[1][1].setBackground(Color.magenta);
		grid2[1][2].setBackground(Color.magenta);
		int[][] a = { { 5, 0 }, { 5, 1 }, { 4, 1 }, { 6, 1 } };
		position0 = a;
		color0 = Color.magenta;
	}

	private void S() {
		grid2[0][1].setBackground(Color.orange);
		grid2[0][2].setBackground(Color.orange);
		grid2[1][0].setBackground(Color.orange);
		grid2[1][1].setBackground(Color.orange);
		int[][] a = { { 5, 0 }, { 5, 1 }, { 4, 1 }, { 6, 0 } };
		position0 = a;
		color0 = Color.orange;
	}

	private void Z() {
		grid2[0][0].setBackground(Color.pink);
		grid2[0][1].setBackground(Color.pink);
		grid2[1][1].setBackground(Color.pink);
		grid2[1][2].setBackground(Color.pink);
		int[][] a = { { 4, 0 }, { 5, 1 }, { 5, 0 }, { 6, 1 } };
		position0 = a;
		color0 = Color.pink;
	}

//设置各个图形以及保存各图形坐标和颜色到缓冲区
	private void roll() {
		int a = (int) (Math.random() * 7);
		for (int n = 0; n < 4; n++) {
			for (int m = 0; m < 4; m++) {
				grid2[n][m].setBackground(Color.black);
			}
		}
		if (a == 0)
			J();
		if (a == 1)
			L();
		if (a == 2)
			I();
		if (a == 3)
			O();
		if (a == 4)
			T();
		if (a == 5)
			S();
		if (a == 6)
			Z();
	}

//随机一个形状
	static void start() {
		new tetris();
	}
}

class grid extends JPanel {
	grid() {
		setBackground(Color.black);
		setBorder(new LineBorder(Color.DARK_GRAY, 1));
	}
}
