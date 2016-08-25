package com.revature.logging;

import org.apache.log4j.Logger;

public class Log {

	static final Logger logger = Logger.getLogger("CONSOLE");
	
	public static void trace(Object obj) { logger.trace(obj); }
	public static void debug(Object obj) { logger.debug(obj); }
	public static void info(Object obj) { logger.info(obj); }
	public static void warn(Object obj) { logger.warn(obj); }
	public static void error(Object obj) { logger.error(obj); }
	public static void fatal(Object obj) { logger.fatal(obj); }
}
