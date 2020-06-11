package org.pentaho.di.deem.database;

import org.pentaho.di.core.exception.KettlePluginException;
import org.pentaho.di.core.plugins.DatabasePluginType;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.deem.PluginAdapter;

public class PluginAdapterDatabase implements PluginAdapter {

    @Override
    public void registerPlugin(PluginRegistry registry) {
        try {
            DatabasePluginType.getInstance().registerCustom(DeemMySqlMeta.class, null, "DEEM_MYSQL", "Deem MySQL", "Deem MySQL", null);
            DatabasePluginType.getInstance().registerCustom(DeemInfobrightMeta.class, null, "DEEM_INFOBRIGHT", "Deem Infobright", "Deem Infobright", null);
            DatabasePluginType.getInstance().registerCustom(InforDataLakeMeta.class, null, "INFOR_DATALAKE", "Infor DataLake (Beta, download drivers)", "Infor DataLake (Beta, download drivers)", null);
        } catch (KettlePluginException e) {
            System.out.println(e.getMessage());
        }
    }

}
