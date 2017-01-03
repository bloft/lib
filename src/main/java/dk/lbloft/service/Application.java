package dk.lbloft.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.LogManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;
import com.google.common.util.concurrent.ServiceManager.Listener;
import com.thoughtworks.xstream.XStream;

import dk.lbloft.locate.Locator;
import dk.lbloft.protocols.URLStreamHandlerFactory;
import dk.lbloft.util.Resources;

public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	private AppCo appCo = null;
	ServiceManager manager = null;

	/**
	 * Create a new application
	 * Load wiring from META-INF/application.xml
	 */
	public Application(String ... args) {
		URLStreamHandlerFactory.register();
		XStream stream = new XStream();

		stream.processAnnotations(AppCo.class);
		loadModules(stream);

		try {
			appCo = (AppCo) stream.fromXML(new URL("classpath:META-INF/services.xml"));

			try {
				LogManager.getLogManager().readConfiguration(new URL(appCo.logging).openStream());
			} catch (FileNotFoundException e) {
				System.err.println("Unable to find logging configuration: " + appCo.logging);
			}
			logger.debug("Logging loaded and started");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		buildObjects();
		logger.debug("Configuration loaded");
	}
	
	public void start() {
		manager = new ServiceManager(Sets.newHashSet(Locator.locateAll(Service.class)));
		manager.addListener(new Listener() {
			@Override
			public void stopped() {
				logger.info("All services stopped");
			}
			@Override
			public void healthy() {
				logger.info("All services started, and are healthy");
			}
			@Override
			public void failure(Service service) {
				logger.error("Unable to start service", service);
				System.exit(1);
			}
		}, MoreExecutors.sameThreadExecutor());
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				Application.this.stop();
			}
		});
		logger.info("Starting services");
		manager.startAsync();
	}
	
	public void stop() {
		try {
			logger.info("Stopping services");
			manager.stopAsync().awaitStopped(5, TimeUnit.SECONDS);
		} catch (TimeoutException timeout) {}
	}

	/**
	 * Build all runtime objects from config objects
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void buildObjects() {
		for (BaseCo configObj : appCo.getExtensions()) {
			logger.trace("Building runtime object for " + configObj.getClass().getSimpleName());
			try {
				RuntimeBuilder builder = configObj.getClass().getAnnotation(Builder.class).builder().newInstance();
				if(configObj.getName() != null) {
					Locator.register(configObj.getName(), builder.buildRuntime(configObj));
				} else {
					Locator.register(builder.buildRuntime(configObj));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Load xstream info from config objects
	 * @param stream
	 */
	private void loadModules(XStream stream) {
		for (Class<ModuleConfig> module : Resources.getServiceClasses(ModuleConfig.class)) {
			try {
				ModuleConfig m = module.getConstructor().newInstance();
				ArrayList<Class<? extends BaseCo>> classes = new ArrayList<Class<? extends BaseCo>>();
				m.getClasses(classes);
				for (Class<? extends BaseCo> c : classes) {
					stream.processAnnotations(c);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
