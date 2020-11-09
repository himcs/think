package io.himcs.idea.thinkphp.codeInsight.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.GotoCompletionContributor;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.GotoCompletionProviderInterface;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.completion.CompletionContributorParameter;
import io.himcs.idea.thinkphp.codeInsight.GotoCompletionStrategy;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * ctrl + space 提示
 */
public class ThinkPHPCompletionContributor extends CompletionContributor {
    public ThinkPHPCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(),
                new CompletionProvider<CompletionParameters>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters completionParameters, @NotNull ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
                        PsiElement psiElement = completionParameters.getOriginalPosition();
                        if (Objects.isNull(psiElement)) {
                            return;
                        }
                        CompletionContributorParameter parameter = null;

                        for (GotoCompletionContributor contributor : GotoCompletionStrategy.getContributors(psiElement)) {
                            GotoCompletionProviderInterface formReferenceCompletionContributor = contributor.getProvider(psiElement);
                            if (Objects.isNull(formReferenceCompletionContributor)) {
                                continue;

                            }
                            completionResultSet.addAllElements(formReferenceCompletionContributor.getLookupElements());

                            if (parameter == null) {
                                parameter = new CompletionContributorParameter(completionParameters, processingContext, completionResultSet);
                            }

                            formReferenceCompletionContributor.getLookupElements(parameter);
                        }
                    }
                });

    }
}
