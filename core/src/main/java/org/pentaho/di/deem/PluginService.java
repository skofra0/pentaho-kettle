package org.pentaho.di.deem;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.pentaho.di.core.plugins.PluginRegistry;

public enum PluginService {

    INSTANCE;

    private final ServiceLoader<PluginAdapter> loader;
    private Map<String, PluginAdapter> adapters = Collections.emptyMap();

    public static PluginService getInstance() {
        return INSTANCE;
    }

    private PluginService() {
        this.loader = ServiceLoader.load(PluginAdapter.class);
        final Iterator<PluginAdapter> managers = this.loader.iterator();
        adapters = new LinkedHashMap<>();
        while (managers.hasNext()) {
            final PluginAdapter adapter = managers.next();
            adapters.put(adapter.getClass().getName(), adapter);
        }
    }

    public void loadPlugins() {
        for (PluginAdapter a : adapters.values()) {
            a.registerPlugin(PluginRegistry.getInstance());
        }

    }

}
