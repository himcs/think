package io.himcs.idea.thinkphp.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "ThinkPHPSettings",
        storages = {
                @Storage("thinkphp3-plugin.xml")
        }
)
public class ThinkPHPSettingsState implements PersistentStateComponent<ThinkPHPSettingsState> {

    public boolean pluginEnabled = true;
    public String theme = "default";
    public String DBLinkName = "work";
    public String TplPath = "Tpl";
    public String AppPath = "App";
    public String ConfPath = "Conf";
    public String Host = "localhost";

    public static ThinkPHPSettingsState getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, ThinkPHPSettingsState.class);
    }


    @Override
    public @Nullable ThinkPHPSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ThinkPHPSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }


}
