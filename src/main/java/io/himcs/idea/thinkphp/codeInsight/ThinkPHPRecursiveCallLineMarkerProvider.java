package io.himcs.idea.thinkphp.codeInsight;


import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.MethodImpl;
import io.himcs.idea.thinkphp.framework.ThinkPHPUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class ThinkPHPRecursiveCallLineMarkerProvider extends RelatedItemLineMarkerProvider {
    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        if (element instanceof PhpClass) {
            PhpClass phpClass = ((PhpClass) element);
            Project project = phpClass.getProject();
            Arrays.stream(PsiTreeUtil.getChildrenOfType(phpClass, MethodImpl.class)).forEach(
                    e -> {
                        if (e.getText().contains("display")) {
                            RelatedItemLineMarkerInfo markerInfo = ThinkPHPUtil.getTplPsi(project, e);
                            if (Objects.nonNull(markerInfo)) {
                                result.add(markerInfo);
                            }
                        }
                    }
            );
        }
    }
}
