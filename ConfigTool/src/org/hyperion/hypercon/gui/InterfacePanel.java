package org.hyperion.hypercon.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hyperion.hypercon.LedStringModel;
import org.hyperion.hypercon.spec.TcpServerModel;

public class InterfacePanel extends JPanel {

	private final TcpServerModel mJsonServerModel;
	private final TcpServerModel mProtoServerModel;
	private final TcpServerModel mBoblightServerModel;
	
	private JPanel mJsonPanel;
	private JCheckBox mJsonCheck;
	private JLabel mJsonPortLabel;
	private JSpinner mJsonPortSpinner;
	
	private JPanel mProtoPanel;
	private JCheckBox mProtoCheck;
	private JLabel mProtoPortLabel;
	private JSpinner mProtoPortSpinner;
	
	private JPanel mBoblightPanel;
	private JCheckBox mBoblightCheck;
	private JLabel mBoblightPortLabel;
	private JSpinner mBoblightPortSpinner;
	
	public InterfacePanel(final LedStringModel pLedStringModel) {
		super();
		
		mJsonServerModel     = pLedStringModel.jsonServer;
		mProtoServerModel    = pLedStringModel.protoServer;
		mBoblightServerModel = pLedStringModel.boblightServer;
		
		initialise();
	}
	
	@Override
	@Transient
	public Dimension getMaximumSize() {
		Dimension maxSize = super.getMaximumSize();
		Dimension prefSize = super.getPreferredSize();
		return new Dimension(maxSize.width, prefSize.height);
	}
	
	private void initialise() {
		//setBorder(BorderFactory.createTitledBorder("External interfaces"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(getJsonPanel());
		add(getProtoPanel());
		add(getBoblightPanel());
		
		toggleEnabledFlags();
	}
	
