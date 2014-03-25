package org.hyperion.hypercon.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import org.hyperion.hypercon.spec.BootsequenceModel;
import org.hyperion.hypercon.spec.EffectEngineModel;

/**
 * THe EffectEnginePanel contains the components for configuring the parameters of the Effect Engine
 */
public class EffectEnginePanel extends JPanel {

	/** The MISC config contains the effect engine settings */
	private final EffectEngineModel mEffectEngineModel;
	private final BootsequenceModel mBootsequenceModel;
	
	private JLabel mPathLabel;
	private JTextField mPathField;
	
	private JPanel mBootSequencePanel;
	private JCheckBox mBootSequenceCheck;
	private JLabel mBootSequenceLabel;
	private JTextField mBootSequenceField;
	private JLabel mBootSequenceLengthLabel;
	private JSpinner mBootSequenceLengthSpinner;

	public EffectEnginePanel(final EffectEngineModel pMiscConfig, final BootsequenceModel pBootSequenceModel) {
		super();
		
		mEffectEngineModel = pMiscConfig;
		mBootsequenceModel = pBootSequenceModel;
		
		initialise();
	}
	
	private void initialise() {
		setBorder(BorderFactory.createTitledBorder("Effect Engine"));
		
		mPathLabel = new JLabel("Directory: ");
		mPathLabel.setMinimumSize(new Dimension(80, 10));
		add(mPathLabel);
		
		mPathField = new JTextField();
		mPathField.setMaximumSize(new Dimension(1024, 20));
		mPathField.setText(mEffectEngineModel.mEffectEnginePath.getValue());
		mPathField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mEffectEngineModel.mEffectEnginePath.setValue(mPathField.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				mEffectEngineModel.mEffectEnginePath.setValue(mPathField.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				mEffectEngineModel.mEffectEnginePath.setValue(mPathField.getText());
			}
		});
		add(mPathField);
		
		add(getBootSequencePanel());
		
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(mPathLabel)
						.addComponent(mPathField))
				.addComponent(getBootSequencePanel()));
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(mPathLabel)
						.addComponent(mPathField))
				.addComponent(getBootSequencePanel()));
	}
	
	private JPanel getBootSequencePanel() {
		if (mBootSequencePanel == null) {
			mBootSequencePanel = new JPanel();
			mBootSequencePanel.setBorder(BorderFactory.createTitledBorder("Bootsequence"));
			
			mBootSequenceCheck = new JCheckBox("Enabled");
			mBootSequenceCheck.setSelected(mBootsequenceModel.enabled.getValue());
			mBootSequenceCheck.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mBootsequenceModel.enabled.setValue(mBootSequenceCheck.isSelected());
					mBootSequenceLabel.setEnabled(mBootsequenceModel.enabled.getValue());
					mBootSequenceField.setEnabled(mBootsequenceModel.enabled.getValue());
				}
			});
			mBootSequencePanel.add(mBootSequenceCheck);
			
			mBootSequenceLabel = new JLabel("Type:");
			mBootSequenceLabel.setMinimumSize(new Dimension(75, 10));
			mBootSequenceLabel.setEnabled(mBootsequenceModel.enabled.getValue());
			mBootSequencePanel.add(mBootSequenceLabel);
			
			mBootSequenceField = new JTextField();
			mBootSequenceField.setMaximumSize(new Dimension(1024, 20));
			mBootSequenceField.setText(mBootsequenceModel.effect.getValue());
			mBootSequenceField.setEnabled(mBootsequenceModel.enabled.getValue());
			mBootSequenceField.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void removeUpdate(DocumentEvent e) {
					mBootsequenceModel.effect.setValue(mBootSequenceField.getText());
				}
				@Override
				public void insertUpdate(DocumentEvent e) {
					mBootsequenceModel.effect.setValue(mBootSequenceField.getText());
				}
				@Override
				public void changedUpdate(DocumentEvent e) {
					mBootsequenceModel.effect.setValue(mBootSequenceField.getText());
				}
			});
			mBootSequencePanel.add(mBootSequenceField);
			
			mBootSequenceLengthLabel = new JLabel("Length[ms]: ");
			mBootSequenceLengthLabel.setMinimumSize(new Dimension(75, 10));
			mBootSequenceLengthLabel.setEnabled(mBootsequenceModel.enabled.getValue());
			mBootSequencePanel.add(mBootSequenceLengthLabel);
		
			mBootSequenceLengthSpinner = new JSpinner(new SpinnerNumberModel(mBootsequenceModel.duration_ms.getValue(), 100, 1500000, 500));
			mBootSequenceLengthSpinner.setMaximumSize(new Dimension(1024, 20));
			mBootSequenceLengthSpinner.setEnabled(mBootsequenceModel.enabled.getValue());
			mBootSequenceLengthSpinner.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					mBootsequenceModel.duration_ms.setValue((Integer)mBootSequenceLengthSpinner.getValue());
				}
			});
			mBootSequencePanel.add(mBootSequenceLengthSpinner);
	
			GroupLayout layout = new GroupLayout(mBootSequencePanel);
			mBootSequencePanel.setLayout(layout);
			
			layout.setVerticalGroup(layout.createSequentialGroup()
					.addComponent(mBootSequenceCheck)
					.addGroup(layout.createParallelGroup()
							.addComponent(mBootSequenceLabel)
							.addComponent(mBootSequenceField))
					.addGroup(layout.createParallelGroup()
							.addComponent(mBootSequenceLengthLabel)
							.addComponent(mBootSequenceLengthSpinner))
					);
			
			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
							.addComponent(mBootSequenceCheck)
							.addComponent(mBootSequenceLabel)
							.addComponent(mBootSequenceLengthLabel))
					.addGroup(layout.createParallelGroup()
							.addComponent(mBootSequenceCheck)
							.addComponent(mBootSequenceField)
							.addComponent(mBootSequenceLengthSpinner)));
		}
		return mBootSequencePanel;
	}
}
