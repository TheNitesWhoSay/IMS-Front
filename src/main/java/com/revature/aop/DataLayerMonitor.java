package com.revature.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.revature.logging.Log;

@Aspect
public class DataLayerMonitor {

	@After("persistencePointcut()")
	public void persistenceMonitor() {
		Log.info("Data Layer Accessed");
	}
	@After("global()")
	public void globalReact() {
		Log.info("global");
	}
	
	@Pointcut("within(com.revature.business)") // point expression
	public void persistencePointcut() {}
	@Pointcut("execution(* *(..))")
	public void global() {}
}
