package io.himcs.idea.thinkphp.stubs.indexes;

import com.intellij.psi.PsiFile;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import com.intellij.util.io.VoidDataExternalizer;
import com.jetbrains.php.lang.PhpFileType;
import com.jetbrains.php.lang.psi.PhpFile;
import de.espend.idea.laravel.util.ArrayReturnPsiRecursiveVisitor;
import gnu.trove.THashMap;
import io.himcs.idea.thinkphp.framework.ConfigFileUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ConfigKeyStubIndex extends FileBasedIndexExtension<String, Void> {

    public static final ID<String, Void> KEY = ID.create("io.himcs.idea.thinkphp.stub.config_keys");

    @NotNull
    @Override
    public ID<String, Void> getName() {
        return KEY;
    }

    @NotNull
    @Override
    public DataIndexer<String, Void, FileContent> getIndexer() {
        return fileContent -> {
            final Map<String, Void> map = new THashMap<>();

            PsiFile psiFile = fileContent.getPsiFile();
            if (!(psiFile instanceof PhpFile)) {
                return map;
            }

            ConfigFileUtil.ConfigFileMatchResult result = ConfigFileUtil.matchConfigFile(fileContent.getProject(), fileContent.getFile());

            // config/app.php
            // config/testing/app.php
            if (result.matches()) {
                psiFile.acceptChildren(new ArrayReturnPsiRecursiveVisitor(result.getKeyPrefix(), (key, psiKey, isRootElement) -> {
                    if (!isRootElement) {
                        map.put(key, null);
                    }
                }));
            }

            return map;
        };
    }

    @NotNull
    @Override
    public KeyDescriptor<String> getKeyDescriptor() {
        return EnumeratorStringDescriptor.INSTANCE;
    }

    @NotNull
    @Override
    public DataExternalizer<Void> getValueExternalizer() {
        return VoidDataExternalizer.INSTANCE;
    }

    @NotNull
    @Override
    public FileBasedIndex.InputFilter getInputFilter() {
        return file -> file.getFileType() == PhpFileType.INSTANCE;
    }

    @Override
    public boolean dependsOnFileContent() {
        return true;
    }

    @Override
    public int getVersion() {
        return 1;
    }


}
