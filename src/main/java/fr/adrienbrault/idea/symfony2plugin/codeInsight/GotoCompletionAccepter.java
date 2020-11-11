package fr.adrienbrault.idea.symfony2plugin.codeInsight;

import com.intellij.psi.PsiElement;

public interface GotoCompletionAccepter {
    boolean accepts(PsiElement psiElement);
}
