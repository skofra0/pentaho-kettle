package plugin.nexus.swt;

import org.pentaho.di.core.exception.KettlePluginException;
import org.pentaho.di.core.extension.ExtensionPointPluginType;

import no.deem.plugin.RegistrerPluginAdapter;

public class PluginAdapterExtensionPoint implements RegistrerPluginAdapter {

    @Override
    public void registerPlugin() {
        try {
            ExtensionPointPluginType.getInstance().registerCustom(plugin.nexus.swt.fileopensave.extension.FileOpenSaveExtensionPoint.class, null, "FileOpenSaveNewExtensionPoint", "SpoonOpenSaveNew", "pen the new file browser", null);
            ExtensionPointPluginType.getInstance().registerCustom(plugin.nexus.swt.fileopensave.extension.RepositoryOpenSaveExtensionPoint.class, null, "RepositoryOpenSaveExtensionPoint", "SpoonOpenSaveRepository", "Open the repository browser", null);
            ExtensionPointPluginType.getInstance().registerCustom(plugin.nexus.swt.repo.extension.RepositorySpoonStartExtensionPoint.class, null, "RepositorySpoonStartExtensionPoint", "SpoonStart", "Do or display login for default repository", null);
        } catch (KettlePluginException e) {
            System.out.println(e.getMessage());
        }
    }

}
