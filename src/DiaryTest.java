
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
	private final String Day_Of_Week[] = { "日", "月", "火", "水", "木", "金", "土" };

	String year = "" + Calendar.getInstance().get(Calendar.YEAR); // 캘린더 연도 선택
	String month = "" + (Calendar.getInstance().get(Calendar.MONTH) + 1); // 캘린더
																			// 월
																			// 선택
	String day = "" + Calendar.getInstance().get(Calendar.DATE); // 캘린더 일 선택

	/* 달력생성을 위한 컴포넌트 설정 */

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

		/* top, left, right 3개 패널 구성 */

		JPanel JPtop = new JPanel(); // top패널
		JLabel mainTitle = new JLabel("일정관리 Test");
		mainTitle.setFont(new Font("바탕", Font.BOLD + Font.ITALIC, 30));

		JPtop.add(mainTitle);

		JPanel JPleft = new JPanel(); // left패널.
		JPleft.setLayout(new BorderLayout());

		JPanel JPright = new JPanel(); // right패널.
		JPright.setLayout(new BorderLayout());

		c.add(JPtop, BorderLayout.NORTH); // 각 패널들을 각각 화면의 위쪽, 왼쪽, 오른쪽에 위치시킴.
		c.add(JPleft, BorderLayout.WEST);
		c.add(JPright, BorderLayout.CENTER);

		/* 달력 패널 */

		JPanel JPcalendar = new JPanel();

		JPcalendar.setBorder(new TitledBorder("[날짜]"));

		JPanel JPcal_year = new JPanel();
		{ // 달력 패널 안의 연도 패널

			JPcal_year.setBorder(new TitledBorder("연도"));// '연도' 파이틀 지정.
			{

				JTcal_year.setText("" + year); // 현재의 연도를 텍스트필드의 문자열로 등록.
				JTcal_year.setFont(new Font("", Font.BOLD, 13));
				JTcal_year.setHorizontalAlignment(JTextField.CENTER);
				JTcal_year.setColumns(3);
				JTcal_year.setEditable(false);
			}
			{
				// 버튼 핸들을 등록.
				JBcal_up.setFont(new Font("", Font.PLAIN, 9));
				JBcal_down.setFont(new Font("", Font.PLAIN, 9));

			}
			JPcal_year.add(JTcal_year);
			JPcal_year.add(JBcal_up);
			JPcal_year.add(JBcal_down);
		}
		JPcalendar.add(JPcal_year);

		JPanel JPcal_month = new JPanel();
		{ // 달력 패널 안의 월 패널

			JPcal_month.setBorder(new TitledBorder("월"));// '월' 타이틀 지정
			{

				JBox_cal_month.setSelectedItem("" + month); // 현재의 달을 콤보박스에서
															// 선택하게 함.

			}
			JPcal_month.add(JBox_cal_month);
			JPcal_month.add(new JLabel("월"));
		}
		JPcalendar.add(JPcal_month);

		JPanel JPcal_day = new JPanel();
		{ // 달력 패널 안의 요일 패널

			JPcal_day.setLayout(new GridLayout(7, 7)); // 요일과 날짜를 나타내기 위한
														// 7x7레이아웃 설정.
			JPcal_day.setBorder(new TitledBorder("일"));

			for (int i = 0; i < JBcal_day.length; i++) { // 날짜들을 버튼으로 만들어 선택이
															// 가능하도록 함.
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
		setBounds(scSize.width / 2 - 295, scSize.height / 2 - 220, 590, 440); // 스크린의
																				// 중앙에
																				// 프로그램을
																				// 위치
																				// 시킴.
		setResizable(false); // 크기 조절 불가능하게 함.
		setVisible(true);
	}

	void updateDiary() {

		updateCalendar();
	}

	void updateCalendar() // 선택된 날짜로 달력의 내용을 갱신함.
	{
		Calendar firstDay = Calendar.getInstance();
		Calendar lastDay = Calendar.getInstance();

		firstDay.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);
		lastDay.set(Integer.parseInt(year), Integer.parseInt(month), 1);
		lastDay.add(Calendar.DATE, -1); // 하루전 날짜

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
