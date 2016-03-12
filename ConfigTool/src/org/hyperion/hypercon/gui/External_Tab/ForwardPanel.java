package org.hyperion.hypercon.gui.External_Tab;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.GroupLayout.Alignment;

import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.MiscConfig;


public class ForwardPanel extends JPanel {

	private final MiscConfig mMiscConfig;
	
	private JCheckBox mforwardenableCheck;
	
	private JLabel mProtoLabel;
	private JTextField mProtoField;
	
	private JLabel mJsonLabel;
	private JTextField mJsonField;
	
	
	public ForwardPanel(final MiscConfig pMiscConfig) {
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
		setBorder(BorderFactory.createTitledBorder(language.getString("external.forward.title"))); //$NON-NLS-1$
		
		mforwardenableCheck = new JCheckBox(language.getString("general.phrase.enabled")); //$NON-NLS-1$
		mforwardenableCheck.setSelected(mMiscConfig.mforwardEnabled);
		mforwardenableCheck.setToolTipText(language.getString("external.forward.enabledtooltip"));
		mforwardenableCheck.addActionListener(mActionListener);
		add(mforwardenableCheck);
		
		mProtoLabel = new JLabel(language.getString("external.forward.protolabel")); //$NON-NLS-1$
		add(mProtoLabel);
		
		mProtoField = new JTextField(mMiscConfig.mProtofield);
		mProtoField.setToolTipText(language.getString("external.forward.protofieldtooltip")); //$NON-NLS-1$
		mProtoField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mMiscConfig.mProtofield = mProtoField.getText();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				mMiscConfig.mProtofield = mProtoField.getText();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				mMiscConfig.mProtofield = mProtoField.getText();
			}
		});
		add(mProtoField);
	
		
		mJsonLabel = new JLabel(language.getString("external.forward.jsonlabel")); //$NON-NLS-1$
		add(mJsonLabel);
	
		mJsonField = new JTextField(mMiscConfig.mJsonfield);
		mJsonField.setToolTipText(language.getString("external.forward.jsonfieldtooltip")); //$NON-NLS-1$
		mJsonField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mMiscConfig.mJsonfield = mJsonField.getText();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				mMiscConfig.mJsonfield = mJsonField.getText();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				mMiscConfig.mJsonfield = mJsonField.getText();
			}
		});
		add(mJsonField);
	
	
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout
				.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(mforwardenableCheck)
							.addComponent(mProtoLabel)
							.addComponent(mJsonLabel)
						)
					.addGroup(layout.createParallelGroup()
//							.addComponent(mforwardenableCheck)
							.addComponent(mProtoField)
							.addComponent(mJsonField)
						)
						);
		layout.setVerticalGroup(layout
				.createSequentialGroup()
					.addComponent(mforwardenableCheck)
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(mProtoLabel)
								.addComponent(mProtoField)
								)
						.addGroup(layout.createParallelGroup(Alignment.CENTER)		
								.addComponent(mJsonLabel)				
								.addComponent(mJsonField)
								)

							);
		toggleEnabled(mMiscConfig.mforwardEnabled);
	}
	
	private void toggleEnabled(boolean pEnabled) {
		mProtoLabel.setEnabled(pEnabled);
		mJsonLabel.setEnabled(pEnabled);
		
		mProtoField.setEnabled(pEnabled);
		mJsonField.setEnabled(pEnabled);
	}
	
	private final ActionListener mActionListener = new ActionListener() {		
	@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(mforwardenableCheck)) {
				mMiscConfig.mforwardEnabled = mforwardenableCheck
				.isSelected();
				toggleEnabled(mMiscConfig.mforwardEnabled);
			}

			}
	};
	
	
	
}	
