import util.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame {
    private JPanel MainPanel;
    private JTable InputField;
    private JTable OutputField;
    private JButton ExecuteButtonDfs;
    private JButton ExecuteButtonBfs;
    private JSpinner spinnerStartX;
    private JSpinner spinnerStartY;
    private JSpinner spinnerFinishX;
    private JSpinner spinnerFinishY;

    public MainForm() {
        this.setTitle("Path Finding");
        int[][] sample = new int[][]{
                {0, 0, -1, 0, 0},
                {0, -1, 0, 0, 0},
                {0, -1, 0, -1, 0},
                {0, 0, -1, -1, 0},
                {0, 0, 0, 0, 0}
        };
        this.setContentPane(MainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        // this.setSize(new Dimension(600, 600));
        JTableUtils.initJTableForArray(InputField, 100, true, true, true, true);
        JTableUtils.initJTableForArray(OutputField, 100, true, true, true, true);

        OutputField.setRowHeight(25);
        InputField.setRowHeight(25);
        OutputField.setEnabled(false);
        JTableUtils.writeArrayToJTable(InputField, sample);

        this.pack();

        ExecuteButtonBfs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MazeData maze = new MazeData(JTableUtils.readIntMatrixFromJTable(InputField));
                    int startRow = (int) spinnerStartY.getValue();
                    int startCol = (int) spinnerStartX.getValue();
                    int finishCol = (int) spinnerFinishX.getValue();
                    int finishRow = (int) spinnerFinishY.getValue();
                    JTableUtils.writeArrayToJTable(OutputField,
                            maze.showMazeWithWay(
                                    maze.findWay(new Cell(startRow, startCol),
                                            new Cell(finishRow, finishCol),
                                            MazeData.TypeOfAlgorithm.BfsWithQueue))
                    );
                } catch (Exception exception) {
                    SwingUtils.showInfoMessageBox("Error");
                }
            }

        });


        ExecuteButtonDfs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MazeData maze = new MazeData(JTableUtils.readIntMatrixFromJTable(InputField));
                    int startRow = (int) spinnerStartY.getValue();
                    int startCol = (int) spinnerStartX.getValue();
                    int finishCol = (int) spinnerFinishX.getValue();
                    int finishRow = (int) spinnerFinishY.getValue();
                    JTableUtils.writeArrayToJTable(OutputField,
                            maze.showMazeWithWay(
                                    maze.findWay(new Cell(startRow, startCol),
                                            new Cell(finishRow, finishCol),
                                            MazeData.TypeOfAlgorithm.DfsWithRecurs))
                    );
                } catch (Exception exception) {
                    SwingUtils.showInfoMessageBox("Error");
                }
            }
        });

    }

}
