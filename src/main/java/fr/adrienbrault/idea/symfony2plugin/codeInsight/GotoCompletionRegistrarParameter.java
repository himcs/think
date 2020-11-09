package fr.adrienbrault.idea.symfony2plugin.codeInsight;

import com.intellij.patterns.ElementPattern;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface GotoCompletionRegistrarParameter {
    public void register(GotoCompletionAccepter accepter, GotoCompletionContributor contributor);
}
