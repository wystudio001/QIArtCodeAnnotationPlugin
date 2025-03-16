package xyz.wystudio.qistudio.program.artcodeannotation.menu;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;

import com.qiplat.open.ui.widget.dialog.QiBottomListDialog;
import com.rapid.api.component.widget.editor.Editor;
import com.rapid.api.framework.DataKeys;
import com.rapid.api.framework.common.icon.IconService;
import com.rapid.api.framework.domain.editor.BaseEditorMenuAction;

import xyz.wystudio.qistudio.program.artcodeannotation.util.AssetUtils;

public class AddAnnotationMenuAction extends BaseEditorMenuAction {
    private final String[] ANNOTATIONLIST = {"方法注释","制作绳包","如来佛祖","NeverGiveUP","神兽保佑","巨龙保佑","皮卡丘保佑"};

    @Override
    public String getId() {
        return "ID_ADD_ANNOTATION";
    }

    @Override
    public String getTitle() {
        return "插入注释";
    }

    @Override
    public Drawable getIcon() {
        return getIconService().getFileIcon(IconService.EDITOR_ACTION_SOURCE).getDrawable();
    }

    @Override
    public void perform(Editor editor) {
        QiBottomListDialog listDialog = new QiBottomListDialog(getActivity());
        listDialog.setTitle("插入注释");
        listDialog.setContentItems(ANNOTATIONLIST, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = AssetUtils.getFileContent("annotation/" + ANNOTATIONLIST[position] + ".txt");
                Editor editor = (Editor) getData(DataKeys.WINDOW);
                editor.paste(content);
                listDialog.dismiss();
            }
        });
        listDialog.show();
    }
}
