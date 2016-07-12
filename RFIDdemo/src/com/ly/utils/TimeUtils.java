package com.ly.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	/**
	 * �ж�ʱ���Ƿ���ʱ�����
	 * 
	 * @param date
	 *            ��ǰʱ�� yyyy-MM-dd HH:mm:ss
	 * @param strDateBegin
	 *            ��ʼʱ�� 00:00:00
	 * @param strDateEnd
	 *            ����ʱ�� 00:05:00
	 * @return
	 */
	public static boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);
		// ��ȡ��ǰʱ��ʱ����
		int strDateH = Integer.parseInt(strDate.substring(11, 13));
		int strDateM = Integer.parseInt(strDate.substring(14, 16));
		int strDateS = Integer.parseInt(strDate.substring(17, 19));
		// ��ȡ��ʼʱ��ʱ����
		int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
		int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
		int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
		// ��ȡ����ʱ��ʱ����
		int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
		int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
		int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
		if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
			// ��ǰʱ��Сʱ���ڿ�ʼʱ��ͽ���ʱ��Сʱ��֮��
			if (strDateH > strDateBeginH && strDateH < strDateEndH) {
				return true;
				// ��ǰʱ��Сʱ�����ڿ�ʼʱ��Сʱ�����������ڿ�ʼ�ͽ���֮��
			} else if (strDateH == strDateBeginH && strDateM >= strDateBeginM && strDateM <= strDateEndM) {
				return true;
				// ��ǰʱ��Сʱ�����ڿ�ʼʱ��Сʱ�������������ڿ�ʼʱ��������������ڿ�ʼ�ͽ���֮��
			} else if (strDateH == strDateBeginH && strDateM == strDateBeginM && strDateS >= strDateBeginS
					&& strDateS <= strDateEndS) {
				return true;

			}
			// ��ǰʱ��Сʱ������ڿ�ʼʱ��Сʱ�������ڽ���ʱ��Сʱ����������С���ڽ���ʱ�������
			else if (strDateH >= strDateBeginH && strDateH == strDateEndH && strDateM <= strDateEndM) {
				return true;
				// ��ǰʱ��Сʱ������ڿ�ʼʱ��Сʱ�������ڽ���ʱ��Сʱ�������������ڽ���ʱ�������������С���ڽ���ʱ������
			} else if (strDateH >= strDateBeginH && strDateH == strDateEndH && strDateM == strDateEndM
					&& strDateS <= strDateEndS) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
