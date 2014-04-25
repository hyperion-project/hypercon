package org.hyperion.hypercon.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.hyperion.hypercon.spec.ColorConfigModel;
import org.hyperion.hypercon.spec.TransformConfigModel;

public class ColorsPanel extends JPanel {

	private final ColorConfigModel mColorConfig;
	private final DefaultComboBoxModel<TransformConfigModel> mTransformsModel;
	
	private JPanel mControlPanel;
	private JComboBox<TransformConfigModel> mTransformCombo;
	private JButton mAddTransformButton;
	private JButton mDelTransformButton;
	
	private JPanel mTransformPanel;
	
	private final Map<TransformConfigModel, ColorTransformPanel> mTransformPanels = new HashMap<TransformConfigModel, ColorTransformPanel>();
	
	
	public ColorsPanel(ColorConfigModel pColorConfig) {
		super();
		
		mColorConfig = pColorConfig;
		mTransformsModel = new DefaultComboBoxModel<TransformConfigModel>();
		for (TransformConfigModel tcm : mColorConfig.transform) {
			mTransformsModel.addElement(tcm);
		}
		
		initialise();
	}
	
	private void initialise() {
		setLayout(new BorderLayout(10,10));
		setBorder(BorderFactory.createTitledBorder("Colors"));
		
		add(getControlPanel(), BorderLayout.NORTH);
		
		mTransformPanel = new JPanel();
		mTransformPanel.setLayout(new BorderLayout());
		add(mTransformPanel, BorderLayout.CENTER);
		
		for (TransformConfigModel config : mColorConfig.transform) {
			mTransformPanels.put(config, new ColorTransformPanel(config));
		}
		ColorTransformPanel currentPanel = mTransformPanels.get(mColorConfig.transform.get(0));
		mTransformPanel.add(currentPanel, BorderLayout.CENTER);
	}
	
	private JPanel getControlPanel() {
		if (mControlPanel == null) {
			mControlPanel = new JPanel();
			mControlPanel.setLayout(new BoxLayout(mControlPanel, BoxLayout.LINE_AXIS));
			
			mTransformCombo = new JComboBox<TransformConfigModel>(mTransformsModel);
			mTransformCombo.addActionListener(mComboListener);
			mControlPanel.add(mTransformCombo);
			
			mAddTransformButton = new JButton(mAddAction);
			mControlPanel.add(mAddTransformButton);
			
			mDelTransformButton = new JButton(mDelAction);
			mDelTransformButton.setEnabled(mTransformCombo.getItemCount() > 1);
			mControlPanel.add(mDelTransformButton);
		}
		return mControlPanel;
	}
	
	private final Action mAddAction = new AbstractAction("Add") {
		@Override
		public void actionPerformed(ActionEvent e) {
			String newId = JOptionPane.showInputDialog("Give an identifier for the new color-transform:");
			if (newId == null || newId.isEmpty()) {
				// No proper value given
				return;
			}
			
			TransformConfigModel config = new TransformConfigModel();
			config.id.setValue(newId);
			
			ColorTransformPanel panel = new ColorTransformPanel(config);
			mTransformPanels.put(config, panel);
			
			mTransformsModel.addElement(config);
			mTransformsModel.setSelectedItem(config);
			
			mDelTransformButton.setEnabled(true);
		}
	};
	private final Action mDelAction = new AbstractAction("Del") {
		@Override
		public void actionPerformed(ActionEvent e) {
			TransformConfigModel config = (TransformConfigModel) mTransformCombo.getSelectedItem();
			mTransformPanels.remove(config);
			mTransformsModel.removeElement(config);
			
			mDelTransformButton.setEnabled(mTransformCombo.getItemCount() > 1);
		}
	};
	
	private final ActionListener mComboListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			TransformConfigModel selConfig = (TransformConfigModel) mTransformsModel.getSelectedItem();
			if (selConfig == null) {
				// Something went wrong here, there should always be a selection!
				return;
			}
			
			ColorTransformPanel panel = mTransformPanels.get(selConfig);
			mTransformPanel.removeAll();
			mTransformPanel.add(panel, BorderLayout.CENTER);
			mTransformPanel.revalidate();
			mTransformPanel.repaint();
		}
	};
}
