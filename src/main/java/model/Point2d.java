package model;

import java.util.*;

public class Point2d {
    private final double x;
    private final double y;

    public Point2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2d point2d = (Point2d) o;
        return Double.compare(point2d.x, x) == 0 &&
                Double.compare(point2d.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static Optional<Point2d> center(List<Point2d> points) {
        OptionalDouble newX = points.stream().mapToDouble(x -> x.x).average();
        OptionalDouble newY = points.stream().mapToDouble(x -> x.y).average();

        if (newX.isPresent() && newY.isPresent()) {
            return Optional.of(new Point2d(newX.getAsDouble(), newY.getAsDouble()));
        } else {
            return Optional.empty();
        }
    }
}
