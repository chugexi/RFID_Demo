package com.ly.ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import gnu.io.*;
import java.util.Enumeration;

public class MainFrame{
    public static void main(String args[]){
        new MyWin("����ϵͳ");
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
       bOpenSPort=new JButton("�򿪴���");
       pNorth.add(bOpenSPort);
       lSportStatus=new JLabel("    ");
       pNorth.add(lSportStatus);
       add(pNorth,BorderLayout.NORTH);
       //�����ǰ�ť�Ķ������������Ӷ������Ĵ���
       bOpenSPort.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(bOpenSPort.getText().equals("�򿪴���")){
					//bOpenSPort.setText("�رմ���");
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
       jtp.addTab("����", pPunch);
       jtp.addTab("���ڻ���", pPunchInfo);
       jtp.addTab("������Ϣ����", pInfo); 
       jtp.addTab("ϵͳ����", pSysConfig); 
       add(jtp);
       //tab ����
       JLabel lClock = new JLabel("2016��06��07�� 11:33:58",JLabel.CENTER);
       pPunch.add(lClock,BorderLayout.NORTH);
       JTable tPunch =new JTable(5,5);
       pPunch.add(tPunch);
       //tab ���ڻ���
       JPanel ppiN=new JPanel();
       JTable tPunchInfo =new JTable(5,8);
       pPunchInfo.add(ppiN,BorderLayout.NORTH);
       pPunchInfo.add(tPunchInfo);
       String chkStr[]={"������","�·�ͳ��"};
       JComboBox<String> checkInfo=new JComboBox<String>(chkStr);
       ppiN.add(checkInfo);
       
       ppiN.add(new JLabel("hello"));
       ppiN.add(new JButton("��ѯ"));
       
       //tab ������Ϣ����
       JButton bReadID =new JButton("������");
       //use cardreader to read CardID
       bReadID.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("������");
				
			}
		});
       pInfo.add(bReadID);
       
       //tab ϵͳ����
       
       
       validate();
       setBounds(100,100,500,500);
       setVisible(true);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}
