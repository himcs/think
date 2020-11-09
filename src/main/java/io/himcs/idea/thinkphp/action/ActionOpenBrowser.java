package io.himcs.idea.thinkphp.action;

import com.intellij.ide.browsers.actions.OpenInBrowserBaseGroupAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.Method;
import io.netty.util.internal.ObjectUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ActionOpenBrowser extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {

        Project currentProject = event.getProject();
        if (Objects.isNull(currentProject))
            return;

        PsiFile psiFile = event.getData(CommonDataKeys.PSI_FILE);
        if (Objects.isNull(psiFile))
            return;

        Editor editor = event.getData(CommonDataKeys.EDITOR);
        if (Objects.isNull(editor))
            return;

        int offset = editor.getCaretModel().getOffset();
        PsiElement psiElement = psiFile.findElementAt(offset);
        if (Objects.isNull(psiElement))
            return;

        Method containingMethod = PsiTreeUtil.getParentOfType(psiElement, Method.class);
        if (Objects.isNull(containingMethod))
            return;

        PsiDirectory psiDirectory = psiFile.getParent();
        String group = "";
        if (Objects.nonNull(psiDirectory)) {
            group = psiDirectory.getName();
        }
        String action = psiFile.getName().substring(0, psiFile.getName().indexOf("Action.class.php"));
        String method = containingMethod.getName();
        StringBuilder dlgMsg = new StringBuilder(group + " " + action + " " + method);
        String dlgTitle = event.getPresentation().getDescription();
        Messages.showMessageDialog(currentProject, dlgMsg.toString(), dlgTitle, Messages.getInformationIcon());
    }

    @Override
    public void update(AnActionEvent e) {
        // 当前文件名后缀为Action.class.php 激活
        Project project = e.getProject();
        if (Objects.isNull(project)) {
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }

        PsiFile file = e.getData(CommonDataKeys.PSI_FILE);
        if (Objects.isNull(file)) {
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }

        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (Objects.isNull(editor)) {
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }
        e.getPresentation().setEnabledAndVisible(file.getName().indexOf("Action.class.php") > 0);
    }
}
