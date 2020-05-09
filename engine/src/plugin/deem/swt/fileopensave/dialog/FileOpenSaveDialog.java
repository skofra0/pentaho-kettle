package plugin.deem.swt.fileopensave.dialog;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.compress.CompressionProvider;
import org.pentaho.di.core.compress.CompressionProviderFactory;
import org.pentaho.di.core.logging.LogChannelInterface;
import org.pentaho.di.ui.core.FileDialogOperation;

import plugin.deem.swt.fileopensave.api.file.FileDetails;

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

    private void setProperties(Object[] arguments) throws ParseException {
        if (arguments.length == 1) {
            String jsonString = (String) arguments[0];
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
            objectId = (String) jsonObject.get(OBJECT_ID_PARAM);
            name = (String) jsonObject.get(NAME_PARAM);
            path = (String) jsonObject.get(PATH_PARAM);
            parentPath = (String) jsonObject.get(PARENT_PARAM);
            connection = (String) jsonObject.get(CONNECTION_PARAM);
            provider = (String) jsonObject.get(PROVIDER_PARAM);
            type = (String) jsonObject.get(TYPE_PARAM);
        }
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
        String dialogPath = fileDialogOperation.getPath() != null ? fileDialogOperation.getPath() : fileDialogOperation.getStartDir();

        try {
            dialogPath = URLEncoder.encode(dialogPath, "UTF-8");
        } catch (Exception e) {
        }

        final FileDialog dialog = new FileDialog(shell, SWT.OPEN);
        dialog.setFilterExtensions(dialog.getFilterExtensions());
        if (dialog.open() != null) {
            String str = dialog.getFilterPath() + System.getProperty("file.separator") + dialog.getFileName();
            fileDialogOperation.setPath(str);
        } else {
            fileDialogOperation.setPath(null);
            fileDialogOperation.setFilename(dialog.getFileName());
            fileDialogOperation.setConnection(null);
        }

    }

    protected void widgetSelected() {

        FileDialogOperation fileDialogOperation = new FileDialogOperation("");

        String fileMask = fileDialogOperation.getFileType(); // ??
        String fileName= fileDialogOperation.getFilename(); // ??
        if (fileMask != null && fileMask.length() > 0) // A mask: a directory!
        {
            DirectoryDialog dialog = new DirectoryDialog(shell, SWT.OPEN);
            if (fileMask != null) {
           //     String fpath = transMeta.environmentSubstitute(fileMask.getText());
                dialog.setFilterPath(fileName);
            }

            if (dialog.open() != null) {
                String str = dialog.getFilterPath();
                fileName = str;
            }
        } else {
            FileDialog dialog = new FileDialog(shell, SWT.OPEN);
            CompressionProvider provider = CompressionProviderFactory.getInstance().getCompressionProviderByName("zip");

            List<String> filterExtensions = new ArrayList<>();
            List<String> filterNames = new ArrayList<>();

            if (!Const.isEmpty(provider.getDefaultExtension()) && !Const.isEmpty(provider.getName())) {
                filterExtensions.add("*." + provider.getDefaultExtension());
                filterNames.add(provider.getName() + " files");
            }

            filterExtensions.add("*.txt;*.csv");
            filterExtensions.add("*.csv");
            filterExtensions.add("*.txt");
            filterExtensions.add("*");
            dialog.setFilterExtensions(filterExtensions.toArray(new String[filterExtensions.size()]));

            if (fileName != null) {
                String fname = fileName ; // transMeta.environmentSubstitute(wFilename.getText());
                dialog.setFileName(fname);
            }

            dialog.setFilterNames(filterNames.toArray(new String[filterNames.size()]));

            if (dialog.open() != null) {
                String str = dialog.getFilterPath() + System.getProperty("file.separator") + dialog.getFileName();
                fileName = str;
            }
        }
    }

}
