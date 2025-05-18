package xyz.wystudio.qistudio.program.artcodeannotation.event;

import android.widget.Toast;

import com.rapid.api.Platform;
import com.rapid.api.component.widget.window.Window;
import com.rapid.api.component.widget.window.Windows;
import com.rapid.api.framework.domain.window.WindowEvent;
import com.rapid.framework.vfs.VirtualFile;

public class AnnotationWindowEvent implements WindowEvent {
    @Override
    public void onWindowCreate(Window window, VirtualFile virtualFile) {
        //Toast.makeText(Platform.getActivity(), "打开文件", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWindowAdd(Window window) {

    }

    @Override
    public void onWindowSelect(Window window) {

    }

    @Override
    public void onWindowRemove(Window window) {

    }

    @Override
    public void onSave(Windows windows, boolean b) {

    }
}
