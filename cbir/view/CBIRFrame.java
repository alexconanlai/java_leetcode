package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Point;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.sql.*;
/*import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.UIManager;import javax.swing.ButtonGroup;
*/
import javax.swing.*;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;
import com.swtdesigner.FocusTraversalOnArray;
import control.*;
import model.*;

public class CBIRFrame extends JFrame{

	private JMenu menu_1;
	private JPanel panel1;
	private JMenu menu;
	private JMenuBar menuBar;
	private ButtonGroup buttonGroup_20 = new ButtonGroup();
	private ButtonGroup buttonGroup_19 = new ButtonGroup();
	private ButtonGroup buttonGroup_18 = new ButtonGroup();
	private ButtonGroup buttonGroup_17 = new ButtonGroup();
	private ButtonGroup buttonGroup_16 = new ButtonGroup();
	private ButtonGroup buttonGroup_15 = new ButtonGroup();
	private ButtonGroup buttonGroup_14 = new ButtonGroup();
	private ButtonGroup buttonGroup_13 = new ButtonGroup();
	private ButtonGroup buttonGroup_12 = new ButtonGroup();
	private ButtonGroup buttonGroup_11 = new ButtonGroup();
	private ButtonGroup buttonGroup_10 = new ButtonGroup();
	private ButtonGroup buttonGroup_9 = new ButtonGroup();
	private ButtonGroup buttonGroup_8 = new ButtonGroup();
	private ButtonGroup buttonGroup_7 = new ButtonGroup();
	private ButtonGroup buttonGroup_6 = new ButtonGroup();
	private JLabel lblCount = new JLabel();
	private JRadioButton rdBtnWrong;
	private JRadioButton rdBtnOK;
	private ButtonGroup buttonGroup_5 = new ButtonGroup();
	private ButtonGroup buttonGroup_4 = new ButtonGroup();
	private ButtonGroup buttonGroup_3 = new ButtonGroup();
	private ButtonGroup buttonGroup_2 = new ButtonGroup();
	private ButtonGroup buttonGroup_1 = new ButtonGroup();
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox cbxCheckType;
	private JComboBox cbxRetriveType;
	private JFrame frame;
	private RoomCanvas cav;
	private RoomCanvas acav[]=new RoomCanvas[20];
	private int fbc=0;
	private boolean isclt=false; 
	private List ltcs=null;	//综合查询反馈的特征向量
	private List lttp=null;	//查询
	private List lt=null;	//能够修改的查询向量
	private int kd=0;		//kd=1，为一种查询，在初次查的时候设置
	private double wt[]=new double[3];//颜色形状
	double wv[]=new double[2];
	private PicRetrivement prt=null;
	private CltPicRetriv cprt=null;
	UpdWgtColandSh uwc=new UpdWgtColandSh();
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			CBIRFrame window = new CBIRFrame();window.frame.setBounds(0,0,900,760);
			window.frame.setVisible(true);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application
	 */
	public CBIRFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		try {
		      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    }
		    catch(Exception e) {
		      e.printStackTrace();
		    }
		frame = new JFrame();
		frame.setTitle("基于图像内容查询软件1.0");
		//frame.setLocationByPlatform(true);frame.setBounds(0,0,900,760);
		frame.setName("frame");	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		menu = new JMenu();
		menu.setText("文件");
		menuBar.add(menu);
		wt[0]=.8;wt[1]=.2;
		final JMenuItem menuItem = new JMenuItem();
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc=new JFileChooser();
				int sta=openFile(fc);
				if(sta==JFileChooser.APPROVE_OPTION)
				{
					try
					{
						File f=fc.getSelectedFile();
						if(fc!=null)
						{
							cav.setGraph(f.toString());
							cav.setVisible(false);
							cav.repaint();
							cav.setVisible(true);						
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		menuItem.setText("载入图片");
		menu.add(menuItem);

		final JMenuItem menuItem_1 = new JMenuItem();
		menuItem_1.setText("检索开始");
		menu.add(menuItem_1);

		final JMenuItem menuItem_2 = new JMenuItem();
		menuItem_2.setText("检索完毕");
		menu.add(menuItem_2);

		menu.addSeparator();

		final JMenuItem menuItem_3 = new JMenuItem();
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 System.exit(0);
			}
		});
		menuItem_3.setText("退出");
		menu.add(menuItem_3);


		menu_1 = new JMenu();
		menu_1.setText("设置");
		menuBar.add(menu_1);

