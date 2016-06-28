package com.meshtasks.components;

import java.nio.channels.SocketChannel;

import com.meshtasks.config.AppConfiguration;
import com.meshtasks.constants.AppConstants;
import com.meshtasks.metadata.beans.MessageBean;
import com.meshtasks.metadata.beans.NetworkNodeBean;
import com.meshtasks.network.listeners.NetworkMessageListener;
import com.meshtasks.network.listeners.TransportListener;
import com.meshtasks.network.listeners.impl.SocketTransportListener;
import com.meshtasks.utils.CommonUtils;
import com.meshtasks.utils.JsonUtils;

public class SocketNetworkComponent implements NetworkMessageListener {

	private AppConfiguration configuration = AppConfiguration.getInstance();
	private final TransportListener listener;
	private WorkerNodeComponent workerNodeComponent = null;
	
	public SocketNetworkComponent(WorkerNodeComponent component) {
		workerNodeComponent = component;
		listener = new SocketTransportListener();
		int availablePort = CommonUtils.getSocketPort(
				Integer.parseInt(configuration.getProperty("network.socket.port")));
		listener.init("localhost", availablePort);
		listener.setMessageListener(this);
		listener.startListener();
		configuration.setSocketPort(availablePort);
	}
	
	@Override
	public void messageReceived(String data, SocketChannel channel) {
		if ( CommonUtils.isEmpty(data) ) return;
		MessageBean message = JsonUtils.createObjectFromJsonData(data, MessageBean.class);
		if ( message.getType().equals(AppConstants.FIND_MASTER_RES) ) {
			System.out.println("FIND_MASTER_RES Received");
			NetworkNodeBean nodeBean = JsonUtils.createObjectFromTree(message.getData(),
					NetworkNodeBean.class);
			System.out.println("Socket message received. Master "+nodeBean.getIpAddress()+" : "+nodeBean.getPort()+" Is Master? "+nodeBean.isMaster());
			configuration.setApplicationMode(AppConstants.CLIENT_MODE);
			/* Master replied with IP and Port.
			 * Now try to connect to Master node */
			workerNodeComponent.createWorkerNode(nodeBean);
		} else if ( message.getType().equals(AppConstants.WORKER_NODE_CON_REQ) ) {
			NetworkNodeBean nodeBean = JsonUtils.createObjectFromTree(message.getData(),
					NetworkNodeBean.class);
			System.out.println("WORKER_NODE_CON_REQ Received");
			workerNodeComponent.addWorkerNode(nodeBean, channel);
		}
	}

}