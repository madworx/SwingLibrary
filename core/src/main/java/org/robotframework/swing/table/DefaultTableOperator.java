/*
 * Copyright 2008 Nokia Siemens Networks Oyj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.robotframework.swing.table;

import java.awt.Point;

import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTableOperator.TableCellChooser;
import org.robotframework.swing.common.IdentifierSupport;

/**
 * @author Heikki Hulkko
 */
public class DefaultTableOperator extends IdentifierSupport implements TableOperator {
    private final JTableOperator jTableOperator;

    public DefaultTableOperator(JTableOperator jTableOperator) {
        this.jTableOperator = jTableOperator;
    }
    
    public Object getCellValue(String rowIdentifier, String columnIdentifier) {
        Point coordinates = findCell(rowIdentifier, columnIdentifier);
        return jTableOperator.getValueAt(coordinates.y, coordinates.x);
    }

    public boolean isCellSelected(String rowIdentifier, String columnIdentifier) {
        Point coordinates = findCell(rowIdentifier, columnIdentifier);
        return jTableOperator.isCellSelected(coordinates.y, coordinates.x);
    }

    public void selectCell(String rowIdentifier, String columnIdentifier) {
        Point coordinates = findCell(rowIdentifier, columnIdentifier);
        jTableOperator.selectCell(coordinates.y, coordinates.x);
    }

    public void setValueAt(Object newValue, String rowIdentifier, String columnIdentifier) {
        Point coordinates = findCell(rowIdentifier, columnIdentifier);
        jTableOperator.setValueAt(newValue, coordinates.y, coordinates.x);
    }
    
    public void changeCellObject(String row, String columnIdentifier, String newValue) {
        Point coordinates = findCell(row, columnIdentifier);
        jTableOperator.changeCellObject(coordinates.y, coordinates.x, newValue);
    }

    public void clearSelection() {
        jTableOperator.clearSelection();
    }

    public int getColumnCount() {
        return jTableOperator.getColumnCount();
    }

    public int getRowCount() {
        return jTableOperator.getRowCount();
    }

    public Object getSelectedCellValue() {
      int selectedRow = jTableOperator.getSelectedRow();
      int selectedColumn = jTableOperator.getSelectedColumn();
      return jTableOperator.getValueAt(selectedRow, selectedColumn);
    }
    
    public Object getSource() {
        return jTableOperator.getSource();
    }

    private Point findCell(String row, String columnIdentifier) {
        TableCellChooser cellChooser = createCellChooser(row, columnIdentifier);
        Point cell = jTableOperator.findCell(cellChooser);
        if (cellIsInvalid(cell))
            throw new InvalidCellException(row, columnIdentifier);
        return cell;
    }
    
    private boolean cellIsInvalid(Point cell) {
        return cell.x < 0 || cell.y < 0;
    }

    private TableCellChooser createCellChooser(String row, String columnIdentifier) {
    	if (isIndex(columnIdentifier)) {
    		return new ColumnIndexTableCellChooser(row, columnIdentifier);
    	} else {
    		return new ColumnNameTableCellChooser(row, columnIdentifier);
    	}
    }
}
