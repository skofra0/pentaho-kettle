package org.pentaho.di.repository;

import java.util.List;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;

public enum RepoReconnectFix {
    
    INSTANCE;
    
    public static DatabaseMeta fixDatabaseMissingIdStepMeta(DatabaseMeta databaseMeta, BaseStepMeta baseStepMeta) {
        if (databaseMeta != null && databaseMeta.getObjectId() == null && baseStepMeta.getParentStepMeta() != null) {
            TransMeta tmeta = baseStepMeta.getParentStepMeta().getParentTransMeta();
            List<DatabaseMeta> databases = tmeta.getDatabases();

            if (databases != null && !databases.isEmpty()) {
                for (DatabaseMeta dbMeta : databases) {
                    if (dbMeta.getDisplayName().equals(databaseMeta.getDisplayName())) {
                        databaseMeta.setObjectId(dbMeta.getObjectId());
                        break;
                    }
                }
            }
        }
        return databaseMeta;
    }

    public static DatabaseMeta fixDatabaseMissingIdJobEntryBase(DatabaseMeta databaseMeta, List<DatabaseMeta> databases) {
        if (databaseMeta != null && databaseMeta.getObjectId() == null) {
            if (databases != null && !databases.isEmpty()) {
                for (DatabaseMeta dbMeta : databases) {
                    if (dbMeta.getDisplayName().equals(databaseMeta.getDisplayName())) {
                        databaseMeta.setObjectId(dbMeta.getObjectId());
                        break;
                    }
                }
            }
        }
        return databaseMeta;
    }

}
