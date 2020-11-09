package io.himcs.idea.thinkphp.builtInWebServer;

import com.intellij.ide.browsers.OpenInBrowserRequest;
import com.intellij.ide.browsers.WebBrowserUrlProvider;
import com.intellij.ide.util.EditorHelper;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Url;
import com.intellij.util.UrlImpl;
import com.intellij.util.Urls;
import com.jetbrains.php.lang.psi.elements.Method;
import io.himcs.idea.thinkphp.framework.ThinkPHPUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.builtInWebServer.BuiltInWebBrowserUrlProvider;

import java.util.Objects;

public class ThinkPHPUrlProvider extends WebBrowserUrlProvider {

    @Override
    protected @Nullable Url getUrl(@NotNull OpenInBrowserRequest request, @NotNull VirtualFile file) throws BrowserException {
        if (Objects.isNull(file)) {
            return super.getUrl(request, file);
        }
        String fName = file.getName();


        if (!ThinkPHPUtil.isAction(fName)) {
            return super.getUrl(request, file);
        }
        String group = "";
        VirtualFile psiDirectory = file.getParent();
        if (Objects.nonNull(psiDirectory)) {
            group = psiDirectory.getName();
        }
        String action = ThinkPHPUtil.formatAction(fName);
        Method containingMethod = ThinkPHPUtil.getMethod(request.getElement());
        String method = "index";
        if (Objects.nonNull(containingMethod)) {
            method = containingMethod.getName();
        }
        return Urls.newHttpUrl("", ThinkPHPUtil.getUrl0(group, action, method));
    }

    @Override
    public boolean canHandleElement(@NotNull OpenInBrowserRequest request) {
        PsiFile file = request.getFile();
        if (Objects.isNull(file))
            return false;
        return ThinkPHPUtil.isAction(file.getName());
    }
}
