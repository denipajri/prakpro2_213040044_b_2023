package biodataapp;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

class BiodataTableModel extends AbstractTableModel {
    private ArrayList<Biodata> biodataList = new ArrayList<>();
    private String[] columnNames = {"Nama", "Jenis Kelamin", "Nomor HP", "Alamat"};

    @Override
    public int getRowCount() {
        return biodataList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Biodata biodata = biodataList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return biodata.nama;
            case 1:
                return biodata.jenisKelamin;
            case 2:
                return biodata.nomorHP;
            case 3:
                return biodata.alamat;
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addBiodata(Biodata biodata) {
        biodataList.add(biodata);
        fireTableRowsInserted(biodataList.size() - 1, biodataList.size() - 1);
    }

    public void removeBiodata(int rowIndex) {
        biodataList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public Biodata getBiodata(int rowIndex) {
        return biodataList.get(rowIndex);
    }

    public ArrayList<Biodata> getBiodataList() {
        return biodataList;
    }
}
