package io.himcs.sdk.codeInsight.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.patterns.PlatformPatterns;

/**
 * ctrl + space 提示
 */
public class MyCompletionContributor extends CompletionContributor {
    public MyCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(),
                new ThinkCompletionProvider());
    }
}
