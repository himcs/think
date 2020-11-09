package io.himcs.idea.thinkphp.codeInsight.functions;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.util.indexing.FileBasedIndex;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import de.espend.idea.laravel.stub.processor.CollectProjectUniqueKeys;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.GotoCompletionProvider;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.GotoCompletionRegistrar;
import fr.adrienbrault.idea.symfony2plugin.codeInsight.GotoCompletionRegistrarParameter;
import io.himcs.idea.thinkphp.codeInsight.AccepterFactory;
import io.himcs.idea.thinkphp.stub.ConfigKeyStubIndex;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class CFunction implements GotoCompletionRegistrar {
    @Override
    public void register(GotoCompletionRegistrarParameter registrar) {
        registrar.register(
                AccepterFactory.getFnctionAccepter("C"),
                CFunction.CFunctionCompletionProvider::new
        );
    }

    private static class CFunctionCompletionProvider extends GotoCompletionProvider {

        public CFunctionCompletionProvider(PsiElement element) {
            super(element);
        }

        @Override
        public @NotNull Collection<LookupElement> getLookupElements() {
            final Collection<LookupElement> lookupElements = new ArrayList<>();

            CollectProjectUniqueKeys ymlProjectProcessor = new CollectProjectUniqueKeys(getProject(), ConfigKeyStubIndex.KEY);
            FileBasedIndex.getInstance().processAllKeys(ConfigKeyStubIndex.KEY, ymlProjectProcessor, getProject());

            for (String key : ymlProjectProcessor.getResult()) {
                lookupElements.add(LookupElementBuilder.create(key));
            }

            return lookupElements;
        }

        @Override
        public @NotNull Collection<PsiElement> getPsiTargets(StringLiteralExpression element) {
            return null;
        }
    }
}
