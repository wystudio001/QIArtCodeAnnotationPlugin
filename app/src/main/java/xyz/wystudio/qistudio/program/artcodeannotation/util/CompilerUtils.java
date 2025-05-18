package xyz.wystudio.qistudio.program.artcodeannotation.util;

import com.rapid.android.ui.widget.editor.QiCodeEditor;
import com.rapid.api.component.widget.editor.Editor;
import com.tiecode.compiler.api.Compiler;
import com.tiecode.compiler.api.file.TiecodeSourceFile;
import com.tiecode.compiler.api.file.TiecodeSourceText;
import com.tiecode.compiler.api.plugin.CompilerPlugin;
import com.tiecode.compiler.source.util.TreePath;
import com.tiecode.compiler.toolchain.CompilerPlugins;
import com.tiecode.compiler.toolchain.parser.Parser;
import com.tiecode.compiler.toolchain.parser.ParserFactory;
import com.tiecode.compiler.toolchain.tree.TCTree;
import com.tiecode.compiler.toolchain.tree.descriptor.PositionImpl;

import java.io.File;

public class CompilerUtils {
    static WCompilerPlugins wCompilerPlugins;
    public static void bindCompilerPlugin(){
        wCompilerPlugins = new WCompilerPlugins();
        CompilerPlugins.assemble(wCompilerPlugins);
    }

    public static Compiler getCurrentCompiler(){
        return wCompilerPlugins.getCompiler();
    }

    static class WCompilerPlugins implements CompilerPlugin {
        final Compiler[] compiler = {null};

        @Override
        public void apply(Compiler arg) {
            compiler[0] = arg;
        }

        public Compiler getCompiler(){
            return compiler[0];
        }
    }

    public static TreePath getCurrentElementPath(Editor editor){
        Compiler compiler = CompilerUtils.getCurrentCompiler();
        Parser parser = ParserFactory.newParser(new TiecodeSourceText(editor.getVirtualFile().getName(),editor.getText().toString()), compiler.getContext());
        TCTree.TCCompilationUnit unit = parser.parseCompilationUnit();

        return new FindCurrentElementScanner().scan(unit, PositionImpl.of(editor.getCursorPosition().line, editor.getCursorPosition().character));
    }
}
