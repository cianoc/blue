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

import blue.soundObject.jmask.Mask;
import javax.swing.SpinnerNumberModel;

/**
 * 
 * @author steven
 */
public class MaskEditor extends javax.swing.JPanel implements DurationSettable {

    private Mask mask = null;

    /** Creates new form maskEditor */
    public MaskEditor(Mask mask) {
        initComponents();

        highTableEditor.setTable(mask.getHighTable());
        lowTableEditor.setTable(mask.getLowTable());

        highSpinner.setModel(
                new SpinnerNumberModel(mask.getHigh(), Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY, 0.1));
        lowSpinner.setModel(
                new SpinnerNumberModel(mask.getLow(), Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY, 0.1));

        mapSpinner.setModel(
                new SpinnerNumberModel(mask.getMapValue(),
                Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.01));

        highTypeComboBox.setSelectedIndex(mask.isHighTableEnabled() ? 1 : 0);
        lowTypeComboBox.setSelectedIndex(mask.isLowTableEnabled() ? 1 : 0);
        
        this.mask = mask;
        
        updateDisplay();
    }
    
    private void updateDisplay() {
        highSpinner.setVisible(!this.mask.isHighTableEnabled());
        highTableEditor.setVisible(this.mask.isHighTableEnabled());
        lowSpinner.setVisible(!this.mask.isLowTableEnabled());
        lowTableEditor.setVisible(this.mask.isLowTableEnabled());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        highButtonGroup = new javax.swing.ButtonGroup();
        lowButtonGroup = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        highSpinner = new javax.swing.JSpinner();
        highTableEditor = new blue.soundObject.editor.jmask.TableEditor();
        lowSpinner = new javax.swing.JSpinner();
        lowTableEditor = new blue.soundObject.editor.jmask.TableEditor();
        jLabel2 = new javax.swing.JLabel();
        mapSpinner = new javax.swing.JSpinner();
        highTypeComboBox = new javax.swing.JComboBox();
        lowTypeComboBox = new javax.swing.JComboBox();

        jLabel1.setText("Mask");

        highSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                highSpinnerStateChanged(evt);
            }
        });

        lowSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lowSpinnerStateChanged(evt);
            }
        });

        jLabel2.setText("Map Value");

        mapSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mapSpinnerStateChanged(evt);
            }
        });

        highTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "High Value (Constant)", "High Value (Table)" }));
        highTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highTypeComboBoxActionPerformed(evt);
            }
        });

        lowTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Low Value (Constant)", "Low Value (Table)" }));
        lowTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lowTypeComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lowTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lowSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(highTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(highSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mapSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(highTableEditor, javax.swing.GroupLayout.PREFERRED_SIZE, 578, Short.MAX_VALUE)
                    .addComponent(lowTableEditor, javax.swing.GroupLayout.PREFERRED_SIZE, 578, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, highTypeComboBox, lowTypeComboBox);

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mapSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(highSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(highTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(highTableEditor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lowTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lowSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lowTableEditor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void highSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_highSpinnerStateChanged
        if (this.mask != null) {
            this.mask.setHigh(((Double) highSpinner.getValue()).doubleValue());
        }
}//GEN-LAST:event_highSpinnerStateChanged

    private void lowSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_lowSpinnerStateChanged
        if (this.mask != null) {
            this.mask.setLow(((Double) lowSpinner.getValue()).doubleValue());
        }
}//GEN-LAST:event_lowSpinnerStateChanged

private void mapSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mapSpinnerStateChanged
    if (this.mask != null) {
        this.mask.setMapValue(((Double) mapSpinner.getValue()).doubleValue());
    }
}//GEN-LAST:event_mapSpinnerStateChanged

private void highTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highTypeComboBoxActionPerformed
    if(this.mask != null) {
        this.mask.setHighTableEnabled(highTypeComboBox.getSelectedIndex() == 1);
        updateDisplay();
    }
}//GEN-LAST:event_highTypeComboBoxActionPerformed

private void lowTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lowTypeComboBoxActionPerformed
    if(this.mask != null) {
        this.mask.setLowTableEnabled(lowTypeComboBox.getSelectedIndex() == 1);
        updateDisplay();
    }
}//GEN-LAST:event_lowTypeComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup highButtonGroup;
    private javax.swing.JSpinner highSpinner;
    private blue.soundObject.editor.jmask.TableEditor highTableEditor;
    private javax.swing.JComboBox highTypeComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.ButtonGroup lowButtonGroup;
    private javax.swing.JSpinner lowSpinner;
    private blue.soundObject.editor.jmask.TableEditor lowTableEditor;
    private javax.swing.JComboBox lowTypeComboBox;
    private javax.swing.JSpinner mapSpinner;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setDuration(double duration) {
        highTableEditor.setDuration(duration);
        lowTableEditor.setDuration(duration);
    }

}