		final JMenuItem menuInit = new JMenuItem();
		menuInit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FactordivSetup optfrm=new FactordivSetup();
				Dimension dimf=getSize();
				Dimension dimt=optfrm.getPreferredSize();
				Point lf=getLocation();
				//optfrm.setLocation((dimf.width-dimt.width)/2+lf.x,(dimf.height-dimt.height)/2+lf.y);
				//optfrm.setBounds(100, 100, 282, 197);
				optfrm.setVisible(true);
			}
		});
		menuInit.setText("初始设置");
		menu_1.add(menuInit);

		final JMenuItem menuWeight = new JMenuItem();
		menuWeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WeightSetup wtfrm=new WeightSetup();
				Dimension dimf=getSize();
				Dimension dimt=wtfrm.getPreferredSize();
				Point lf=getLocation();
				wtfrm.setLocation((dimf.width-dimt.width)/2+lf.x,(dimf.height-dimt.height)/2+lf.y);
				//wtfrm.setBounds(100, 100, 282, 197);
				wtfrm.setVisible(true);
			}
		});
		menuWeight.setText("权值");
		menu_1.add(menuWeight);

		final JMenu menu_2 = new JMenu();
		menu_2.setText("数据库操作");
		menuBar.add(menu_2);

		final JMenuItem menuAdd = new JMenuItem();
		menuAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DBAddSetup dbfrm=new DBAddSetup();
				Dimension dimf=getSize();
				Dimension dimt=dbfrm.getPreferredSize();
				Point lf=getLocation();
				dbfrm.setLocation((dimf.width-dimt.width)/2+lf.x,(dimf.height-dimt.height)/2+lf.y);
				//dbfrm.setBounds(100, 100, 318, 170);
				dbfrm.setVisible(true);
			}
		});
		menuAdd.setText("图像增加");
		menu_2.add(menuAdd);

		final JMenuItem menuSct = new JMenuItem();
		menuSct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DBQuerySetup dbfrm=new DBQuerySetup();
				Dimension dimf=getSize();
				Dimension dimt=dbfrm.getPreferredSize();
				Point lf=getLocation();
				dbfrm.setLocation((dimf.width-dimt.width)/2+lf.x,(dimf.height-dimt.height)/2+lf.y);
				dbfrm.setBounds(100, 100, 320, 181);
				dbfrm.setVisible(true);
			}
		});
		menuSct.setText("图像查询");
		menu_2.add(menuSct);

		final JMenuItem menuItem_4 = new JMenuItem();
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClustDB clt=new ClustDB();
				clt.clustData();
			}
		});
		menuItem_4.setText("聚类");
		menu_2.add(menuItem_4);

		final JMenu menu_3 = new JMenu();
		menu_3.setText("修改");
		menu_2.add(menu_3);
		
		final JMenuItem menuItem_5 = new JMenuItem();
		menu_3.add(menuItem_5);
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			upc h=new upc();
			h.updbs();}
		});
		menuItem_5.setText("形状");

		final JMenuItem menuItem_6 = new JMenuItem();
		menuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				upc h=new upc();
				h.updbc();
			}
		});
		menuItem_6.setText("颜色 ");
		menu_3.add(menuItem_6);
		
		final JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		final JPanel panelr = new JPanel();
		panelr.setPreferredSize(new Dimension(600, 400));
		panelr.setLayout(new BorderLayout());
		splitPane.setRightComponent(panelr);

		final JPanel panel3 = new JPanel();
		panel3.setPreferredSize(new Dimension(600, 160));
		panelr.add(panel3, BorderLayout.CENTER);

		final JPanel panel_1;
		panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout());

		final JPanel panelopt_1 = new JPanel();
		panelopt_1.setLayout(new FlowLayout());
		panel_1.add(panelopt_1, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_1 = new JRadioButton();
		rdBtnOK_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(0)).set(3, 1);
				}
			}
		});
		buttonGroup_1.add(rdBtnOK_1);
		panelopt_1.add(rdBtnOK_1);
		rdBtnOK_1.setText("相似");

		final JRadioButton rdBtnWrong_1 = new JRadioButton();
		rdBtnWrong_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(0)).set(3, -1);
				}
			}
		});
		buttonGroup_1.add(rdBtnWrong_1);
		panelopt_1.add(rdBtnWrong_1);
		rdBtnWrong_1.setText("不相似");

		final JPanel panel_2;
		panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout());

		final JPanel panelopt_2 = new JPanel();
		panelopt_2.setLayout(new FlowLayout());
		panel_2.add(panelopt_2, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_2 = new JRadioButton();
		rdBtnOK_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(1)).set(3, 1);
				}
			}
		});
		buttonGroup_2.add(rdBtnOK_2);
		rdBtnOK_2.setText("相似");
		panelopt_2.add(rdBtnOK_2);

		final JRadioButton rdBtnWrong_2 = new JRadioButton();
		rdBtnWrong_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(1)).set(3, -1);
				}
			}
		});
		buttonGroup_2.add(rdBtnWrong_2);
		rdBtnWrong_2.setText("不相似");
		panelopt_2.add(rdBtnWrong_2);

		final JPanel panel_3;
		panel_3 = new JPanel();
		panel_3.setLayout(new BorderLayout());

		final JPanel panelopt_3 = new JPanel();
		panelopt_3.setLayout(new FlowLayout());
		panel_3.add(panelopt_3, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_3 = new JRadioButton();
		rdBtnOK_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(2)).set(3, 1);
				}
			}
		});
		buttonGroup_3.add(rdBtnOK_3);
		rdBtnOK_3.setText("相似");
		panelopt_3.add(rdBtnOK_3);

		final JRadioButton rdBtnWrong_3 = new JRadioButton();
		rdBtnWrong_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(2)).set(3, -1);
				}
			}
		});
		buttonGroup_3.add(rdBtnWrong_3);
		rdBtnWrong_3.setText("不相似");
		panelopt_3.add(rdBtnWrong_3);

		final JPanel panel_4;
		panel_4 = new JPanel();
		panel_4.setLayout(new BorderLayout());

		final JPanel panelopt_4 = new JPanel();
		panelopt_4.setLayout(new FlowLayout());
		panel_4.add(panelopt_4, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_4 = new JRadioButton();
		rdBtnOK_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(3)).set(3, 1);
				}
			}
		});
		buttonGroup_4.add(rdBtnOK_4);
		rdBtnOK_4.setText("相似");
		panelopt_4.add(rdBtnOK_4);

		final JRadioButton rdBtnWrong_4 = new JRadioButton();
		rdBtnWrong_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(3)).set(3, -1); //不相似
				}
			}
		});
		buttonGroup_4.add(rdBtnWrong_4);
		rdBtnWrong_4.setText("不相似");
		panelopt_4.add(rdBtnWrong_4);

		final JPanel panel_5;
		panel_5 = new JPanel();
		panel_5.setLayout(new BorderLayout());

		final JPanel panelopt_5 = new JPanel();
		panelopt_5.setLayout(new FlowLayout());
		panel_5.add(panelopt_5, BorderLayout.SOUTH);

		rdBtnOK = new JRadioButton();
		rdBtnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(4)).set(3, 1); 	//相似
				}
			}
		});
		buttonGroup_5.add(rdBtnOK);
		panelopt_5.add(rdBtnOK);
		rdBtnOK.setText("相似");

		rdBtnWrong = new JRadioButton();
		rdBtnWrong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(4)).set(3, -1); 	//不相似
				}
			}
		});
		buttonGroup_5.add(rdBtnWrong);
		panelopt_5.add(rdBtnWrong);
		rdBtnWrong.setText("不相似");
	
		final JPanel panel_6;
		panel_6 = new JPanel();
		panel_6.setLayout(new BorderLayout());

		final JPanel panelopt_6 = new JPanel();
		panelopt_6.setLayout(new FlowLayout());
		panel_6.add(panelopt_6, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_6 = new JRadioButton();
		rdBtnOK_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(5)).set(3, 1);
				}
			}
		});
		/*rdBtnOK_6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent a ) {
				if(a.getItemSelectable()==rdBtnOK_6&&lt!=null)
				{
					((ArrayList)lt.get(5)).set(3, 1);	//相似
				}
			}
		});*/
		buttonGroup_6.add(rdBtnOK_6);
		rdBtnOK_6.setText("相似");
		panelopt_6.add(rdBtnOK_6);

		final JRadioButton rdBtnWrong_6 = new JRadioButton();
		rdBtnWrong_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lt!=null)
				{
					((ArrayList)lt.get(5)).set(3, -1);
				}
			}
		});
		/*rdBtnWrong_6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent a) {
				if(a.getItemSelectable()==rdBtnWrong_6&&lt!=null)
				{
					((ArrayList)lt.get(5)).set(3, -1);	//不相似
				}
			}
		});*/
		buttonGroup_6.add(rdBtnWrong_6);
		rdBtnWrong_6.setText("不相似");
		panelopt_6.add(rdBtnWrong_6);

		final JPanel panel_7;
		panel_7 = new JPanel();
		panel_7.setLayout(new BorderLayout());

		final JPanel panelopt_7 = new JPanel();
		panelopt_7.setLayout(new FlowLayout());
		panel_7.add(panelopt_7, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_7 = new JRadioButton();
		rdBtnOK_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(6)).set(3, 1);//相似
				}
			}
		});
		buttonGroup_7.add(rdBtnOK_7);
		rdBtnOK_7.setText("相似");
		panelopt_7.add(rdBtnOK_7);

		final JRadioButton rdBtnWrong_7 = new JRadioButton();
		rdBtnWrong_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(6)).set(3, -1);	//不相似
				}
			}
		});
		buttonGroup_7.add(rdBtnWrong_7);
		rdBtnWrong_7.setText("不相似");
		panelopt_7.add(rdBtnWrong_7);

		final JPanel panel_8;
		panel_8 = new JPanel();
		panel_8.setLayout(new BorderLayout());

		final JPanel panelopt_8 = new JPanel();
		panelopt_8.setLayout(new FlowLayout());
		panel_8.add(panelopt_8, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_8 = new JRadioButton();
		rdBtnOK_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(7)).set(3, 1);	//相似
				}
			}
		});
		buttonGroup_8.add(rdBtnOK_8);
		rdBtnOK_8.setText("相似");
		panelopt_8.add(rdBtnOK_8);

		final JRadioButton rdBtnWrong_8 = new JRadioButton();
		rdBtnWrong_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(7)).set(3, -1);	//不相似
				}
			}
		});
		buttonGroup_8.add(rdBtnWrong_8);
		rdBtnWrong_8.setText("不相似");
		panelopt_8.add(rdBtnWrong_8);

		final JPanel panel_9;
		panel_9 = new JPanel();
		panel_9.setLayout(new BorderLayout());

		final JPanel panelopt_9 = new JPanel();
		panelopt_9.setLayout(new FlowLayout());
		panel_9.add(panelopt_9, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_9 = new JRadioButton();
		rdBtnOK_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(8)).set(3, 1);	//相似
				}
			}
		});
		buttonGroup_9.add(rdBtnOK_9);
		rdBtnOK_9.setText("相似");
		panelopt_9.add(rdBtnOK_9);

		final JRadioButton rdBtnWrong_9 = new JRadioButton();
		rdBtnWrong_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(8)).set(3, -1);	//不相似
				}
			}
		});
		buttonGroup_9.add(rdBtnWrong_9);
		rdBtnWrong_9.setText("不相似");
		panelopt_9.add(rdBtnWrong_9);

		final JPanel panel_10;
		panel_10 = new JPanel();
		panel_10.setLayout(new BorderLayout());

		final JPanel panelopt_10 = new JPanel();
		panelopt_10.setLayout(new FlowLayout());
		panel_10.add(panelopt_10, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_10 = new JRadioButton();
		rdBtnOK_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(9)).set(3, 1);	//相似
				}
			}
		});
		buttonGroup_10.add(rdBtnOK_10);
		rdBtnOK_10.setText("相似");
		panelopt_10.add(rdBtnOK_10);

		final JRadioButton rdBtnWrong_10 = new JRadioButton();
		rdBtnWrong_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(9)).set(3, -1);	//不相似
				}
			}
		});
		buttonGroup_10.add(rdBtnWrong_10);
		rdBtnWrong_10.setText("不相似");
		panelopt_10.add(rdBtnWrong_10);

		final JPanel panel_11;
		panel_11 = new JPanel();
		panel_11.setLayout(new BorderLayout());

		final JPanel panelopt_11 = new JPanel();
		panelopt_11.setLayout(new FlowLayout());
		panel_11.add(panelopt_11, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_11 = new JRadioButton();
		rdBtnOK_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(10)).set(3, 1);	//相似
				}
			}
		});
		buttonGroup_11.add(rdBtnOK_11);
		rdBtnOK_11.setText("相似");
		panelopt_11.add(rdBtnOK_11);

		final JRadioButton rdBtnWrong_11 = new JRadioButton();
		rdBtnWrong_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(10)).set(3, -1);	//不相似
				}
			}
		});
		buttonGroup_11.add(rdBtnWrong_11);
		rdBtnWrong_11.setText("不相似");
		panelopt_11.add(rdBtnWrong_11);

		final JPanel panel_12;
		panel_12 = new JPanel();
		panel_12.setLayout(new BorderLayout());

		final JPanel panelopt_12 = new JPanel();
		panelopt_12.setLayout(new FlowLayout());
		panel_12.add(panelopt_12, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_12 = new JRadioButton();
		rdBtnOK_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(11)).set(3, 1);	//相似
				}
			}
		});
		buttonGroup_12.add(rdBtnOK_12);
		rdBtnOK_12.setText("相似");
		panelopt_12.add(rdBtnOK_12);

		final JRadioButton rdBtnWrong_12 = new JRadioButton();
		rdBtnWrong_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(11)).set(3, -1);	//不相似
				}
			}
		});
		buttonGroup_12.add(rdBtnWrong_12);
		rdBtnWrong_12.setText("不相似");
		panelopt_12.add(rdBtnWrong_12);

		final JPanel panel_13;
		panel_13 = new JPanel();
		panel_13.setLayout(new BorderLayout());

		final JPanel panelopt_13 = new JPanel();
		panelopt_13.setLayout(new FlowLayout());
		panel_13.add(panelopt_13, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_13 = new JRadioButton();
		rdBtnOK_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(12)).set(3, 1);	//相似
				}
			}
		});
		buttonGroup_13.add(rdBtnOK_13);
		rdBtnOK_13.setText("相似");
		panelopt_13.add(rdBtnOK_13);

		final JRadioButton rdBtnWrong_13 = new JRadioButton();
		rdBtnWrong_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null) 
				{
					((ArrayList)lt.get(12)).set(3, -1);	//不相似
				}
			}
		});
		buttonGroup_13.add(rdBtnWrong_13);
		rdBtnWrong_13.setText("不相似");
		panelopt_13.add(rdBtnWrong_13);

		final JPanel panel_14;
		panel_14 = new JPanel();
		panel_14.setLayout(new BorderLayout());

		final JPanel panelopt_14 = new JPanel();
		panelopt_14.setLayout(new FlowLayout());
		panel_14.add(panelopt_14, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_14 = new JRadioButton();
		rdBtnOK_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(13)).set(3, 1);	//相似
				}
			}
		});
		buttonGroup_14.add(rdBtnOK_14);
		rdBtnOK_14.setText("相似");
		panelopt_14.add(rdBtnOK_14);

		final JRadioButton rdBtnWrong_14 = new JRadioButton();
		rdBtnWrong_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(13)).set(3, -1);	//不相似
				}
			}
		});
		buttonGroup_14.add(rdBtnWrong_14);
		rdBtnWrong_14.setText("不相似");
		panelopt_14.add(rdBtnWrong_14);

		final JPanel panel_15;
		panel_15 = new JPanel();
		panel_15.setLayout(new BorderLayout());

		final JPanel panelopt_15 = new JPanel();
		panelopt_15.setLayout(new FlowLayout());
		panel_15.add(panelopt_15, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_15 = new JRadioButton();
		rdBtnOK_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(14)).set(3, 1);	//相似
				}
			}
		});
		buttonGroup_15.add(rdBtnOK_15);
		rdBtnOK_15.setText("相似");
		panelopt_15.add(rdBtnOK_15);

		final JRadioButton rdBtnWrong_15 = new JRadioButton();
		rdBtnWrong_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(14)).set(3, -1);	//不相似
				}
			}
		});
		buttonGroup_15.add(rdBtnWrong_15);
		rdBtnWrong_15.setText("不相似");
		panelopt_15.add(rdBtnWrong_15);

		final JPanel panel_16;
		panel_16 = new JPanel();
		panel_16.setLayout(new BorderLayout());

		final JPanel panelopt_16 = new JPanel();
		panelopt_16.setLayout(new FlowLayout());
		panel_16.add(panelopt_16, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_16 = new JRadioButton();
		rdBtnOK_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(15)).set(3, 1);	//相似
				}
			}
		});
		buttonGroup_16.add(rdBtnOK_16);
		rdBtnOK_16.setText("相似");
		panelopt_16.add(rdBtnOK_16);

		final JRadioButton rdBtnWrong_16 = new JRadioButton();
		rdBtnWrong_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(15)).set(3, -1);	//不相似
				}
			}
		});
	
		buttonGroup_16.add(rdBtnWrong_16);
		rdBtnWrong_16.setText("不相似");
		panelopt_16.add(rdBtnWrong_16);

		final JPanel panel_17;
		panel_17 = new JPanel();
		panel_17.setLayout(new BorderLayout());

		final JPanel panelopt_17 = new JPanel();
		panelopt_17.setLayout(new FlowLayout());
		panel_17.add(panelopt_17, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_17 = new JRadioButton();
		rdBtnOK_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(16)).set(3, 1);	//相似
				}
			}
		});
		buttonGroup_17.add(rdBtnOK_17);
		rdBtnOK_17.setText("相似");
		panelopt_17.add(rdBtnOK_17);

		final JRadioButton rdBtnWrong_17 = new JRadioButton();
		rdBtnWrong_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(16)).set(3, -1);	//不相似
				}
			}
		});
		buttonGroup_17.add(rdBtnWrong_17);
		rdBtnWrong_17.setText("不相似");
		panelopt_17.add(rdBtnWrong_17);

		final JPanel panel_18;
		panel_18 = new JPanel();
		panel_18.setLayout(new BorderLayout());

		final JPanel panelopt_18 = new JPanel();
		panelopt_18.setLayout(new FlowLayout());
		panel_18.add(panelopt_18, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_18 = new JRadioButton();
		rdBtnOK_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(17)).set(3, 1);	//相似
				}
			}
		});
		buttonGroup_18.add(rdBtnOK_18);
		rdBtnOK_18.setText("相似");
		panelopt_18.add(rdBtnOK_18);

		final JRadioButton rdBtnWrong_18 = new JRadioButton();
		rdBtnWrong_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(17)).set(3, -1);	//不相似
				}
			}
		});
		buttonGroup_18.add(rdBtnWrong_18);
		rdBtnWrong_18.setText("不相似");
		panelopt_18.add(rdBtnWrong_18);

		final JPanel panel_19;
		panel_19 = new JPanel();
		panel_19.setLayout(new BorderLayout());

		final JPanel panelopt_19 = new JPanel();
		panelopt_19.setLayout(new FlowLayout());
		panel_19.add(panelopt_19, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_19 = new JRadioButton();
		rdBtnOK_19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(18)).set(3, 1);	//相似
				}
			}
		});
		buttonGroup_19.add(rdBtnOK_19);
		rdBtnOK_19.setText("相似");
		panelopt_19.add(rdBtnOK_19);

		final JRadioButton rdBtnWrong_19 = new JRadioButton();
		rdBtnWrong_19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(18)).set(3, -1);	//不相似
				}
			}
		});
		buttonGroup_19.add(rdBtnWrong_19);
		rdBtnWrong_19.setText("不相似");
		panelopt_19.add(rdBtnWrong_19);

		final JPanel panel_20;
		panel_20 = new JPanel();
		panel_20.setLayout(new BorderLayout());

		final JPanel panelopt_20 = new JPanel();
		panelopt_20.setLayout(new FlowLayout());
		panel_20.add(panelopt_20, BorderLayout.SOUTH);

		final JRadioButton rdBtnOK_20 = new JRadioButton();
		rdBtnOK_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(19)).set(3, 1);	//相似
				}
			}
		});
		buttonGroup_20.add(rdBtnOK_20);
		rdBtnOK_20.setText("相似");
		panelopt_20.add(rdBtnOK_20);

		final JRadioButton rdBtnWrong_20 = new JRadioButton();
		rdBtnWrong_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				if(lt!=null)
				{
					((ArrayList)lt.get(19)).set(3, -1);	//不相似
				}
			}
		});
		buttonGroup_20.add(rdBtnWrong_20);
		rdBtnWrong_20.setText("不相似");
		panelopt_20.add(rdBtnWrong_20);

		for(int c=0;c<20;c++)
		{
			acav[c]=new RoomCanvas(130,120);
		}
		panel_1.add(acav[0],BorderLayout.CENTER);
		panel_2.add(acav[1],BorderLayout.CENTER);
		panel_3.add(acav[2],BorderLayout.CENTER);
		panel_4.add(acav[3],BorderLayout.CENTER);
		panel_5.add(acav[4],BorderLayout.CENTER);
		panel_6.add(acav[5],BorderLayout.CENTER);
		panel_7.add(acav[6],BorderLayout.CENTER);
		panel_8.add(acav[7],BorderLayout.CENTER);
		panel_9.add(acav[8],BorderLayout.CENTER);
		panel_10.add(acav[9],BorderLayout.CENTER);
		panel_11.add(acav[10],BorderLayout.CENTER);
		panel_12.add(acav[11],BorderLayout.CENTER); 
		panel_13.add(acav[12],BorderLayout.CENTER);
		panel_14.add(acav[13],BorderLayout.CENTER);
		panel_15.add(acav[14],BorderLayout.CENTER);
		panel_16.add(acav[15],BorderLayout.CENTER);
		panel_17.add(acav[16],BorderLayout.CENTER);
		panel_18.add(acav[17],BorderLayout.CENTER);
		panel_19.add(acav[18],BorderLayout.CENTER);
		panel_20.add(acav[19],BorderLayout.CENTER);
		
		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText("检索结果： ");
		final GroupLayout groupLayout = new GroupLayout((JComponent) panel3);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(GroupLayout.TRAILING)
				.add(groupLayout.createSequentialGroup()
					.addContainerGap()
					.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
						.add(groupLayout.createSequentialGroup()
							.add(panel_11, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(panel_12, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(panel_13, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(panel_14, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(panel_15, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
							.add(0, 0, Short.MAX_VALUE))
						.add(groupLayout.createSequentialGroup()
							.addPreferredGap(LayoutStyle.RELATED)
							.add(panel_16, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(panel_17, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(panel_18, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(panel_19, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(panel_20, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
							.add(0, 0, Short.MAX_VALUE))
						.add(groupLayout.createSequentialGroup()
							.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
								.add(groupLayout.createSequentialGroup()
									.addPreferredGap(LayoutStyle.RELATED)
									.add(panel_6, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
								.add(panel_1, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.RELATED)
							.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
								.add(GroupLayout.TRAILING, panel_7, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
								.add(panel_2, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.RELATED)
							.add(groupLayout.createParallelGroup(GroupLayout.TRAILING)
								.add(panel_3, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
								.add(panel_8, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.RELATED)
							.add(groupLayout.createParallelGroup(GroupLayout.TRAILING)
								.add(panel_4, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
								.add(panel_9, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.RELATED)
							.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
								.add(panel_10, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
								.add(panel_5, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)))
						.add(groupLayout.createSequentialGroup()
							.add(label_3, GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
							.add(590, 590, 590)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(GroupLayout.LEADING)
				.add(groupLayout.createSequentialGroup()
					.add(label_3)
					.add(16, 16, 16)
					.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
						.add(groupLayout.createParallelGroup(GroupLayout.TRAILING)
							.add(panel_5, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
							.add(GroupLayout.LEADING, panel_4, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
							.add(GroupLayout.LEADING, panel_3, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
							.add(GroupLayout.LEADING, panel_2, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
						.add(panel_1, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
					.add(15, 15, 15)
					.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
						.add(panel_6, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
						.add(panel_8, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
						.add(panel_9, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
						.add(panel_7, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
						.add(panel_10, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
					.add(16, 16, 16)
					.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
						.add(panel_12, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
						.add(panel_13, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
						.add(panel_14, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
						.add(panel_15, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
						.add(panel_11, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE))
					.add(16, 16, 16)
					.add(groupLayout.createParallelGroup(GroupLayout.TRAILING)
						.add(panel_16, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
						.add(groupLayout.createParallelGroup(GroupLayout.LEADING)
							.add(GroupLayout.TRAILING, panel_17, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
							.add(panel_18, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
							.add(panel_19, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
							.add(panel_20, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)))
					.add(24, 24, 24))
		);
		panel3.setLayout(groupLayout);

		final JPanel panell = new JPanel();
		panell.setLayout(new BorderLayout());
		panell.setPreferredSize(new Dimension(160, 400));
		splitPane.setLeftComponent(panell);

		panel1 = new JPanel();
		panel1.setOpaque(false);
		panel1.setPreferredSize(new Dimension(160, 160));
		panel1.setLayout(new BorderLayout());
		panell.add(panel1, BorderLayout.NORTH);
		
		cav=new RoomCanvas(160,140);
		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc=new JFileChooser();
				//cav=new RoomCanvas(160,140);
				int sta=openFile(fc);
				if(sta==JFileChooser.APPROVE_OPTION)
				{
					try
					{
						File f=fc.getSelectedFile();
						if(fc!=null)
						{
							cav.setGraph(f.toString());
							cav.setVisible(false);
							cav.repaint();
							cav.setVisible(true);
							ColorandShapeVector csv=new ColorandShapeVector(f.toString());
							csv.runcasv();
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		button.setPreferredSize(new Dimension(0, 20));
		panel1.add(cav,BorderLayout.CENTER);
		panel1.add(button, BorderLayout.SOUTH);
		button.setText("载入图片");

		final JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		panell.add(panel2, BorderLayout.CENTER);

		cbxRetriveType = new JComboBox();
		cbxRetriveType.setBounds(10, 212, 133, 23);
		panel2.add(cbxRetriveType);
		cbxRetriveType.setModel(new DefaultComboBoxModel(new String[] {"颜色特征检索", "形状特征检索", "颜色和形状特征检索"}));

		cbxCheckType = new JComboBox();
		cbxCheckType.setBounds(10, 262, 133, 23);
		panel2.add(cbxCheckType);
		cbxCheckType.setModel(new DefaultComboBoxModel(new String[] {"采用上次结果", "不采用上次结果"}));

		final JRadioButton rdnoCluster = new JRadioButton();
		rdnoCluster.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent a) {
				if(a.getItemSelectable()==rdnoCluster)
				{
					isclt=false;
				}
			}
		});
		buttonGroup.add(rdnoCluster);
		rdnoCluster.setBounds(10, 320, 85, 23);
		panel2.add(rdnoCluster);
		rdnoCluster.setText("未采用聚类");

		final JRadioButton rdCluster = new JRadioButton();
		rdCluster.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent a) {
				if(a.getItemSelectable()==rdCluster)
					isclt=true;
			}
		});
		buttonGroup.add(rdCluster);
		rdCluster.setBounds(10, 291, 73, 23);
		panel2.add(rdCluster);
		rdCluster.setText("采用聚类");

		final JButton btnStart = new JButton();
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fbc=0;
				lblCount.setText("");
				wt=uwc.gtWgt("颜色：形状");
				if(!isclt)	//没采用聚类，反馈还未进行
				{
					prt=new PicRetrivement();
					String par="";
					if(cbxRetriveType.getSelectedItem().toString().equals("颜色特征检索"))
					{
						lttp=ColorandShapeVector.ltcv;
						par="colorvec";
						lt=null;
						kd=1;
						lt=prt.getCorShpSimPic(lttp, par, 1);
					}
					else if(cbxRetriveType.getSelectedItem().toString().equals("形状特征检索"))
					{
						lttp=ColorandShapeVector.ltsv;
						par="shapevec";
						lt=null;
						kd=1;
						lt=prt.getCorShpSimPic(lttp, par, 1);
					}
					else
					{
						long v=System.currentTimeMillis();
						lt=null;
						kd=0;
						lt=prt.getSimPic(ColorandShapeVector.ltcv, ColorandShapeVector.ltsv, (float)wt[0], (float)wt[1]);
						System.out.print(System.currentTimeMillis()-v+"\n");
					}
				}
				else	//采用聚类
				{
					cprt=new CltPicRetriv();
					String par="";
					if(cbxRetriveType.getSelectedItem().toString().equals("颜色特征检索"))
					{
						lttp=ColorandShapeVector.ltcv;
						par="colorvec";
						lt=null;
						kd=1;
						lt=cprt.getCorShpSimPic(lttp, par, cprt.getClut(lttp), 1);
					}
					else if(cbxRetriveType.getSelectedItem().toString().equals("形状特征检索"))
					{
						lttp=ColorandShapeVector.ltsv;
						par="shapevec";
						lt=null;
						kd=1;
						lt=cprt.getCorShpSimPic(lttp, par, cprt.getClut(lttp), 1);
					}
					else
					{
						lt=null;
						kd=0;
						long a=System.currentTimeMillis();
						if(cbxCheckType.getSelectedItem().toString().equals("采用上次结果"))
							lt=cprt.getSimPic(ColorandShapeVector.ltcv, ColorandShapeVector.ltsv, (float)wt[0], (float)wt[1],1);
						else
							lt=cprt.getSimPic(ColorandShapeVector.ltcv, ColorandShapeVector.ltsv, (float)wt[0], (float)wt[1],0);
						System.out.print(System.currentTimeMillis()-a+"\n");
					}
				}
				//for(int c=0;c<20;c++)
				//{
				acav[0].setGraph(((ArrayList)lt.get(0)).get(1).toString());
				acav[0].setVisible(false);
				acav[0].repaint();
				acav[0].setVisible(true);
				acav[1].setGraph(((ArrayList)lt.get(1)).get(1).toString());
				acav[1].setVisible(false);
				acav[1].repaint();
				acav[1].setVisible(true);
				acav[2].setGraph(((ArrayList)lt.get(2)).get(1).toString());
				acav[2].setVisible(false);
				acav[2].repaint();
				acav[2].setVisible(true);
				acav[3].setGraph(((ArrayList)lt.get(3)).get(1).toString());
				acav[3].setVisible(false);
				acav[3].repaint();
				acav[3].setVisible(true);
				acav[4].setGraph(((ArrayList)lt.get(4)).get(1).toString());
				acav[4].setVisible(false);
				acav[4].repaint();
				acav[4].setVisible(true);
				acav[5].setGraph(((ArrayList)lt.get(5)).get(1).toString());
				acav[5].setVisible(false);
				acav[5].repaint();
				acav[5].setVisible(true);
				acav[6].setGraph(((ArrayList)lt.get(6)).get(1).toString());
				acav[6].setVisible(false);
				acav[6].repaint();
				acav[6].setVisible(true);					 
				acav[7].setGraph(((ArrayList)lt.get(7)).get(1).toString());
				acav[7].setVisible(false);
				acav[7].repaint();
				acav[7].setVisible(true);			 
				acav[8].setGraph(((ArrayList)lt.get(8)).get(1).toString());
				acav[8].setVisible(false);
				acav[8].repaint();
				acav[8].setVisible(true);				
				acav[9].setGraph(((ArrayList)lt.get(9)).get(1).toString());
				acav[9].setVisible(false);
				acav[9].repaint();
				acav[9].setVisible(true);
				acav[10].setGraph(((ArrayList)lt.get(10)).get(1).toString());
				acav[10].setVisible(false);
				acav[10].repaint();
				acav[10].setVisible(true);				 
				acav[11].setGraph(((ArrayList)lt.get(11)).get(1).toString());
				acav[11].setVisible(false);
				acav[11].repaint();
				acav[11].setVisible(true);				 
				acav[12].setGraph(((ArrayList)lt.get(12)).get(1).toString());
				acav[12].setVisible(false);
				acav[12].repaint();
				acav[12].setVisible(true);				
				acav[13].setGraph(((ArrayList)lt.get(13)).get(1).toString());
				acav[13].setVisible(false);
				acav[13].repaint();
				acav[13].setVisible(true);					 
				acav[14].setGraph(((ArrayList)lt.get(14)).get(1).toString());
				acav[14].setVisible(false);
				acav[14].repaint();
				acav[14].setVisible(true);					 
				acav[15].setGraph(((ArrayList)lt.get(15)).get(1).toString());
				acav[15].setVisible(false);
				acav[15].repaint();
				acav[15].setVisible(true);				 
				acav[16].setGraph(((ArrayList)lt.get(16)).get(1).toString());
				acav[16].setVisible(false);
				acav[16].repaint();
				acav[16].setVisible(true);		 
				acav[17].setGraph(((ArrayList)lt.get(17)).get(1).toString());
				acav[17].setVisible(false);
				acav[17].repaint();
				acav[17].setVisible(true);		
				acav[18].setGraph(((ArrayList)lt.get(18)).get(1).toString());
				acav[18].setVisible(false);
				acav[18].repaint();
				acav[18].setVisible(true);
				acav[19].setGraph(((ArrayList)lt.get(19)).get(1).toString());
				acav[19].setVisible(false);
				acav[19].repaint();
				acav[19].setVisible(true);
				//}
			}
		});
		btnStart.setText("检索开始");
		btnStart.setBounds(32, 30, 99, 23);
		panel2.add(btnStart);

		final JButton btnNext = new JButton();
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wt=uwc.gtWgt("颜色：形状");
				wv=uwc.gtWgt("系数");fbc++;
				lblCount.setText(fbc+"次");
				if(!isclt&&prt!=null)	//没采用聚类的反馈
				{
				//wt[0]=.8;
					//wt[1]=.2;
					List ltdl[]=new List[2];
					prt=new PicRetrivement();
					String par="";
					//double wv[]=new double[2];
					//wv[0]=.8;
					//wv[1]=.2;
					if(cbxRetriveType.getSelectedItem().toString().equals("颜色特征检索"))
					{
						//lttp=ColorandShapeVector.ltcv;
						par="colorvec";
						//lttp=null;
						ltdl=prt.chageVec(wt, wv, lt, lttp, kd, par);	
						lt=ltdl[0];
						lttp=ltdl[1];
					}
					else if(cbxRetriveType.getSelectedItem().toString().equals("形状特征检索"))
					{
						//lttp=ColorandShapeVector.ltsv;
						par="shapevec";
						//lttp=null;
						ltdl=prt.chageVec(wt, wv, lt, lttp, kd, par);//lt=prt.getCorShpSimPic(lttp, par, 1);
						lt=ltdl[0];
						lttp=ltdl[1];
					}
					else
					{
						//wt[0]=.5;
						//wt[1]=.5;//lt=null;
						//List ltcs=new LinkedList();
						//double wv[]=new double[2];
						if(ltcs==null)	//初次反馈
						{
							ltcs=new LinkedList();
							int lc=ColorandShapeVector.ltcv.size();
							int ls=ColorandShapeVector.ltsv.size();
							for(int i=0;i<lc+ls;i++)
							{
								long a=System.currentTimeMillis();
								if(i<lc)
									ltcs.add(ColorandShapeVector.ltcv.get(i));
								else
									ltcs.add(ColorandShapeVector.ltsv.get(i-lc));
								System.out.print(System.currentTimeMillis()-a+"\n");
							}
						}
						ltdl=prt.chageVec(wt, wv, lt, ltcs, kd, par);
						lt=ltdl[0];
						ltcs=ltdl[1];
					}
				}
				else if(cprt!=null)	//存在聚类
				{
					//cprt=new CltPicRetriv();
					//wt[0]=.8;
					//wt[1]=.2;
					//double wv[]=new double[2];//反馈系数
					//wv[0]=.8;
					//wv[1]=.2;
					String par="";
					List ltdl[]=new List[2];
					if(cbxRetriveType.getSelectedItem().toString().equals("颜色特征检索"))
					{
						//lttp=ColorandShapeVector.ltcv;
						par="colorvec";
						//lt=null;
						if(cbxCheckType.getSelectedItem().toString().equals("采用上次结果"))
							ltdl=cprt.chageVec(wt, wv, lt, lttp, kd, par,1);//getCorShpSimPic(lttp, par, cprt.getClut(lttp), 1);
						else
							ltdl=cprt.chageVec(wt, wv, lt, lttp, kd, par,0);
						lt=ltdl[0];
						lttp=ltdl[1];
					}
					else if(cbxRetriveType.getSelectedItem().toString().equals("形状特征检索"))
					{
						//lttp=ColorandShapeVector.ltsv;
						par="shapevec";
						//lt=null;
						if(cbxCheckType.getSelectedItem().toString().equals("采用上次结果"))
							ltdl=cprt.chageVec(wt, wv, lt, lttp, kd, par,1);//getCorShpSimPic(lttp, par, cprt.getClut(lttp), 1);
						else
							ltdl=cprt.chageVec(wt, wv, lt, lttp, kd, par,0);
						lt=ltdl[0];
						lttp=ltdl[1];
					}
					else
					{
						//wt[0]=.5;
						//wt[1]=.5;
						if(ltcs==null)	//初次反馈
						{
							ltcs=new LinkedList();
							int lc=ColorandShapeVector.ltcv.size();
							int ls=ColorandShapeVector.ltsv.size();
							for(int i=0;i<lc+ls;i++)
							{
								long c=System.currentTimeMillis();
								if(i<lc)
									ltcs.add(ColorandShapeVector.ltcv.get(i));
								else
									ltcs.add(ColorandShapeVector.ltsv.get(i-lc));
								System.out.print(System.currentTimeMillis()-c+"\n");
							}
						}
						long c=System.currentTimeMillis();
						if(cbxCheckType.getSelectedItem().toString().equals("采用上次结果"))
							ltdl=cprt.chageVec(wt, wv, lt, ltcs, kd, par,1);
						else
							ltdl=cprt.chageVec(wt, wv, lt, ltcs, kd, par,0);
						System.out.print(System.currentTimeMillis()-c+"\n");
						lt=ltdl[0];
						ltcs=ltdl[1];
						//lttp=cprt.chageVec(wt, lt, lttp, kd, par);//lt=cprt.getSimPic(ColorandShapeVector.ltcv, ColorandShapeVector.ltsv, (float).5, (float).5);
					}
				}
				acav[0].setGraph(((ArrayList)lt.get(0)).get(1).toString());
				acav[0].setVisible(false);
				acav[0].repaint();
				acav[0].setVisible(true);
				acav[1].setGraph(((ArrayList)lt.get(1)).get(1).toString());
				acav[1].setVisible(false);
				acav[1].repaint();
				acav[1].setVisible(true);
				acav[2].setGraph(((ArrayList)lt.get(2)).get(1).toString());
				acav[2].setVisible(false);
				acav[2].repaint();
				acav[2].setVisible(true);
				acav[3].setGraph(((ArrayList)lt.get(3)).get(1).toString());
				acav[3].setVisible(false);
				acav[3].repaint();
				acav[3].setVisible(true);
				acav[4].setGraph(((ArrayList)lt.get(4)).get(1).toString());
				acav[4].setVisible(false);
				acav[4].repaint();
				acav[4].setVisible(true);
				acav[5].setGraph(((ArrayList)lt.get(5)).get(1).toString());
				acav[5].setVisible(false);
				acav[5].repaint();
				acav[5].setVisible(true);
				acav[6].setGraph(((ArrayList)lt.get(6)).get(1).toString());
				acav[6].setVisible(false);
				acav[6].repaint();
				acav[6].setVisible(true);					 
				acav[7].setGraph(((ArrayList)lt.get(7)).get(1).toString());
				acav[7].setVisible(false);
				acav[7].repaint();
				acav[7].setVisible(true);			 
				acav[8].setGraph(((ArrayList)lt.get(8)).get(1).toString());
				acav[8].setVisible(false);
				acav[8].repaint();
				acav[8].setVisible(true);				
				acav[9].setGraph(((ArrayList)lt.get(9)).get(1).toString());
				acav[9].setVisible(false);
				acav[9].repaint();
				acav[9].setVisible(true);
				acav[10].setGraph(((ArrayList)lt.get(10)).get(1).toString());
				acav[10].setVisible(false);
				acav[10].repaint();
				acav[10].setVisible(true);				 
				acav[11].setGraph(((ArrayList)lt.get(11)).get(1).toString());
				acav[11].setVisible(false);
				acav[11].repaint();
				acav[11].setVisible(true);				 
				acav[12].setGraph(((ArrayList)lt.get(12)).get(1).toString());
				acav[12].setVisible(false);
				acav[12].repaint();
				acav[12].setVisible(true);				
				acav[13].setGraph(((ArrayList)lt.get(13)).get(1).toString());
				acav[13].setVisible(false);
				acav[13].repaint();
				acav[13].setVisible(true);					 
				acav[14].setGraph(((ArrayList)lt.get(14)).get(1).toString());
				acav[14].setVisible(false);
				acav[14].repaint();
				acav[14].setVisible(true);					 
				acav[15].setGraph(((ArrayList)lt.get(15)).get(1).toString());
				acav[15].setVisible(false);
				acav[15].repaint();
				acav[15].setVisible(true);				 
				acav[16].setGraph(((ArrayList)lt.get(16)).get(1).toString());
				acav[16].setVisible(false);
				acav[16].repaint();
				acav[16].setVisible(true);		 
				acav[17].setGraph(((ArrayList)lt.get(17)).get(1).toString());
				acav[17].setVisible(false);
				acav[17].repaint();
				acav[17].setVisible(true);		
				acav[18].setGraph(((ArrayList)lt.get(18)).get(1).toString());
				acav[18].setVisible(false);
				acav[18].repaint();
				acav[18].setVisible(true);
				acav[19].setGraph(((ArrayList)lt.get(19)).get(1).toString());
				acav[19].setVisible(false);
				acav[19].repaint();
				acav[19].setVisible(true);
			}
		});
		btnNext.setBounds(32, 59, 99, 23);
		panel2.add(btnNext);
		btnNext.setText("下一次检索");

		final JLabel label = new JLabel();
		label.setText("检索类型： ");
		label.setBounds(10, 191, 84, 15);
		panel2.add(label);

		final JLabel label_1 = new JLabel();
		label_1.setText("查询初始条件： ");
		label_1.setBounds(11, 241, 98, 15);
		panel2.add(label_1);

		final JButton btnFinish = new JButton();
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fbc=0;
				lblCount.setText("");
				if(!isclt&&prt!=null)
				{
					prt.svDB(kd);
				}
				if(isclt&&cprt!=null)
				{
					cprt.svDB(kd,ltcs);
				}
			}
		});
		btnFinish.setText("检索完毕");
		btnFinish.setBounds(32, 138, 99, 23);
		panel2.add(btnFinish);

		final JLabel label_2 = new JLabel();
		label_2.setText("反馈次数： 第");
		label_2.setBounds(10, 100, 85, 15);
		panel2.add(label_2);

		//final JLabel lblCount = new JLabel();
		lblCount.setBounds(89, 100, 36, 15);
		panel2.add(lblCount);

		final JSeparator separator = new JSeparator();
		separator.setBounds(0, 179, 160, 9);
		panel2.add(separator);
		
	}

	public int openFile(JFileChooser fc)
	{
		fc.setCurrentDirectory(new File("F:\\TDdownload\\lw\\tp"));
		fc.addChoosableFileFilter(new FileFilterGph("jpg","jpge file"));
		fc.addChoosableFileFilter(new FileFilterGph("tiff","tiff file"));
		//fc.addChoosableFileFilter(new FileFilterGph("jpg","jpge file"));
		fc.addChoosableFileFilter(new FileFilterGph("bmp","bmp file"));
		return fc.showOpenDialog(this);
	}
}

class MenuAdapter implements java.awt.event.ActionListener
{
	CBIRFrame cbir;
	public MenuAdapter(CBIRFrame cbir)
	{
		this.cbir=cbir;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		 cbir.dispose();
	}
}
