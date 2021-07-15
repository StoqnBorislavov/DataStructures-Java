package implementations;

import org.junit.Test;


public class ReversedListTest {

    @Test
    public void ReversedList() {
        ReversedList<Integer> list = new ReversedList<>();
        System.out.println(list.size());
        list.add(13);
        list.add(12);
        list.add(10);
        System.out.println(list.size());
        list.removeAt(2);
        System.out.println(list.size());
        for (Integer integer : list) {
            System.out.println(integer);
        }

    }

}