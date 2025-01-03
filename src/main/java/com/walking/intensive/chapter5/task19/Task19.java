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
        Point a = new Point(0,0,0);
        Point b = new Point(10,2,10);
        Parallelepiped parallelepiped = new Parallelepiped(a, b);

        Point center = new Point(5,4,-2);
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
        boolean[] isCenterWithinLimits = isPointInsideSegment(
                 parallelepiped.getA(), parallelepiped.getB(), sphereCenter);
        int trues = getTrueCount(isCenterWithinLimits);

        if (trues == 3) {
            // центр сферы попадает в границы параллелепипеда по всем трем проекциям.
            // Значит центр сферы внутри параллелепипеда.
            return true;
        }

        //Случай 2. Сфера цепляет одну из вершин параллелепипеда
        Point[] vertices = parallelepiped.getAllVertices();
        for (Point vertex : vertices) {
            if (getDistance(sphereCenter, vertex) <= sphereRadius) {
                return true;
            }
        }

        if (trues == 0) {
            // сфера не достает радиусом ни одну из вершин, и ее центр находится снаружи параллелепипеда
            // во всех проекциях - пересечения не может быть
            return false;
        }

        Point[] auxPoints = parallelepiped.getAuxiliaryPoints();
        if (trues == 1) {
            // Случай 3. Сфера цепляет одно из ребер параллелепипеда.
            // для этого нужно попадание центра сферы между двух точек параллелепипеда в одной проекции
            // и рсстояние от центра сферы до ребра меньше радиуса. Расстояние от центра сферы до
            // 4х ребер проверяется на плоскости.

            for (int i = 0; i < 3; i++) {
                if (isCenterWithinLimits[i]) {
                    // возможно пересечение ребра. проверим расстояние от центра сферы до aux points
                    for (Point auxPoint : auxPoints) {
                        if (getDistance(auxPoint, sphereCenter, i) <= sphereRadius) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        // Случай 4. Сфера цепляет грань. trues=2. Найдем плоскость, в которой центр не лежит в границах
        // проекций параллелепипеда. Это та, где isCenterWithinLimits = false
        for (int i = 0; i < 3; i++) {
            if (!isCenterWithinLimits[i]) {
                // нужно попадание любой точки параллелепипеда в пределы диаметра сферы по выбранной оси
                for (Point auxPoint : auxPoints) {
                    if (isWithinRadius(sphere, auxPoint, i)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    static double getDistance (Point a, Point b) {
        // вычисление расстояния между двумя точками
        int x1 = a.getX();
        int x2 = b.getX();
        int y1 = a.getY();
        int y2 = b.getY();
        int z1 = a.getZ();
        int z2 = b.getZ();

        return Math.sqrt(
                Math.pow(x2 - x1, 2)
                + Math.pow(y2 - y1, 2)
                + Math.pow(z2 - z1, 2));
    }

    static double getDistance (Point a, Point b, int axeToIgnore) {
        //метод вернет расстояние между двумя точками на плоскости, умножая на 0 одно из измерений
        int[] pointA = getCoordinatesArray(a);
        int[] pointB = getCoordinatesArray(b);
        int[] axeMultiplier = {1, 1, 1};
        axeMultiplier[axeToIgnore] = 0;

        double intermediateResult = 0;
        for (int i = 0; i < 3; i++) {
            intermediateResult += axeMultiplier[i] * Math.pow(pointB[i] - pointA[i], 2);
        }

        return Math.sqrt(intermediateResult);
    }

    static boolean[] isPointInsideSegment(Point segmentA, Point segmentB, Point point) {
        // метод возвращает массив из 3х элементов: X, Y, Z. Если точка point попадает в проекцию segment
        // по оси X, Y или Z, то элемент массива 0, 1 или 2 соответственно - будет true

        boolean[] pointInsideSegment = new boolean[3];

        if (point.getX() >= segmentA.getX() && point.getX() <= segmentB.getX()) {
            pointInsideSegment[0] = true;
        }
        if (point.getY() >= segmentA.getY() && point.getY() <= segmentB.getY()) {
            pointInsideSegment[1] = true;
        }
        if (point.getZ() >= segmentA.getZ() && point.getZ() <= segmentB.getZ()) {
            pointInsideSegment[2] = true;
        }

        return pointInsideSegment;
    }

    static int getTrueCount(boolean[] booleans) {
        // метод возвращает количество true в массиве booleans
        int trueCount = 0;
        for (boolean bool : booleans) {
            if (bool) {
                trueCount++;
            }
        }

        return trueCount;
    }

    static int[] getCoordinatesArray(Point point) {
        return new int[] {point.getX(), point.getY(), point.getZ()};
    }

    static boolean isWithinRadius(Sphere sphere, Point point, int axeToCheck) {
        //метод вернет true, осли координата находится в пределах диаметра
        // (проекции сферы на любую плоскость)

        int radius = sphere.getRadius();
        int[] centerCoordinate = getCoordinatesArray(sphere.getCenter());
        int[] pointCoordinate = getCoordinatesArray(point);

        return pointCoordinate[axeToCheck] >= centerCoordinate[axeToCheck] - radius &&
                pointCoordinate[axeToCheck] <= centerCoordinate[axeToCheck] + radius;
    }
}
