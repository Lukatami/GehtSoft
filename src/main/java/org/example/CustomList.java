package org.example;

import java.util.*;

public class CustomList <T> implements List<T> {                //Класс с имплементацией
    private T[] items;                                          //Список items с типом данных T
    private int size;                                           //Переменную size с типом данных int

    public CustomList() {
        items = (T[]) new Object[10];                           //Список Object приводим его к типу данных T, длиной 10
        size = 0;                                               //Устанавливаем начальное количество элементов массива равный 0
    }

    @Override
    public int size() {                                         //Метод проверки размера массива
        return size;                                            //При запросе возвращаем размер массива
    }

    @Override
    public boolean isEmpty() {                                  //Метод проверяющий пуст ли список
        return size == 0;                                       //Возвращаем true если список пуст
    }

    @Override
    public boolean add(T element) {                             //Метод добавления объекта в массив
        if (size == items.length) {                             //Проверяем возможность добавления. Если размер массива
            T[] newItems = (T[]) new Object[items.length * 2];  //равен длине списка, то удваиваем длину списка
            for (int i = 0; i < items.length; i++) {            //Копируем старые элементы в новый массив
                newItems[i] = items[i];
            }
            items = newItems;                                   //Заменяем старый массив новым
        }
        items[size] = element;                                  //Новый объект кладется в первую свободную ячейку списка
        size++;                                                 //Увеличиваем количество заполненных ячеек на 1
        return true;                                            //Возвращаем true при успешном добавлении
    }

