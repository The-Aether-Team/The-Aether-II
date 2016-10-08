package us.ichun.mods.ichunutil.common.module.tabula.common.math;

/**
 * Simple version of Apache's Math PolynomialFunctionLagrangeForm. All creds on calculations go to them.
 */
public class PolynomialFunctionLagrangeForm
{
    private final double x[];
    private final double y[];

    public PolynomialFunctionLagrangeForm(double x[], double y[])
    {
        this.x = new double[x.length];
        this.y = new double[y.length];
        System.arraycopy(x, 0, this.x, 0, x.length);
        System.arraycopy(y, 0, this.y, 0, y.length);
    }

    public double value(double z) {
        return evaluateInternal(x, y, z);
    }

    private static double evaluateInternal(double x[], double y[], double z) {
        int nearest = 0;
        final int n = x.length;
        final double[] c = new double[n];
        final double[] d = new double[n];
        double min_dist = Double.POSITIVE_INFINITY;
        for (int i = 0; i < n; i++) {
            // initialize the difference arrays
            c[i] = y[i];
            d[i] = y[i];
            // find out the abscissa closest to z
            final double dist = Double.longBitsToDouble(0x7fffffffffffffffl & Double.doubleToRawLongBits(z - x[i]));
            if (dist < min_dist) {
                nearest = i;
                min_dist = dist;
            }
        }

        // initial approximation to the function value at z
        double value = y[nearest];

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n-i; j++) {
                final double tc = x[j] - z;
                final double td = x[i+j] - z;
                final double divider = x[j] - x[i+j];
                // update the difference arrays
                final double w = (c[j+1] - d[j]) / divider;
                c[j] = tc * w;
                d[j] = td * w;
            }
            // sum up the difference terms to get the final value
            if (nearest < 0.5*(n-i+1)) {
                value += c[nearest];    // fork down
            } else {
                nearest--;
                value += d[nearest];    // fork up
            }
        }

        return value;
    }
}
