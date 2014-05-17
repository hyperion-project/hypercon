package org.mufassa.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.hyperion.control.DeviceController;
import org.hyperion.hypercon.LedStringModel;
import org.hyperion.hypercon.gui.ModelListEditor;
import org.mufassa.model.AbstractModel;
import org.mufassa.model.IModelObserver;
import org.mufassa.model.ModelEvent;
import org.mufassa.model.ModelList;
import org.mufassa.model.OptionalModel;
import org.mufassa.model.Parameter;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterDouble;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterObject;
import org.mufassa.model.ParameterString;
import org.mufassa.model.ParameterStringEnum;
import org.mufassa.model.Suggestions;
import org.mufassa.model.util.ModelUtil;

public class ModelPanel extends JPanel {

	/** The 'running' size of the first column (with labels) */
	private final Dimension mFirstCol;

	/** The model displayed/edited on this panel */
	private final AbstractModel mModel;
	
	private final IModelObserver mModelObserver = new IModelObserver() {
		@Override
		public void modelUpdate(Set<ModelEvent> pEvents) {
			for (ModelEvent event : pEvents) {
				System.out.println("[" + mModel.getClass() + "] event of " + event.getSource());
			}
		}
	};
	
	private List<Component> mComponents = new Vector<Component>();
	
	public ModelPanel(final AbstractModel pModel, Dimension pFirstColDim) {
		super();
		
		mFirstCol = pFirstColDim;
		
		mModel = pModel;
		
		if (mModel != null) {
			initialise();
			
			mModel.addObserver(mModelObserver, AbstractModel.MODEL_TREE_CHANGE_EVENT);
		}
	}
	
	private boolean mEnabled = true;
	
	@Override
	public boolean isEnabled() {
		return mEnabled;
	}
	
	@Override
	public void setEnabled(boolean pEnabled) {
		mEnabled = pEnabled;
		
		for (Component c : mComponents) {
			c.setEnabled(mEnabled);
		}
	}
	
	private void initialise() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// First find the 'Enabled' or 'enabled' field
		Parameter enabledPar = ModelUtil.findParameter(mModel, "enabled");
		if (enabledPar != null) {
			if (!(enabledPar instanceof ParameterBool)) {
				System.err.println("Field named 'Enabled' should always be of type 'ParameterBool'");
				return;
			}
			JComponent enabledComponent = getEnabledComponent((ParameterBool)enabledPar);
			add(enabledComponent);
		}
		
		for (final Field modelField : mModel.getClass().getFields()) {
			Object fieldInstance;
			try {
				fieldInstance = modelField.get(mModel);
			} catch (Exception e) {
				continue;
			}
			
			String[] suggestions = null;
			if (modelField.isAnnotationPresent(Suggestions.class)) {
				suggestions = modelField.getAnnotation(Suggestions.class).value();
			}
			
			if (fieldInstance instanceof Parameter) {
				Parameter parameter = (Parameter)fieldInstance;
				// Make sure to skip the enabled parameter (we already handled that one)
				if (parameter.getName().equalsIgnoreCase("enabled")) {
					continue;
				}
				add(getEditorComponent((Parameter)fieldInstance, suggestions));
			} else if (fieldInstance instanceof ModelList<?>) {
				@SuppressWarnings("unchecked")
				final ModelList<AbstractModel> list = (ModelList<AbstractModel>) fieldInstance;
		        ParameterizedType elementType = (ParameterizedType) modelField.getGenericType();
		        final Class<?> elementClass = (Class<?>) elementType.getActualTypeArguments()[0];

				ModelListEditor modelListEditor = new ModelListEditor(list, elementClass);
				modelListEditor.setBorder(BorderFactory.createTitledBorder(getPrettyName(modelField.getName() + "s")));
				add(modelListEditor);
			} else if (fieldInstance instanceof OptionalModel) {
				final JPanel optionPanel = new JPanel(new BorderLayout());
				optionPanel.setBorder(BorderFactory.createTitledBorder(getPrettyName(modelField.getName())));
				
				final OptionalModel<?> optionalModel = (OptionalModel<?>)fieldInstance;
				optionalModel.addObserver(new IModelObserver() {
					@Override
					public void modelUpdate(Set<ModelEvent> pEvents) {
						optionPanel.removeAll();
						
						ModelPanel panel = new ModelPanel(optionalModel.get(), mFirstCol);
						optionPanel.add(panel, BorderLayout.CENTER);
						optionPanel.revalidate();
					}
				}, OptionalModel.OPTION_SET);
				if (optionalModel.get() != null) {
					ModelPanel panel = new ModelPanel(optionalModel.get(), mFirstCol);
					optionPanel.add(panel, BorderLayout.CENTER);
				}
				
				add(optionPanel);
				
			} else if (fieldInstance instanceof AbstractModel) {
				ModelPanel panel = new ModelPanel((AbstractModel)fieldInstance, mFirstCol);
				panel.setBorder(BorderFactory.createTitledBorder(getPrettyName(modelField.getName())));
				add(panel);
			}
		}
		
