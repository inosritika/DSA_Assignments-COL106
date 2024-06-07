public class Queue_new<E> {//used in previous assignmentof organizational hierarchy
    private E[] queue; // generic array used for storing elements
    private int f = 0; // index of the front element
    private int size = 0; // size of queue
    private static int fix_size = 100;
    public Queue_new() {
        this(fix_size);
    } // constructor

    public Queue_new(int size) { // constructs queue with given capacity
        queue = (E[]) new Object[size];
    }
    public int size_que(){//size of queue
        return size;
    }
    public E front(){//return front node
        return queue[f];
    }
    public int front_idx(){//returns front index
        return f;
    }
    public E next(int idx){//return next element
        return queue[idx % queue.length];
    }

    // Tests whether the queue is empty. ∗/
    public boolean isEmpty_que() {
        return (size == 0);
    }

    //Inserts an element at the rear of the queue. ∗/
    public void add_que(E e) {
        if (size == queue.length) {
            E [] new_que = (E[])new Object[2*queue.length];
            for(int i = 0;i<size;i++){
                new_que [i] = queue[i];
            }
            queue = new_que;
        }
        int avail = (f + size) % queue.length;
        queue[avail] = e;
        size++;
    }

    // Removes and returns the first element of the queue (null if empty). ∗/
    public E remove_que() {
        if (isEmpty_que()) return null;
        E answer = queue[f];
        queue[f] = null; // dereference to help garbage collection
        f = (f + 1) % queue.length;
        size--;
        return answer;
    }

    //Returns front element of the queue
    public E front_que() {
        if (!isEmpty_que()) {
            return queue[f];
        } else {
            return null;
        }
    }

}
