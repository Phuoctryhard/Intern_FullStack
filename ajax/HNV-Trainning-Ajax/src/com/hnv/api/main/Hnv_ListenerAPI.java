package com.hnv.api.main;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.hnv.def.DefAPIExt;

@WebListener()
public class Hnv_ListenerAPI implements ServletContextListener, HttpSessionListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce){
		//---init context 
		DefAPIExt.doInit(sce);
		
		Hnv_CfgHibernate.doInitSessionFactory(Hnv_CfgHibernate.ID_FACT_MAIN	, Hnv_CfgHibernate.CFG_XML_MAIN);
//		Hnv_CfgHibernate.doInitSessionFactory(Hnv_CfgHibernate.ID_FACT_SYS	, Hnv_CfgHibernate.CFG_XML_SYS);
		
		//-----init DAO for some sys table
//		FactoryEMSession			factDAO		= Hnv_CfgHibernate.reqFactoryEMSession(Hnv_CfgHibernate.ID_FACT_MAIN);
//		EntityDAO<TaSysLock> 		lockDAO 	= new EntityDAO<TaSysLock>		(factDAO , TaSysLock.class);
//		EntityDAO<TaSysException> 	excepDAO 	= new EntityDAO<TaSysException>	(factDAO , TaSysException.class);
//		EntityDAO<TaSysAudit> 		auditkDAO 	= new EntityDAO<TaSysAudit>		(factDAO , TaSysAudit.class);
//		
//		TaSysLock		.doSetDAO(lockDAO);
//		TaSysException	.doSetDAO(excepDAO);
//		TaSysAudit		.doSetDAO(auditkDAO);
		
		//---launch process
//		DefProcess.do_LaunchJob();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		Hnv_CfgHibernate.doCloseSessionFactory();
		DefAPIExt.doClose();
	}

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
	}
	
	
}

