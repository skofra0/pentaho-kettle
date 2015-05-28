package org.pentaho;

import com.jcabi.manifests.Manifests;

public class VersionTest {
    public static void main(String[] args) {
        String version = Manifests.read("pdi-version");
        System.out.println("pdi-version " + version);
    }
}
