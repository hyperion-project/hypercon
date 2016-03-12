package org.hyperion.hypercon.gui.External_Tab;

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
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.MiscConfig;

public class InterfacePanel extends JPanel {

	public final MiscConfig mMiscConfig;
	
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
	private JLabel mBoblightPriorityLabel;
	private JSpinner mBoblightPrioritySpinner;
	
	public InterfacePanel(final MiscConfig pMiscConfig) {
		super();
		
		mMiscConfig = pMiscConfig;
		
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
		setBorder(BorderFactory.createTitledBorder(language.getString("external.server.jsonprotoboblighttitel")));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(getJsonPanel());
		add(getProtoPanel());
		add(getBoblightPanel());
		
		toggleEnabledFlags();
	}
	
	private JPanel getJsonPanel() {
		if (mJsonPanel == null) {
			mJsonPanel = new JPanel();
			
			mJsonCheck = new JCheckBox(language.getString("external.server.jsonenabled")); //$NON-NLS-1$
			mJsonCheck.setSelected(mMiscConfig.mJsonInterfaceEnabled);
			mJsonCheck.addActionListener(mActionListener);
			mJsonPanel.add(mJsonCheck);
			
			mJsonPortLabel = new JLabel(language.getString("external.server.jsonportlabel")); //$NON-NLS-1$
			mJsonPanel.add(mJsonPortLabel);
			
			mJsonPortSpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mJsonPort, 1, 65536, 1));
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
					.addGroup(layout.createParallelGroup(Alignment.CENTER)
							.addComponent(mJsonPortLabel)
							.addComponent(mJsonPortSpinner)
							));
		}
		return mJsonPanel;
	}
	private JPanel getProtoPanel() {
		if (mProtoPanel == null) {
			mProtoPanel = new JPanel();
			
			mProtoCheck = new JCheckBox(language.getString("external.server.protoenabled")); //$NON-NLS-1$
			mProtoCheck.setSelected(mMiscConfig.mProtoInterfaceEnabled);
			mProtoCheck.addActionListener(mActionListener);
			mProtoPanel.add(mProtoCheck);
			
			mProtoPortLabel = new JLabel(language.getString("external.server.protoportlabel")); //$NON-NLS-1$
			mProtoPanel.add(mProtoPortLabel);
			
			mProtoPortSpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mProtoPort, 1, 65536, 1));
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
					.addGroup(layout.createParallelGroup(Alignment.CENTER)
							.addComponent(mProtoPortLabel)
							.addComponent(mProtoPortSpinner)
							));
		}
		return mProtoPanel;
	}
	
	private JPanel getBoblightPanel() {
		if (mBoblightPanel == null) {
			mBoblightPanel = new JPanel();
			
			mBoblightCheck = new JCheckBox(language.getString("external.server.boblightenabled")); //$NON-NLS-1$
			mBoblightCheck.setSelected(mMiscConfig.mBoblightInterfaceEnabled);
			mBoblightCheck.addActionListener(mActionListener);
			mBoblightPanel.add(mBoblightCheck);
			
			mBoblightPortLabel = new JLabel(language.getString("external.server.boblightportlabel")); //$NON-NLS-1$
			mBoblightPanel.add(mBoblightPortLabel);
			
			mBoblightPortSpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mBoblightPort, 1, 65536, 1));
			mBoblightPortSpinner.addChangeListener(mChangeListener);
			mBoblightPanel.add(mBoblightPortSpinner);
	
			mBoblightPriorityLabel = new JLabel(language.getString("general.phrase.prioritylabel")); //$NON-NLS-1$
			mBoblightPanel.add(mBoblightPriorityLabel);
			
			mBoblightPrioritySpinner = new JSpinner(new SpinnerNumberModel(mMiscConfig.mBoblightPriority, 0, 5000, 1));
			mBoblightPrioritySpinner.addChangeListener(mChangeListener);
			mBoblightPrioritySpinner.setToolTipText(language.getString("general.phrase.prioritytooltip"));
			mBoblightPanel.add(mBoblightPrioritySpinner);
			
			GroupLayout layout = new GroupLayout(mBoblightPanel);
			layout.setAutoCreateGaps(true);
			mBoblightPanel.setLayout(layout);
			
			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(mBoblightCheck)
							.addComponent(mBoblightPortLabel)
							.addComponent(mBoblightPriorityLabel)
							)
					.addGroup(layout.createParallelGroup()
							.addComponent(mBoblightCheck)
							.addComponent(mBoblightPortSpinner)
							.addComponent(mBoblightPrioritySpinner)
							));
			layout.setVerticalGroup(layout.createSequentialGroup()
					.addComponent(mBoblightCheck)
					.addGroup(layout.createParallelGroup(Alignment.CENTER)
							.addComponent(mBoblightPortLabel)
							.addComponent(mBoblightPortSpinner)
							)
					.addGroup(layout.createParallelGroup(Alignment.CENTER)
							.addComponent(mBoblightPriorityLabel)
							.addComponent(mBoblightPrioritySpinner)
							));
		}
		return mBoblightPanel;
	}
	
	private  void toggleEnabledFlags() {
		mJsonPortLabel.setEnabled(mMiscConfig.mJsonInterfaceEnabled);
		mJsonPortSpinner.setEnabled(mMiscConfig.mJsonInterfaceEnabled);
		
		mProtoPortLabel.setEnabled(mMiscConfig.mProtoInterfaceEnabled);
		mProtoPortSpinner.setEnabled(mMiscConfig.mProtoInterfaceEnabled);
		
		mBoblightPortLabel.setEnabled(mMiscConfig.mBoblightInterfaceEnabled);
		mBoblightPortSpinner.setEnabled(mMiscConfig.mBoblightInterfaceEnabled);
		mBoblightPrioritySpinner.setEnabled(mMiscConfig.mBoblightInterfaceEnabled);
	}
	
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			mMiscConfig.mJsonInterfaceEnabled = mJsonCheck.isSelected();
			mMiscConfig.mProtoInterfaceEnabled = mProtoCheck.isSelected();
			mMiscConfig.mBoblightInterfaceEnabled = mBoblightCheck.isSelected();
			
			toggleEnabledFlags();
		}
	};
	private final ChangeListener mChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			mMiscConfig.mJsonPort = (Integer)mJsonPortSpinner.getValue();
			mMiscConfig.mProtoPort = (Integer)mProtoPortSpinner.getValue();
			mMiscConfig.mBoblightPort = (Integer)mBoblightPortSpinner.getValue();
			mMiscConfig.mBoblightPriority = (Integer)mBoblightPrioritySpinner.getValue();
		}
	};
}
