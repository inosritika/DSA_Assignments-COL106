class node_heap{
        int index ;
        int size_queue;// array of the queue
        int curr_index ;
}

class minHeap{
    public node_heap[] arrayOfQue;//arrayOfQue will contain size_queue of the queues of the different counter
    int size ;
    
    public void initialise_heap(int size){//for initialising heap
        this.arrayOfQue = new node_heap[size+1];//creating new array
        this.size=size+1 ;
        for (int i=1; i<=size;i++){
            node_heap nw_node = new node_heap();//create objects
            arrayOfQue[i] = nw_node ;
            nw_node.index = i ;
            nw_node.size_queue = 0;
            nw_node.curr_index = i;
        }
    }

    int parent(int i){//parent of node at index i
        return i/2 ;
    }
    int right_child(int i){//right child of node at index i
        return (2*i+1);
    }
    int left_child(int i){//left child of node at index i
        return (2*i);
    }
    int min_element(){//minimum element of the heap , i.e, first element
        return arrayOfQue[1].index;
    }
    private void swap(int a, int b){//swapping elements at index a and b
        node_heap temp = arrayOfQue[a];
        arrayOfQue[a]=arrayOfQue[b];
    }
    private boolean isLeaf(int i){//checking if the element at index i
        if (i >= (size/ 2) && i <= size){
            return true;
        }
        return false;
    }
    void update_pos(){//increment the no of elements in the queue
        arrayOfQue[1].size_queue++;
        heapify_down(1);
    }

    node_heap min_node_heap(){//return minimum node
        return arrayOfQue[1];
    }

    void heapify_down(int i){
        if(!isLeaf(i)){//if node at index i is not a leaf
            if(arrayOfQue[left_child(i)].size_queue <= arrayOfQue[i].size_queue || arrayOfQue[right_child(i)].size_queue <= arrayOfQue[i].size_queue){//comparing size of queue at each child of node at i
                if (arrayOfQue[left_child(i)].size_queue< arrayOfQue[right_child(i)].size_queue){//comparing left and right child's queue size
                    if(arrayOfQue[i].size_queue == arrayOfQue[left_child(i)].size_queue){//if queue size is same
                        if(arrayOfQue[i].index > arrayOfQue[left_child(i)].index) {//then compare the index of the element
                            swap(i, left_child(i));
                            heapify_down(left_child(i));
                        }
                    }else if(arrayOfQue[i].size_queue > arrayOfQue[left_child(i)].size_queue){//if queue size is different
                        swap(i, left_child(i));
                        heapify_down(left_child(i));
                    }
                }else if(arrayOfQue[right_child(i)].size_queue < arrayOfQue[left_child(i)].size_queue){//repeating the cases we have done followed above
                    if(arrayOfQue[i].size_queue == arrayOfQue[right_child(i)].size_queue){
                        if(arrayOfQue[i].index > arrayOfQue[right_child(i)].index) {
                            swap(i, right_child(i));
                            heapify_down(right_child(i));
                        }
                    }else if(arrayOfQue[i].size_queue > arrayOfQue[right_child(i)].size_queue){
                        swap(i, right_child(i));
                        heapify_down(right_child(i));
                    }
                }else{
                    if (arrayOfQue[left_child(i)].index< arrayOfQue[right_child(i)].index){//after comparing the length of queues we will compare their indexes in case the size of queue is same for both
                        if(arrayOfQue[i].size_queue == arrayOfQue[left_child(i)].size_queue){
                            if(arrayOfQue[i].index > arrayOfQue[left_child(i)].index){//comapare the index of each element after comparing their queue sizes
                                swap(i, left_child(i));
                                heapify_down(left_child(i));
                            }
                        }else if(arrayOfQue[i].size_queue > arrayOfQue[left_child(i)].size_queue){
                            swap(i, left_child(i));
                            heapify_down(left_child(i));
                        }
                    }else if (arrayOfQue[left_child(i)].index > arrayOfQue[right_child(i)].index){
                        if(arrayOfQue[i].size_queue == arrayOfQue[right_child(i)].size_queue){
                            if(arrayOfQue[i].index > arrayOfQue[right_child(i)].index) {
                                swap(i, right_child(i));
                                heapify_down(right_child(i));
                            }
                        }else if(arrayOfQue[i].size_queue > arrayOfQue[right_child(i)].size_queue){
                            swap(i,right_child(i));
                            heapify_down(right_child(i));
                        }
                    }
                }
            }
        }
    }

    void heapify_up(int i){//to update heap after removing minimum element using recursion over the heap
        if(i >1 && arrayOfQue[i].size_queue <=arrayOfQue[parent(i)].size_queue){
            if(arrayOfQue[i].size_queue < arrayOfQue[parent(i)].size_queue) {
                swap(i, parent(i));
                heapify_up(parent(i));
            }else{
                if(arrayOfQue[i].index < arrayOfQue[parent(i)].index){
                    swap(i,parent(i));
                    heapify_up(parent(i));
                }
            }
        }
    }
}
