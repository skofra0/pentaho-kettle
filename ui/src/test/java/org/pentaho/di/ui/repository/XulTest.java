package org.pentaho.di.ui.repository;

import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;
import org.pentaho.di.ui.spoon.XulSpoonResourceBundle;
import org.pentaho.di.ui.spoon.XulSpoonSettingsManager;
import org.pentaho.di.ui.xul.KettleXulLoader;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;

public class XulTest {

    private static final Class<?> CLZ = RepositoriesDialog.class;
    private ResourceBundle resourceBundle = new XulSpoonResourceBundle(CLZ);

    @Test
    public void loadRepositoryDialog() {
        try {
            KettleXulLoader xulLoader = new KettleXulLoader();
            // xulLoader.setOuterContext( shell );
            xulLoader.setSettingsManager(XulSpoonSettingsManager.getInstance());
            XulDomContainer container = xulLoader.loadXul("org/pentaho/di/ui/repository/xul/repositories.xul", resourceBundle);
            Assert.assertNotNull(container);
        } catch (XulException e) {
            Assert.fail(e.getMessage());
        }
    }

}
