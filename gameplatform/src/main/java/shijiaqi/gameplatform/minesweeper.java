package shijiaqi.gameplatform;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

class minesweeper {
	private JFrame frame = new JFrame();
	private JTextField text = new JTextField("0");
	private JMenuBar menubar = new JMenuBar();
	private JMenu menu = new JMenu("菜单");
	private JMenuItem menuitem1 = new JMenuItem("重新开始");
	private JMenuItem menuitem2 = new JMenuItem("自定义难度");
	private Timer time = new Timer();
	private int a;
	private int b;
	private int c;
	private boolean timeset = false;

	private minesweeper() {
		this(9, 9, 10);
	}

	private minesweeper(int a, int b, int c) {
		this.a = a;
		this.b = b;
		this.c = c;
		piece.remainpiece = a * b - c;
		piece.a = a;
		piece.b = b;
		piece.c = c;
		UIManager.put("Button.font", new Font(null, 0, 20));
		UIManager.put("Label.font", new Font(null, 0, 25));
		piece[][] pieces0 = new piece[b + 2][a + 2];
// 在边界这样更加方便实际区域只有a*b
		for (int q = 0; q < b + 2; q++) {
			for (int w = 0; w < a + 2; w++) {
				pieces0[q][w] = new piece();
			}
		}
		piece[][] pieces = new piece[b][a];
		for (int q = 0; q < b; q++) {
			for (int w = 0; w < a; w++) {
				pieces[q][w] = new piece();
			}
		}
		int[] minesint = new int[c];
		minesint[c - 1] = -1;
		int n = 0;
		label: while (minesint[c - 1] == -1) {
			int result = (int) (Math.random() * a * b);
			for (int result2 : minesint) {
				if (result2 == result) {
					continue label;
				}
			}
			minesint[n++] = result;
		}
// 随机选出c个雷
		for (int n1 : minesint) {
			int z = (int) (n1 / a) + 1;
			int x = n1 % a + 1;
			pieces0[z][x].Mnumber = -1;
			pieces[z - 1][x - 1].Mnumber = -1;
		}
		for (int b1 = 1; b1 < b + 1; b1++) {
			for (int a1 = 1; a1 < a + 1; a1++) {
				if (pieces0[b1][a1].Mnumber == -1) {
					continue;
				}
				int m = 0;
				if (pieces0[b1 - 1][a1 - 1].Mnumber == -1) {
					m++;
				}
				if (pieces0[b1 - 1][a1].Mnumber == -1) {
					m++;
				}
				if (pieces0[b1 - 1][a1 + 1].Mnumber == -1) {
					m++;
				}
				if (pieces0[b1][a1 - 1].Mnumber == -1) {
					m++;
				}
				if (pieces0[b1][a1 + 1].Mnumber == -1) {
					m++;
				}
				if (pieces0[b1 + 1][a1 - 1].Mnumber == -1) {
					m++;
				}
				if (pieces0[b1 + 1][a1].Mnumber == -1) {
					m++;
				}
				if (pieces0[b1 + 1][a1 + 1].Mnumber == -1) {
					m++;
				}
				pieces[b1 - 1][a1 - 1].Mnumber = m;
			}
		}
// 设置每个格子周围的雷数
		text.setFont(new Font(null, 0, 40));
		text.setEnabled(false);
		text.setDisabledTextColor(Color.black);
		menu.setFont(new Font(null, 0, 20));
		menuitem1.setFont(new Font(null, 0, 20));
		menuitem2.setFont(new Font(null, 0, 20));
		ActionListener itemlisten1 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				time.cancel();
				framestart.start(new minesweeper(piece.a, piece.b, piece.c).frame, 50 * piece.a, 50 * piece.b + 150,
						"minesweeper");
				frame.dispose();
			}
		};
		ActionListener itemlisten2 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog("长宽雷的数量用/隔开，如9/9/10");
				String[] inputs = input.split("/");
				int a = Integer.parseInt(inputs[0]);
				int b = Integer.parseInt(inputs[1]);
				int c = Integer.parseInt(inputs[2]);
				time.cancel();
				framestart.start(new minesweeper(a, b, c).frame, 50 * piece.a, 50 * piece.b + 150, "minesweeper");
				frame.dispose();
			}
		};
//菜单监视完成,这边发现：参数传入是按顺序来的，前一个参数实例化后再传入下一个参数
		menuitem1.addActionListener(itemlisten1);
		menuitem2.addActionListener(itemlisten2);
		menu.add(menuitem1);
		menu.add(menuitem2);
		menubar.add(menu);
		JPanel panel = new JPanel(new GridLayout(b, a));
		class listen2 extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				piece piece = (piece) e.getSource();
				Icon icon = new inserticon().insert("mine.gif", piece, 0.8, 0.8);
				if (e.getButton() == MouseEvent.BUTTON3) {
					if (piece.getIcon() == null) {
						piece.setIcon(icon);
					} else {
						piece.setIcon(null);
					}
				}
				;
			}
		}
//右键标记监听完毕
		for (int q = 0; q < b; q++) {
			for (int w = 0; w < a; w++) {
				ActionListener listen1 = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						piece piece1 = (piece) e.getSource();
						if (piece1.Mnumber != -1) {
							timeset = true;
							piece.remainpiece--;
							if (piece.remainpiece == 0) {
								time.cancel();
								JOptionPane.showMessageDialog(null, "哇！恭喜您！您的分数是：" + text.getText() + 
										"秒!", "成功",JOptionPane.PLAIN_MESSAGE);
								try {
									cjdbc jdbc = new cjdbc();
									Connection conn = jdbc.conn;
									Statement stmt = jdbc.stmt;
									System.out.print("zheli");
									stmt.executeUpdate("insert into minesweeper(score) values ("+ text.getText() + ");");
									stmt.close();
									conn.close();
								} catch (SQLException e1) {
									
								}
//连接数据库
								framestart.start(new minesweeper(piece.a, piece.b, piece.c).frame, 50 * piece.a,
										50 * piece.b + 150, "minesweeper");
								frame.dispose();
							}
							if(piece1.Mnumber!=0){piece1.setText("" + piece1.Mnumber);}
							piece1.setBackground(Color.LIGHT_GRAY);
							piece1.setForeground(Color.red);
							piece1.removeActionListener(piece1.getActionListeners()[0]);
							piece1.removeMouseListener(piece1.getMouseListeners()[1]);
//注意左键点开之后把两个监听都给取消掉
						} else {
							time.cancel();
							JOptionPane.showMessageDialog(null, "完了，您炸了", "失败", JOptionPane.PLAIN_MESSAGE);
							framestart.start(new minesweeper(piece.a, piece.b, piece.c).frame, 50 * piece.a,
									50 * piece.b + 150, "minesweeper");
							frame.dispose();
						}
					}
				};
// 左键设置完毕游戏输赢设置完毕
				pieces[q][w].addActionListener(listen1);
				pieces[q][w].addMouseListener(new listen2());
				panel.add(pieces[q][w]);
			}
		}
		frame.add(BorderLayout.NORTH, text);
		frame.add(panel);
		frame.setJMenuBar(menubar);
		time.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if (timeset) {
					text.setText("" + (Integer.parseInt(text.getText()) + 1));
				}
			}
		}, 0, 1000);
//设置计时器，等第一个按钮按下后开始计时
	}

	static void start() {
		minesweeper game = new minesweeper();
		framestart.start(game.frame, 50 * game.a, 50 * game.b + 150, "minesweeper");
	}
}

class piece extends JButton {
	int Mnumber = 0;
	static int remainpiece = 0;
	static int a;
	static int b;
	static int c;
}
