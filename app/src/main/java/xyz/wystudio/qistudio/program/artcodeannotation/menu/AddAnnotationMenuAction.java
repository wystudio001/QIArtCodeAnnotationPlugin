package xyz.wystudio.qistudio.program.artcodeannotation.menu;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.qiplat.open.ui.widget.dialog.QiBottomListDialog;
import com.rapid.android.ui.widget.editor.QiCodeEditor;
import com.rapid.api.component.widget.editor.Editor;
import com.rapid.api.framework.common.icon.IconService;
import com.rapid.api.framework.domain.editor.BaseEditorMenuAction;
import com.rapid.api.lang.Language;
import com.tiecode.compiler.source.tree.Tree;
import com.tiecode.compiler.source.tree.VariableTree;
import com.tiecode.compiler.source.util.TreePath;
import com.tiecode.compiler.toolchain.tree.TCTree;

import java.util.List;

import xyz.wystudio.qistudio.program.artcodeannotation.util.AssetUtils;
import xyz.wystudio.qistudio.program.artcodeannotation.util.CompilerUtils;

public class AddAnnotationMenuAction extends BaseEditorMenuAction {
    private final String[] ANNOTATIONLIST = {">>>自动注释<<<","制作绳包","如来佛祖","NeverGiveUP","神兽保佑","巨龙保佑","皮卡丘保佑"};

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
    public void perform(Editor originEditor) {
        QiCodeEditor editor = (QiCodeEditor) originEditor;
        Language language = editor.getLanguage();
        if(!language.getClass().getSimpleName().equals("TiecodeLanguage")){
            Toast.makeText(getActivity(), "只能在.t文件中插入注释", Toast.LENGTH_SHORT).show();
            return;
        }


        QiBottomListDialog listDialog = new QiBottomListDialog(getActivity());
        listDialog.setTitle("插入注释");
        listDialog.setContentItems(ANNOTATIONLIST, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    addAutoAnnotation(editor,listDialog);
                }else {
                    String content = AssetUtils.getFileContent("annotation/" + ANNOTATIONLIST[position] + ".txt");
                    editor.paste(content);
                    listDialog.dismiss();
                }
            }
        });
        listDialog.show();
    }

    @Override
    public boolean isEnabled(Editor editor) {
        return getProjectFramework().getActiveManager().getIdentifier().equals("cn.tiecode.android");
    }

    public void addAutoAnnotation(QiCodeEditor editor,QiBottomListDialog listDialog){
        TreePath path = CompilerUtils.getCurrentElementPath(editor);

        if (path.getLeaf() == null){
            Toast.makeText(getActivity(), "解析当前类失败，请重试", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (path.getLeaf().getKind()){
            case Tree.Kind.METHOD:
                TCTree.TCMethodDeclare method = (TCTree.TCMethodDeclare) path.getLeaf();
                if(method.position.getStartLine()==1 || method.position.getStartLine()==0 || !editor.getText().getLine(method.position.getStartLine() - 1).toString().trim().isEmpty()){
                    editor.insertText(method.position.getStartLine() - 1,editor.getText().getLine(method.position.getStartLine() - 1).length(),"\n");
                    editor.insertText(method.position.getStartLine(),method.position.getStartColumn(),builderMethodAnnotation(method));
                }else {
                    editor.insertText(method.position.getStartLine() - 1,method.position.getStartColumn(),builderMethodAnnotation(method));
                }
                listDialog.dismiss();
                break;
            case Tree.Kind.EVENT:
                TCTree.TCEvent event = (TCTree.TCEvent) path.getLeaf();
                if(event.position.getStartLine()==1 || event.position.getStartLine()==0 || !editor.getText().getLine(event.position.getStartLine() - 1).toString().trim().isEmpty()){
                    editor.insertText(event.position.getStartLine() - 1,editor.getText().getLine(event.position.getStartLine() - 1).length(),"\n");
                    editor.insertText(event.position.getStartLine(),event.position.getStartColumn(),builderEventAnnotation(event));
                }else {
                    editor.insertText(event.position.getStartLine() - 1,event.position.getStartColumn(),builderEventAnnotation(event));
                }
                listDialog.dismiss();
                break;
            case Tree.Kind.CLASS:
                TCTree.TCClass clazz = (TCTree.TCClass) path.getLeaf();
                if(clazz.position.getStartLine()==1 || clazz.position.getStartLine()==0 || !editor.getText().getLine(clazz.position.getStartLine() - 1).toString().trim().isEmpty()) {
                    editor.insertText(clazz.position.getStartLine(), 0, "\n");
                    editor.insertText(clazz.position.getStartLine(), clazz.position.getStartColumn(), builderClassAnnotation(clazz));
                }else {
                    editor.insertText(clazz.position.getStartLine() - 1, clazz.position.getStartColumn(), builderClassAnnotation(clazz));
                }
                listDialog.dismiss();
                break;
            case Tree.Kind.VARIABLE:
                //Toast.makeText(getActivity(), "333", Toast.LENGTH_SHORT).show();
                break;
            default:
                //Toast.makeText(getActivity(), "444", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public String builderMethodAnnotation(TCTree.TCMethodDeclare method){
        StringBuilder builder = new StringBuilder();
        List<TCTree.TCVariableDeclare> parameters = (List<TCTree.TCVariableDeclare>) method.getParameters();
        builder.append("\n    /*\n    * 【方法作用】\n    * \n");

        if (parameters != null){
            int num = 1;
            for (TCTree.TCVariableDeclare parameter : parameters) {
                String namePar = parameter.getName().toString();
                String typePar = ((TCTree.TCIdentifier)parameter.getType()).getName().toString();
                builder.append("    * @参数" + num + " " + namePar + " 为 " + typePar + "【参数" + num + "解释】\n");
                num++;
            }
        }

        if (method.getReturnType() == null){
            builder.append("    */");
        }else {
            String typeReturn = ((TCTree.TCIdentifier)method.getReturnType()).getName().toString();
            builder.append("    * \n    * @返回值为 " + typeReturn + " 【返回值解释】\n    */");
        }

        return builder.toString();
    }

    public String builderEventAnnotation(TCTree.TCEvent event){
        StringBuilder builder = new StringBuilder();
        List<TCTree.TCVariableDeclare> parameters = (List<TCTree.TCVariableDeclare>)event.getParameters();
        builder.append("\n    /*\n    * 【事件说明】\n    * \n");

        if (parameters != null){
            int num = 1;
            for (TCTree.TCVariableDeclare parameter : parameters) {
                String namePar = parameter.getName().toString();
                String typePar = ((TCTree.TCIdentifier)parameter.getType()).getName().toString();
                builder.append("    * @参数" + num + " " + namePar + " 为 " + typePar + "【参数" + num + "解释】\n");
                num++;
            }
        }

        if (event.getReturnType() == null){
            builder.append("    */");
        }else {
            String typeReturn = ((TCTree.TCIdentifier)event.getReturnType()).getName().toString();
            builder.append("    * \n    * @返回值为 " + typeReturn + " 【返回值解释】\n    */");
        }

        return builder.toString();
    }

    public String builderClassAnnotation(TCTree.TCClass clazz){
        StringBuilder builder = new StringBuilder();
        builder.append("/*\n* 【类说明】\n* \n*/");
        return builder.toString();
    }
}
