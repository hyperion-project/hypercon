package org.hyperion.hypercon.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.hyperion.gui.ModelPanel;
import org.hyperion.model.AbstractModel;
import org.hyperion.model.IModelObserver;
import org.hyperion.model.ModelEvent;
import org.hyperion.model.ModelList;
import org.hyperion.model.ParameterString;

public class ModelListEditor extends JPanel {

	private final ModelList<AbstractModel> mModelList;
	private final Class<?> mElementClass;
	
	private final Field mIdField;
	
	private JComboBox<String> mComboBox;
	private final ComboBoxModel<String> mComboModel = new DefaultComboBoxModel<String>() {
		
		@Override
		public int getSize() {
			return mModelList.size();
		}
		
		@Override
		public String getElementAt(int index) {
			Object element = mModelList.get(index);
			try {
				ParameterString idParameter = (ParameterString) mIdField.get(element);
				return idParameter.getValue();
			} catch (Throwable t) {
				t.printStackTrace();
			}
			return null;
		}
	};
	
	private final Action mAddAction = new AbstractAction("Add") {
		@Override
		public void actionPerformed(ActionEvent e) {
			String newName = JOptionPane.showInputDialog("Name for the new " + "[TODO]: ");
			if (newName == null || newName.length() == 0) {
				// No name or id set
				return;
			}
			try {
				AbstractModel newElement = (AbstractModel) mElementClass.newInstance();
				ParameterString idPar = (ParameterString) mIdField.get(newElement);
				idPar.setValue(newName);
				newElement.commitEvents();
				
				mModelList.add(newElement);
				mModelList.commitEvents();

				mComboModel.setSelectedItem(newName);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	};
	
	private final Action mDeleteAction = new AbstractAction("Del") {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (mModelList.isEmpty()) {
				setEnabled(false);
				return;
			}
			int selIndex = mComboBox.getSelectedIndex();
			if (selIndex < 0) {
				// No active selection
				return;
			}
			mModelList.remove(selIndex);
			mComboBox.setSelectedIndex(Math.max(0, selIndex-1));
		}
	};
	
	public ModelListEditor(final ModelList<AbstractModel> pModelList, Class<?> pElementClass) {
		super();
		
		mModelList    = pModelList;
        mElementClass = pElementClass;
        mIdField      = getIdField();
		
		initialise();
		
		mModelList.addObserver(new IModelObserver() {
			@Override
			public void modelUpdate(Set<ModelEvent> pEvents) {
				//mComboModel.
			}
		}, ModelList.LIST_CHANGED);
		
		if (!mModelList.isEmpty()) {
			try {
				ParameterString idParameter = (ParameterString) mIdField.get(mModelList.get(0));
				mComboModel.setSelectedItem(idParameter.getValue());
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
	
	private Field getIdField() {
		for (Field field : mElementClass.getFields()) {
			String name = field.getName();
			if (name.charAt(0) == 'm' && name.charAt(1) >= 'A' && name.charAt(1) <= 'Z') {
				name = name.substring(1);
			}
			System.out.println("Field: " + field.getName());
			if (name.equalsIgnoreCase("id") || name.equalsIgnoreCase("name")) {
				return field;
			}
		}
		System.out.println("Unable to find the required id-field for " + mElementClass.getName());
		return null;
	}
	
	private void initialise() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(getSelectionPanel(), BorderLayout.NORTH);
		updateItemPanel();
	}
	private JPanel mSelectionPanel = null;
	private JPanel getSelectionPanel() {
		if (mSelectionPanel == null) {
			mSelectionPanel = new JPanel();
			mSelectionPanel.setLayout(new BorderLayout());
			
			mComboBox = new JComboBox<String>(mComboModel);
			if (!mModelList.isEmpty()) {
				mComboBox.setSelectedIndex(0);
			}
			mComboBox.setPreferredSize(new Dimension(150, 25));
			mComboBox.setMinimumSize(new Dimension(100, 25));
			mSelectionPanel.add(mComboBox, BorderLayout.CENTER);
			mComboBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					updateItemPanel();
				}
			});
			
			JPanel controlPanel = new JPanel(new GridLayout(1, 2));
			controlPanel.add(new JButton(mAddAction), BorderLayout.EAST);
			controlPanel.add(new JButton(mDeleteAction), BorderLayout.WEST);
			mSelectionPanel.add(controlPanel, BorderLayout.EAST);
		}
		return mSelectionPanel;
	}
	
	private int mCurrentIdx = -1;
	private JComponent mCurrentItemPanel = null;
	private void updateItemPanel() {
		int selIndx = mComboBox.getSelectedIndex();
		if (selIndx == mCurrentIdx) {
			return;
		}
		mCurrentIdx = selIndx;
		if (mCurrentItemPanel != null) {
			remove(mCurrentItemPanel);
		}
		if (selIndx < 0 || mModelList.size() <= selIndx) {
			return;
		}

		AbstractModel itemModel = mModelList.get(selIndx);
		mCurrentItemPanel = new ModelPanel(itemModel, new Dimension(150, 20));
		add(mCurrentItemPanel, BorderLayout.CENTER);
		
		revalidate();
	}
}
