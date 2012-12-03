/*
 * GenericEditor2.java
 *
 * Created on November 16, 2006, 2:41 PM
 */

package blue.ui.core.orchestra.editor;

import java.util.HashMap;

import javax.swing.Action;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

import org.syntax.jedit.tokenmarker.JavaScriptTokenMarker;
import org.syntax.jedit.tokenmarker.PythonTokenMarker;
import org.syntax.jedit.tokenmarker.TokenMarker;

import skt.swing.SwingUtil;
import blue.BlueSystem;
import blue.actions.RedoAction;
import blue.actions.UndoAction;
import blue.gui.CsoundTokenMarker;
import blue.gui.InfoDialog;
import blue.orchestra.GenericInstrument;
import blue.orchestra.Instrument;
import blue.orchestra.PythonInstrument;
import blue.orchestra.RhinoInstrument;
import blue.orchestra.editor.GenericEditable;
import blue.orchestra.editor.InstrumentEditor;
import blue.ui.core.udo.EmbeddedOpcodeListPanel;
import blue.ui.nbutilities.MimeTypeEditorComponent;
import blue.ui.utilities.SimpleDocumentListener;
import blue.undo.NoStyleChangeUndoManager;
import blue.undo.TabSelectionWrapper;

/**
 * 
 * @author steven
 */
public class GenericEditor extends InstrumentEditor {

    private static HashMap tokenMarkerTypes = new HashMap();
    
    protected MimeTypeEditorComponent codeEditor = 
            new MimeTypeEditorComponent("text/x-csound-orc");

    static {
        tokenMarkerTypes.put(GenericInstrument.class, new CsoundTokenMarker());
        tokenMarkerTypes.put(PythonInstrument.class, new PythonTokenMarker());
        tokenMarkerTypes
                .put(RhinoInstrument.class, new JavaScriptTokenMarker());
    }

    GenericEditable instr;

    EmbeddedOpcodeListPanel udoPanel = new EmbeddedOpcodeListPanel();

    UndoManager undo = new NoStyleChangeUndoManager();

    /** Creates new form GenericEditor2 */
    public GenericEditor() {
        initComponents();
        
        tabs.insertTab("Instrument Text", null, codeEditor, null, 0);
        
        tabs.add(BlueSystem.getString("instrument.udo"), udoPanel);
        
        tabs.setSelectedIndex(0);

        globalOrcEditPane.getDocument().addDocumentListener(
                new SimpleDocumentListener() {
                    public void documentChanged(DocumentEvent e) {
                        if (instr != null) {
                            instr.setGlobalOrc(globalOrcEditPane.getText());
                        }
                    }
                });

        globalScoEditPane.getDocument().addDocumentListener(
                new SimpleDocumentListener() {
                    public void documentChanged(DocumentEvent e) {
                        if (instr != null) {
                            instr.setGlobalSco(globalScoEditPane.getText());
                        }
                    }
                });

        codeEditor.getDocument().addDocumentListener(
                new SimpleDocumentListener() {
                    public void documentChanged(DocumentEvent e) {
                        if (instr != null) {
                            instr.setText(codeEditor.getText());
                        }
                    }
                });

        UndoableEditListener ul = new UndoableEditListener() {

            public void undoableEditHappened(UndoableEditEvent e) {
                UndoableEdit event = e.getEdit();

                if (event.getPresentationName().equals("style change")) {
                    undo.addEdit(event);
                } else {
                    TabSelectionWrapper wrapper = new TabSelectionWrapper(
                            event, tabs);
                    undo.addEdit(wrapper);
                }
            }

        };

        codeEditor.getDocument().addUndoableEditListener(ul);
        globalOrcEditPane.getDocument().addUndoableEditListener(ul);
        globalScoEditPane.getDocument().addUndoableEditListener(ul);

        Action[] undoActions = new Action[] { new UndoAction(undo),
                new RedoAction(undo) };

        //SwingUtil.installActions(textEditPane, undoActions);
        SwingUtil.installActions(globalOrcEditPane, undoActions);
        SwingUtil.installActions(globalScoEditPane, undoActions);

        codeEditor.setUndoManager(undo);
        
        undo.setLimit(1000);

    }

    public final void editInstrument(Instrument instr) {
        if (instr == null) {
            this.instr = null;
            editorLabel.setText(BlueSystem
                    .getString("instrument.noEditorAvailable"));
            codeEditor.setText("Null Instrument");
            codeEditor.getJEditorPane().setEnabled(false);
            return;
        }

        if (!(instr instanceof GenericEditable)) {
            this.instr = null;
            editorLabel.setText(BlueSystem
                    .getString("instrument.noEditorAvailable"));
            codeEditor
                    .setText("[ERROR] GenericEditor::editInstrument - not instance of GenericEditable");
            codeEditor.getJEditorPane().setEnabled(false);
            return;
        }

//        Object marker = tokenMarkerTypes.get(instr.getClass());
//
//        if (marker != null) {
//            textEditPane.setTokenMarker((TokenMarker) marker);
//        }

        editorLabel.setText("Generic Editor - Type: "
                + instr.getClass().getName());

        this.instr = (GenericEditable) instr;

        codeEditor.setText(this.instr.getText());
        codeEditor.getJEditorPane().setEnabled(true);
        codeEditor.getJEditorPane().setCaretPosition(0);

        globalOrcEditPane.setText(this.instr.getGlobalOrc());
        globalOrcEditPane.setEnabled(true);
        globalOrcEditPane.setCaretPosition(0);

        globalScoEditPane.setText(this.instr.getGlobalSco());
        globalScoEditPane.setEnabled(true);
        globalScoEditPane.setCaretPosition(0);

        udoPanel.editOpcodeList(this.instr.getOpcodeList());

        undo.discardAllEdits();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editorLabel = new javax.swing.JLabel();
        testButton = new javax.swing.JButton();
        tabs = new javax.swing.JTabbedPane();
        globalOrcEditPane = new blue.gui.BlueEditorPane();
        globalScoEditPane = new blue.gui.BlueEditorPane();

        editorLabel.setText("jLabel1");

        testButton.setText(BlueSystem.getString("common.test"));
        testButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testButtonActionPerformed(evt);
            }
        });

        tabs.addTab(BlueSystem.getString("global.orchestra"), globalOrcEditPane);
        tabs.addTab(BlueSystem.getString("global.score"), globalScoEditPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tabs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(editorLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
                        .addComponent(testButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(testButton)
                    .addComponent(editorLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void testButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_testButtonActionPerformed
        if (this.instr == null) {
            return;
        }
        String instrumentText = ((Instrument) this.instr).generateInstrument();
        InfoDialog.showInformationDialog(SwingUtilities.getRoot(this),
                instrumentText, BlueSystem
                        .getString("instrument.generatedInstrument"));
    }// GEN-LAST:event_testButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel editorLabel;
    private blue.gui.BlueEditorPane globalOrcEditPane;
    private blue.gui.BlueEditorPane globalScoEditPane;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JButton testButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public Class getInstrumentClass() {
        return GenericEditable.class;
    }

}
