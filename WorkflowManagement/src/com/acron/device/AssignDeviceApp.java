package com.acron.device;

import java.util.Timer;

import org.apache.log4j.BasicConfigurator;

import com.sybase.sup.admin.client.SUPApplication;
import com.sybase.sup.admin.client.SUPDomain;
import com.sybase.sup.admin.client.SUPMobileWorkflow;
import com.sybase.sup.admin.client.SUPObjectFactory;
import com.sybase.sup.admin.client.SUPServer;
import com.sybase.sup.admin.context.AgentContext;
import com.sybase.sup.admin.context.ClusterContext;
import com.sybase.sup.admin.context.DomainContext;
import com.sybase.sup.admin.context.ServerContext;
import com.sybase.sup.admin.exception.SUPAdminException;

public class AssignDeviceApp {
	private final static String mHost		= "relay.aslanoba.com";
	private final static int	mAgentPort	= 9999;
	private final static int	mMngPort	= 2000;
	private final static String mUsername	= "alperkarabulut" ;
	private final static String mPassword 	= "06111984";
	private final static long 	mTimeout	= 120;
	public static int			mTimes		= 0;
	public static ClusterContext mClusterContext;
	/**
	 * @param args
	 * @throws SUPAdminException 
	 */
	public static void main(String[] args) throws SUPAdminException {
		try{
		if( args.length > 0 ){
			mTimes = Integer.valueOf(args[0]); 
		}
		}catch (Exception e) {
			mTimes = 0;
		}
		BasicConfigurator.configure();
		
		AgentContext agentContext = new AgentContext(mHost, mAgentPort, mUsername, mPassword);
		ServerContext serverContext = new ServerContext(mHost, mMngPort, mUsername, mPassword,false, agentContext);
		ClusterContext clusterContext = serverContext.getClusterContext("relay");
		SUPServer server = SUPObjectFactory.getSUPServer(serverContext);
		SUPDomain domain = SUPObjectFactory.getSUPDomain(new DomainContext(serverContext.getClusterContext("relay"), "default"));
		SUPApplication app = SUPObjectFactory.getSUPApplication(clusterContext);
		SUPMobileWorkflow mobileWorkflow = SUPObjectFactory.getSUPMobileWorkflow(clusterContext);
		
		mClusterContext = clusterContext;
		
		Timer timer = new Timer("AssignDeviceTimer");
		
		//AssignDeviceTask assignDeviceTask = new AssignDeviceTask();
		
		
		timer.schedule(new AssignDeviceTask(), 0, 120 * 1000);
		
	}

}