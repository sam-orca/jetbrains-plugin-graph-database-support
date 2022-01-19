package com.neueda.jetbrains.plugin.graphdb.jetbrains.component.highlighter;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.EditorFactory;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.component.highlighter.listener.QueryHighlighterCaretListener;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.component.highlighter.listener.QueryHighlighterDocumentListener;

public class QueryHighlighterComponentImpl implements QueryHighlighterComponent {

    private QueryHighlighterCaretListener queryHighlighterCaretListener;
    private QueryHighlighterDocumentListener queryHighlighterDocumentListener;
    private SyncedElementHighlighter syncedElementHighlighter;

    @Override
    public void initComponent() {
        EditorFactory editorFactory = ApplicationManager.getApplication().getService(EditorFactory.class);

        syncedElementHighlighter = new SyncedElementHighlighter();
        queryHighlighterCaretListener = new QueryHighlighterCaretListener(syncedElementHighlighter);
        queryHighlighterDocumentListener = new QueryHighlighterDocumentListener(syncedElementHighlighter, editorFactory);

        editorFactory.getEventMulticaster().addCaretListener(queryHighlighterCaretListener);
        editorFactory.getEventMulticaster().addDocumentListener(queryHighlighterDocumentListener);
    }

    @Override
    public void disposeComponent() {
        EditorFactory editorFactory = ApplicationManager.getApplication().getService(EditorFactory.class);
        if (queryHighlighterCaretListener != null) {
            editorFactory.getEventMulticaster().removeCaretListener(queryHighlighterCaretListener);
        }
        if (queryHighlighterDocumentListener != null) {
            editorFactory.getEventMulticaster().removeDocumentListener(queryHighlighterDocumentListener);
        }
        if (syncedElementHighlighter != null) {
            syncedElementHighlighter.dispose();
        }
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "GraphDatabaseSupport.QueryHighlighter";
    }
}
