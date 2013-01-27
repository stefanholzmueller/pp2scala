package stefanholzmueller.pp2.service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jettison.JettisonFeature;

import stefanholzmueller.pp2.checks.CheckDecider;
import stefanholzmueller.pp2.checks.CheckStatisticsCalculator;

@ApplicationPath("/")
public class App extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(HelloService.class);
        classes.add(RandomService.class);
        classes.add(CheckService.class);
        classes.add(CheckStatisticsCalculator.class);
        classes.add(CheckDecider.class);
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        final Set<Object> instances = new HashSet<Object>();
        instances.add(new JettisonFeature());
        instances.add(new LoggingFilter());
        return instances;
    }

}
