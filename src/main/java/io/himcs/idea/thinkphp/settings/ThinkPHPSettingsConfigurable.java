package io.himcs.idea.thinkphp.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

//Controller
public class ThinkPHPSettingsConfigurable implements Configurable {

    private Project project;

    //View
    private ThinkPHPSettingsComponent mySettingsComponent;

    public ThinkPHPSettingsConfigurable(@NotNull final Project project) {
        this.project = project;
    }


    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "ThinkPHP3 Plugin";
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return mySettingsComponent.getPreferredFocusedComponent();
    }

    @Override
    public @Nullable JComponent createComponent() {
        mySettingsComponent = new ThinkPHPSettingsComponent();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        ThinkPHPSettingsState settings = getSettings();
        boolean modified = !(mySettingsComponent.getEnabled() == (settings.pluginEnabled));
        modified |= !(mySettingsComponent.getTheme().equals(settings.theme));
        modified |= !(mySettingsComponent.getDBLinkName().equals(settings.DBLinkName));
        modified |= !(mySettingsComponent.getTplPath().equals(settings.TplPath));
        modified |= !(mySettingsComponent.getHost().equals(settings.Host));
        modified |= !(mySettingsComponent.getAppPath().equals(settings.AppPath));
        modified |= !(mySettingsComponent.getConfPath().equals(settings.ConfPath));
        return modified;
    }

    @Override
    public void apply() {
        ThinkPHPSettingsState settings = getSettings();
        settings.pluginEnabled = mySettingsComponent.getEnabled();
        settings.DBLinkName = mySettingsComponent.getDBLinkName();
        settings.Host = mySettingsComponent.getHost();
        settings.theme = mySettingsComponent.getTheme();
        settings.TplPath = mySettingsComponent.getTplPath();
        settings.AppPath = mySettingsComponent.getAppPath();
        settings.ConfPath = mySettingsComponent.getConfPath();
    }

    @Override
    public void reset() {
        ThinkPHPSettingsState settings = getSettings();
        mySettingsComponent.setEnabled(settings.pluginEnabled);
        mySettingsComponent.setDBLinkName(settings.DBLinkName);
        mySettingsComponent.setTheme(settings.theme);
        mySettingsComponent.setTplPath(settings.TplPath);
        mySettingsComponent.setAppPath(settings.AppPath);
        mySettingsComponent.setConfPath(settings.ConfPath);
        mySettingsComponent.setHost(settings.Host);
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

    private ThinkPHPSettingsState getSettings() {
        return ThinkPHPSettingsState.getInstance(this.project);
    }

}
