package io.himcs.sdk.codeInsight.completion;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.database.model.DasTable;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import io.himcs.sdk.util.MDbUtil;
import io.himcs.sdk.util.PhpElementsUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ThinkCompletionProvider extends CompletionProvider {
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        PsiElement psiElement = parameters.getOriginalPosition();
        if (Objects.isNull(psiElement))
            return;
        // 检测是否在 M 函数中
        if (isInM(psiElement)) {
            result.
                    addAllElements(MDbUtil.getTables(parameters.getPosition().getProject()).
                            map(DasTable::getName).
                            filter(s -> s.startsWith("tp_")).
                            map(s -> s.substring(3)).
                            map(LookupElementBuilder::create)
                    );
        }
    }

    public boolean isInM(PsiElement psiElement) {
        return PhpElementsUtil.isFunctionReference(psiElement.getContext(), "M");
    }
}
