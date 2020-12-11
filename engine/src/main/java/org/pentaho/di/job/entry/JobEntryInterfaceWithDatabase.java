package org.pentaho.di.job.entry;

import java.util.List;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.metastore.api.IMetaStore;

public interface JobEntryInterfaceWithDatabase extends JobEntryInterface {

    void saveRep(Repository rep, IMetaStore metaStore, ObjectId id_job, List<DatabaseMeta> databases) throws KettleException;


}
