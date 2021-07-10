package plugin.nexus;

import java.util.ArrayList;

import org.pentaho.di.core.annotations.JobEntry;
import org.pentaho.di.core.annotations.Step;
import org.pentaho.di.core.exception.KettlePluginException;
import org.pentaho.di.core.plugins.JobEntryPluginType;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.steps.blockingstep.BlockingStepMeta;
import org.slf4j.Logger;

import no.nexus.core.utils.Slf4j;
import no.nexus.plugin.RegistrerPluginAdapter;

public class PluginDebugRegister implements RegistrerPluginAdapter {
    private static final Logger LOGGER = Slf4j.logger();

    @Override
    public void registerPlugin() {
        addStepPluginType(BlockingStepMeta.class);
    }

    public static void addStepPluginType(Class<?> clazz) {
        try {
            if (clazz.isAnnotationPresent(Step.class)) {
                Step a = clazz.getDeclaredAnnotation(Step.class);
                LOGGER.info("{}", a);
                StepPluginType.getInstance().handlePluginAnnotation(clazz, a, new ArrayList<>(), true, null);
            }
        } catch (KettlePluginException e) {
            LOGGER.error("StepPlugin: {}", e.getMessage(), e);
        }
    }

    public static void addJobEntryPluginType(Class<?> clazz) {
        try {
            if (clazz.isAnnotationPresent(JobEntry.class)) {
                JobEntry a = clazz.getDeclaredAnnotation(JobEntry.class);
                LOGGER.info("{}", a);
                JobEntryPluginType.getInstance().handlePluginAnnotation(clazz, a, new ArrayList<>(), true, null);
            }
        } catch (KettlePluginException e) {
            LOGGER.error("JobEntryPlugin: {}", e.getMessage(), e);
        }
    }
}
