package plugin.nexus.swt.repo.dialog;

import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryMeta;
import org.pentaho.di.ui.repository.ILoginCallback;
import org.pentaho.di.ui.repository.RepositoriesDialog;
import org.pentaho.di.ui.spoon.Spoon;
import org.pentaho.di.ui.spoon.SpoonPluginManager;
import org.pentaho.di.ui.spoon.SpoonLifecycleListener.SpoonLifeCycleEvent;

import plugin.nexus.swt.repo.controller.RepositoryConnectController;

public class RepositoryDialog {

    private Shell shell;
    private RepositoriesDialog loginDialog;
    private RepositoryConnectController repoConnectController;

    public RepositoryDialog(Shell shell, RepositoryConnectController repoConnectController) {
        this.shell = shell;
        this.repoConnectController = repoConnectController;

    }

    public boolean openLogin(RepositoryMeta repositoryMeta) {
        loginDialog = new RepositoriesDialog(shell, repositoryMeta.getName(), new ILoginCallback() {
            public void onSuccess(Repository repository) {
                if (repository != null) {
                    repoConnectController.setConnectedRepository(repository.getRepositoryMeta());
                }
                Spoon.getInstance().setRepository(repository);
                SpoonPluginManager.getInstance().notifyLifecycleListeners(SpoonLifeCycleEvent.REPOSITORY_CONNECTED);
            }

            public void onError(Throwable t) {
                // do nothing
            }

            public void onCancel() {
                // do nothing
            }
        });
        loginDialog.show();
        return false;

    }

    public void openManager() {
        loginDialog = new RepositoriesDialog(shell, null, new ILoginCallback() {
            public void onSuccess(Repository repository) {
                if (repository != null) {
                    repoConnectController.setCurrentRepository(repository.getRepositoryMeta());
                }
                Spoon.getInstance().setRepository(repository);
                SpoonPluginManager.getInstance().notifyLifecycleListeners(SpoonLifeCycleEvent.REPOSITORY_CONNECTED);
            }

            public void onError(Throwable t) {}

            public void onCancel() {
                // do nothing
            }
        });
        loginDialog.show();
    }

    public void openCreation() {
        loginDialog = new RepositoriesDialog(shell, null, new ILoginCallback() {
            public void onSuccess(Repository repository) {
                if (repository != null) {
                    repoConnectController.setConnectedRepository(repository.getRepositoryMeta());
                }
                Spoon.getInstance().setRepository(repository);
                SpoonPluginManager.getInstance().notifyLifecycleListeners(SpoonLifeCycleEvent.REPOSITORY_CONNECTED);
            }

            public void onError(Throwable t) {}

            public void onCancel() {
                // do nothing
            }
        });
        loginDialog.show();
    }

    public boolean openRelogin(RepositoryMeta repositoryMeta, String message) {
        loginDialog = new RepositoriesDialog(shell, null, new ILoginCallback() {
            public void onSuccess(Repository repository) {
                if (repository != null) {
                    repoConnectController.setConnectedRepository(repository.getRepositoryMeta());
                }
                Spoon.getInstance().setRepository(repository);
                SpoonPluginManager.getInstance().notifyLifecycleListeners(SpoonLifeCycleEvent.REPOSITORY_CONNECTED);
            }

            public void onError(Throwable t) {}

            public void onCancel() {
                // do nothing
            }
        });
        loginDialog.show();
        return true;
    }

}
