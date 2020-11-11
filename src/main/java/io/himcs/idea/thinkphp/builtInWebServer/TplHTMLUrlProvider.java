package io.himcs.idea.thinkphp.builtInWebServer;

import com.intellij.ide.browsers.OpenInBrowserRequest;
import com.intellij.ide.browsers.WebBrowserUrlProvider;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.util.Url;
import com.intellij.util.Urls;
import io.himcs.idea.thinkphp.framework.ThinkPHPUtil;
import io.himcs.idea.thinkphp.settings.ThinkPHPSettingsState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TPL 路径下 html文件浏览器中打开
 */
public class TplHTMLUrlProvider extends WebBrowserUrlProvider {
    @Override
    protected @Nullable Url getUrl(@NotNull OpenInBrowserRequest request, @NotNull VirtualFile file) throws BrowserException {

        String path = file.getPath();
        ThinkPHPSettingsState settingsState = ThinkPHPSettingsState.getInstance(request.getProject());
        Pattern pattern = Pattern.compile(".*/" + settingsState.TplPath + "/(\\w+)/" + settingsState.theme + "/(\\w+)/(\\w)\\.html");
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            String group = matcher.group(1);
            String action = matcher.group(2);
            String method = matcher.group(3);
            return Urls.newHttpUrl("", ThinkPHPUtil.getUrl0(request.getProject(), group, action, method));
        } else {
            return super.getUrl(request, file);
        }
    }

    @Override
    public boolean canHandleElement(@NotNull OpenInBrowserRequest request) {
        PsiFile file = request.getFile();
        if (Objects.isNull(file))
            return false;
        ThinkPHPSettingsState settingsState = ThinkPHPSettingsState.getInstance(request.getProject());
        String path = file.getVirtualFile().getPath();
        boolean isInTplPath = path.contains(settingsState.TplPath) && path.contains(settingsState.theme);
        System.out.println(path + ":" + isInTplPath);
        return isInTplPath;
    }
}
