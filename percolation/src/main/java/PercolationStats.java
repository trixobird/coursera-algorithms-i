import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] mean;
    private final int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        mean = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int nextSiteToOpen = (int) StdRandom.uniform(1,  Math.pow(n, 2));
                while (percolation.isOpen(getRow(n, nextSiteToOpen), getCol(n, nextSiteToOpen))) {
                    nextSiteToOpen = (int) StdRandom.uniform(1, Math.pow(n, 2));
                }
                percolation.open(getRow(n, nextSiteToOpen), getCol(n, nextSiteToOpen));
            }
            mean[i] = percolation.numberOfOpenSites();
        }
    }

    private int getCol(int n, int nextSiteToOpen) {
        return ((nextSiteToOpen - 1) % n) + 1;
    }

    private int getRow(int n, int nextSiteToOpen) {
        return ((nextSiteToOpen - 1) / n) + 1;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(mean);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(mean);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / StrictMath.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / StrictMath.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        throw new UnsupportedOperationException();
    }
}
