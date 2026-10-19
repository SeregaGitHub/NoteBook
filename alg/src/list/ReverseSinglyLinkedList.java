package list;

public class ReverseSinglyLinkedList {
    public static void main(String[] args) {
        Node<String> node3 = new Node<>("3", null);
        Node<String> node2 = new Node<>("2", node3);
        Node<String> node1 = new Node<>("1", node2);

        printList(node1);
        System.out.println("=========================");
        printList(solution(node1));
    }

    private static Node<String> solution(Node<String> head) {
        //      1 -> 2 -> 3
        // n -- 1 -- 2

        Node<String> prev = null;
        Node<String> current = head;
        while (current != null) {
            Node<String> buffer = current.next;
            current.next = prev;
            prev = current;
            current = buffer;
        }
        return prev;
    }

    private static void printList(Node<String> head) {
        while (head != null) {
            System.out.println(head.value);
            head = head.next;
        }
    }
}
