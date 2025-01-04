package com.walking.intensive.chapter5.task19;


/**
 * Создайте классы фигур: класс сфера и параллелепипед.
 * Объект «Сфера» должен задаваться по координате точки центра
 * (x;y;z), где каждая координата задается полем класса,
 * а так же радиусом R.
 *
 * <p>Объект «Параллелепипед» должен задаваться по координате точек
 * A (x1;y1;z1) и B (x2;y2;z2), где каждая координата задается полем класса.
 * AB - главная диагональ прямоугольного параллелепипеда, а ребра параллельны базису.
 *
 * <p>Создайте в main() несколько объектов сфер и параллелепипедов и напишите метод,
 * который отвечает на вопрос: пересекается (или касается) ли объект сфера с объектом параллелепипед?
 *
 * <p><a href="https://github.com/KFalcon2022/intensive-tasks-2024/blob/master/README.md">Требования к оформлению</a>
 */
public class Task19 {
    public static void main(String[] args) {
//        Для собственных проверок можете делать любые изменения в этом методе
        Point a = new Point(0, 0, 0);
        Point b = new Point(10, 8, 10);
        Parallelepiped parallelepiped = new Parallelepiped(a, b);

        Point center = new Point(10, 0, -2);
        Sphere sphere = new Sphere(center, 2);

        System.out.println(isIntersected(sphere, parallelepiped));
    }

    static boolean isIntersected(Sphere sphere, Parallelepiped parallelepiped) {
/*
  Случаи пересечения:
  1. Центр сферы внутри параллелепипеда
  2. Сфера "цепляет" одну из вершин параллелепипеда
  3. Сфера "цепляет" одно из ребер параллелепипеда
  4. Сфера "цепляет" одну из граней параллелепипеда
 */

        Point sphereCenter = sphere.getCenter();
        int sphereRadius = sphere.getRadius();

        // Случай 1. Центр сферы внутри параллелепипеда
        boolean[] isCenterWithinLimits = parallelepiped.isPointWithinProjection(sphereCenter);
        int trues = getTruesCount(isCenterWithinLimits);

        if (trues == 3) {
            // центр сферы попадает в границы параллелепипеда по всем трем проекциям.
            // Значит центр сферы внутри параллелепипеда.
            System.out.println("Центр внутри");
            return true;
        }

        // Случай 2. Сфера цепляет одну из вершин параллелепипеда
        // сравниваем расстояние от центра до каждой вершины
        Point[] vertices = parallelepiped.getAllVertices();
        for (Point vertex : vertices) {
            if (getDistance(sphereCenter, vertex) <= sphereRadius) {
                System.out.println("Вершина");
                return true;
            }
        }

        Point[] auxPoints = parallelepiped.getAuxiliaryPoints();
        if (trues == 1) {
            // Случай 3. Сфера цепляет одно из ребер параллелепипеда.
            // для этого нужно попадание центра сферы между двух точек параллелепипеда в одной проекции
            // и расстояние от центра сферы до ребра меньше радиуса. Расстояние от центра сферы до
            // 4х ребер проверяется на плоскости.

            for (int projection = 0; projection < 3; projection++) {
                if (isCenterWithinLimits[projection]) {
                    // возможно пересечение ребра. проверим расстояние от центра сферы до aux points
                    for (Point auxPoint : auxPoints) {
                        if (getDistance(auxPoint, sphereCenter, projection) <= sphereRadius) {
                            System.out.println("Ребро");
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        // Случай 4. Сфера цепляет грань. Найдем плоскость, в которой центр не лежит в границах
        // проекций параллелепипеда. Это та, где isCenterWithinLimits = false
        if (trues == 2) {
            for (int projection = 0; projection < 3; projection++) {
                if (!isCenterWithinLimits[projection]) {
                    // нужно попадание любой точки параллелепипеда в пределы диаметра сферы
                    // по выбранной оси
                    for (Point auxPoint : auxPoints) {
                        if (isWithinRadius(sphere, auxPoint, projection)) {
                            System.out.println("Грань");
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    static double getDistance(Point a, Point b) {
        // вычисление расстояния между двумя точками
        int x1 = a.getX();
        int x2 = b.getX();
        int y1 = a.getY();
        int y2 = b.getY();
        int z1 = a.getZ();
        int z2 = b.getZ();

        return Math.sqrt(Math.pow(x2 - x1, 2)
                + Math.pow(y2 - y1, 2)
                + Math.pow(z2 - z1, 2));
    }

    static double getDistance(Point a, Point b, int axeToIgnore) {
        // метод вернет расстояние между двумя точками на плоскости, умножая на 0
        // третье измерение axeToIgnore
        int[] pointA = a.getCoordinatesArray();
        int[] pointB = b.getCoordinatesArray();
        int[] axeMultiplier = {1, 1, 1};
        axeMultiplier[axeToIgnore] = 0;

        double intermediateResult = 0;
        for (int i = 0; i < 3; i++) {
            intermediateResult += axeMultiplier[i] * Math.pow(pointB[i] - pointA[i], 2);
        }

        return Math.sqrt(intermediateResult);
    }

    static int getTruesCount(boolean[] booleans) {
        // метод возвращает количество true в массиве booleans
        int trueCount = 0;
        for (boolean bool : booleans) {
            if (bool) {
                trueCount++;
            }
        }

        return trueCount;
    }

    static boolean isWithinRadius(Sphere sphere, Point point, int axeToCheck) {
        // метод вернет true, если координата точки по указанной оси находится в пределах
        // диаметра сферы по этой же оси.

        int radius = sphere.getRadius();
        int[] centerCoordinate = sphere.getCenter().getCoordinatesArray();
        int[] pointCoordinate = point.getCoordinatesArray();

        return pointCoordinate[axeToCheck] >= centerCoordinate[axeToCheck] - radius &&
                pointCoordinate[axeToCheck] <= centerCoordinate[axeToCheck] + radius;
    }
}
