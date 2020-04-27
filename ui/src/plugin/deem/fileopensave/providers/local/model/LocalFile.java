/*! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2019 by Hitachi Vantara : http://www.pentaho.com
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

package plugin.deem.fileopensave.providers.local.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

import plugin.deem.fileopensave.api.providers.BaseEntity;
import plugin.deem.fileopensave.api.providers.File;
import plugin.deem.fileopensave.providers.local.LocalFileProvider;

/**
 * Created by bmorrise on 2/16/19.
 */
public class LocalFile extends BaseEntity implements File {
  private static final String TYPE = "file";

  public LocalFile() {
  }

  @Override public String getType() {
    return TYPE;
  }

  @Override public String getProvider() {
    return LocalFileProvider.TYPE;
  }

  public static LocalFile create( String parent, Path path ) {
    LocalFile localFile = new LocalFile();
    localFile.setName( path.getFileName().toString() );
    localFile.setPath( path.toString() );
    localFile.setParent( parent );
    try {
      localFile.setDate( new Date( Files.getLastModifiedTime( path ).toMillis() ) );
    } catch ( IOException e ) {
      localFile.setDate( new Date() );
    }
    localFile.setRoot( LocalFileProvider.NAME );
    localFile.setCanEdit( true );
    return localFile;
  }
}
