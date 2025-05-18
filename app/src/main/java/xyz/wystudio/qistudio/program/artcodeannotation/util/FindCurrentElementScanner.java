package xyz.wystudio.qistudio.program.artcodeannotation.util;

import com.tiecode.compiler.api.descriptor.Position;
import com.tiecode.compiler.source.tree.ClassTree;
import com.tiecode.compiler.source.tree.EventTree;
import com.tiecode.compiler.source.tree.MethodTree;
import com.tiecode.compiler.source.tree.VariableTree;
import com.tiecode.compiler.source.util.TreePath;
import com.tiecode.compiler.source.util.TreePathScanner;

public class FindCurrentElementScanner extends TreePathScanner<TreePath, Position> {
    @Override
    public TreePath visitClass(ClassTree tree, Position find) {
        TreePath subPath = super.visitClass(tree, find);
        if (subPath != null) {
            return subPath;
        }
        Position pos = tree.getPosition();
        if (inSide(pos, find)) {
            return getCurrentPath();
        }
        return subPath;
    }

    @Override
    public TreePath visitMethod(MethodTree tree, Position find) {
        TreePath subPath = super.visitMethod(tree, find);
        if (subPath != null) {
            return subPath;
        }
        Position pos = tree.getPosition();
        if (inSide(pos, find)) {
            return getCurrentPath();
        }
        return subPath;
    }

    @Override
    public TreePath visitEvent(EventTree tree, Position find) {
        TreePath subPath = super.visitEvent(tree, find);
        if (subPath != null) {
            return subPath;
        }
        Position pos = tree.getPosition();
        if (inSide(pos, find)) {
            return getCurrentPath();
        }
        return subPath;
    }

    @Override
    public TreePath visitVariable(VariableTree tree, Position find) {
        Position pos = tree.getPosition();
        if (inSide(pos, find)) {
            return getCurrentPath();
        }
        return super.visitVariable(tree, find);
    }

    @Override
    public TreePath reduce(TreePath a, TreePath b) {
        if (a != null) return a;
        return b;
    }

    private boolean inSide(Position treePos, Position cursor) {
        int startLine = treePos.getStartLine();
        int startColumn = treePos.getStartColumn();
        int endLine = treePos.getEndLine();
        int endColumn = treePos.getEndColumn();
        if (startLine <= cursor.getStartLine() && endLine > cursor.getStartLine()) {
            return true;
        } else if (startLine == cursor.getStartLine() || endLine == cursor.getStartLine()) {
            return startColumn <= cursor.getStartColumn() && endColumn >= cursor.getStartColumn();
        }
        return false;
    }
}
