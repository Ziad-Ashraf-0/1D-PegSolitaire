import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Peg
 */
public class Peg {

    class Move implements Comparable<Move> {
        int from;
        int hole;
        int to;

        Move(int from, int hole, int to) {
            this.from = from;
            this.hole = hole;
            this.to = to;
        }

        public String toString() {
            return ("(" + this.from + "," + this.hole + "," + this.to + ")");
        }

        @Override
        public int compareTo(Move o) {
            // TODO Auto-generated method stub
            return Integer.valueOf(this.from).compareTo(Integer.valueOf(o.from));
        }
    }

    int no;
    ArrayList<Integer> grid;
    ArrayList<Move> moveList;
    ArrayList<ArrayList<Integer>> unsuccessfulGrid;

    Peg(ArrayList<Integer> grid, int no) {
        this.no = no;
        this.grid = grid;
        moveList = new ArrayList<>();
        unsuccessfulGrid = new ArrayList<>();
    }

    private void printOut() {
        for (Move move : moveList) {
            System.out.println(move.toString());
        }
    }

    private void displayGrid() {
        String temp = " ";
        for (int i : grid) {
            // System.out.println(Integer.toString(i) + " ");
            temp += Integer.toString(i);

        }
        System.out.println(temp);
    }

    private void makeMove(Move move) {
        grid.set(move.from, 0);
        grid.set(move.hole, 0);
        grid.set(move.to, 1);
        moveList.add(move);

    }

    private void undoMove(Move move) {
        grid.set(move.from, 1);
        grid.set(move.hole, 1);
        grid.set(move.to, 0);
        moveList.remove(moveList.size() - 1);

    }

    private ArrayList<Move> computePossibilities() {
        ArrayList<Move> possiblities = new ArrayList<>();
        for (int i = 0; i < grid.size(); i++) {
            if (grid.get(i) == 0) {
                if (i - 2 >= 0) {
                    if ((grid.get(i - 2) == 1) && (grid.get(i - 1) == 1)) {
                        possiblities.add(new Move(i - 2, i - 1, i));
                    }
                }
                if (i + 2 <= no - 1) {
                    if ((grid.get(i + 2) == 1) && (grid.get(i + 1) == 1)) {
                        possiblities.add(new Move(i + 2, i + 1, i));
                    }
                }

            }
        }
        return possiblities;
    }

    public int getCount() {
        int count = 0;
        for (int i = 0; i < grid.size(); i++) {
            if (grid.get(i) == 1) {
                count++;
            }
        }
        return count;
    }

    private ArrayList<Integer> deepCopy(ArrayList<Integer> input) {
        ArrayList<Integer> newGrid = new ArrayList<>();
        for (Integer i : input) {
            newGrid.add(Integer.valueOf(i));
        }
        return newGrid;
    }

    public boolean solve() {
        if (unsuccessfulGrid.contains(grid)) {
            return false;
        }

        if (getCount() == 1) {
            displayGrid();
            printOut();
            return true;
        } else {
            ArrayList<Move> moves = computePossibilities();
            Collections.sort(moves);

            for (Move move : moves) {
                makeMove(move);
                if (solve()) {
                    return true;
                } else {
                    undoMove(move);
                }
            }

        }

        if (!unsuccessfulGrid.contains(grid)) {
            unsuccessfulGrid.add(deepCopy(grid));
        }

        return false;

    }

    public static void main(String[] args) {
        Integer[] line = { 1, 1, 1, 1, 1, 1, 0, 1 };
        ArrayList<Integer> grid = new ArrayList<Integer>(Arrays.asList(line));

        Peg peg = new Peg(grid, grid.size());
        peg.solve();
        if (peg.getCount() != 1) {
            System.out.println("No Solution Found!!!");
        } else {
            System.out.println("Solution Found!!!");
        }

    }

}