package org.mufassa.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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

import org.hyperion.hypercon.LedStringModel;
import org.mufassa.model.AbstractModel;
import org.mufassa.model.ModelList;
import org.mufassa.model.Parameter;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterDouble;
import org.mufassa.model.ParameterInt;
import org.mufassa.model.ParameterObject;
import org.mufassa.model.ParameterString;
import org.mufassa.model.ParameterStringEnum;
import org.mufassa.model.Suggestions;

public class ModelPanel extends JPanel {

	/** The 'running' size of the first column (with labels) */
	private final Dimension mFirstCol;

	/** The model displayed/edited on this panel */
	private final AbstractModel mModel;
	
	private List<Component> mComponents = new Vector<Component>();
	
	public ModelPanel(final AbstractModel pModel, Dimension pFirstColDim) {
		super();
		
		mFirstCol = pFirstColDim;
		
		mModel = pModel;
		
		initialise();
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
		Parameter enabledPar = findParameter(mModel, "enabled");
		if (enabledPar != null) {
			if (!(enabledPar instanceof ParameterBool)) {
				System.err.println("Field named 'Enabled' should always be of type 'ParameterBool'");
				return;
			}
			JComponent enabledComponent = getEnabledComponent((ParameterBool)enabledPar);
			add(enabledComponent);
		}
		
		for (Field modelField : mModel.getClass().getFields()) {
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
				ModelList<?> list = (ModelList<?>) fieldInstance;
				Vector<String> ids = new Vector<String>();
				
				for (Object element : list) {
					if (element instanceof Parameter) {
						// Is this a valid use of the list?
					} else if (element instanceof AbstractModel) {
						AbstractModel elemModel = (AbstractModel) element;
						Parameter idPar = findParameter(elemModel, "id");
						if (idPar == null) {
							idPar = findParameter(elemModel, "name");
						}
						if (idPar == null) {
							System.err.println("Missing required field 'id' or 'name' in list elemnt (" + modelField.getName() + ")");
							return;
						} else if (!(idPar instanceof ParameterString)) {
							System.err.println("'id' or 'name' parameter in list should be of type string");
							return;
						}
						ids.add(((ParameterString)idPar).getValue());
					}
				}
				
				JPanel panel = new JPanel(new BorderLayout());
				panel.setBorder(BorderFactory.createTitledBorder(getPrettyName(modelField.getName() + "s")));
				
				JPanel controlPanel = new JPanel();
				controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.LINE_AXIS));
				if (!ids.isEmpty()) {
					JComboBox<String> selCombo = new JComboBox<String>(ids);
					controlPanel.add(selCombo);
				}
				
				controlPanel.add(new JButton("Add"));
				controlPanel.add(new JButton("Del"));
				panel.add(controlPanel, BorderLayout.NORTH);

				if (!list.isEmpty()) {
					AbstractModel firstElem = (AbstractModel)list.get(0);

					ParameterString idPar = (ParameterString) findParameter(firstElem, "id");
					if (idPar == null) {
						idPar = (ParameterString) findParameter(firstElem, "name");
					}
					JPanel detailPanel = new ModelPanel(firstElem, mFirstCol);
					detailPanel.setBorder(BorderFactory.createTitledBorder(modelField.getName() + "[" + idPar.getValue() + "]"));
					panel.add(detailPanel, BorderLayout.CENTER);
				}
				
				add(panel);
			} else if (fieldInstance instanceof AbstractModel) {
				ModelPanel panel = new ModelPanel((AbstractModel)fieldInstance, mFirstCol);
				panel.setBorder(BorderFactory.createTitledBorder(getPrettyName(modelField.getName())));
				add(panel);
			}
		}
		
		setEnabled(isEnabled());
	}
	
	private Parameter findParameter(AbstractModel pModel, String pParName) {
		for (Field modelField : pModel.getClass().getFields()) {
			Object fieldInstance;
			try {
				fieldInstance = modelField.get(pModel);
			} catch (Exception e) {
				continue;
			}
			
			if (!(fieldInstance instanceof Parameter)) {
				continue;
			}
			String paramName = ((Parameter) fieldInstance).getName();
			if (paramName.equalsIgnoreCase(pParName)) {
				return (Parameter) fieldInstance;
			}
		}
		return null;
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
	
	private JComponent getEditorComponent(Parameter pParameter, String[] pSuggestions) {
		JPanel parameterPanel = new JPanel();
		parameterPanel.setLayout(new BorderLayout());
		
		JLabel nameLabel = new JLabel(getPrettyName(pParameter.getName()));
		mFirstCol.width = Math.max(mFirstCol.width, nameLabel.getPreferredSize().width);
		nameLabel.setPreferredSize(mFirstCol);
		parameterPanel.add(nameLabel, BorderLayout.WEST);
		mComponents.add(nameLabel);
		
		
		if (pParameter instanceof ParameterBool) {
			ParameterBool parBool = (ParameterBool)pParameter;
			
			JCheckBox valueCheck = new JCheckBox();
			parameterPanel.add(valueCheck, BorderLayout.CENTER);
			mComponents.add(valueCheck);
			valueCheck.setSelected(parBool.getValue());
			
			if (parBool.getName().equalsIgnoreCase("enabled")) {
				setEnabled(parBool.getValue());
				valueCheck.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setEnabled(((JCheckBox)e.getSource()).isSelected());
					}
				});
			}
		} else if (pParameter instanceof ParameterInt) {
			ParameterInt parInt = (ParameterInt) pParameter;
			JSpinner valueSpinner = new JSpinner(new SpinnerNumberModel(parInt.getValue(), parInt.getMinimum(), parInt.getMaximum(), 1.0)); 
			parameterPanel.add(valueSpinner, BorderLayout.CENTER);
			mComponents.add(valueSpinner);
		} else if (pParameter instanceof ParameterDouble) {
			ParameterDouble parDouble = (ParameterDouble) pParameter;
			JSpinner valueSpinner = new JSpinner(new SpinnerNumberModel(parDouble.getValue(), parDouble.getMinimum(), parDouble.getMaximum(), 1.0)); 
			parameterPanel.add(valueSpinner, BorderLayout.CENTER);
			mComponents.add(valueSpinner);
		} else if (pParameter instanceof ParameterStringEnum) {
			ParameterStringEnum parEnum = (ParameterStringEnum) pParameter;
			JComboBox<String> valueCombo = new JComboBox<String>(parEnum.values());
			valueCombo.setSelectedItem(parEnum.getValue());
			parameterPanel.add(valueCombo, BorderLayout.CENTER);
			mComponents.add(valueCombo);
		} else if (pParameter instanceof ParameterString) {
			ParameterString parString = (ParameterString)pParameter;
			if (pSuggestions == null) {
				JTextField valueField = new JTextField(parString.getValue());
				parameterPanel.add(valueField);
				mComponents.add(valueField);
			} else {
				JComboBox<String> valueCombo = new JComboBox<String>(pSuggestions);
				valueCombo.setEditable(true);
				valueCombo.setSelectedItem(parString.getValue());
				parameterPanel.add(valueCombo);
				mComponents.add(valueCombo);
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
		JScrollPane scroll = new JScrollPane(new ModelPanel(model, new Dimension(50,20)));
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		frame.setContentPane(scroll);
		
		frame.setVisible(true);
	}
}
