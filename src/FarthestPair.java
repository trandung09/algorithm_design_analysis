import java.util.Scanner;

public class FarthestPair {

    // farthest pair of points and distance
    private Point2D best1, best2;
    private double bestDistanceSquared = Double.NEGATIVE_INFINITY;

    /**
     * Computes the farthest pair of points in the specified array of points.
     *
     * @param  points the array of points
     * @throws IllegalArgumentException if {@code points} is {@code null} or if any
     *         entry in {@code points[]} is {@code null}
     */
    public FarthestPair(Point2D[] points) {
        if (points == null) throw new IllegalArgumentException("constructor argument is null");
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("array element " + i + " is null");
        }

        GrahamScan graham = new GrahamScan(points);

        // single point
        if (points.length <= 1) return;

        // number of points on the hull
        int m = 0;
        for (Point2D p : graham.hull())
            m++;

        // the hull, in counterclockwise order hull[1] to hull[m]
        Point2D[] hull = new Point2D[m+1];
        m = 1;
        for (Point2D p : graham.hull()) {
            hull[m++] = p;
        }
        m--;

        // all points are equal
        if (m == 1) return;

        // points are collinear
        if (m == 2) {
            best1 = hull[1];
            best2 = hull[2];
            bestDistanceSquared = best1.distanceSquaredTo(best2);
            return;
        }

        // k = farthest vertex from edge from hull[1] to hull[m]
        int k = 2;
        while (Point2D.area2(hull[m], hull[1], hull[k+1]) > Point2D.area2(hull[m], hull[1], hull[k])) {
            k++;
        }

        int j = k;
        for (int i = 1; i <= k && j <= m; i++) {
            // StdOut.println("hull[i] + " and " + hull[j] + " are antipodal");
            if (hull[i].distanceSquaredTo(hull[j]) > bestDistanceSquared) {
                best1 = hull[i];
                best2 = hull[j];
                bestDistanceSquared = hull[i].distanceSquaredTo(hull[j]);
            }
            while ((j < m) && Point2D.area2(hull[i], hull[i+1], hull[j+1]) > Point2D.area2(hull[i], hull[i+1], hull[j])) {
                j++;
                // StdOut.println(hull[i] + " and " + hull[j] + " are antipodal");
                double distanceSquared = hull[i].distanceSquaredTo(hull[j]);
                if (distanceSquared > bestDistanceSquared) {
                    best1 = hull[i];
                    best2 = hull[j];
                    bestDistanceSquared = hull[i].distanceSquaredTo(hull[j]);
                }
            }
        }
    }

    /**
     * Returns one of the points in the farthest pair of points.
     */
    public Point2D either() {
        return best1;
    }

    /**
     * Returns the other point in the farthest pair of points.
     */
    public Point2D other() {
        return best2;
    }

    /**
     * Returns the Euclidean distance between the farthest pair of points.
     * This quantity is also known as the <em>diameter</em> of the set of points.
     */
    public double distance() {
        return Math.sqrt(bestDistanceSquared);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\nTran Van Dung - 221230773\n");
        int n = sc.nextInt();
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            points[i] = new Point2D(x, y);
        }
        System.out.println();
        FarthestPair farthest = new FarthestPair(points);
        String s = String.format("%.2f", farthest.distance());
        System.out.println("Dist = " + s + " from " + farthest.either() + " to " + farthest.other());

        System.out.println("\n");
        sc.close();
    }

}