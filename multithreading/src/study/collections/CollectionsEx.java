package study.collections;

import java.util.*;
import java.util.concurrent.*;

public class CollectionsEx {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        Map<String, Integer> map = new HashMap<>();

/*
          У такого подхода есть существенный минус:
        синхронизация коллекции целиком приводит к большому времени ожидания.
        Если потоки постоянно считывают значения, но редко записывают, они будут блокировать друг друга.
*/

        List<String> syncList = Collections.synchronizedList(list);
        Set<Integer> syncSet = Collections.synchronizedSet(set);
        Map<String, Integer> syncMap = Collections.synchronizedMap(map);

//  Collections
        List<Integer> integers = new CopyOnWriteArrayList<>(List.of(1, 2, 3));
        Set<Integer> integerSet = new CopyOnWriteArraySet<>();
// Его работа построена на такой идее: при изменении копировать всё содержимое в новый базовый массив.

        // Очередь, в которой не гарантируется последовательность получения и вставки
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        // Очередь, в которой гарантируется последовательность получения и вставки
        BlockingQueue<Integer> accessOrderedQueue = new ArrayBlockingQueue<>(5, true);

        // Очередь LinkedBlockingQueue может быть неограниченной.
        BlockingQueue<Integer> linkedQueue = new LinkedBlockingQueue<>();

        //   ConcurrentHashSet отсутствует в стандартной библиотеке.
        // Однако можно получить его имитацию, работая лишь с ключами ConcurrentHashMap
        Map<Integer, Integer> integerMap = new ConcurrentHashMap<>();

        //   Для упорядоченных ключей предназначен интерфейс ConcurrentNavigatableMap.
        // Одна из реализацией этого интерфейса — класс ConcurrentSkipListMap.
        Map<Integer, Integer> intMap1 = new ConcurrentSkipListMap<>();

    }
}
