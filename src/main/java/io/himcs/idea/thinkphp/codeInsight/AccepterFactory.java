package io.himcs.idea.thinkphp.codeInsight;

import com.intellij.psi.PsiElement;
import de.espend.idea.laravel.util.PsiElementUtils;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.GotoCompletionAccepter;

import java.util.Objects;

public class AccepterFactory {
    public static GotoCompletionAccepter getFnctionAccepter(String functionName) {

        return (psiElement) -> {
            if (Objects.isNull(psiElement)) {
                return false;
            }
            PsiElement parent = psiElement.getParent();
            return parent != null && PsiElementUtils.isFunctionReference(parent, functionName, 0);
        };
    }
}
