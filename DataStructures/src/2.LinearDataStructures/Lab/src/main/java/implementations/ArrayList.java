package implementations;

import interfaces.List;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<E> implements List<E> {
    Object[] elements = new Object[1];
    int size = 0;

    @Override
    public boolean add(E element) {
        if(this.size == this.elements.length){
            this.elements = grow();
        }
        this.elements[this.size++] = element;
        return true;
    }

    private Object[] grow() {
        return Arrays.copyOf(this.elements, this.elements.length * 2);
    }

    @Override
    public boolean add(int index, E element) {
        isInBounds(index);
        insert(index, element);
        return true;
    }

    private void insert(int index, E element) {
        if(this.size == this.elements.length){
            this.elements = grow();
        }
        E lastElement = this.getElement(this.size -1);
        for (int i = this.size - 1; i > index ; i--) {
            this.elements[i] = this.elements[i - 1];
        }
        this.elements[this.size] = lastElement;
        this.elements[index] = element;
        this.size++;
    }

    private void isInBounds(int index) {
        if( index < 0 || index >= this.size ){
            throw new IndexOutOfBoundsException(String.format("Index out of bounds: %d for size: %d", index, this.size));
        }
    }

    @Override
    public E get(int index) {
        isInBounds(index);
        return this.getElement(index);
    }

    private E getElement(int index) {
        return (E)this.elements[index];
    }

    @Override
    public E set(int index, E element) {
        isInBounds(index);
        E oldElement = this.getElement(index);
        this.elements[index] =  element;
        return oldElement;
    }

    @Override
    public E remove(int index) {
        this.isInBounds(index);
        E element = this.getElement(index);
        this.elements[index] = null;
        this.size--;
        shift(index);
        ensureCapacity();
        return element;
    }

    private void shift(int index) {
        for (int i = index; i <= this.size; i++) {
            this.elements[i] = this.elements[i + 1];
        }
    }

    private void ensureCapacity() {
        if(this.size < this.elements.length / 3){
            this.elements = shrink();
        }
    }

    private Object[] shrink() {
        return Arrays.copyOf(this.elements, this.elements.length /2);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        int index = -1;
        for (int i = 0; i < this.size; i++) {
            if(this.elements[i].equals(element)){
                index = i;
            }
        }
        return index;
    }

    @Override
    public boolean contains(E element) {
        for (int i = 0; i < this.size; i++) {
            if(this.elements[i].equals(element)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public E next() {
                return get(index++);
            }
        };
    }
}