    @Override
    public void add (int index, T element) {                    //Перегружаем add метод с добавлением по индексу
        if (index < 0 || index > size) {                        //Если индекс вне диапазона выдаем исключение
            throw new IndexOutOfBoundsException ("Запрашиваемый индекс вне диапазона данных");
        }
        if (size == items.length) {                             //Проверяем возможность добавления. Если размер массива
            T[] newItems = (T[]) new Object[items.length * 2];  //равен длине списка, то удваиваем длину списка
            for (int i = 0; i < items.length; i++) {            //Копируем старые элементы в новый массив
                newItems[i] = items[i];
            }
            items = newItems;                                   //Заменяем старый массив новым
        }
        for (int i = size; i > index; i--) {                    //Сдвигаем элементы вправо
            items[i] = items[i - 1];
        }
        items[index] = element;                                 //Добавляем новый элемент
        size++;                                                 //Увеличиваем размер
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {          //Метод добавления внешнего списка в текущий
        boolean modified = false;                               //Объявляем переменную modified
        for (T element : c) {                                   //Перебор всех элементов списка с
            if (add(element)) {                                 //С добавлением в CustomList
                modified = true;                                //Если хотя бы один элемент добавлен присваиваем modified true
            }
        }
        return modified;
    }

    @Override
    public void clear() {                                       //Метод полной очистки массива и ссылок для освобождения памяти
        for (int i = 0; i < size; i++) {                        //Перебираем массив
            items[i] = null;                                    //Присваиваем каждому значению null
        }
        size = 0;                                               //Определяем размер массива 0
    }

    @Override
    public T remove(int index) {                                //Метод удаляет объект в массиве по индексу путем сдвига
        if (index < 0 || index >= size) {                       //Если индекс вне диапазона выдаем исключение
            throw new IndexOutOfBoundsException("Запрашиваемый индекс вне диапазона данных");
        }
        T removed = items[index];                               //Сохраняем в переменную removed значение удаленного элемента
        for (int i = index; i < size - 1; i++) {                //Сдвигаем массив, удаляя предыдущее значение ячейки
            items[i] = items[i + 1];
        }
        items[size - 1] = null;                                 //Определяем на месте последней записи null
        size--;                                                 //Уменьшаем размер массива
        return removed;                                         //Возвращаем значение удаленного элемента
    }

    @Override
    public boolean remove(Object o) {                           //Удаление элемента по значению
        for (int i = 0; i < size; i++) {                        //Перебираем массив с корректной обработкой null
            if (o == null ? items[i] == null : o.equals(items[i])) {
                remove(i);                                      //Первый найденный элемент удаляется
                return true;                                    //Возвращается true
            }
        }
        return false;                                           //Если элемент не найден, возвращается false
    }

    @Override
    public boolean removeAll(Collection<?> c) {                 //Метод удаляющий все совпадения с внешним списком
        boolean modified = false;                               //Объявляем переменную modified
        for (int i = 0; i < size; ) {                           //Перебираем массив определяя условие шага в теле if
            if (c.contains(items[i])) {                         //Сравниваем каждый элемент с каждым элементом внешнего списка
                remove(i);                                      //Вызываем метод remove, который удаляет, сдвигает и уменьшает размер
                modified = true;                                //При успешном удалении присваиваем modified true
            } else {
                i++;                                            //Дальше перебираем
            }
        }
        return modified;                                        //Возвращаем значение modified
    }

    @Override
    public boolean retainAll(Collection<?> c) {                 //Метод удаляет расхождения с внешним списком
        boolean modified = false;                               //Объявляем переменную modified
        for (int i = 0; i < size; ) {                           //Перебираем массив определяя условие шага в теле if
            if (!c.contains(items[i])) {                        //Сравниваем каждый элемент с каждым элементом внешнего списка
                remove(i);                                      //Вызываем метод remove, который удаляет, сдвигает и уменьшает размер
                modified = true;                                //При успешном удалении присваиваем modified true
            } else {
                i++;                                            //Дальше перебираем
            }
        }
        return modified;                                        //Возвращаем значение modified
    }

    @Override
    public T get(int index) {                                   //Метод поиска в массиве занятых ячеек по индексу
        if (index < 0 || index >= size) {                       //Если индекс вне диапазона выдаем исключение
            throw new IndexOutOfBoundsException("Запрашиваемый индекс вне диапазона данных");
        }
        return items[index];                                    //Возвращаем элемент по индексу
    }

    @Override
    public T set(int index, T element) {                        //Метод замены значения
        if (index < 0 || index >= size) {                       //Если индекс вне диапазона выдаем исключение
            throw new IndexOutOfBoundsException("Запрашиваемый индекс вне диапазона данных");
        }
        T oldElement = items[index];                            //Сохраняем в переменную oldElement значение по индексу
        items[index] = element;                                 //Заменяем ячейку новым элементом по индексу
        return oldElement;                                      //Возвращаем старый элемент
    }

    @Override
    public int indexOf(Object o) {                              //Метод возвращает индекс первого элемента
        for (int i = 0; i < size; i++) {                        //Перебираем список
            if (o == null) {
                if (items[i] == null) {                         //Если ищем null
                    return i;                                   //Первое вхождение null возвращается индексом
                }
            } else {
                if (o.equals(items[i])) {                       //Первое вхождение "о" возвращается индексом
                    return i;
                }
            }
        }
        return -1;                                              //Если "о" не найден, возвращает -1
    }

    @Override
    public int lastIndexOf(Object o) {                          //Метод возвращает индекс последнего элемента
        for (int i = size - 1; i >= 0; i--) {                   //Перебираем список с конца
            if (o == null) {
                if (items[i] == null) {                         //Условие при поиске null
                    return i;                                   //Возвращаем индекс последнего вхождения null
                }
            } else {
                if (o.equals(items[i])) {                       //Условие при поиске элемента
                    return i;                                   //Возвращаем индекс последнего вхождения элемента
                }
            }
        }
        return -1;                                              //Возвращаем -1 если элемент не найден
    }

    @Override
    public boolean contains(Object o) {                         //Метод поиска совпадения по элементу
        for (int i = 0; i < size; i++) {
            if (o == null) {                                    //Условие при поиске null
                if (items[i] == null)
                    return true;
            } else {
                if (o.equals(items[i]))
                    return true;                                //Возвращаем true если элемент найден
            }
        }
        return false;                                           //Возвращаем false, если элемент не найден
    }

    @Override
    public boolean containsAll(Collection<?> c) {               //Метод сравнения всех элементов с внешней коллекцией
        for (Object element : c) {                              //Перебор элементов массива с внешней коллекцией
            if (!this.contains(element)) {                      //Если хотя бы один элемент не найден
                return false;                                   //Возвращает false
            }
        }
        return true;                                            //Если все элементы найдены возвращает true
    }

    @Override
    public Object[] toArray() {                                 //Метод возвращает массив элементов CustomList
        Object[] result = new Object[size];                     //Новый массив
        for (int i =0; i < size; i++) {                         //Перебираем элементы и копируем в новый массив
            result[i] = items[i];
        }
        return result;                                          //Возвращает result - новый массив
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {                          //Метод возвращает массив нужного типа с элементами из списка
        if (a.length < size) {                                  //Если переданный массив меньше по размеру, чем список,
                                                                //создаем новый массив нужного типа и нужной длины
            a = (T1[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        for (int i = 0; i < size; i++) {                        //Копируем элементы из списка в массив
            a[i] = (T1) items[i];                               //Каждый элемент присваивается в новый массив
        }
        if (a.length > size) {                                  //Если переданный массив больше нужного размера
            a[size] = null;                                     //На следующую после списка позицию добавляем null
        }
        return a;                                               //Возвращаем итоговый массив
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

    private class CustomIterator implements Iterator<T> {

        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[cursor++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new CustomIterator();
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
