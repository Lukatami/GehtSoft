import org.example.CustomList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomListInterfaceTest {

    static Stream<Supplier<List<String>>> listProviders() {
        return Stream.of(
                () -> new ArrayList<>(),
                () -> new CustomList<>()
        );
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    void testConstructor (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        assertTrue(list.isEmpty(), "Список должен быть пустым после создания");
        assertEquals(0, list.size(), "Размер списка должен быть 0 после создания");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testAddString (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        assertFalse(list.isEmpty(), "Список не пустой");
        assertEquals(1, list.size(),"Список содержит 1 элемент");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testAddAll (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        List<String> additional = List.of("one", "two", "three");
        assertTrue(list.addAll(additional), "addAll должен вернуть true при успешном добавлении");
        assertEquals("two", list.get(1),"Второй элемент - two");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testAddReturnsTrue (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        assertTrue(list.add("Test"), "Метод add должен возвращать true");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testMultipleAddInt (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }
        assertEquals(100, list.size(),"Список содержит 100 элементов");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testAddIndex (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        list.add("Test1");
        list.add(1,"Test2");
        assertEquals("[Test, Test2, Test1]", list.toString(), "Вставка по индексу корректна");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testGet (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        list.add("Test1");
        list.add("Test2");
        assertEquals("Test", list.get(0), "Запрашиваемый индекс элемента имеет значение Test");
        assertEquals("Test2", list.get(2), "Запрашиваемый индекс элемента имеет значение Test2");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testGetOutOfBounds (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(1);
        },"Запрошенный индекс вне допустимого диапазона");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        }, "Запрашиваемый индекс меньше 0 должен вызывать исключение");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testGetNull (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        list.add(null);
        assertNull(list.get(1), "Запрашиваемый элемент имеет значение null");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testSet (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        list.set(0,"NewTest");
        assertEquals("NewTest", list.get(0), "Измененный элемент имеет значение NewTest");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testSetOutOfBounds (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set(1, "NewTest");
        },"Запрошенный индекс вне допустимого диапазона");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set(-1, "NewTest");
        }, "Запрашиваемый индекс меньше 0 должен вызывать исключение");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testSetNull (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        list.set(0,null);
        assertNull(list.get(0), "Значение null корректно принимается");
        assertNull(list.get(0), "Запрашиваемый элемент имеет значение null");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testRemoveByIndex (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        list.add("Test1");
        list.add("Test2");
        assertEquals("[Test, Test1, Test2]", list.toString());
        list.remove(1);
        assertEquals("[Test, Test2]", list.toString(), "Элемент 1 удалился, а Элемент 2 сдвинулся");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testRemoveByObject (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();

        list.add("Test");
        list.add("Test1");
        list.add("Test2");
        assertEquals("[Test, Test1, Test2]", list.toString(), "Изначальное состояние списка");

        boolean removed1 = list.remove("Test1");
        assertTrue(removed1, "Метод должен вернуть true при успешном удалении");
        assertEquals("[Test, Test2]", list.toString(), "Элемент Test1 удален, остальные сдвинулись");

        boolean removed2 = list.remove(null);
        assertFalse(removed2, "Метод должен вернуть false, если элемент не найден");
        assertEquals("[Test, Test2]", list.toString(), "Состояние списка не изменилось");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testRemoveAll (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        list.add("Test1");
        list.add("Test2");
        list.add("Test5");

        List<String> toRemove = List.of("Test1", "Test5", "Test10");

        boolean removed = list.removeAll(toRemove);
        assertTrue(removed, "Метод должен вернуть true, если хотя бы один элемент удален");
        assertEquals(List.of("Test", "Test2"), list, "После удаления остались только 'Test' и 'Test2'");

        boolean removedAgain = list.removeAll(toRemove);
        assertFalse(removedAgain, "Метод должен вернуть false, если ничего не было удалено");
        assertEquals(List.of("Test", "Test2"), list, "Список остался без изменений");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testRemoveOutOfBounds (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(1);
        },"Запрошенный индекс вне допустимого диапазона");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        }, "Запрашиваемый индекс меньше 0 вне допустимого диапазона");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testRetainAll (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        list.add("Test1");
        list.add("Test2");
        list.add("Test5");

        List<String> toRetain = List.of("Test1", "Test5", "Test10");

        boolean retained = list.retainAll(toRetain);
        assertTrue(retained, "Метод должен вернуть true, если список был изменен");
        assertEquals(List.of("Test1", "Test5"), list, "После изменения остались только 'Test1' и 'Test5'");

        boolean retainAgain = list.retainAll(toRetain);
        assertFalse(retainAgain, "Метод должен вернуть false, если ничего не было изменено");
        assertEquals(List.of("Test1", "Test5"), list, "Список остался без изменений");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testClear (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        list.clear();
        assertTrue(list.isEmpty(), "Список пуст");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testClearOnEmptyList(Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        assertTrue(list.isEmpty(), "Новый список должен быть пустым");

        list.clear();

        assertTrue(list.isEmpty(), "После очистки пустой список должен оставаться пустым");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testContains (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        assertTrue(list.contains("Test"), "Список содержит искомое Test");
        assertFalse(list.contains("Test1"), "Список не содержит искомое Test1");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testContainsAll(Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add(null);

        List<String> subList1 = List.of("A", "C");
        List<String> subList2 = List.of("A", "D");

        List<String> subList3 = new ArrayList<>();
        subList3.add(null);

        assertTrue(list.containsAll(subList1), "Список должен содержать все элементы из subList1");
        assertFalse(list.containsAll(subList2), "Список не содержит все элементы из subList2");
        assertTrue(list.containsAll(subList3), "Список должен содержать null");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testContainsNull (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add(null);
        assertTrue(list.contains(null), "Список содержит искомое null");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testIndexOf (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        list.add("Test1");
        assertEquals(1, list.indexOf("Test1"), "Элемент Test1 найден с индексом 1");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testIndexOfDouble (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        list.add("Test1");
        list.add("Test1");
        assertEquals(1, list.indexOf("Test1"), "Первое вхождение элемента Test1 найдено с индексом 1");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testIndexOfNotFound (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test0");
        list.add("Test1");
        assertEquals(-1, list.indexOf("Test2"), "Элемента Test2 не найдено");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testIndexOfNull (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test0");
        list.add(null);
        assertEquals(1, list.indexOf(null), "Элемент null найден с индексом 1");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testToStringEmpty (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        assertEquals("[]", list.toString(), "В списке нет элементов");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testToStringNull (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        list.add("Test");
        list.add(null);
        list.add("Test2");
        assertEquals("[Test, null, Test2]", list.toString(), "null корректно воспринимается");
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testToArray (Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();
        Object[] emptyArray = list.toArray();
        assertNotNull(emptyArray, "toArray не должен возвращать null");
        assertEquals(0, emptyArray.length, "Массив пустого списка должен иметь длину 0");

        list.add("A");
        list.add("B");
        list.add("C");
        list.add(null);

        Object[] array = list.toArray();

        assertNotNull(array, "toArray не должен возвращать null");
        assertEquals(list.size(), array.length, "Размер массива должен совпадать с размером списка");
        for (int i = 0; i < array.length; i++) {
            assertEquals(list.get(i), array[i], "Элемент массива и списка на позиции " + i + " должны совпадать");
        }
    }

    @ParameterizedTest
    @MethodSource("listProviders")
    public void testToArrayWithParameter(Supplier<List<String>> listSupplier) {
        List<String> list = listSupplier.get();

        list.add("A");
        list.add("B");
        list.add("C");
        list.add(null);

        String[] arrayExact = new String[list.size()];
        String[] resultExact = list.toArray(arrayExact);

        assertSame(arrayExact, resultExact, "Если массив нужного размера, должен возвращаться тот же массив");
        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i), resultExact[i], "Элемент массива и списка на позиции " + i + " должны совпадать");
        }

        String[] arraySmaller = new String[2];
        String[] resultNew = list.toArray(arraySmaller);

        assertNotSame(arraySmaller, resultNew, "Если переданный массив меньше размера, должен быть создан новый массив");

        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i), resultNew[i], "Элемент нового массива и списка на позиции " + i + " должны совпадать");
        }

        String[] arrayLarger = new String[list.size() + 2];
        String[] resultLarger = list.toArray(arrayLarger);

        assertSame(arrayLarger, resultLarger, "Если массив больше размера, возвращается тот же массив");

        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i), resultLarger[i], "Элемент массива и списка на позиции " + i + " должны совпадать");
        }

        assertNull(resultLarger[list.size()], "Элемент после последнего должен быть null");
    }
}
