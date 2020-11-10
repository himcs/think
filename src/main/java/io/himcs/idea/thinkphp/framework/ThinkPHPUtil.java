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
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl;
import kotlin.reflect.jvm.internal.impl.renderer.ClassifierNamePolicy;

import java.nio.file.Paths;
import java.util.Objects;

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

    public static String getUrl0(String group, String action, String method) {
        return String.format("test.hudoufc.com/index.php?g=%s&m=%s&a=%s", group, action, method);
    }

    public static RelatedItemLineMarkerInfo getTplPsi(Project project, MethodImpl method) {

        String mName = method.getName();
        System.out.println(mName);
        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(Paths.get(project.getBasePath(), "Tpl\\Admin\\default\\Admin\\" + mName + ".html").toString());
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

}
