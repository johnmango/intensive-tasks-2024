package com.walking.intensive.chapter5.task19;


public class Parallelepiped {
    private final Point a;
    private final Point b;

    public Parallelepiped(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    /**
     * метод возвращает все вершины параллелепипеда
     */
    public Point[] getAllVertices() {
        int aX = a.getX();
        int bX = b.getX();

        int aY = a.getY();
        int bY = b.getY();

        int aZ = a.getZ();
        int bZ = b.getZ();

        Point[] vertices = new Point[8];
        vertices[0] = a;
        vertices[1] = new Point(bX, aY, aZ);
        vertices[2] = new Point(aX, bY, aZ);
        vertices[3] = new Point(aX, aY, bZ);
        vertices[4] = b;
        vertices[5] = new Point(aX, bY, bZ);
        vertices[6] = new Point(bX, aY, bZ);
        vertices[7] = new Point(bX, bY, aZ);

        return vertices;
    }

    /**
     * метод вернет 4 точки. Первые 3 это концы ребер, выходящих из точки А. 4я точка - это точка B
     * Через эти точки можно провести 4 ребра на все проекции.
     */
    public Point[] getAuxiliaryPoints() {
        Point[] auxPoints = new Point[4];
        auxPoints[0] = new Point(b.getX(), a.getY(), a.getZ());
        auxPoints[1] = new Point(a.getX(), b.getY(), a.getZ());
        auxPoints[2] = new Point(a.getX(), a.getY(), b.getZ());
        auxPoints[3] = b;

        return auxPoints;
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
}
