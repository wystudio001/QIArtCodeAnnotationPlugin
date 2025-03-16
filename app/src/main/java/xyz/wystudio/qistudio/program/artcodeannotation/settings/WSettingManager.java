package xyz.wystudio.qistudio.program.artcodeannotation.settings;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.rapid.api.Platform;
import com.rapid.api.component.page.setting.SettingsPage;
import com.rapid.api.component.widget.item.ISettingGroupView;
import com.rapid.api.framework.common.setting.AsSubSettingsInfo;
import com.rapid.api.framework.common.setting.SettingsBuilder;

public class WSettingManager implements SettingsBuilder {
    @Override
    public void build(SettingsPage page) {
        ISettingGroupView item1 = page.getOrAddSettingGroup("基础设置");
        item1.addArrowItem("自动添加注释", "设置自动添加注释功能", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Platform.getActivity(), "开发中...", Toast.LENGTH_SHORT).show();
            }
        });

        ISettingGroupView item2 = page.getOrAddSettingGroup("关于");
        item2.addArrowItem("版本号", "注释也是一种艺术！","1.0.0", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public String getParentId() {
        return ID_ROOT;
    }

    @Override
    public String getId() {
        return "ID_WANNOTATION_SETTING";
    }

    @Override
    public AsSubSettingsInfo getAsSubSettingsInfo() {
        return new AsSubSettingsInfo("插件设置","艺术注释插件","配置相关设置");
    }

    @Override
    public void onActivityResult(int i, int i1, @Nullable Intent intent) {

    }

    @Override
    public void dispose() {

    }
}
