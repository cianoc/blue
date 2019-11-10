/*
 * blue - object composition environment for csound
 * Copyright (c) 2000-2007 Steven Yi (stevenyi@gmail.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by  the Free Software Foundation; either version 2 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; see the file COPYING.LIB.  If not, write to
 * the Free Software Foundation Inc., 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307 USA
 */

package blue.soundObject.editor.jmask;

import blue.soundObject.jmask.ItemList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;

/**
 * 
 * @author steven
 */
public class ItemListEditor extends javax.swing.JPanel implements DurationSettable {

    ItemList itemList;

    /** Creates new form ItemListEditor */
    public ItemListEditor(ItemList itemList) {
        this.itemList = itemList;

        initComponents();

        itemsTable.setModel(itemList);
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        optionsComboBox.setSelectedIndex(itemList.getListType());
        itemList.addTableModelListener(itemsTable);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        optionsComboBox = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemsTable = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        pushDownButton = new javax.swing.JButton();
        pushUpButton = new javax.swing.JButton();

        jLabel1.setText("Item List");

        optionsComboBox.setModel(new DefaultComboBoxModel(ItemList.MODES));
        optionsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsComboBoxActionPerformed(evt);
            }
        });

        itemsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(itemsTable);

        addButton.setText("+");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        removeButton.setText("-");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        pushDownButton.setText("V");
        pushDownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pushDownButtonActionPerformed(evt);
            }
        });

        pushUpButton.setText("^");
        pushUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pushUpButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(optionsComboBox, 0, 353, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pushUpButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pushDownButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, addButton, pushDownButton, pushUpButton, removeButton);

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(optionsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(removeButton)
                    .addComponent(pushDownButton)
                    .addComponent(pushUpButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void optionsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_optionsComboBoxActionPerformed
        itemList.setListType(optionsComboBox.getSelectedIndex());
    }// GEN-LAST:event_optionsComboBoxActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addButtonActionPerformed
        itemList.addListItem(new Double(0.0), itemsTable.getSelectedRow());
    }// GEN-LAST:event_addButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_removeButtonActionPerformed
        int selectedIndex = itemsTable.getSelectedRow();

        if (selectedIndex >= 0 && selectedIndex < itemList.getRowCount()) {
            itemList.removeListItem(selectedIndex);
        }
    }// GEN-LAST:event_removeButtonActionPerformed

    private void pushDownButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_pushDownButtonActionPerformed
        int selectedIndex = itemsTable.getSelectedRow();

        if (selectedIndex >= 0 && selectedIndex < itemList.getRowCount() - 1) {
            itemList.pushDown(selectedIndex);
            itemsTable.setRowSelectionInterval(selectedIndex + 1,
                    selectedIndex + 1);
        }
    }// GEN-LAST:event_pushDownButtonActionPerformed

    private void pushUpButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_pushUpButtonActionPerformed
        int selectedIndex = itemsTable.getSelectedRow();

        if (selectedIndex > 0 && selectedIndex < itemList.getRowCount()) {
            itemList.pushUp(selectedIndex);
            itemsTable.setRowSelectionInterval(selectedIndex - 1,
                    selectedIndex - 1);
        }
    }// GEN-LAST:event_pushUpButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTable itemsTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox optionsComboBox;
    private javax.swing.JButton pushDownButton;
    private javax.swing.JButton pushUpButton;
    private javax.swing.JButton removeButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setDuration(double duration) {
        //ignore
    }

}
