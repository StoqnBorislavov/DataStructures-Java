package implementations;

import interfaces.AbstractStack;

import java.util.Iterator;

public class Stack<E> implements AbstractStack<E> {

    public static class Node<E> {
        private E element;
        private Node<E> prev;

        Node(E element) {
            this.element = element;
        }
    }

    private Node<E> top;
    private int size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    @Override
    public void push(E element) {
        Node<E> newNode = new Node<>(element);
        newNode.prev = this.top;
        this.top = newNode;
        this.size++;
    }

    @Override
    public E pop() {
        ensureNonEmpty("pop");
        E element = this.top.element;
        this.top = this.top.prev;
        this.size--;
        return element;

    }

    private void ensureNonEmpty(String operation) {
        if (this.isEmpty()) {
            throw new IllegalStateException("Cannot" + operation + "on empty stack");
        }
    }

    @Override
    public E peek() {
        ensureNonEmpty("peek");
        return this.top.element;
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
            private Node<E> current = top;

            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public E next() {
                E element = current.element;
                this.current = this.current.prev;
                return element;
            }
        };
    }
}
