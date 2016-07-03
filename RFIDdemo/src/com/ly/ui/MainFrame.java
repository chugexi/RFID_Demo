package com.ly.ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import gnu.io.*;
import java.util.Enumeration;

public class MainFrame{
    public static void main(String args[]){
        new MyWin("考勤系统");
    }
}
class MyWin extends JFrame{
    JComboBox<String> cbportList;
    String portname;
    JButton bOpenSPort;
    JLabel lSportStatus;
    JPanel pInfo,pPunch,pPunchInfo,pSysConfig;
    
    MyWin(String s){
       setTitle(s); 
       //this is main north, may move to sysconfig
       JPanel pNorth=new JPanel();       
       //String comStr[]={"COM1","COM3"};
       cbportList=new JComboBox<String>();
       //cbportList.addItem("COM9");
       CommPortIdentifier portId;
       Enumeration<CommPortIdentifier> en = CommPortIdentifier.getPortIdentifiers();
		while (en.hasMoreElements()) {                  
			portId = en.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) { 
				cbportList.addItem(portId.getName()); 
			}
		} 
       pNorth.add(cbportList);
       bOpenSPort=new JButton("打开串口");
       pNorth.add(bOpenSPort);
       lSportStatus=new JLabel("    ");
       pNorth.add(lSportStatus);
       add(pNorth,BorderLayout.NORTH);
       //这里是按钮的动作，增加连接读卡器的代码
       bOpenSPort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(bOpenSPort.getText().equals("打开串口")){
					//bOpenSPort.setText("关闭串口");
					lSportStatus.setText((String)cbportList.getSelectedItem());
				}
			}
		});

       //this is center
       JTabbedPane jtp=new JTabbedPane();
       pPunch=new JPanel(new BorderLayout());
       pPunchInfo=new JPanel(new BorderLayout());
       pInfo=new JPanel();
       pSysConfig =new JPanel();
       jtp.addTab("考勤", pPunch);
       jtp.addTab("考勤汇总", pPunchInfo);
       jtp.addTab("个人信息管理", pInfo); 
       jtp.addTab("系统设置", pSysConfig); 
       add(jtp);
       //tab 考勤
       JLabel lClock = new JLabel("2016年06月07日 11:33:58",JLabel.CENTER);
       pPunch.add(lClock,BorderLayout.NORTH);
       JTable tPunch =new JTable(5,5);
       pPunch.add(tPunch);
       //tab 考勤汇总
       JPanel ppiN=new JPanel();
       JTable tPunchInfo =new JTable(5,8);
       pPunchInfo.add(ppiN,BorderLayout.NORTH);
       pPunchInfo.add(tPunchInfo);
       String chkStr[]={"按工号","月份统计"};
       JComboBox<String> checkInfo=new JComboBox<String>(chkStr);
       ppiN.add(checkInfo);
       
       ppiN.add(new JLabel("hello"));
       ppiN.add(new JButton("查询"));
       
       //tab 个人信息管理
       JButton bReadID =new JButton("读卡号");
       //use cardreader to read CardID
       bReadID.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("读卡号");
				
			}
		});
       pInfo.add(bReadID);
       
       //tab 系统设置
       
       
       validate();
       setBounds(100,100,500,500);
       setVisible(true);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}
