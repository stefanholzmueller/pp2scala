package stefanholzmueller.pp2.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;

import stefanholzmueller.pp2.check.CheckResultCalculator;
import stefanholzmueller.pp2.check.CheckStatisticsCalculator;

@ApplicationPath("/")
public class App extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		final Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(RandomService.class);
		classes.add(CheckService.class);
		classes.add(CheckStatisticsCalculator.class);
		classes.add(CheckResultCalculator.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		final Set<Object> instances = new HashSet<Object>();
		instances.add(new JacksonFeature());
		instances.add(new LoggingFilter());
		return instances;
	}

}
