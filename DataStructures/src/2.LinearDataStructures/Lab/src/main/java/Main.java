import implementations.Queue;
import implementations.SinglyLinkedList;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<Integer> queue = new SinglyLinkedList<>();
        queue.addLast(1);
        queue.addLast(2);
        queue.addLast(3);
        for (Integer integer : queue) {
            System.out.println(integer);
        }
    }
}