		setEnabled(isEnabled());
	}
	
	public int getFirstColWidth() {
		return mFirstCol.width;
	}
	
	private JComponent getEnabledComponent(final ParameterBool pParameter) {
		JPanel panel = new JPanel(new BorderLayout());
		
		JCheckBox valueCheck = new JCheckBox("Enabled");
		mFirstCol.width = Math.max(mFirstCol.width, valueCheck.getPreferredSize().width);
		valueCheck.setPreferredSize(new Dimension(240, 20));
		valueCheck.setSelected(pParameter.getValue());
		panel.add(valueCheck, BorderLayout.WEST);
		
		setEnabled(pParameter.getValue());
		valueCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean enabled = ((JCheckBox)e.getSource()).isSelected();
				setEnabled(enabled);
				pParameter.setValue(enabled);
				
				mModel.commitEvents();
			}
		});
		
		return panel;
	}
	
	private char toUpperCase(char pChar) {
		if (pChar >= 'a' && pChar <= 'z') {
			pChar += ('A'-'a');
		}
		return pChar;
	}
	private boolean isUpperCase(char pChar) {
		return pChar >= 'A' && pChar <= 'Z';
	}
	
	private String getPrettyName(String pParamName) {
		StringBuilder strb = new StringBuilder();
		
		// Skip the first letter if it is the prepended member 'm'
		int i = 0;
		if (pParamName.charAt(0) == 'm' && isUpperCase(pParamName.charAt(1))) {
			++i;
		}
		strb.append(toUpperCase(pParamName.charAt(i++)));
		
		for (; i<pParamName.length(); ++i) {
			char c = pParamName.charAt(i);
			if (isUpperCase(c)) {
				// Next word
				strb.append(' ');
			}
			
			if (c == '_' && i > pParamName.length()-4) {
				// found unit at end of name
				strb.append(" [").append(pParamName.substring(i+1)).append(']');
				break;
			}
			
			strb.append(c);
		}
		
		return strb.toString();
	}
	
	private JComponent getEditorComponent(final Parameter pParameter, String[] pSuggestions) {
		JPanel parameterPanel = new JPanel();
		parameterPanel.setLayout(new BorderLayout());
		
		JLabel nameLabel = new JLabel(getPrettyName(pParameter.getName()));
		mFirstCol.width = Math.max(mFirstCol.width, nameLabel.getPreferredSize().width);
		nameLabel.setPreferredSize(mFirstCol);
		nameLabel.setMinimumSize(mFirstCol);
		parameterPanel.add(nameLabel, BorderLayout.WEST);
		mComponents.add(nameLabel);
		
		
		if (pParameter instanceof ParameterBool) {
			final ParameterBool parBool = (ParameterBool)pParameter;
			
			JCheckBox valueCheck = new JCheckBox();
			parameterPanel.add(valueCheck, BorderLayout.CENTER);
			mComponents.add(valueCheck);
			valueCheck.setSelected(parBool.getValue());
			
			// Add the listener to set and commit the user selected value
			valueCheck.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JCheckBox valueCheck = (JCheckBox) e.getSource();
					parBool.setValue(valueCheck.isSelected());
					mModel.commitEvents();
				}
			});
			
		} else if (pParameter instanceof ParameterInt) {
			final ParameterInt parInt = (ParameterInt) pParameter;
			JSpinner valueSpinner = new JSpinner(new SpinnerNumberModel(parInt.getValue(), parInt.getMinimum(), parInt.getMaximum(), 1)); 
			parameterPanel.add(valueSpinner, BorderLayout.CENTER);
			mComponents.add(valueSpinner);
			
			valueSpinner.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					JSpinner valueSpinner = (JSpinner) e.getSource();
					parInt.setValue((Integer) valueSpinner.getValue());
					mModel.commitEvents();
				}
			});
		} else if (pParameter instanceof ParameterDouble) {
			final ParameterDouble parDouble = (ParameterDouble) pParameter;
			JSpinner valueSpinner = new JSpinner(new SpinnerNumberModel(parDouble.getValue(), parDouble.getMinimum(), parDouble.getMaximum(), 0.1)); 
			parameterPanel.add(valueSpinner, BorderLayout.CENTER);
			mComponents.add(valueSpinner);
			
			valueSpinner.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					JSpinner valueSpinner = (JSpinner) e.getSource();
					parDouble.setValue((Double) valueSpinner.getValue());
					mModel.commitEvents();
				}
			});
		} else if (pParameter instanceof ParameterStringEnum) {
			final ParameterStringEnum parEnum = (ParameterStringEnum) pParameter;
			JComboBox<String> valueCombo = new JComboBox<String>(parEnum.values());
			valueCombo.setSelectedItem(parEnum.getValue());
			parameterPanel.add(valueCombo, BorderLayout.CENTER);
			mComponents.add(valueCombo);
			
			valueCombo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					@SuppressWarnings("unchecked")
					JComboBox<String> valueCombo = (JComboBox<String>) e.getSource();
					parEnum.setValue((String) valueCombo.getSelectedItem());
					mModel.commitEvents();
				}
			});
		} else if (pParameter instanceof ParameterString) {
			final ParameterString parString = (ParameterString)pParameter;
			if (pSuggestions == null) {
				JTextField valueField = new JTextField(parString.getValue());
				parameterPanel.add(valueField);
				mComponents.add(valueField);
				
				valueField.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void removeUpdate(DocumentEvent e) {
						update(e);
					}
					@Override
					public void insertUpdate(DocumentEvent e) {
						update(e);
					}
					@Override
					public void changedUpdate(DocumentEvent e) {
						update(e);
					}
					private void update(DocumentEvent e) {
						try {
							String valueStr = e.getDocument().getText(0, e.getDocument().getLength());
							parString.setValue(valueStr);
							mModel.commitEvents();
						} catch (Throwable t) {
							t.printStackTrace();
						}
					}
				});
			} else {
				JComboBox<String> valueCombo = new JComboBox<String>(pSuggestions);
				valueCombo.setEditable(true);
				valueCombo.setSelectedItem(parString.getValue());
				parameterPanel.add(valueCombo);
				mComponents.add(valueCombo);
				
				valueCombo.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						@SuppressWarnings("unchecked")
						JComboBox<String> valueCombo = (JComboBox<String>) e.getSource();
						parString.setValue((String) valueCombo.getSelectedItem());
						mModel.commitEvents();
					}
				});
			}
		} else if (pParameter instanceof ParameterObject) {
//			ParameterObject<?> parameter = (ParameterObject<?>) pParameter;
//			ParameterizedType fieldType = (ParameterizedType) parameter.getGenericType();
//			Type genericType = fieldType.getActualTypeArguments()[0];
			
		}

		return parameterPanel;
	}
	
	public static void main(String[] pArgs) {
		JFrame frame = new JFrame();
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		LedStringModel model = new LedStringModel();
		
		@SuppressWarnings("unused")
		DeviceController deviceController = new DeviceController(model);
		
		JScrollPane scroll = new JScrollPane(new ModelPanel(model, new Dimension(50,20)));
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		frame.setContentPane(scroll);
		
		frame.setVisible(true);
	}
}
