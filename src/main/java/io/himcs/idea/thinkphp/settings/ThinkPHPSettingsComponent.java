// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package io.himcs.idea.thinkphp.settings;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Supports creating and managing a JPanel for the Settings Dialog.
 */
public class ThinkPHPSettingsComponent {

    private final JPanel myMainPanel;
    private JCheckBox enabled = new JBCheckBox("开启TP3 插件?");
    private final JBTextField Theme = new JBTextField();
    private final JBTextField Host = new JBTextField();
    private final JBTextField TplPath = new JBTextField();
    private final JBTextField AppPath = new JBTextField();
    private final JBTextField ConfPath = new JBTextField();
    private final JBTextField DBLinkName = new JBTextField();


    public ThinkPHPSettingsComponent() {
        myMainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("测试地址"), Host, 1, false)
                .addLabeledComponent(new JBLabel("数据库连接名称"), DBLinkName, 1, false)
                .addLabeledComponent(new JBLabel("Tpl路径"), TplPath, 1, false)
                .addLabeledComponent(new JBLabel("App路径"), AppPath, 1, false)
                .addLabeledComponent(new JBLabel("Conf路径"), ConfPath, 1, false)
                .addLabeledComponent(new JBLabel("主题名称"), Theme, 1, false)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return myMainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return Theme;
    }

    @NotNull
    public String getTheme() {
        return Theme.getText();
    }

    public void setTheme(@NotNull String newText) {
        Theme.setText(newText);
    }

    public boolean getEnabled() {
        return enabled.isSelected();
    }

    public void setEnabled(boolean enabled) {
        this.enabled.setSelected(enabled);
    }

    public String getTplPath() {
        return TplPath.getText();
    }

    public void setTplPath(String tplPath) {
        TplPath.setText(tplPath);
    }

    public String getAppPath() {
        return AppPath.getText();
    }

    public void setAppPath(String path) {
        AppPath.setText(path);
    }

    public String getConfPath() {
        return ConfPath.getText();
    }

    public void setConfPath(String path) {
        ConfPath.setText(path);
    }

    public String getHost() {
        return Host.getText();
    }

    public void setHost(String path) {
        Host.setText(path);
    }

    public String getDBLinkName() {
        return DBLinkName.getText();
    }

    public void setDBLinkName(String linkName) {
        DBLinkName.setText(linkName);
    }
}
