/*
 * ! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2013 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.trans.steps.stagingupsert;

import java.sql.PreparedStatement;

import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

/**
 * Stores data for the StagingUpsert step.
 *
 */
public class StagingUpsertData extends BaseStepData implements StepDataInterface {
    public Database db;

    int[] keynrs; // nr of keylookup -value in row...
    int[] keynrs2; // nr of keylookup2-value in row...
    int[] valuenrs; // Stream valuename nrs to prevent searches.
    int versionFieldNumber = -1;

    RowMetaInterface outputRowMeta;

    String schemaTable;

    PreparedStatement prepStatementLookup;
    PreparedStatement prepStatementUpdate;

    RowMetaInterface updateParameterRowMeta;
    RowMetaInterface lookupParameterRowMeta;
    RowMetaInterface lookupReturnRowMeta;
    RowMetaInterface insertRowMeta;

    public StagingUpsertData() {
        super();
        db = null;
    }
}
