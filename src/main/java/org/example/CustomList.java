package org.example;

public class CustomList <T> {
    private T[] items;                                          //Создаем массив items с типом данных T
    private int size;                                           //Создаем переменную size с типом данных int

    public CustomList() {
        items = (T[]) new Object[10];                           //Создаем массив Object приводим его к типу данных T, длиной 10
        size = 0;                                               //Устанавливаем начальное количество элементов массива равный 0
    }

    public void add(T element) {                                //Создаем метод добавления объекта в массив
        if (size == items.length) {                             //Проверяем возможность добавления. Если размер занятых ячеек
            T[] newItems = (T[]) new Object[items.length * 2];  //равен длине массива, то удваиваем длину массива
            for (int i = 0; i < items.length; i++) {            //Перебираем массив в поисках свободной ячейки
                newItems[i] = items[i];
            }
            items = newItems;                                   //Добавляем объект
        }
        items[size] = element;                                  //Новый объект кладется в первую свободную ячейку массива
        size++;                                                 //Увеличиваем количество заполненных ячеек на 1
    }

    public T get(int index) {                                   //Создаем метод поиска в массиве занятых ячеек по индексу
        if (index < 0 || index >= size) {                       //Проверка на корректность запрашиваемого диапазона (не меньше 0, не больше размера)
            throw new IndexOutOfBoundsException("Запрашиваемый индекс вне диапазона данных"); //Останавливаем программу, указывая ошибку запроса
        }
        return items[index];                                    //Возвращаем индекс
    }

    public int size() {                                         //Создаем метод проверки размера массива заполненных ячеек
        return size;
    }

    public boolean isEmpty() {                                  //Создаем метод проверяющий пуст ли список
        return size == 0;
    }

    public void set(int index, T element) {                     //Создаем метод замены значения
        if (index < 0 || index >= size) {                       //Проверяем на корректность индекса
            throw new IndexOutOfBoundsException("Запрашиваемый индекс вне диапазона данных");
        }
        items[index] = element;
    }

    public void clear() {                                       //Метод полной очистки массива, в т.ч. ссылок для освобождения памяти
        for (int i = 0; i < size; i++) {
            items[i] = null;
            }
        size = 0;
    }

    public void remove(int index) {                             //Метод удаляет объект в массиве по индексу путем сдвига
        if (index < 0 || index >= size) {                       //Проверяем корректность индекса
            throw new IndexOutOfBoundsException("Запрашиваемый индекс вне диапазона данных");
        }
        for (int i = index; i < size - 1; i++) {                //Сдвигаем массив, удаляя предыдущее значение ячейки
            items[i] = items[i + 1];
        }
        items[size - 1] = null;                                 //Определяем на месте последней записи null
        size--;                                                 //Удаляем последнюю ячейку массива
    }

    @Override
    public String toString() {                                  //Создаем метод вывода значений из массива в виде строки
        StringBuilder sb = new StringBuilder();                 //Используем класс StringBuilder из-за большей эффективности, чем "+"
        sb.append("[");                                         //Вызов метода append добавляющий читаемость
        for (int i = 0; i < size; i++) {                        //Перебираем все объекты в массиве и добавляем их в выдачу
            sb.append(items[i]);
            if (i < size - 1) {
                sb.append(", ");                                //Разделяем объекты в выдаче ", "
            }
        }
        sb.append("]");
        return sb.toString();                                   //Возвращаем построенную после переборки строку
    }

    public boolean contains(T element) {                        //Метод проверки массива на наличие соответствия запросу
        for (int i = 0; i < size; i++) {                        //Перебираем массив
            if (element == null) {                              //Если ищем null, и находим, то возвращаем true
                if (items[i] == null)
                    return true;
            } else {
                if (element.equals(items[i]))                   //Ищем через equals, чтобы манипулировать конкретными значениями, а не ссылками
                    return true;                                //Если находим соответствие запросу, то возвращаем true
            }
        }
    return false;                                               //Во всех других случаях - не нашли соответствия, false
    }

    public int indexOf(T element) {                             //Метод поиска индекса по соответствию значению
        for (int i = 0; i < size; i++) {                        //Перебираем массив
            if (element == null) {                              //Если ищем null
                if (items[i] == null) {
                    return i;
                }
            } else {                                            //Если ищем элемент по соответствию
                if (element.equals(items[i])) {
                    return i;
                }
            }
        }
        return -1;                                              //Элемент не найден
    }
}
