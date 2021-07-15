package implementations;

import interfaces.LinkedList;

import java.util.Iterator;

public class SinglyLinkedList<E> implements LinkedList<E> {
    private Node<E> head;
    private int size;

    public static class Node<E> {
        private E element;
        private Node<E> next;

        public Node(E element) {
            this.element = element;
        }
    }

    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element);
        if (this.head != null) {
            newNode.next = this.head;
        }
        this.head = newNode;
        this.size++;

    }

    @Override
    public void addLast(E element) {
        Node<E> newNode = new Node<>(element);
        if (this.head == null) {
            this.head = newNode;
        } else {
            Node<E> current = this.head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        this.size++;
    }

    @Override
    public E removeFirst() {
        ensureNonEmpty("removeFirst");
        E element = this.head.element;
        if (size == 1) {
            this.head = null;
        } else {
            Node<E> newHead = this.head.next;
            this.head = newHead;
        }
        this.size--;
        return element;
    }

    private void ensureNonEmpty(String operation) {
        if (this.isEmpty()) {
            throw new IllegalStateException("Cannot " + operation + " on empty LinkedList");
        }
    }

    @Override
    public E removeLast() {
        ensureNonEmpty("removeLast");
        E element = null;
        if (this.size == 1) {
            element = this.head.element;
            this.head = null;
        } else {
            Node<E> current = this.head;
            Node<E> prev = this.head;
            while (current.next != null) {
                prev = current;
                current = current.next;
            }
            element = current.element;
            prev.next = null;
        }
        this.size--;
        return element;
    }

    @Override
    public E getFirst() {
        ensureNonEmpty("removeFirst");
        return  this.head.element;
    }

    @Override
    public E getLast() {
        ensureNonEmpty("getLast");
        Node<E> current = this.head;
        while (current.next != null){
            current = current.next;
        }
        return current.element;
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
