package implementations;


import java.util.Iterator;

public class ReversedList<E> implements Iterable<E> {
    private Object[] elements;
    private int size;
    private int head;

    public ReversedList() {
        this.elements = new Object[2];
        this.size = 0;
        this.head = this.elements.length - 1;
    }

    public void add(E element) {
        if (this.size == 0) {
            this.elements[this.head--] = element;
        } else {
            if (this.size == this.elements.length) {
                this.elements = this.grow();
            }
            this.elements[this.head--] = element;
        }
        this.size++;
    }

    public E removeAt(int index) {
        ensureIndex(index + 1);
        E element = (E) this.elements[head + 1];
        this.elements[index + 1] = null;
        for (int i = index + 1; i > this.head; i--) {
            this.elements[i] = this.elements[i - 1];
            this.elements[this.head] = null;
        }
        this.head++;
        this.size--;
        return element;

    }

    private void ensureIndex(int index) {
        if (index < 0 || index >= this.elements.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    private Object[] grow() {
        Object[] newElements = new Object[this.elements.length * 2];
        int index = 0;
        for (int i = newElements.length - this.size; i < newElements.length; i++) {
            newElements[i] = this.elements[index++];
        }
        this.head = this.size - 1;
        return newElements;
    }

    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.elements.length;
    }

    public E get(int index) {
        ensureIndex(index + head + 1);
        return (E) this.elements[index + head + 1];
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index = head + 1;

            @Override
            public boolean hasNext() {
                return index <= size + head;
            }

            @Override
            public E next() {
                return (E) elements[index++];
            }
        };
    }
}
