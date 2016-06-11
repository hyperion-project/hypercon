package org.hyperion.hypercon.gui.Hardware_Tab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.hyperion.hypercon.gui.Hardware_Tab.device.DeviceTypePanel;
import org.hyperion.hypercon.language.language;
import org.hyperion.hypercon.spec.ColorByteOrder;
import org.hyperion.hypercon.spec.DeviceConfig;
import org.hyperion.hypercon.spec.DeviceType;

public class DevicePanel extends JPanel {

	public static final String[] KnownOutputs = {"/dev/spidev0.0", "/dev/spidev0.1", "/dev/ttyS0", "/dev/ttyUSB0", "/dev/ttyprintk", "/home/pi/test.out", "/dev/null"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$

	private final DeviceConfig mDeviceConfig;
	
	private JLabel mNameLabel;
	private JTextField mNameField;
	
	private JLabel mTypeLabel;
	private JComboBox<DeviceType> mTypeCombo;

	private JPanel mDevicePanel;
	
	private JLabel mRgbLabel;
	private JComboBox<ColorByteOrder> mRgbCombo;
	
	public DevicePanel(DeviceConfig pDeviceConfig) {
		super();
		
		mDeviceConfig = pDeviceConfig;
		
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
		setBorder(BorderFactory.createTitledBorder(language.getString("hardware.device.title"))); //$NON-NLS-1$

		mNameLabel = new JLabel(language.getString("hardware.device.namelabel")); //$NON-NLS-1$
		mNameLabel.setMinimumSize(new Dimension(80, 10));
		add(mNameLabel);
		
		mNameField = new JTextField(mDeviceConfig.mNameField);
		mNameField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				mDeviceConfig.mNameField = mNameField.getText();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				mDeviceConfig.mNameField = mNameField.getText();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				mDeviceConfig.mNameField = mNameField.getText();
			}
		});
		add(mNameField);
		
		mTypeLabel = new JLabel(language.getString("hardware.device.typlabel")); //$NON-NLS-1$
		mTypeLabel.setMinimumSize(new Dimension(80, 10));
		add(mTypeLabel);
		
		mTypeCombo = new JComboBox<>(DeviceType.values());
		mTypeCombo.setSelectedItem(mDeviceConfig.mType);
		mTypeCombo.addActionListener(mActionListener);
		add(mTypeCombo);

		mDevicePanel = new JPanel();
		mDevicePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		mDevicePanel.setLayout(new BorderLayout());
		DeviceTypePanel typePanel = mDeviceConfig.mType.getConfigPanel(mDeviceConfig);
		if (typePanel != null) {
			mDevicePanel.add(typePanel, BorderLayout.CENTER);
		}
		add(mDevicePanel);
		
		mRgbLabel = new JLabel(language.getString("hardware.device.rgborderlabel")); //$NON-NLS-1$
		mRgbLabel.setMinimumSize(new Dimension(80, 10));
		add(mRgbLabel);
		
		mRgbCombo = new JComboBox<>(ColorByteOrder.values());
		mRgbCombo.setSelectedItem(mDeviceConfig.mColorByteOrder);
		mRgbCombo.addActionListener(mActionListener);
		add(mRgbCombo);
		
		GroupLayout layout = new GroupLayout(this);
		layout.setAutoCreateGaps(true);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(mNameLabel)
						.addComponent(mNameField))
				.addGroup(layout.createSequentialGroup()
						.addComponent(mTypeLabel)
						.addComponent(mTypeCombo))
						.addComponent(mDevicePanel)
				.addGroup(layout.createSequentialGroup()
						.addComponent(mRgbLabel)
						.addComponent(mRgbCombo)));
		layout.setVerticalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(mNameLabel)
						.addComponent(mTypeLabel)
						.addComponent(mDevicePanel)
						.addComponent(mRgbLabel))
				.addGroup(layout.createSequentialGroup()
						.addComponent(mNameField)
						.addComponent(mTypeCombo)
						.addComponent(mDevicePanel)
						.addComponent(mRgbCombo)));
	}
	
	private final ActionListener mActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			mDeviceConfig.mType = (DeviceType)mTypeCombo.getSelectedItem();
			mDeviceConfig.mColorByteOrder = (ColorByteOrder)mRgbCombo.getSelectedItem();

			mDevicePanel.removeAll();
			DeviceTypePanel typePanel = mDeviceConfig.mType.getConfigPanel(mDeviceConfig);
			if (typePanel != null) {
				mDevicePanel.add(typePanel, BorderLayout.CENTER);
			}
			revalidate();
			repaint();
		}
	};
}