	private JPanel getJsonPanel() {
		if (mJsonPanel == null) {
			mJsonPanel = new JPanel();
			mJsonPanel.setBorder(BorderFactory.createTitledBorder("Json server"));
			
			mJsonCheck = new JCheckBox("Enabled");
			mJsonCheck.setSelected(mJsonServerModel.enabled.getValue());
			mJsonCheck.addActionListener(mActionListener);
			mJsonPanel.add(mJsonCheck);
			
			mJsonPortLabel = new JLabel("TCP Port: ");
			mJsonPanel.add(mJsonPortLabel);
			
			mJsonPortSpinner = new JSpinner(new SpinnerNumberModel(mJsonServerModel.tcpPort.getValue(), 1, 65536, 1));
			mJsonPortSpinner.addChangeListener(mChangeListener);
			mJsonPanel.add(mJsonPortSpinner);
			
			GroupLayout layout = new GroupLayout(mJsonPanel);
			layout.setAutoCreateGaps(true);
			mJsonPanel.setLayout(layout);
			
			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(mJsonCheck)
							.addComponent(mJsonPortLabel)
							)
					.addGroup(layout.createParallelGroup()
							.addComponent(mJsonCheck)
							.addComponent(mJsonPortSpinner)
							));
			layout.setVerticalGroup(layout.createSequentialGroup()
					.addComponent(mJsonCheck)
					.addGroup(layout.createParallelGroup()
							.addComponent(mJsonPortLabel)
							.addComponent(mJsonPortSpinner)
							));
		}
		return mJsonPanel;
	}
	private JPanel getProtoPanel() {
		if (mProtoPanel == null) {
			mProtoPanel = new JPanel();
			mProtoPanel.setBorder(BorderFactory.createTitledBorder("Proto server"));
			
			mProtoCheck = new JCheckBox("Enabled");
			mProtoCheck.setSelected(mProtoServerModel.enabled.getValue());
			mProtoCheck.addActionListener(mActionListener);
			mProtoPanel.add(mProtoCheck);
			
			mProtoPortLabel = new JLabel("TCP Port: ");
			mProtoPanel.add(mProtoPortLabel);
			
			mProtoPortSpinner = new JSpinner(new SpinnerNumberModel(mProtoServerModel.tcpPort.getValue(), 1, 65536, 1));
			mProtoPortSpinner.addChangeListener(mChangeListener);
			mProtoPanel.add(mProtoPortSpinner);
			
			GroupLayout layout = new GroupLayout(mProtoPanel);
			layout.setAutoCreateGaps(true);
			mProtoPanel.setLayout(layout);
			
			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(mProtoCheck)
							.addComponent(mProtoPortLabel)
							)
					.addGroup(layout.createParallelGroup()
							.addComponent(mProtoCheck)
							.addComponent(mProtoPortSpinner)
							));
			layout.setVerticalGroup(layout.createSequentialGroup()
					.addComponent(mProtoCheck)
					.addGroup(layout.createParallelGroup()
							.addComponent(mProtoPortLabel)
							.addComponent(mProtoPortSpinner)
							));
		}
		return mProtoPanel;
	}
	
	private JPanel getBoblightPanel() {
		if (mBoblightPanel == null) {
			mBoblightPanel = new JPanel();
			mBoblightPanel.setBorder(BorderFactory.createTitledBorder("Boblight server"));
			
			mBoblightCheck = new JCheckBox("Enabled");
			mBoblightCheck.setSelected(mBoblightServerModel.enabled.getValue());
			mBoblightCheck.addActionListener(mActionListener);
			mBoblightPanel.add(mBoblightCheck);
			
			mBoblightPortLabel = new JLabel("TCP Port: ");
			mBoblightPanel.add(mBoblightPortLabel);
			
			mBoblightPortSpinner = new JSpinner(new SpinnerNumberModel(mBoblightServerModel.tcpPort.getValue(), 1, 65536, 1));
			mBoblightPortSpinner.addChangeListener(mChangeListener);
			mBoblightPanel.add(mBoblightPortSpinner);
			
			GroupLayout layout = new GroupLayout(mBoblightPanel);
			layout.setAutoCreateGaps(true);
			mBoblightPanel.setLayout(layout);
			
			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(mBoblightCheck)
							.addComponent(mBoblightPortLabel)
							)
					.addGroup(layout.createParallelGroup()
							.addComponent(mBoblightCheck)
							.addComponent(mBoblightPortSpinner)
							));
			layout.setVerticalGroup(layout.createSequentialGroup()
					.addComponent(mBoblightCheck)
					.addGroup(layout.createParallelGroup()
							.addComponent(mBoblightPortLabel)
							.addComponent(mBoblightPortSpinner)
							));
		}
		return mBoblightPanel;
	}
	
	private  void toggleEnabledFlags() {
		mJsonPortLabel.setEnabled(mJsonServerModel.enabled.getValue());
		mJsonPortSpinner.setEnabled(mJsonServerModel.enabled.getValue());
		
		mProtoPortLabel.setEnabled(mProtoServerModel.enabled.getValue());
		mProtoPortSpinner.setEnabled(mProtoServerModel.enabled.getValue());
		
		mBoblightPortLabel.setEnabled(mBoblightServerModel.enabled.getValue());
		mBoblightPortSpinner.setEnabled(mBoblightServerModel.enabled.getValue());
	}
	
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			mJsonServerModel.enabled.setValue(mJsonCheck.isSelected());
			mProtoServerModel.enabled.setValue(mProtoCheck.isSelected());
			mBoblightServerModel.enabled.setValue(mBoblightCheck.isSelected());
			
			toggleEnabledFlags();
		}
	};
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mJsonServerModel.tcpPort.setValue((Integer)mJsonPortSpinner.getValue());
			mProtoServerModel.tcpPort.setValue((Integer)mJsonPortSpinner.getValue());
			mBoblightServerModel.tcpPort.setValue((Integer)mJsonPortSpinner.getValue());
		}
	};
}
