package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {
    private Node<E> head;
    private int size;

    public static class Node<E>{
        private E element;
        private Node<E> next;

        public Node(E element) {
            this.element = element;
        }
    }

    public Queue() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void offer(E element) {
        Node<E> newNode = new Node<E>(element);
        if(this.size == 0) {
            this.head = newNode;
        } else{
            Node<E> current = this.head;
            while (current.next != null){
                current = current.next;
            }
            current.next = newNode;
            current = newNode;
        }
        this.size++;
    }

    @Override
    public E poll() {
        ensureNonEmpty("poll");
        E element = this.head.element;
        this.head = this.head.next;
        this.head.next = null;
        this.size--;
        return element;
    }

    private void ensureNonEmpty(String operation) {
        if(this.isEmpty()){
            throw new IllegalStateException("Cannot" + operation + "on empty queue");
        }
    }

    @Override
    public E peek() {
        ensureNonEmpty("peek");
        return this.head.element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E element = current.element;
                current = current.next;
                return element;
            }
        };
    }
}
