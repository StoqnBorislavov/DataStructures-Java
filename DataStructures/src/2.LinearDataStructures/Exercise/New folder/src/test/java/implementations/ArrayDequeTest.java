package implementations;


import org.junit.Test;

public class ArrayDequeTest {

    @Test
    public void arrayDeque(){
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        System.out.println(deque.remove(12));
    }

}