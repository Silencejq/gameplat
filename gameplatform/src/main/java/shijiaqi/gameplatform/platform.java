package shijiaqi.gameplatform;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.*;

public class platform {
	JFrame home = new JFrame();
	JLabel logo = new JLabel("适度游戏益脑 沉迷游戏伤身 合理安排时间 享受健康生活");
	JMenuBar menubar = new JMenuBar();
	JMenu menu = new JMenu("选项");
	JMenuItem item = new JMenuItem("作者信息");
	JButton game1 = new JButton("");
	JButton game2 = new JButton("");
	JButton game3 = new JButton("");
	JButton game4 = new JButton("");
	JTextArea score1 = new JTextArea("minesweeper\r\n");
	JTextArea score2 = new JTextArea("gomoku\r\n");
	JTextArea score3 = new JTextArea("snake\r\n");
	JTextArea score4 = new JTextArea("tetris\r\n");
	JPanel panel = new JPanel(new GridLayout(2, 2));
//内嵌grid布局
	JPanel panel1 = new JPanel(new BorderLayout());
	JPanel panel2 = new JPanel(new BorderLayout());
	JPanel panel3 = new JPanel(new BorderLayout());
	JPanel panel4 = new JPanel(new BorderLayout());
	Border matte = new MatteBorder(10, 10, 10, 10, Color.lightGray);
	Font font1 = new Font(null, 0, 25);
	ActionListener listen1 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			minesweeper.start();
		}
	};
	ActionListener listen2 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			gomoku.start();
		}
	};
	ActionListener listen3 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			snake.start();
		}
	};
	ActionListener listen4 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			tetris.start();
		}
	};

	platform() {
		UIManager.put("Button.font", font1);
		UIManager.put("Label.font", font1);
		item.setFont(font1);
		menu.setFont(font1);
		game1.addActionListener(listen1);
		game2.addActionListener(listen2);
		game3.addActionListener(listen3);
		game4.addActionListener(listen4);
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//		}
		logo.setFont(new Font(null, 0, 50));
		logo.setForeground(Color.red);
		score1.setFont(new Font(null, 0, 30));
		score2.setFont(new Font(null, 0, 30));
		score3.setFont(new Font(null, 0, 30));
		score4.setFont(new Font(null, 0, 30));
		home.add(BorderLayout.NORTH, logo);
		panel1.add(BorderLayout.WEST, game1);
		panel2.add(BorderLayout.WEST, game2);
		panel3.add(BorderLayout.WEST, game3);
		panel4.add(BorderLayout.WEST, game4);
		panel1.add(score1);
		panel2.add(score2);
		panel3.add(score3);
		panel4.add(score4);
		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		panel.add(panel4);
		ActionListener listen5 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "姓名：阿史，联系方式：silencegt@foxmail.com", "您好",
						JOptionPane.PLAIN_MESSAGE);
			}
		};
		item.addActionListener(listen5);
		menubar.add(menu);
		menu.add(item);
		home.setJMenuBar(menubar);
		home.add(panel);
		panel.setBorder(matte);
		panel1.setBorder(matte);
		panel2.setBorder(matte);
		panel3.setBorder(matte);
		panel4.setBorder(matte);
		score1.setEnabled(false);
		score2.setEnabled(false);
		score3.setEnabled(false);
		score4.setEnabled(false);
		score1.setDisabledTextColor(Color.black);
		score2.setDisabledTextColor(Color.black);
		score3.setDisabledTextColor(Color.black);
		score4.setDisabledTextColor(Color.black);

		try {
			cjdbc jdbc = new cjdbc();
			Connection conn = jdbc.conn;
			Statement stmt = jdbc.stmt;
			ResultSet rs1 = stmt.executeQuery("select score from minesweeper order by score asc limit 10;");
			while (rs1.next()) {
				score1.append("" + rs1.getInt(1) + "秒\r\n");
			}
			rs1.close();
			ResultSet rs2 = stmt.executeQuery("select score from snake order by score desc limit 10;");
			while (rs2.next()) {
				score3.append("" + rs2.getInt(1) + "分\r\n");
			}
			rs2.close();
			ResultSet rs3 = stmt.executeQuery("select score from tetris order by score desc limit 10;");
			while (rs3.next()) {
				score4.append("" + rs3.getInt(1) + "分\r\n");
			}
			rs3.close();
			stmt.close();
			conn.close();
		} catch (SQLException e1) {
			score1.append("没有连接上网络");
			score3.append("没有连接上网络");
			score4.append("没有连接上网络");
		}
//连接数据库,注意一个statement同时只能有一个resultset
	}

	public static void main(String[] args) {
		platform window = new platform();
		window.home.setLocation(320, 180);
		window.home.setTitle("主页");
		window.home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.home.setSize(1280, 720);
		window.home.setVisible(true);
		window.game1.setIcon(new inserticon().insert("minesweeper.gif", window.panel1, 0.5, 1));
		window.game2.setIcon(new inserticon().insert("gomoku.gif", window.panel2, 0.5, 1));
		window.game3.setIcon(new inserticon().insert("snake.gif", window.panel3, 0.5, 1));
		window.game4.setIcon(new inserticon().insert("tetris.gif", window.panel4, 0.5, 1));
	}
}
