/*
 * ! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2017-2019 by Hitachi Vantara : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package plugin.deem.fileopensave.extension;

import java.util.function.Supplier;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.extension.ExtensionPoint;
import org.pentaho.di.core.extension.ExtensionPointInterface;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.ui.core.FileDialogOperation;
import org.pentaho.di.ui.spoon.Spoon;

import plugin.deem.fileopensave.providers.ProviderService;
import plugin.deem.fileopensave.providers.local.LocalFileProvider;

/**
 * Created by bmorrise on 5/23/17.
 */

@ExtensionPoint(id = "FileOpenSaveNewExtensionPoint", extensionPointId = "SpoonOpenSaveNew", description = "Open the new file browser")
public class FileOpenSaveExtensionPoint implements ExtensionPointInterface {

    private static final int WIDTH = (Const.isOSX() || Const.isLinux()) ? 930 : 947;
    private static final int HEIGHT = (Const.isOSX() || Const.isLinux()) ? 618 : 626;

    private Supplier<Spoon> spoonSupplier = Spoon::getInstance;
    private final ProviderService providerService;

    public FileOpenSaveExtensionPoint(ProviderService providerService) {
        this.providerService = providerService;
    }

  @Override public void callExtensionPoint( LogChannelInterface logChannelInterface, Object o ) throws KettleException {
    FileDialogOperation fileDialogOperation = (FileDialogOperation) o;
 
    
    final FileDialog fileOpenSaveDialog =
      new FileDialog( spoonSupplier.get().getShell(), SWT.OPEN);

    resolveProvider( fileDialogOperation );
    fileOpenSaveDialog.open(  );

    fileDialogOperation.setPath( null );
    fileDialogOperation.setFilename( null );
    fileDialogOperation.setConnection( null );

//    if ( !Utils.isEmpty( fileOpenSaveDialog.getProvider() ) ) {
//      try {
//        FileProvider fileProvider = providerService.get( fileOpenSaveDialog.getProvider() );
//        fileProvider.setFileProperties( fileOpenSaveDialog, fileDialogOperation );
//      } catch ( InvalidFileProviderException e ) {
//        throw new KettleException( e );
//      }
//    }
  }

//  public void widgetSelected( SelectionEvent e ) {
//      if ( wFilemask.getText() != null && wFilemask.getText().length() > 0 ) // A mask: a directory!
//      {
//        DirectoryDialog dialog = new DirectoryDialog( shell, SWT.OPEN );
//        if ( wFilename.getText() != null ) {
//          String fpath = transMeta.environmentSubstitute( wFilename.getText() );
//          dialog.setFilterPath( fpath );
//        }
//
//        if ( dialog.open() != null ) {
//          String str = dialog.getFilterPath();
//          wFilename.setText( str );
//        }
//      } else {
//        FileDialog dialog = new FileDialog( shell, SWT.OPEN );
//        CompressionProvider provider =
//            CompressionProviderFactory.getInstance().getCompressionProviderByName( wCompression.getText() );
//
//        List<String> filterExtensions = new ArrayList<>();
//        List<String> filterNames = new ArrayList<>();
//
//        if ( !Const.isEmpty( provider.getDefaultExtension() ) && !Const.isEmpty( provider.getName() ) ) {
//          filterExtensions.add( "*." + provider.getDefaultExtension() );
//          filterNames.add( provider.getName() + " files" );
//        }
//
//        filterExtensions.add( "*.txt;*.csv" );
//        filterNames.add( BaseMessages.getString( PKG, "TextFileInputDialog.FileType.TextAndCSVFiles" ) );
//        filterExtensions.add( "*.csv" );
//        filterNames.add( BaseMessages.getString( PKG, "System.FileType.CSVFiles" ) );
//        filterExtensions.add( "*.txt" );
//        filterNames.add( BaseMessages.getString( PKG, "System.FileType.TextFiles" ) );
//        filterExtensions.add( "*" );
//        filterNames.add( BaseMessages.getString( PKG, "System.FileType.AllFiles" ) );
//        dialog.setFilterExtensions( filterExtensions.toArray( new String[filterExtensions.size()] ) );
//
//        if ( wFilename.getText() != null ) {
//          String fname = transMeta.environmentSubstitute( wFilename.getText() );
//          dialog.setFileName( fname );
//        }
//
//        dialog.setFilterNames( filterNames.toArray( new String[filterNames.size()] ) );
//
//        if ( dialog.open() != null ) {
//          String str = dialog.getFilterPath() + System.getProperty( "file.separator" ) + dialog.getFileName();
//          wFilename.setText( str );
//        }
//      }
//    }
  
  
    private void resolveProvider(FileDialogOperation op) {
        if (op.getProvider() == null) {
            op.setProvider(LocalFileProvider.TYPE);
        }
    }
}
