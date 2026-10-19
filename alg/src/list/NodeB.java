package list;

class NodeB<V> {
    public V value;
    public NodeB<V> next;
    public NodeB<V> prev;

    public NodeB(V value, NodeB<V> next, NodeB<V> prev) {
        this.value = value;
        this.next = next;
        this.prev = prev;
    }
}
