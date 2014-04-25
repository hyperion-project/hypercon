package org.hyperion.hypercon;

import java.awt.BorderLayout;
import java.lang.reflect.Field;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.hyperion.hypercon.spec.LedFrameConstructionModel;
import org.mufassa.model.AbstractModel;
import org.mufassa.model.Parameter;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterDouble;
import org.mufassa.model.ParameterInt;

public class ModelPanel extends JPanel {

	private final AbstractModel mModel;
	
	public ModelPanel(final AbstractModel pModel) {
		super();
		
		mModel = pModel;
		
		initialise();
	}
	
	private void initialise() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		for (Field modelField : mModel.getClass().getFields()) {
			Object fieldInstance;
			try {
				fieldInstance = modelField.get(mModel);
			} catch (Exception e) {
				continue;
			}
			
			if (fieldInstance instanceof Parameter) {
				System.out.println("Field instance: " + fieldInstance);
				add(getEditorComponent((Parameter)fieldInstance));
			} else if (fieldInstance instanceof AbstractModel) {
				add(new ModelPanel((AbstractModel)fieldInstance));
			}
		}
	}
	
	private JComponent getEditorComponent(Parameter pParameter) {
		JPanel parameterPanel = new JPanel();
		parameterPanel.setLayout(new BorderLayout());
		
		JLabel nameLabel = new JLabel(pParameter.getName());
		parameterPanel.add(nameLabel, BorderLayout.WEST);
		
		if (pParameter instanceof ParameterBool) {
			JCheckBox valueCheck = new JCheckBox();
			parameterPanel.add(valueCheck, BorderLayout.CENTER);
		} else if (pParameter instanceof ParameterInt) {
			ParameterInt parInt = (ParameterInt) pParameter;
			JSpinner valueSpinner = new JSpinner(new SpinnerNumberModel(parInt.getValue(), parInt.getMinimum(), parInt.getMaximum(), 1.0)); 
			parameterPanel.add(valueSpinner, BorderLayout.CENTER);
		} else if (pParameter instanceof ParameterDouble) {
			ParameterDouble parDouble = (ParameterDouble) pParameter;
			JSpinner valueSpinner = new JSpinner(new SpinnerNumberModel(parDouble.getValue(), parDouble.getMinimum(), parDouble.getMaximum(), 1.0)); 
			parameterPanel.add(valueSpinner, BorderLayout.CENTER);
		}
		
		return parameterPanel;
	}
	
	public static void main(String[] pArgs) {
		JFrame frame = new JFrame();
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		LedFrameConstructionModel model = new LedFrameConstructionModel();
		frame.setContentPane(new ModelPanel(model));
		
		frame.setVisible(true);
	}
}
