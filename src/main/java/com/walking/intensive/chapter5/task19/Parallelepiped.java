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

        if (point.getX() >= a.getX() && point.getX() <= b.getX()) {
            pointWithinProjection[0] = true;
        }
        if (point.getY() >= a.getY() && point.getY() <= b.getY()) {
            pointWithinProjection[1] = true;
        }
        if (point.getZ() >= a.getZ() && point.getZ() <= b.getZ()) {
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
