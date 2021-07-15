package implementations;

import interfaces.Deque;

import java.util.Iterator;

public class ArrayDeque<E> implements Deque<E> {
    private static final int DEFAULT_CAPACITY = 7;
    private Object[] elements;
    private int size;
    private int head;
    private int tail;

    public ArrayDeque() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.head = this.elements.length / 2;
        this.tail = this.head;
    }

    @Override
    public void add(E element) {
        this.addLast(element);
    }

    private void grow() {
        Object[] newElements = new Object[this.elements.length * 2];
        int middle = newElements.length / 2;
//        for (int i = this.elements.length / 2 + this.head - 1; i < middle + (this.tail - this.elements.length / 2); i++) {
//
//
//        }
        for (int i = middle; i < middle + this.size; i++) {
            newElements[i] = this.elements[this.head++];
        }
        this.head = middle;
        this.tail = middle + this.size - 1;
        this.elements = newElements;
    }

    @Override
    public void offer(E element) {
        this.addFirst(element);
    }

    @Override
    public void addFirst(E element) {
        if (this.size == 0) {
            this.elements[this.head] = element;
        } else {
            if (this.head == 0) {
                this.grow();
            }
            this.elements[--this.head] = element;

        }
    }

    @Override
    public void addLast(E element) {
        if (this.size == 0) {
            this.elements[this.tail] = element;
        } else {
            if (this.tail == this.elements.length - 1) {
                this.grow();
            }
            this.elements[++this.tail] = element;
        }
        this.size++;
    }

    @Override
    public void push(E element) {
        this.addLast(element);
    }

    @Override
    public void insert(int index, E element) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(index);
        }
        if(index == 0){
            this.addFirst(element);
        } else if(index == this.size){
            this.addLast(element);
        } else {
            if(this.tail == this.elements.length + 1){
                this.grow();
            }
            this.tail++;
            for (int i = this.tail + 1; i >= this.head + index; i--) {
                this.elements[i] = this.elements[i - 1];
            }
            this.elements[this.head + index] = element;
        }

    }

    @Override
    public void set(int index, E element) {

    }

    @Override
    public E peek() {
        return (E) this.elements[this.head];
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public E get(int index) {
        return (E) this.elements[this.head + index];
    }

    @Override
    public E get(Object object) {
        return null;
    }

    @Override
    public E remove(int index) {
        E element = null;
        if(this.isEmpty()){
            return null;
        }
        if(index == 0){
             element = (E)this.elements[this.head];
            this.head++;
        } else if (index == this.size - 1){
             element =(E)this.elements[this.tail];
            this.tail--;
        } else {
            for (int i = this.head + index; i < this.tail; i++) {
                this.elements[i] = this.elements[i -1];
            }
            this.elements[this.tail] = null;
            this.tail--;
        }
        return element;
    }

    @Override
    public E remove(Object object) {
        return null;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return 0;
    }

    @Override
    public void trimToSize() {

    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
