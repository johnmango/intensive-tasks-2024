package com.walking.intensive.chapter5.task20;


/**
 * Создайте метод, возвращающий определитель матрицы (R) ранга N и дополнительный метод валидации,
 * который будет определять, что матрица является квадратной и у нее может быть рассчитан определитель.
 *
 * <p>Описание основного метода:
 *
 * <ul>
 * <li>Метод может реализовывать рекурсивный алгоритм подсчета определителя (A или <i>det A</i>)
 *      разложением по элементам первой строки. Можно ознакомиться
 *      <a href="https://portal.tpu.ru/SHARED/k/KONVAL/Sites/Russian_sites/2/06_e1.htm">здесь</a>.
 * <li>Метод должен проверять, является ли матрица квадратной.
 * <li>Метод должен принимать в качестве аргумента двумерный массив.
 * <li>В случае некорректных входных данных метод должен возвращать null.
 * </ul>
 *
 * <p>P.S. Алгоритмы для расчета могут быть как рекурсивными, так и нет.
 *
 * <p>P.P.S. Решение не должно использовать встроенные методы сортировки, коллекции,
 * Stream API и иной материал, выходящий за рамки пройденного курса.
 *
 * <p><a href="https://github.com/KFalcon2022/intensive-tasks-2024/blob/master/README.md">Требования к оформлению</a>
 */
public class Task20 {
    public static void main(String[] args) {
//        Для собственных проверок можете делать любые изменения в этом методе
        int[][] matrix = {{3, 2, 4},
                         {2, 2, 1, 5},
                         {0, 2, 3}};
        int[][] matrix2 =   {{3, 2, 4, 8, 7},
                            {1, 4, 3, 7, 9},
                            {6, 2, 1, 5, 5},
                            {0, 4, 2, 1, 3},
                            {6, 4, 2, 8, 9}};
        Integer determinant = getDeterminant(matrix);
        Integer determinant2 = getDeterminant(matrix2);
        System.out.println(determinant);
        System.out.println(determinant2);

    }

    /**
     * Входное значение - матрица, представленная в виде двумерного массива.
     *
     * <p>Пояснение о типе Integer: это ссылочный тип - класс-обертка над int.
     * В основном курсе вы сможете ознакомиться с такими типами подробнее.
     * Вы можете работать со значением Integer как с обычной переменной типа int,
     * но также у нее есть и другие возможности. В данной задаче нам этот тип нужен только
     * для возможности вернуть в случае ошибки не числовое значение - null.
     *
     * <p>Позже вы познакомитесь с концепцией исключений, что упростит обработку ошибок.
     * До тех пор приходится находить обходные пути для обозначения ситуаций, когда что-то пошло не по плану.
     */
    static Integer getDeterminant(int[][] matrix) {
        // Ваш код
        if (!isValid(matrix)) {
            return null;
        }

        int length = matrix.length;
        if (length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        int determinant = 0;
        int sign = 1;

        for (int i = 0; i < length; i++) {
            int[][] smallerMatrix = getSmallerMatrix(matrix, i);
            determinant += sign * matrix[0][i] * getDeterminant(smallerMatrix);
            sign *= -1;
        }

        return determinant;
    }

    /**
     * Входное значение - валидируемая матрица, представленная в виде двумерного массива.
     *
     * <p>Метод должен возвращать true, если у матрицы может быть рассчитан определитель. В противном случае - false.
     *
     * <p>Логика валидации должна быть определена в этом методе, чтобы не усложнять логику getDeterminant(). При этом
     * getDeterminant() должен использовать isValid().
     */
    static boolean isValid(int[][] matrix) {
        if (matrix == null || matrix.length < 2) {
            return false;
        }

        int length = matrix.length;
        for (int[] line : matrix) {
            if (line.length != length) {
                return false;
            }
        }

        return true;
    }

    static int[][] getSmallerMatrix(int [][] matrix, int columnToIgnore) {
        int length = matrix.length;
        int[][] smallerMatrix = new int[length - 1][length - 1];

        for (int y = 1; y < length; y++) {
            for (int x = 0; x < length - 1; x++) {
                int correctedX = x >= columnToIgnore ? x + 1 : x;
                smallerMatrix[y - 1][x] = matrix[y][correctedX];
            }
        }

        return smallerMatrix;
    }
}
