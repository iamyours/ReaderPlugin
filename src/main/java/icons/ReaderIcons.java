package icons;

import com.intellij.execution.testframework.sm.runner.ui.SMPoolOfTestIcons;
import com.intellij.icons.AllIcons;
import com.intellij.util.IconUtil;

import javax.swing.*;

public interface ReaderIcons {
    Icon LOGO = AllIcons.General.InspectionsEye;
    Icon NEXT = IconUtil.getMoveDownIcon();
    Icon RE_RUN_Failed = AllIcons.RunConfigurations.RerunFailedTests;
    Icon PASSED = AllIcons.RunConfigurations.TestPassed;
}
