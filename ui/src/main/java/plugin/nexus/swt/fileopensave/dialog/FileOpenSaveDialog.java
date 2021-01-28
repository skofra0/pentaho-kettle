package plugin.nexus.swt.fileopensave.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.ui.core.FileDialogOperation;

import no.deem.core.utils.Strings;
import plugin.nexus.swt.fileopensave.api.file.FileDetails;

public class FileOpenSaveDialog implements FileDetails {

    private Shell shell;

    private String objectId;
    private String name;
    private String path;
    private String parentPath;
    private String type;
    private String connection;
    private String provider;

    public static final String PATH_PARAM = "path";
    public static final String CONNECTION_PARAM = "connection";
    public static final String PROVIDER_PARAM = "provider";
    public static final String OBJECT_ID_PARAM = "objectId";
    public static final String NAME_PARAM = "name";
    public static final String PARENT_PARAM = "parent";
    public static final String TYPE_PARAM = "type";

    public FileOpenSaveDialog(Shell shell, int width, int height, LogChannelInterface logger) {
        this.shell = shell;
        this.provider = "local";
    }

    public String getProvider() {
        return provider;
    }

    @Override
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getParentPath() {
        return parentPath;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public void open(FileDialogOperation fileDialogOperation) {
        final FileDialog dialog = new FileDialog(shell, SWT.OPEN);
        List<String> filterExtensions = new ArrayList<>();
        for (String f : Strings.splitToList(fileDialogOperation.getFilter(), ",")) {
            if ("ALL".equals(f)) {
                filterExtensions.add("*");
            } else {
                filterExtensions.add("*." + f.toLowerCase());
            }
        }

        dialog.setFilterExtensions(filterExtensions.toArray(new String[filterExtensions.size()]));
        if (dialog.open() != null) {
            String str = dialog.getFilterPath() + System.getProperty("file.separator") + dialog.getFileName();
            setPath(str);
        } else {
            fileDialogOperation.setPath(null);
            fileDialogOperation.setFilename(dialog.getFileName());
            fileDialogOperation.setConnection(null);
        }
    }

}
