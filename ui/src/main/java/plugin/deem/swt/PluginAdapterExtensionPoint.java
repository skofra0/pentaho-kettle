package plugin.deem.swt;

import org.pentaho.di.core.exception.KettlePluginException;
import org.pentaho.di.core.extension.ExtensionPointPluginType;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.deem.PluginAdapter;

public class PluginAdapterExtensionPoint implements PluginAdapter {

    @Override
    public void registerPlugin(PluginRegistry registry) {
        try {
            ExtensionPointPluginType.getInstance().registerCustom(plugin.deem.swt.fileopensave.extension.FileOpenSaveExtensionPoint.class, null, "FileOpenSaveNewExtensionPoint", "SpoonOpenSaveNew", "pen the new file browser", null);
            ExtensionPointPluginType.getInstance().registerCustom(plugin.deem.swt.fileopensave.extension.RepositoryOpenSaveExtensionPoint.class, null, "RepositoryOpenSaveExtensionPoint", "SpoonOpenSaveRepository", "Open the repository browser", null);
            ExtensionPointPluginType.getInstance().registerCustom(plugin.deem.swt.repo.extension.RepositorySpoonStartExtensionPoint.class, null, "RepositorySpoonStartExtensionPoint", "SpoonStart", "Do or display login for default repository", null);
        } catch (KettlePluginException e) {
            System.out.println(e.getMessage());
        }
    }

}
