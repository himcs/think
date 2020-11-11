package io.himcs.idea.thinkphp.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import io.himcs.idea.thinkphp.framework.ThinkPHPUtil;
import io.himcs.idea.thinkphp.settings.ThinkPHPSettingsState;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Matcher;

public class TplAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        ThinkPHPSettingsState setting = ThinkPHPSettingsState.getInstance(project);
        CopyPasteManager instance = CopyPasteManager.getInstance();
        Transferable contents = instance.getContents();
        try {
            String text = contents.getTransferData(DataFlavor.stringFlavor).toString();
            Matcher matcher = ThinkPHPUtil.parseUrl(text);

            if (!matcher.matches()) {
                System.out.println("not match");
                return;
            }
            String group = matcher.group(1);
            String module = matcher.group(2);
            String action = matcher.group(3);

            VirtualFile virtualFileHTML = LocalFileSystem.getInstance().findFileByPath(
                    Paths.get(
                            Objects.requireNonNull(project.getBasePath()),
                            setting.TplPath, group, setting.theme, module, action + ".html").toString());
            if (virtualFileHTML != null && virtualFileHTML.exists()) {
                FileEditorManager.getInstance(project).openFile(virtualFileHTML, false);
            }

            VirtualFile virtualFileAction = LocalFileSystem.getInstance().findFileByPath(
                    Paths.get(
                            Objects.requireNonNull(project.getBasePath()),
                            setting.AppPath, "Lib", "Action", group, module + "Action.class.php").toString());
            if (virtualFileAction != null && virtualFileAction.exists()) {
                System.out.println(virtualFileAction);
                FileEditorManager.getInstance(project).openFile(virtualFileAction, true);
                // Work off of the primary caret to get the selection info
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();

                int offset = editor.getDocument().getText().indexOf("function " + action);
                if (offset < 0) {
                    editor.getDocument().getText().indexOf("function    " + action);
                }
                if (offset < 0) {
                    editor.getDocument().getText().indexOf("function  " + action);
                }
                if (offset < 0) {
                    editor.getDocument().getText().indexOf("function    " + action);
                }
                if (offset < 0) {
                    offset = 0;
                }
                primaryCaret.moveToOffset(offset);
            }

        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(AnActionEvent e) {
        // Set the availability based on whether a project is open
        Project project = e.getProject();
        e.getPresentation().setEnabledAndVisible(project != null);
    }
}
