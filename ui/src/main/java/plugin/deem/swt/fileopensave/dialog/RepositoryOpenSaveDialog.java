/*
 * Copyright 2017-2020 Hitachi Vantara. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 */

package plugin.deem.swt.fileopensave.dialog;

import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositoryObjectType;
import org.pentaho.di.ui.repository.dialog.SelectObjectDialog;

public class RepositoryOpenSaveDialog {

    private Shell shell;

    private String objectId;
    private String objectName;
    private String objectDirectory;
    private String objectType;

    public RepositoryOpenSaveDialog(Shell shell, int width, int height) {
        this.shell = shell;
    }

    public void open(Repository repository, String directory, String state, String title, String filter, String origin) {
        open(repository, directory, state, title, filter, origin, null, "");
    }

    public void open(Repository repository, String directory, String state, String title, String filter, String origin, String filename, String fileType) {
        SelectObjectDialog sod = new SelectObjectDialog(shell, repository);
        if (sod.open() != null) {
            RepositoryObjectType type = sod.getObjectType();
            objectType = type.getExtension();
            objectName = sod.getObjectName();
            RepositoryDirectoryInterface repDir = sod.getDirectory();
            objectDirectory = repDir.getPath();
            objectId = sod.getObjectId().getId();
        }
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getObjectDirectory() {
        return objectDirectory;
    }

    public void setObjectDirectory(String objectDirectory) {
        this.objectDirectory = objectDirectory;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
}
