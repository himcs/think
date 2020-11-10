package io.himcs.idea.thinkphp.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class TplAction extends AnAction {

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
        PhpClass phpClass = PsiTreeUtil.getParentOfType(psiElement, PhpClass.class);

        if (Objects.isNull(phpClass)) {
            System.out.println("null class");
            return;
        }

        Arrays.stream(PsiTreeUtil.getChildrenOfType(phpClass, MethodImpl.class)).forEach(
                e -> {
                    System.out.println(e);
                    if (e.getText().contains("display")) {
                        System.out.println(System.currentTimeMillis());
                        System.out.println(e.getText());
                    }
                }
        );

    }

    @Override
    public void update(AnActionEvent e) {
        // Set the availability based on whether a project is open
        Project project = e.getProject();
        e.getPresentation().setEnabledAndVisible(project != null);
    }
}
