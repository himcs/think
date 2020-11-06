package io.himcs.sdk.action;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.database.model.DasNamed;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import io.himcs.sdk.util.MDbUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ProjectAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        String tables = MDbUtil.getTables(e.getProject()).map(DasNamed::getName).map(LookupElementBuilder::create).toSet().toString();
        Messages.showInfoMessage(tables, "Tables");
    }

    @Override
    public void update(AnActionEvent e) {
        // Set the availability based on whether a project is open
        Project project = e.getProject();
        e.getPresentation().setEnabledAndVisible(project != null);
    }

}
