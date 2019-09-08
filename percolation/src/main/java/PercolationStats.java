import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_FACTOR = 1.96;
    private final double[] means;
    private final int trials;
    private double mean;
    private double stdDev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;
        means = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int nextSiteToOpen = (int) StdRandom.uniform(1,  Math.pow(n, 2) + 1);
                while (percolation.isOpen(getRow(n, nextSiteToOpen), getCol(n, nextSiteToOpen))) {
                    nextSiteToOpen = (int) StdRandom.uniform(1, Math.pow(n, 2) + 1);
                }
                percolation.open(getRow(n, nextSiteToOpen), getCol(n, nextSiteToOpen));
            }
            means[i] = percolation.numberOfOpenSites() / Math.pow(n, 2);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return cachedMean();
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return cachedStddev();
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return cachedMean() - CONFIDENCE_FACTOR * cachedStddev() / StrictMath.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return cachedMean() + CONFIDENCE_FACTOR * cachedStddev() / StrictMath.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        percolationStats.output();
    }

    private int getCol(int n, int nextSiteToOpen) {
        return ((nextSiteToOpen - 1) % n) + 1;
    }

    private int getRow(int n, int nextSiteToOpen) {
        return ((nextSiteToOpen - 1) / n) + 1;
    }

    private void output() {
        System.out.println(String.format("mean                    = %f", cachedMean()));
        System.out.println(String.format("stddev                  = %f", cachedStddev()));
        System.out.println(String.format("95%% confidence interval = [%f, %f]", confidenceLo(), confidenceHi()));
    }

    private double cachedMean() {
        if (mean == 0) {
            mean = StdStats.mean(means);
        }
        return mean;
    }

    private double cachedStddev() {
        if  (stdDev == 0) {
            stdDev = StdStats.stddev(means);
        }
        return stdDev;
    }
}
