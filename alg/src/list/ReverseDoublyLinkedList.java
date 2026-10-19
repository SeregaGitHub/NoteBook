package list;

public class ReverseDoublyLinkedList {
    public static void main(String[] args) {
        NodeB<String> head2 = new NodeB<>("2", null, null);

        NodeB<String> head1 = new NodeB<>("1", head2, null);
        NodeB<String> head3 = new NodeB<>("3", null, head2);
        head2.next = head3;
        head2.prev = head1;

        printList(head1);
        System.out.println("==============================");
        printList(solution(head1));
        System.out.println("==============================");
        NodeB<String> head4 = new NodeB<>("4", null, null);
        printList(solution(head4));
    }

    private static NodeB<String> solution(NodeB<String> head) {

        NodeB<String> current = head;

        while (current != null) {
            NodeB<String> buffer = current.next;
            current.next = current.prev;
            current.prev = buffer;

            if (buffer == null) return current;
            current = buffer;
        }

        return current;
    }

    private static void printList(NodeB<String> head) {
        while (head != null) {
            System.out.println(head.value);
            head = head.next;
        }
    }
}
