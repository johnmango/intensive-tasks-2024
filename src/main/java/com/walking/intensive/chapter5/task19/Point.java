package com.walking.intensive.chapter5.task19;

public class Point {
    private final int x;
    private final int y;
    private final int z;

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    /**
     * метод вернет массив из 3х int. Это координаты по оси X, Y и Z.
     */
    public int[] getCoordinatesArray() {
        return new int[]{x, y, z};
    }
}
