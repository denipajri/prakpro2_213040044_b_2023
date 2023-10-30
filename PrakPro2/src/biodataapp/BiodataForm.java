package biodataapp;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class BiodataForm {
    private JFrame frame;
    private BiodataTableModel tableModel = new BiodataTableModel();
    private JTable table = new JTable(tableModel);
    private JTextField namaField = new JTextField(20);
    private JComboBox<String> jenisKelaminComboBox = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});
    private JTextField nomorHPField = new JTextField(15);
    private JTextArea alamatArea = new JTextArea(5, 20);

    public BiodataForm() {
        frame = new JFrame("Aplikasi Biodata");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Nama:"));
        inputPanel.add(namaField);
        inputPanel.add(new JLabel("Jenis Kelamin:"));
        inputPanel.add(jenisKelaminComboBox);
        inputPanel.add(new JLabel("Nomor HP:"));
        inputPanel.add(nomorHPField);
        inputPanel.add(new JLabel("Alamat:"));
        inputPanel.add(new JScrollPane(alamatArea));

        JButton simpanButton = new JButton("Simpan");
        simpanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (namaField.getText().isEmpty() || nomorHPField.getText().isEmpty() || alamatArea.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Harap isi semua inputan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else {
                    Biodata biodata = new Biodata();
                    biodata.nama = namaField.getText();
                    biodata.jenisKelamin = (String) jenisKelaminComboBox.getSelectedItem();
                    biodata.nomorHP = nomorHPField.getText();
                    biodata.alamat = alamatArea.getText();

                    tableModel.addBiodata(biodata);

                    namaField.setText("");
                    jenisKelaminComboBox.setSelectedIndex(0);
                    nomorHPField.setText("");
                    alamatArea.setText("");
                }
            }
        });

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Biodata biodata = tableModel.getBiodata(selectedRow);
                    biodata.nama = namaField.getText();
                    biodata.jenisKelamin = (String) jenisKelaminComboBox.getSelectedItem();
                    biodata.nomorHP = nomorHPField.getText();
                    biodata.alamat = alamatArea.getText();
                    tableModel.fireTableDataChanged();
                } else {
                    JOptionPane.showMessageDialog(frame, "Pilih baris untuk diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton hapusButton = new JButton("Hapus");
        hapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeBiodata(selectedRow);
                    namaField.setText("");
                    jenisKelaminComboBox.setSelectedIndex(0);
                    nomorHPField.setText("");
                    alamatArea.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Pilih baris untuk dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(simpanButton);
        buttonPanel.add(editButton);
        buttonPanel.add(hapusButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Apakah Anda yakin ingin keluar?",
                        "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    saveDataToFile();
                    System.exit(0);
                }
            }
        });
        loadDataFromFile();
    }

    private void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("C:\\Users\\ACER NITRO 5\\IdeaProjects\\PrakPro2\\src\\biodataapp\\biodata.txt"))) {
            oos.writeObject(tableModel.getBiodataList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:\\Users\\ACER NITRO 5\\IdeaProjects\\PrakPro2\\src\\biodataapp\\biodata.txt"))) {
            ArrayList<Biodata> biodataList = (ArrayList<Biodata>) ois.readObject();
            for (Biodata biodata : biodataList) {
                tableModel.addBiodata(biodata);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BiodataForm();
            }
        });
    }
}
