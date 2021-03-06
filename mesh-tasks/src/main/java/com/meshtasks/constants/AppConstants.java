package com.meshtasks.constants;

import java.nio.charset.Charset;

public class AppConstants {
	
	public static final String FIND_MASTER_REQ = "findMasterReq";
	
	public static final String FIND_MASTER_RES = "findMasterRes";
	
	public static final String WORKER_NODE_CON_REQ = "nodeConReq";
	
	public static final String WORKER_NODE_CON_RES = "nodeConRes";
	
	public static final String TASK_DATA_REQ = "taskDataReq";
	
	public static final String NODE_TASK_DATA_REQ = "nodeTaskDataReq";
	
	public static final String MASTER_MODE = "master";
	
	public static final String CLIENT_MODE = "client";
	
	public static final Charset SOCKET_CHAR_SET = Charset.forName("UTF-8");
}