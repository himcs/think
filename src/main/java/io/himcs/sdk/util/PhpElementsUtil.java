package io.himcs.sdk.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class PhpElementsUtil {

    @NotNull
    public static Collection<String> getMethodParameterTypeHints(@NotNull Method method) {
        ParameterList childOfType = PsiTreeUtil.getChildOfType(method, ParameterList.class);
        if (childOfType == null) {
            return Collections.emptyList();
        }

        PsiElement[] parameters = childOfType.getParameters();
        if (parameters.length == 0) {
            return Collections.emptyList();
        }

//        PhpTypeDeclaration typeDeclaration = PsiTreeUtil.getChildOfType(parameters[0], PhpTypeDeclaration.class);
//        if (typeDeclaration == null) return Collections.emptyList();
//        return typeDeclaration.getClassReferences().stream()
//                .map(PhpReference::getFQN)
//                .collect(Collectors.toList());
        return null;
    }

    public static boolean isFunctionReference(PsiElement psiElement, String functionName) {
        FunctionReference method = PsiTreeUtil.getParentOfType(psiElement, FunctionReference.class);
        if (method.getName().equals(functionName))
            return true;
        else return false;
    }

}
