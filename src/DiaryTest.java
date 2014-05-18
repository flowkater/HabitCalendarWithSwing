
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class DiaryTest extends JFrame {

	private final String MONTH[] = { "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "10", "11", "12" };
	private final String Day_Of_Week[] = { "��", "��", "��", "�", "��", "��", "��" };

	String year = "" + Calendar.getInstance().get(Calendar.YEAR); // Ķ���� ���� ����
	String month = "" + (Calendar.getInstance().get(Calendar.MONTH) + 1); // Ķ����
																			// ��
																			// ����
	String day = "" + Calendar.getInstance().get(Calendar.DATE); // Ķ���� �� ����

	/* �޷»����� ���� ������Ʈ ���� */

	JTextField JTcal_year = new JTextField("");
	JButton JBcal_up = new JButton("Up");
	JButton JBcal_down = new JButton("Down");
	JComboBox JBox_cal_month = new JComboBox(MONTH);
	JButton JBcal_day[] = new JButton[42];

	public static void main(String[] args) {

		new DiaryTest();

	}

	DiaryTest() {
		super("Diary");

		Container c = getContentPane();

		/* top, left, right 3�� �г� ���� */

		JPanel JPtop = new JPanel(); // top�г�
		JLabel mainTitle = new JLabel("�������� Test");
		mainTitle.setFont(new Font("����", Font.BOLD + Font.ITALIC, 30));

		JPtop.add(mainTitle);

		JPanel JPleft = new JPanel(); // left�г�.
		JPleft.setLayout(new BorderLayout());

		JPanel JPright = new JPanel(); // right�г�.
		JPright.setLayout(new BorderLayout());

		c.add(JPtop, BorderLayout.NORTH); // �� �гε��� ���� ȭ���� ����, ����, �����ʿ� ��ġ��Ŵ.
		c.add(JPleft, BorderLayout.WEST);
		c.add(JPright, BorderLayout.CENTER);

		/* �޷� �г� */

		JPanel JPcalendar = new JPanel();

		JPcalendar.setBorder(new TitledBorder("[��¥]"));

		JPanel JPcal_year = new JPanel();
		{ // �޷� �г� ���� ���� �г�

			JPcal_year.setBorder(new TitledBorder("����"));// '����' ����Ʋ ����.
			{

				JTcal_year.setText("" + year); // ������ ������ �ؽ�Ʈ�ʵ��� ���ڿ��� ���.
				JTcal_year.setFont(new Font("", Font.BOLD, 13));
				JTcal_year.setHorizontalAlignment(JTextField.CENTER);
				JTcal_year.setColumns(3);
				JTcal_year.setEditable(false);
			}
			{
				// ��ư �ڵ��� ���.
				JBcal_up.setFont(new Font("", Font.PLAIN, 9));
				JBcal_down.setFont(new Font("", Font.PLAIN, 9));

			}
			JPcal_year.add(JTcal_year);
			JPcal_year.add(JBcal_up);
			JPcal_year.add(JBcal_down);
		}
		JPcalendar.add(JPcal_year);

		JPanel JPcal_month = new JPanel();
		{ // �޷� �г� ���� �� �г�

			JPcal_month.setBorder(new TitledBorder("��"));// '��' Ÿ��Ʋ ����
			{

				JBox_cal_month.setSelectedItem("" + month); // ������ ���� �޺��ڽ�����
															// �����ϰ� ��.

			}
			JPcal_month.add(JBox_cal_month);
			JPcal_month.add(new JLabel("��"));
		}
		JPcalendar.add(JPcal_month);

		JPanel JPcal_day = new JPanel();
		{ // �޷� �г� ���� ���� �г�

			JPcal_day.setLayout(new GridLayout(7, 7)); // ���ϰ� ��¥�� ��Ÿ���� ����
														// 7x7���̾ƿ� ����.
			JPcal_day.setBorder(new TitledBorder("��"));

			for (int i = 0; i < JBcal_day.length; i++) { // ��¥���� ��ư���� ����� ������
															// �����ϵ��� ��.
				JBcal_day[i] = new JButton();
				JBcal_day[i].setFont(new Font("", Font.BOLD, 10));
			}
			{
				for (int i = 0; i < Day_Of_Week.length; i++) {
					JLabel jlabel_dayOfWeek = new JLabel(Day_Of_Week[i]);
					jlabel_dayOfWeek.setHorizontalAlignment(JLabel.CENTER);
					JPcal_day.add(jlabel_dayOfWeek);
				}
				for (int i = 0; i < JBcal_day.length; i++) {

					JPcal_day.add(JBcal_day[i]);
				}
			}
		}
		JPcalendar.add(JPcal_day);
		JPright.add(JPcalendar, BorderLayout.CENTER);

		updateDiary();

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension scSize = tk.getScreenSize();
		setBounds(scSize.width / 2 - 295, scSize.height / 2 - 220, 590, 440); // ��ũ����
																				// �߾ӿ�
																				// ���α׷���
																				// ��ġ
																				// ��Ŵ.
		setResizable(false); // ũ�� ���� �Ұ����ϰ� ��.
		setVisible(true);
	}

	void updateDiary() {

		updateCalendar();
	}

	void updateCalendar() // ���õ� ��¥�� �޷��� ������ ������.
	{
		Calendar firstDay = Calendar.getInstance();
		Calendar lastDay = Calendar.getInstance();

		firstDay.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);
		lastDay.set(Integer.parseInt(year), Integer.parseInt(month), 1);
		lastDay.add(Calendar.DATE, -1); // �Ϸ��� ��¥

		for (int i = 0; i < JBcal_day.length; i++) {
			int gap = firstDay.get(Calendar.DAY_OF_WEEK) - 1;

			if (i < gap || i > lastDay.get(Calendar.DATE) + gap - 1)
				JBcal_day[i].setVisible(false);
			else {
				JBcal_day[i].setText((i - gap + 1) + "");
				JBcal_day[i].setVisible(true);
			}
			JBcal_day[i].setEnabled(true);
			if (JBcal_day[i].getText().equals(day))
				JBcal_day[i].setEnabled(false);
		}
	}

}
