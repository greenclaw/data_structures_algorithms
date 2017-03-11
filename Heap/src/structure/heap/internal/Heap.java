package structure.heap.internal;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by rom on 20.10.16.
 */

public class Heap<T> {
    private T[] heap;
    private int size;
    private Comparator<T> comp;

    Heap(int capacity, Comparator<T> comparator) {
        this.comp = comparator;
        this.heap = (T[]) new Object[capacity];
        this.size = -1;
    }

    void add(T element) {
        size++;
        heap[size] = element;
        upHeap(size);
    }

    // This procedure needed to heapify element with given index after insert
    private void upHeap(int index) {
        int i = index;
        while(i != 0 &&
                comp.compare(heap[(i - 1) / 2], heap[i]) < 0) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    // delete and returns top element
    T delete() {
        if (size < 0) return null;
        T root = heap[0];
        heap[0] = heap[size];
        downHeap(0);
        size--;
        return root;
    }

    // Executes when you need find place for element with given index
    private void downHeap(int index) {
        int i = index;
        int des = chooseChild(i);
        while(des != 0 &&
                comp.compare(heap[des], heap[i]) > 0) {
            swap(des, i);
            i = des;
            des = chooseChild(i);
        }
    }

    // returns index of the biggest/smallest child of element with given index
    private int chooseChild(int index) {
        int des = index * 2 + 1;
        if (des > size) return 0;
        if (des == size) return des;
        if (comp.compare(heap[des], heap[des + 1]) >= 0)
            return des;
        else
            return des + 1;
    }

    private void swap(int index1, int index2) {
        T temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    public int size() {
        return size + 1;
    }
    // peek top element
    public T root() {
        return heap[0];
    }

    public boolean isEmpty() {
        return size == -1;
    }

}
