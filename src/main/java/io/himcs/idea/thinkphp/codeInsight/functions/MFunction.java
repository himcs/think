package io.himcs.idea.thinkphp.codeInsight.functions;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.database.model.DasTable;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.GotoCompletionProvider;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.GotoCompletionRegistrar;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.GotoCompletionRegistrarParameter;
import io.himcs.idea.thinkphp.codeInsight.AccepterFactory;
import io.himcs.idea.thinkphp.codeInsight.Constants;
import io.himcs.idea.thinkphp.util.MDbUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * M 方法支持
 */
public class MFunction implements GotoCompletionRegistrar {

    @Override
    public void register(GotoCompletionRegistrarParameter registrar) {
        registrar.register(
                AccepterFactory.getFnctionAccepter(Constants.M),
                MFunctionCompletionProvider::new
        );
        registrar.register(
                AccepterFactory.getFnctionAccepter("D"),
                MFunctionCompletionProvider::new
        );
        registrar.register(
                AccepterFactory.getFnctionAccepter("table"),
                TabablFunctionCompletionProvider::new
        );
        registrar.register(
                AccepterFactory.getFnctionAccepter("join"),
                TabablFunctionCompletionProvider::new
        );
    }

    private static class MFunctionCompletionProvider extends GotoCompletionProvider {

        public MFunctionCompletionProvider(PsiElement element) {
            super(element);
        }

        @Override
        public @NotNull Collection<LookupElement> getLookupElements() {
            return MDbUtil.getTables(getProject())
                    .map(DasTable::getName)
                    .filter(s -> s.startsWith("tp_"))
                    .map(s -> s.substring(3))
                    .toList()
                    .parallelStream()
                    .map(LookupElementBuilder::create)
                    .collect(Collectors.toList());
        }

        @Override
        public @NotNull Collection<PsiElement> getPsiTargets(StringLiteralExpression element) {
            return null;
        }
    }

    private static class TabablFunctionCompletionProvider extends GotoCompletionProvider {

        public TabablFunctionCompletionProvider(PsiElement element) {
            super(element);
        }

        @Override
        public @NotNull Collection<LookupElement> getLookupElements() {
            return MDbUtil.getTables(getProject())
                    .map(DasTable::getName)
                    .toList()
                    .parallelStream()
                    .map(LookupElementBuilder::create)
                    .collect(Collectors.toList());
        }

        @Override
        public @NotNull Collection<PsiElement> getPsiTargets(StringLiteralExpression element) {
            return null;
        }
    }


}
