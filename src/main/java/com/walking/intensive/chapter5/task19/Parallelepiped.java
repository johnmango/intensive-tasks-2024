package com.walking.intensive.chapter5.task19;


public class Parallelepiped {
    private final Point a;
    private final Point b;

    public Parallelepiped(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    /**
     * метод возвращает массив boolean из 3х элементов: X, Y, Z.
     * Если точка point попадает в проекцию параллелепипеда по оси X, Y или Z,
     * то элемент массива 0, 1 или 2 соответственно - будет true
     */
    public boolean[] isPointWithinProjection(Point point) {
        boolean[] pointWithinProjection = new boolean[3];

        int xMin = Math.min(a.getX(), b.getX());
        int xMax = Math.max(a.getX(), b.getX());
        if (point.getX() >= xMin && point.getX() <= xMax) {
            pointWithinProjection[0] = true;
        }

        int yMin = Math.min(a.getY(), b.getY());
        int yMax = Math.max(a.getY(), b.getY());
        if (point.getY() >= yMin && point.getY() <= yMax) {
            pointWithinProjection[1] = true;
        }

        int zMin = Math.min(a.getZ(), b.getZ());
        int zMax = Math.max(a.getZ(), b.getZ());
        if (point.getZ() >= zMin && point.getZ() <= zMax) {
            pointWithinProjection[2] = true;
        }

        return pointWithinProjection;
    }

    /**
     * Метод вернет ближайшую вершину к point
     */
    public Point getNearestVertex(Point point) {
        int x = point.getX();
        int y = point.getY();
        int z = point.getZ();

        int nearestX = Math.abs(x - a.getX()) < Math.abs(x - b.getX()) ? a.getX() : b.getX();
        int nearestY = Math.abs(y - a.getY()) < Math.abs(y - b.getY()) ? a.getY() : b.getY();
        int nearestZ = Math.abs(z - a.getZ()) < Math.abs(z - b.getZ()) ? a.getZ() : b.getZ();

        return new Point(nearestX, nearestY, nearestZ);
    }
}
