package org.hyperion.hypercon.spec;

import org.mufassa.model.AbstractModel;
import org.mufassa.model.ParameterBool;
import org.mufassa.model.ParameterInt;

public class TcpServerModel extends AbstractModel {
	
	public TcpServerModel(final boolean pEnabled, final int pTcpPort) {
		super();
		
		enabled.setValue(pEnabled);
		tcpPort.setValue(pTcpPort);
		
		initialize();
	}
	
	/** Flag indicating that the JSON interface is enabled */
	public final ParameterBool enabled = new ParameterBool("enabled", true);
	
	/** The TCP port at which the JSON server is listening for incoming connections */
	public final ParameterInt tcpPort = new ParameterInt("port", 19444, 1, 65535);
}
