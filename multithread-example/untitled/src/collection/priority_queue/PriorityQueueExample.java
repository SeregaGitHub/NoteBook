package collection.priority_queue;

public class PriorityQueueExample {
    public static void main(String[] args) {

/*
                PriorityBlockingQueue

BlockingQueue<Integer> pq1 = new PriorityBlockingQueue<>();
Потокобезопасная очередь с приоритетом (на базе кучи)
Универсальный выбор для большинства задач
(пул задач, планировщики с конкурентными производителями и потребителями).
Знакомый API (offer/take/poll). Использует одну блокировку (ReentrantLock) на все операции.
(Не имеет границы по размеру (риск OutOfMemoryError).)
(Не гарантирует строгий глобальный порядок при нескольких потребителях.)
----------------------------------------------------------------------
        ConcurrentSkipListSet (или ConcurrentSkipListMap)

ConcurrentSkipListSet<Integer> sls1 = new ConcurrentSkipListSet<>();
Потокобезопасное отсортированное множество на основе скиплиста
Сценарии с очень высокой конкуренцией, где блокировка PriorityBlockingQueue становится узким местом.
Чтение (и получение первого элемента) без блокировок. Вставка и удаление — на основе CAS.
Средняя сложность O(log n). Больше потребление памяти (в несколько раз по сравнению с кучей).
(Это Set, а не Queue — нужно использовать pollFirst().)
----------------------------------------------------------------------
                    DelayQueue
EXAMPLE -> DOWN !!!
Очередь с задержкой
(элементы становятся доступны только после истечения заданного времени)
Задачи с отложенным выполнением (таймауты, повторные попытки, кэши с TTL).
Потокобезопасна. Элементы должны реализовывать Delayed.
(Не совсем замена PriorityQueue, но решает смежный класс задач планирования.)
----------------------------------------------------------------------

                Collections.synchronized...

Queue<Integer> sync = Collections.synchronizedQueue(new PriorityQueue<>());
Обёртка с синхронизацией над обычной PriorityQueue
Крайне редко; только для очень низкой конкуренции или простых случаев.
Не рекомендуется для реальной многопоточности:
(грубая синхронизация на каждом вызове, низкая масштабируемость, итерация требует внешней синхронизации.)
 */

/*
                                        Рекомендации по выбору

1. PriorityBlockingQueue — выбор по умолчанию

Это прямой и наиболее очевидный аналог.
Он сохраняет семантику очереди с приоритетом и добавляет блокирующие операции (take()),
что делает его идеальным для паттерна «производитель-потребитель».
Для подавляющего большинства приложений (пулы задач, обработка событий с приоритетом)
его производительности достаточно.

2. Когда стоит присмотреться к ConcurrentSkipListSet

Только если профилирование показало, что единая блокировка PriorityBlockingQueue
является реальным узким местом при очень высокой конкуренции.
Скиплист обеспечивает неблокирующее чтение и лучшую масштабируемость,
но ценой большего потребления памяти и несколько иного API (это Set, а не Queue).
Переходить на него стоит осознанно, после измерений.

3. Важные нюансы PriorityBlockingQueue, о которых нужно помнить

Неограниченный размер: если производители работают быстрее потребителей,
очередь будет расти до исчерпания памяти.
В таких случаях нужно предусмотреть внешний контроль
(например, семафор или собственную обёртку с ограничением).

Строгий порядок только при одном потребителе:
при нескольких потоках, вызывающих poll()/take(), каждый вызов возвращает текущий минимум,
но между вызовами куча может измениться.
Глобальная последовательность обработки в порядке приоритета не гарантируется.
Если это критично, используйте одного потребителя или сериализуйте обработку.
 */

/*
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class DelayedTask implements Delayed {
    private final String name;
    private final long expireAtNanos;   // момент, когда элемент станет доступен

    DelayedTask(String name, long delayMillis) {
        this.name = name;
        this.expireAtNanos = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(delayMillis);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long remaining = expireAtNanos - System.nanoTime();
        return unit.convert(remaining, TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        return Long.compare(this.expireAtNanos,
                            ((DelayedTask) other).expireAtNanos);
    }

    @Override
    public String toString() { return name; }
}

// Создание
DelayQueue<DelayedTask> dq = new DelayQueue<>();

dq.put(new DelayedTask("retry-1", 1000));   // станет доступна через 1 секунду
dq.put(new DelayedTask("retry-2", 5000));   // через 5 секунд

// take() заблокируется до истечения задержки ближайшего элемента
DelayedTask ready = dq.take();

// poll() без ожидания: вернёт элемент только если его задержка уже истекла
DelayedTask maybe = dq.poll();              // скорее всего null сразу после вставки
 */
    }
}
