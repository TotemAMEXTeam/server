package net.twilightstudios.amex.util.timer.listener;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.twilightstudios.amex.util.timer.FlightsTimer;

public class FlightsTimerListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		final WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
	    final FlightsTimer task = (FlightsTimer)springContext.getBean("flightsTimer");
		Timer timer = new Timer();
		timer.schedule(task, 0, task.getPeriod());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
