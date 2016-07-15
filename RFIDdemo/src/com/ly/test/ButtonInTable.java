package com.ly.test;

import java.awt.BorderLayout;

import java.awt.Component;

import java.awt.Dimension;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JOptionPane;

import javax.swing.JScrollPane;

import javax.swing.JTable;

import javax.swing.UIManager;

import javax.swing.table.AbstractTableModel;

import javax.swing.table.TableCellRenderer;

import org.junit.Test;

import com.ly.service.SignService;
import com.ly.service.impl.SignServiceImpl;
import com.ly.utils.TimeUtil2;
import com.ly.utils.TimeUtils;

public class ButtonInTable {
	private static Properties timeconfig = new Properties();
	@Test
	   public void aaa(){
		try {
			timeconfig.load(SignServiceImpl.class.getClassLoader().getResourceAsStream("time.properties"));
		} catch (IOException e) {
			throw new RuntimeException();
		}
//		System.out.println(timeconfig.getProperty("signinMin"));
//		System.out.println(timeconfig.getProperty("signinMax"));
//		System.out.println( timeconfig.getProperty("signoutMin"));
//		System.out.println(timeconfig.getProperty("signoutMax"));
		String signinMax = timeconfig.getProperty("signinMax");
		String inMax[] = signinMax.split(":");
		String delay =  timeconfig.getProperty("lateDelay");
		int i;
		switch (delay) {
		case "0":
			i = (Integer.parseInt(inMax[0]))+100;
			inMax[0] = i +"";
			break;
		case "1":
			i = (Integer.parseInt(inMax[0]))+1;
			inMax[0] = i +"";
			break;
		case "2":
			i = (Integer.parseInt(inMax[0]))+2;
			inMax[0] = i +"";
			break;
		case "3":
			i = (Integer.parseInt(inMax[0]))+3;
			inMax[0] = i +"";
			break;
		default:
			break;
		}
		
		String latetime = inMax[0]+":"+inMax[1]+":"+inMax[2];
		System.out.println(latetime);
	   }
	@Test
	public void sfd(){
		URL url = this.getClass().getClassLoader().getResource("rfid.db");
		System.out.println(url);
	}
	@Test
	public void dfs(){
		Date date = new Date();
		if(TimeUtil2.isInDate(date, "16:55", "17:01")){
			System.out.println("wocao ");
		}
	}
	@Test
	public void dsfs(){
		SignService ss = new SignServiceImpl();
		ss.sign("56456");
	}
	public static void main(String[] args) {

		final ButtonInTable example = new ButtonInTable();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				example.createAndShowGUI();

			}

		});

	}

	private void createAndShowGUI() {

		JFrame frame = new JFrame("Button Example");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTable table = new JTable(new JTableModel());

		JScrollPane scrollPane = new JScrollPane(table);

		table.setFillsViewportHeight(true);

		TableCellRenderer buttonRenderer = new JTableButtonRenderer();

		table.getColumn("Button1").setCellRenderer(buttonRenderer);

		table.getColumn("Button2").setCellRenderer(buttonRenderer);

		table.addMouseListener(new JTableButtonMouseListener(table));

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		frame.getContentPane().setPreferredSize(new Dimension(500, 200));

		frame.pack();

		frame.setVisible(true);

	}

	public static class JTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		private static final String[] COLUMN_NAMES = new String[] { "Id", "Stuff", "Button1", "Button2" };

		private static final Class<?>[] COLUMN_TYPES = new Class<?>[] { Integer.class, String.class, JButton.class,
				JButton.class };

		@Override
		public int getColumnCount() {

			return COLUMN_NAMES.length;

		}

		@Override
		public int getRowCount() {

			return 4;

		}

		@Override
		public String getColumnName(int columnIndex) {

			return COLUMN_NAMES[columnIndex];

		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {

			return COLUMN_TYPES[columnIndex];

		}

		@Override
		public Object getValueAt(final int rowIndex, final int columnIndex) {

			switch (columnIndex) {

			case 0:
				return rowIndex;

			case 1:
				return "Text for " + rowIndex;

			case 2: // fall through

			case 3:
				final JButton button = new JButton(COLUMN_NAMES[columnIndex]);

				button.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {

						JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(button),

								"Button clicked for row " + rowIndex);

					}

				});

				return button;

			default:
				return "Error";

			}

		}

	}

	private static class JTableButtonRenderer implements TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			JButton button = (JButton) value;

			if (isSelected) {

				button.setForeground(table.getSelectionForeground());

				button.setBackground(table.getSelectionBackground());

			} else {

				button.setForeground(table.getForeground());

				button.setBackground(UIManager.getColor("Button.background"));

			}

			return button;

		}

	}

	private static class JTableButtonMouseListener extends MouseAdapter {

		private final JTable table;

		public JTableButtonMouseListener(JTable table) {

			this.table = table;

		}

		public void mouseClicked(MouseEvent e) {

			int column = table.getColumnModel().getColumnIndexAtX(e.getX());

			int row = e.getY() / table.getRowHeight();

			if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {

				Object value = table.getValueAt(row, column);

				if (value instanceof JButton) {

					((JButton) value).doClick();

				}

			}

		}

	}

}
