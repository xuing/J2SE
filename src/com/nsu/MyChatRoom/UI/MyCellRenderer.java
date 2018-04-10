package com.nsu.MyChatRoom.UI;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.nsu.MyChatRoom.Util.FrameUtil;

public	class MyCellRenderer extends JLabel implements ListCellRenderer {
	ArrayList<Icon> icons;
	public MyCellRenderer(){};
//	public MyCellRenderer(Icon[] icons) {
//		this.icons=icons;
//	}
	public MyCellRenderer(ArrayList<Icon> listIcons) {
		this.icons = listIcons;
	}
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		String s = value.toString();
		setText(s);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));//加入宽度为5的空白边框
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setIcon(icons.get(index));//设置图片
		setEnabled(list.isEnabled());
		setFont(list.getFont().deriveFont((float) (30)));
		setOpaque(true);
		return this;
	}

}

