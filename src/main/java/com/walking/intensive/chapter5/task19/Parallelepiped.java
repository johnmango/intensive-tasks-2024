package com.walking.intensive.chapter5.task19;


public class Parallelepiped {
    private final Point a;
    private final Point b;

    public Parallelepiped(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public Point[] getAllVertices() {
        //метод возвращает все вершины параллелепипеда
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

    public Point[] getAuxiliaryPoints() {
        // метод вернет 4 точки. Первые 3 это концы ребер, выходящих из точки А. 4я точка - это точка B
        Point[] auxPoints = new Point[4];
        auxPoints[0] = new Point(b.getX(), a.getY(), a.getZ());
        auxPoints[1] = new Point(a.getX(), b.getY(), a.getZ());
        auxPoints[2] = new Point(a.getX(), a.getY(), b.getZ());
        auxPoints[3] = b;

        return auxPoints;
    }
}
