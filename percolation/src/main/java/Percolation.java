import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int size;
    private final WeightedQuickUnionUF unionFind;
    private final boolean[] open;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        open = new boolean[(int) (Math.pow(size, 2) + 2)];
        open[0] = true;
        open[open.length - 1] = true;
        for (int i = 1; i < open.length - 1; i++) {
            open[i] = false;
        }

        WeightedQuickUnionUF uf = new WeightedQuickUnionUF((int) (Math.pow(n, 2) + 2));
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
        }
        for (int i = (int) (Math.pow(n, 2) - n + 1); i <= Math.pow(n, 2); i++) {
            uf.union((int) (Math.pow(n, 2) + 1), i);
        }
        unionFind = uf;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        validateSquare(row, col);
        open[to1D(row, col)] = true;

        if (col > 1 && open[to1D(row, col - 1)]) {
            unionFind.union(to1D(row, col), to1D(row, col - 1));
        }
        if (col < size && open[to1D(row, col + 1)]) {
            unionFind.union(to1D(row, col), to1D(row, col + 1));
        }
        if (row > 1 && open[to1D(row - 1, col)]) {
            unionFind.union(to1D(row, col), to1D(row - 1, col));
        }
        if (row < 1 && open[to1D(row + 1, col)]) {
            unionFind.union(to1D(row, col), to1D(row + 1, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        validateSquare(row, col);
        return open[to1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        validateSquare(row, col);
        return unionFind.connected(0, to1D(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (boolean b : open) {
            if (b) {
                count++;
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.connected(0, (int) Math.pow(size, 2) + 1);
    }

    // UncommentedEmptyMethodBody
    public static void main(String[] args) {
        throw new UnsupportedOperationException();
    }

    private void validateSquare(int row, int col) {
        validateSide(row);
        validateSide(col);
    }

    private void validateSide(int side) {
        if (side > size
                || side <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private int to1D(int row, int col) {
        return size * (col - 1) + row;
    }
}
