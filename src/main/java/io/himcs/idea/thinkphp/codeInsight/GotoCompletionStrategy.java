package io.himcs.idea.thinkphp.codeInsight;

import com.intellij.psi.PsiElement;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.GotoCompletionContributor;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.GotoCompletionLanguageRegistrar;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.GotoCompletionRegistrar;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.GotoCompletionRegistrarParameter;
import io.himcs.idea.thinkphp.codeInsight.functions.CFunction;
import io.himcs.idea.thinkphp.codeInsight.functions.MFunction;

import java.util.ArrayList;
import java.util.Collection;

public class GotoCompletionStrategy {

    private final static GotoCompletionRegistrar[] CONTRIBUTORS = new GotoCompletionRegistrar[]{
            new MFunction(),
            new CFunction()
    };


    public static Collection<GotoCompletionContributor> getContributors(final PsiElement psiElement) {
        Collection<GotoCompletionContributor> contributors = new ArrayList<>();

        GotoCompletionRegistrarParameter registrar = (pattern, contributor) -> {
            if (pattern.accepts(psiElement)) {
                contributors.add(contributor);
            }
        };

        for (GotoCompletionRegistrar register : CONTRIBUTORS) {
            // filter on language
            if (register instanceof GotoCompletionLanguageRegistrar) {
                if (((GotoCompletionLanguageRegistrar) register).support(psiElement.getLanguage())) {
                    register.register(registrar);
                }
            } else {
                register.register(registrar);
            }
        }

        return contributors;
    }
}
