package io.himcs.idea.thinkphp.framework;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.PlatformIcons;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl;
import io.himcs.idea.thinkphp.settings.ThinkPHPSettingsState;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThinkPHPUtil {
    public static boolean isAction(String action) {
        return action.indexOf("Action.class.php") > 0;
    }

    public static String formatAction(String fName) {
        return fName.substring(0, fName.indexOf("Action.class.php"));
    }


    public static Method getMethod(PsiElement psiElement) {
        Method containingMethod = PsiTreeUtil.getParentOfType(psiElement, Method.class);
        return containingMethod;
    }

    public static String getUrl0(Project project, String group, String action, String method) {
        return String.format(ThinkPHPSettingsState.getInstance(project).Host + "/index.php?g=%s&m=%s&a=%s", group, action, method);
    }

    public static RelatedItemLineMarkerInfo getTplPsi(Project project, MethodImpl method) {
        String mName = method.getName();
        ThinkPHPSettingsState setting = ThinkPHPSettingsState.getInstance(project);
        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(
                Paths.get(
                        project.getBasePath(),
                        setting.TplPath, computeGroup(method), setting.theme, computeModule(method), mName + ".html").toString());
        if (Objects.nonNull(virtualFile) && virtualFile.exists()) {
            PsiElement target = PsiManager.getInstance(project).findFile(virtualFile).getFirstChild();
            NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder
                    .create(PlatformIcons.WEB_ICON)
                    .setTarget(target)
                    .setTooltipTitle(mName + ".html");
            return builder.createLineMarkerInfo(method.getNameIdentifier());
        }
        return null;
    }

    public static String computeGroup(MethodImpl method) {
        PhpClass phpClass = PsiTreeUtil.getParentOfType(method, PhpClass.class);
        String group = phpClass.getContainingFile().getParent().getName();
        return group;
    }

    public static String computeModule(MethodImpl method) {
        PhpClass phpClass = PsiTreeUtil.getParentOfType(method, PhpClass.class);
        String module = phpClass.getName().substring(0, phpClass.getName().lastIndexOf("Action"));
        return module;
    }

    public static String computePath(MethodImpl method) {
        PhpClass phpClass = PsiTreeUtil.getParentOfType(method, PhpClass.class);
        String module = phpClass.getName().substring(0, phpClass.getName().lastIndexOf("Action"));
        String group = phpClass.getContainingFile().getParent().getName();
        return String.join(File.separator, group, module, method.getName());
    }

    public static Matcher parseUrl(String url) {
        String s = url;
        Pattern pattern = Pattern.compile(".*\\?g=(\\w+)&m=(\\w+)&a=(\\w+)");
        Matcher matcher = pattern.matcher(s);
        return matcher;
    }
}
