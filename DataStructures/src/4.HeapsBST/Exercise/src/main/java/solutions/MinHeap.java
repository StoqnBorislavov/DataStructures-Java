package solutions;

import interfaces.Decrease;
import interfaces.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinHeap<E extends Comparable<E> & Decrease<E>> implements Heap<E> {
    private List<E> data;

    public MinHeap() {
        this.data = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public void add(E element) {
        this.data.add(element);
        this.heapifyUp(this.data.size() - 1);
    }

    private void heapifyUp(int startIndex) {
        int index = startIndex;
        int parentIndex = getParentIndexFor(index);
        while (index > 0 && isLess(index, parentIndex)){
            Collections.swap(this.data, index, parentIndex);
            index = parentIndex;
            parentIndex = this.getParentIndexFor(index);
        }
    }

    private boolean isLess(int first, int second) {
        return this.data.get(first).compareTo(this.data.get(second)) < 0;
    }

    private int getParentIndexFor(int index) {
        return  (index - 1) / 2;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.data.get(0);
    }

    private void ensureNonEmpty() {
        if(this.data.isEmpty()){
            throw new IllegalStateException();
        }
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        Collections.swap(this.data , 0 , this.data.size() -1);
        E toReturn = this.data.remove(this.data.size() - 1);
        this.heapifyDown();
        return toReturn;
    }

    private void heapifyDown() {
        int index = 0;
        int leftChildIndex = this.getLeftChildIndex(index);

        while (leftChildIndex < this.data.size() && isLess(leftChildIndex, index)){
            int rightChildIndex = this.getRightChildIndex(index);
            int child = getLeftChildIndex(index);
            if(rightChildIndex < this.data.size() && isLess(rightChildIndex, child)){
                child = rightChildIndex;
            }
            if(this.isLess(index, child)){
                break;
            }
            Collections.swap(this.data, index, child);
            index = child;
            leftChildIndex = this.getLeftChildIndex(index);
        }
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }
    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    @Override
    public void decrease(E element) {
        int indexOfDecreasedElement = this.data.indexOf(element);
        E elementToDecrease = this.data.get(indexOfDecreasedElement);
        elementToDecrease.decrease();
        this.heapifyUp(indexOfDecreasedElement);

    }
}
