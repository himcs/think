package io.himcs.idea.thinkphp.types;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.resolve.types.PhpType;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider4;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;

public class PhpStaticFactoryTypeProvider implements PhpTypeProvider4 {
    @Override
    public char getKey() {
        return 'D';
    }

    @Override
    public @Nullable PhpType getType(PsiElement psiElement) {
        if (!isD(psiElement)) {
            return null;
        }
        FunctionReference fun = (FunctionReference) psiElement;
//        ParameterList parameterList = fun.getParameterList();
//        PsiElement[] parameters = parameterList.getParameters();
//        String moduleName = "";
        String clsRef = "CLSREFPRVIDER";
        PhpType type = PhpType.builder().add(clsRef).build();
        return type;

    }

    private boolean isD(PsiElement psiElement) {
        return psiElement instanceof FunctionReference && "D".equals(((FunctionReference) psiElement).getName());
    }

    @Override
    public @Nullable PhpType complete(String s, Project project) {
        return null;
    }

    @Override
    public Collection<? extends PhpNamedElement> getBySignature(String s, Set<String> set, int i, Project project) {
        return null;
    }
}
