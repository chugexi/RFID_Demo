package com.ly.ui;

import java.awt.FontMetrics;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class MyPaneUI extends BasicTabbedPaneUI {
	protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
		return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 60;
	}

	protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
		return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 60;
	}
}
