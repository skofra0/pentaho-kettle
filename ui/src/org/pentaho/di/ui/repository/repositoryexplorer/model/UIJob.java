/*******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2012 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.ui.repository.repositoryexplorer.model;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryElementMetaInterface;

public class UIJob extends UIRepositoryContent {

  private static final long serialVersionUID = -7673439096628166542L;

  public UIJob() {
  }

  public UIJob(RepositoryElementMetaInterface rc, UIRepositoryDirectory parent, Repository rep) {
    super(rc, parent, rep);
  }


  @Override
  public void setName(String name) throws Exception {
    super.setName(name);
    rep.renameJob(this.getObjectId(), getRepositoryDirectory(), name);
    uiParent.fireCollectionChanged();
  }
  
  public void delete()throws Exception{
    rep.deleteJob(this.getObjectId());
    if(uiParent.getRepositoryObjects().contains(this))
      uiParent.getRepositoryObjects().remove(this);
  }
  
  public void move(UIRepositoryDirectory newParentDir) throws KettleException {
    if(newParentDir != null) {
      rep.renameJob(obj.getObjectId(), newParentDir.getDirectory(), null);
      newParentDir.refresh();
    }
  }
}