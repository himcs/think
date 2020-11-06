package io.himcs.sdk.util;

import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.jetbrains.php.lang.psi.PhpPsiElementFactory;
import com.jetbrains.php.lang.psi.elements.*;

import java.util.Arrays;
import java.util.Collections;

public class PhpElementsUtilTest extends BasePlatformTestCase {
    public void setUp() throws Exception {
        super.setUp();
        myFixture.copyFileToProject("AdminAction.class.php");
    }

    @Override
    public String getTestDataPath() {
        return "src/test/java/io/himcs/sdk/util/fixtures";
    }


    public void testGetMethodParameterTypeHints() {
        Method mock = PhpPsiElementFactory.createMethod(getProject(), "function foo(\\DateTime $e) {}");
        assertEquals(Collections.singletonList("\\DateTime"), PhpElementsUtil.getMethodParameterTypeHints(mock));
        assertEquals(Arrays.asList("\\DateTime", "\\DateInterval"), PhpElementsUtil.getMethodParameterTypeHints(
                PhpPsiElementFactory.createMethod(getProject(), "function foo(\\DateTime|\\DateInterval $e) {}")
        ));
        assertEquals(Collections.singletonList("\\Iterator"), PhpElementsUtil.getMethodParameterTypeHints(
                PhpPsiElementFactory.createMethod(getProject(), "function foo(/* foo */ \\Iterator $a, \\DateTime $b")
        ));
        assertEmpty(PhpElementsUtil.getMethodParameterTypeHints(
                PhpPsiElementFactory.createMethod(getProject(), "function foo(/* foo */ $a, \\DateTime $b")
        ));

    }

    public void testIsFunctionReference() {

        FunctionReference methodReference = PhpPsiElementFactory.createFunctionReference(getProject(), "foo('a')");
        PsiElement psiElement = methodReference.getParameter(0);
        String functionName = "foo";
        assertEquals(true, PhpElementsUtil.isFunctionReference(psiElement, functionName));
    }

}
