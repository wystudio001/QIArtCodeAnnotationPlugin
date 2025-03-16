package xyz.wystudio.qistudio.program.artcodeannotation;

import android.content.res.AssetManager;

import com.rapid.framework.program.android.DvmPluginDescriptor;
import com.rapid.framework.program.android.app.AndroidPlugin;

public class App extends AndroidPlugin  {
    private static AssetManager assetManager;

    @Override
    public void onInit(DvmPluginDescriptor descriptor) {
        assetManager = descriptor.getAssetManager();
    }

    public static AssetManager getAssetManager(){
        return assetManager;
    }
}
