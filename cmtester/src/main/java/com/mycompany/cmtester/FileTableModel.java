package com.mycompany.cmtester;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/**
 * @author GN
 * @description
 * @date 2020/9/7
 */
public class FileTableModel extends AbstractTableModel {
    private static final long serialVersionUID=106L;
    String[] columnHeader = new String[]{"Test Case ID","Test Case Name"};
    Object[][] data;
    public FileTableModel(Vector value){
        data = new Object[value.size()][2];
        for (int i = 0; i < value.size(); i++) {
            data[i][0] = i+1;
            data[i][1] = value.get(i);
        }
    }

    @Override
    public String getColumnName(int col){
        return columnHeader[col];
    }




    public int getColumnCount() {
        return columnHeader.length;
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
    public void setAllSelectValue(boolean b){
        for (int i = 0; i < data.length; i++) {
            data[i][0] = new Boolean(b);

        }
    }

    public void setValueAt(Object valueObject,int row,int col){
        if (data[0][col] instanceof Integer && !(valueObject instanceof Integer)) {
            try {
                data[row][col] = new Integer(valueObject.toString());
                fireTableCellUpdated(row, col);

            } catch (Exception e) {

            }
        } else {
            data[row][col] = valueObject;
            fireTableCellUpdated(row, col);
        }
    }

    public boolean isCellEditable(int row,int col){
        if (col<1) {
            return true;
        }else{
            return false;
        }
    }


        public int getRowCount() {
            return data.length;
        }
}
