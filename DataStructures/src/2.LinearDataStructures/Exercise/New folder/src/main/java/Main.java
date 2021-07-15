import implementations.DoublyLinkedList;

public class Main {
    public static void main(String[] args) {
        DoublyLinkedList<Integer> linkedList = new DoublyLinkedList<>();
        linkedList.addLast(10);
        linkedList.addLast(11);
        linkedList.addLast(12);
        linkedList.removeLast();
        linkedList.removeLast();
        linkedList.removeLast();
    }
}
