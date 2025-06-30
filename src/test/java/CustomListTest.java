import org.example.CustomList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomListTest {

    @Test
    public void testConstructor () {
        CustomList<String> list = new CustomList<>();
        assertTrue(list.isEmpty(), "Список должен быть пустым после создания");
        assertEquals(0, list.size(), "Размер списка должен быть 0 после создания");
    }

    @Test
    public void testAddString () {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        assertFalse(list.isEmpty(), "Список не пустой");
        assertEquals(1, list.size(),"Список содержит 1 элемент");
    }

    @Test
    public void testMultipleAddInt () {
        CustomList<Integer> list = new CustomList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        assertEquals(100, list.size(),"Список содержит 100 элементов");
    }

    @Test
    public void testGet () {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        list.add("Test1");
        list.add("Test2");
        assertEquals("Test", list.get(0), "Запрашиваемый индекс элемента имеет значение Test");
        assertEquals("Test2", list.get(2), "Запрашиваемый индекс элемента имеет значение Test2");
    }
    @Test
    public void testGetOutOfBounds () {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(1);
        },"Запрошенный индекс вне допустимого диапазона");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        }, "Запрашиваемый индекс меньше 0 должен вызывать исключение");
    }

    @Test
    public void testGetNull () {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        list.add(null);
        assertNull(list.get(1), "Запрашиваемый элемент имеет значение null");
    }

    @Test
    public void testSet () {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        list.set(0,"NewTest");
        assertEquals("NewTest", list.get(0), "Измененный элемент имеет значение NewTest");
    }

    @Test
    public void testSetOutOfBounds () {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set(1, "NewTest");
        },"Запрошенный индекс вне допустимого диапазона");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set(-1, "NewTest");
        }, "Запрашиваемый индекс меньше 0 должен вызывать исключение");
    }

    @Test
    public void testSetNull () {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        list.set(0,null);
        assertNull(list.get(0), "Значение null корректно принимается");
        assertNull(list.get(0), "Запрашиваемый элемент имеет значение null");
    }

    @Test
    public void testRemove () {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        list.add("Test1");
        list.add("Test2");
        assertEquals("[Test, Test1, Test2]", list.toString());
        list.remove(1);
        assertEquals("[Test, Test2]", list.toString(), "Элемент 1 удалился, а Элемент 2 сдвинулся");
    }

    @Test
    public void testRemoveOutOfBounds () {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(1);
        },"Запрошенный индекс вне допустимого диапазона");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        }, "Запрашиваемый индекс меньше 0 вне допустимого диапазона");
    }

    @Test
    public void testClear () {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        list.clear();
        assertTrue(list.isEmpty(), "Список пуст");
    }

    @Test
    public void testContains () {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        assertTrue(list.contains("Test"), "Список содержит искомое Test");
        assertFalse(list.contains("Test1"), "Список не содержит искомое Test1");
    }

    @Test
    public void testContainsNull () {
        CustomList<String> list = new CustomList<>();
        list.add(null);
        assertTrue(list.contains(null), "Список содержит искомое null");
    }

    @Test
    public void testIndexOf () {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        list.add("Test1");
        assertEquals(1, list.indexOf("Test1"), "Элемент Test1 найден с индексом 1");
    }

    @Test
    public void testIndexOfDouble () {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        list.add("Test1");
        list.add("Test1");
        assertEquals(1, list.indexOf("Test1"), "Первое вхождение элемента Test1 найдено с индексом 1");
    }

    @Test
    public void testIndexOfNotFound () {
        CustomList<String> list = new CustomList<>();
        list.add("Test0");
        list.add("Test1");
        assertEquals(-1, list.indexOf("Test2"), "Элемента Test2 не найдено");
    }

    @Test
    public void testIndexOfNull () {
        CustomList<String> list = new CustomList<>();
        list.add("Test0");
        list.add(null);
        assertEquals(1, list.indexOf(null), "Элемент null найден с индексом 1");
    }

    @Test
    public void testToStringEmpty() {
        CustomList<String> list = new CustomList<>();
        assertEquals("[]", list.toString(), "В списке нет элементов");
    }

    @Test
    public void testToStringNull() {
        CustomList<String> list = new CustomList<>();
        list.add("Test");
        list.add(null);
        list.add("Test2");
        assertEquals("[Test, null, Test2]", list.toString(), "null корректно воспринимается");
    }
}
