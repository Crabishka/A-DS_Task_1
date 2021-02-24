import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MazeData {

    int[][] field;

    public MazeData(int[][] field) {
        this.field = field;
    }

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    //  Pair<x,y>
    //  Pair<key,value>
    //  Pair<column,row>

    enum TypeOfAlgorithm {
        DfsWithRecurs,
        BfsWithQueue;
    }

    public List<Cell> findWay(Cell start, Cell finish, TypeOfAlgorithm type) {
        int[][] waveField = this.field.clone();
        switch (type) {
            case BfsWithQueue: {
                mazeBfsWithQueue(waveField, start.getRow(), start.getCol());
                return makeReturnWay(waveField, finish);
            }
            case DfsWithRecurs: {
                mazeDfsWithRecurs(waveField, start.getRow(), start.getCol(), 1); // пустили волну
                return makeReturnWay(waveField, finish);
            }
            default: {
                return new ArrayList<>();
            }
        }

    }

    // в глубину
    private static void mazeDfsWithRecurs(int[][] maze, int row, int col, int step) {
        int rowCount = maze.length;
        int colCount = maze[0].length;

        if (row < 0 || row >= rowCount ||
                col < 0 || col >= colCount) {
            return;
        }
        if (maze[row][col] != 0) {
            return;
        }
        maze[row][col] = step;
        mazeDfsWithRecurs(maze, row - 1, col, step + 1);
        mazeDfsWithRecurs(maze, row, col - 1, step + 1);
        mazeDfsWithRecurs(maze, row, col + 1, step + 1);
        mazeDfsWithRecurs(maze, row + 1, col, step + 1);
    }

    // алгоритм Дмитрия Ивановича
    private static void mazeBfsWithQueue(int[][] maze, int startRow, int startCol) {
        int rowCount = maze.length;
        int colCount = maze[0].length;

        Queue<Cell> cells = new LinkedList<>();
        // "отмечаем" и заносим в очередь начальную клетку
        maze[startRow][startCol] = 1;
        cells.add(new Cell(startRow, startCol, 1));
        while (!cells.isEmpty()) {
            Cell cell = cells.poll();
            // обход непосещенных смежных клеток
            for (int r = cell.row - 1; r <= cell.row + 1; r++) {
                for (int c = cell.col - 1; c <= cell.col + 1; c++) {
                    if (0 <= r && r < rowCount && 0 <= c && c < colCount &&
                            (r == cell.row || c == cell.col) &&
                            maze[r][c] == 0) {
                        // "отмечаем" и заносим в очередь смежную с cell клетку
                        maze[r][c] = cell.step + 1;
                        cells.add(new Cell(r, c, cell.step + 1));
                    }
                }
            }
        }
    }

    private List<Cell> makeReturnWay(int[][] field, Cell finish) {
        List<Cell> way = new ArrayList<>();
        int rowCount = this.field.length;
        int colCount = this.field[0].length;
        int[][] waveField = this.field.clone();
        Cell currentCell = new Cell(finish.getRow(), finish.getCol(), waveField[finish.getRow()][finish.getCol()]);


        for (int i = 0; i < waveField[finish.getRow()][finish.getCol()]; i++) {  // проще написать 4 повторения
            for (int c = currentCell.getCol() - 1; c <= currentCell.getCol() + 1; c++)
                for (int r = currentCell.getRow() - 1; r <= currentCell.getRow() + 1; r++) {
                    if (c >= 0
                            && c < colCount
                            && r >= 0
                            && r < rowCount
                            && (c != currentCell.getCol() || r != currentCell.getRow())
                            && (c == currentCell.getCol() || r == currentCell.getRow())
                            && (currentCell.getStep() - 1 == waveField[r][c])) {
                        currentCell = new Cell(r, c, waveField[r][c]);
                        way.add(currentCell);
                        break;
                    }
                }
        }
        return way;
    }

    public int[][] showMazeWithWay(List<Cell> way) {
        int[][] maze = this.field.clone();
        for (Cell cell : way) {
            maze[cell.getRow()][cell.getCol()] = 1;
        }
        return  maze;
    }


}


