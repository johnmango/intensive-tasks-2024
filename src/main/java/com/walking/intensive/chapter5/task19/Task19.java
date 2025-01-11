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

        Point center = new Point(11, -2, 0);
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
        boolean[] isCenterWithinLimits = parallelepiped.isPointWithinProjection(sphereCenter);
        int trues = getTruesCount(isCenterWithinLimits);

//        Случай 1. Центр сферы внутри параллелепипеда (попадает в границы параллелепипеда
//        по всем трем проекциям).
        if (trues == 3) {
            return true;
        }

        int sphereRadius = sphere.getRadius();
        Point nearestVertex = parallelepiped.getNearestVertex(sphereCenter);

//        Случай 2. Сфера цепляет одну из вершин параллелепипеда
//        сравниваем расстояние от центра до ближайшей вершины
        if (getDistance(sphereCenter, nearestVertex, -1) <= sphereRadius) {
            return true;
        }

//        Случай 3. Сфера цепляет одно из ребер параллелепипеда.
//        для этого нужно попадание центра сферы между двух точек параллелепипеда в одной проекции
//        и расстояние от центра сферы до ближайшего ребра меньше радиуса.
        if (trues == 1) {
            for (int projection = 0; projection < 3; projection++) {
                if (isCenterWithinLimits[projection]) {
//                    возможно пересечение ребра. проверим расстояние от центра сферы до ближайшей вершины
//                    на плоскости, перпендикулярной текущей оси (=расстояние до ближайшего ребра)
                    if (getDistance(sphereCenter, nearestVertex, projection) <= sphereRadius) {
                        return true;
                    }
                }
            }

            return false;
        }

//        Случай 4. Сфера цепляет грань. Найдем ось, на которой центр не лежит в границах
//        проекций параллелепипеда. Это та, где isCenterWithinLimits = false
        if (trues == 2) {
            for (int projection = 0; projection < 3; projection++) {
                if (!isCenterWithinLimits[projection]) {
//                    нужно попадание ближайшей точки параллелепипеда в пределы диаметра сферы по выбранной оси
                    if (isWithinRadius(sphere, nearestVertex, projection)) {
                        return true;
                    }
                }
            }
        }

        return false;
//        случай 3 и 4 можно объединить в один код, добавляя if (т.к.циклы очень похожи),
//        но тогда читаемость кода будет хуже
    }

    /**
     * метод вернет расстояние между двумя точками в пространстве, если axeToIgnore != 0, 1 или 2.
     * Если axeToIgnore = 0, 1 или 2, то соответствующая координата будет умножена на 0, и метод
     * вернет расстояние между двумя проекциями точек на плоскость, перпендикулярную axeToIgnore
     */
    static double getDistance(Point a, Point b, int axeToIgnore) {
        int[] pointA = a.getCoordinatesArray();
        int[] pointB = b.getCoordinatesArray();
        int[] axeMultiplier = {1, 1, 1};
        if (axeToIgnore >= 0 && axeToIgnore <= 3) {
            axeMultiplier[axeToIgnore] = 0;
        }

        double intermediateResult = 0;
        for (int i = 0; i < 3; i++) {
            intermediateResult += axeMultiplier[i] * Math.pow(pointB[i] - pointA[i], 2);
        }

        return Math.sqrt(intermediateResult);
    }

    /**
     * метод возвращает количество true в массиве booleans
     */
    static int getTruesCount(boolean[] booleans) {
        int trueCount = 0;
        for (boolean bool : booleans) {
            if (bool) {
                trueCount++;
            }
        }

        return trueCount;
    }

    /**
     * метод вернет true, если координата точки по указанной оси находится в пределах
     * диаметра сферы по этой же оси.
     */
    static boolean isWithinRadius(Sphere sphere, Point point, int axeToCheck) {
        int radius = sphere.getRadius();
        int[] centerCoordinate = sphere.getCenter().getCoordinatesArray();
        int[] pointCoordinate = point.getCoordinatesArray();

        return pointCoordinate[axeToCheck] >= centerCoordinate[axeToCheck] - radius &&
                pointCoordinate[axeToCheck] <= centerCoordinate[axeToCheck] + radius;
    }
}
