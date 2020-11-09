package io.himcs.idea.thinkphp.framework;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.usages.impl.rules.UsageTypeGroupingRule;
import com.jetbrains.php.lang.psi.elements.Method;

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
}
