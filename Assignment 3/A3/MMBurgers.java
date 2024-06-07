public class MMBurgers implements MMBurgersInterface {
    public int k;//no of counters
    public int m;//no of max burgers on griddle
    public int time;//current time
    public counter_node[] countersForBilling;// for billing counters
    public Queue_new<griddle_burger> burgersWithChef = new Queue_new<>(); //for queue of customers for burgers on griddle given to chef
    public Queue_new<griddle_node> griddle = new Queue_new<>();//  for updating no. of burgers on griddle for customers
    public Queue_new<customer> orderDelivery = new Queue_new<>();//  for final delivery of the orders
    public int outTime = 0;//total out time
    public int inTime = 0;//total in time
    public int numCustomer = 0;//total customers
    public tree idTree = new tree(); // for searching customers on the basis of id
    public minHeap heap = new minHeap();//heap for length of queue at counters

    public boolean isEmpty() {
        if (!burgersWithChef.isEmpty_que() || !griddle.isEmpty_que()) {//check if griddle or queue of burger are empty
            return false;
        }
        int i = k;
        while (i >= 1) {
            if (!countersForBilling[i].counter.isEmpty_que()) {//check if counters are empty
                return false;
            }
            i--;
        }
        return true;
    }

    public void setK(int k) throws IllegalNumberException {
        if (k < 0) {
            throw new IllegalNumberException(" ");
        }

        this.k = k;
        heap.initialise_heap(k);//initialise heap for storing counters according to queue of customers

        countersForBilling = new counter_node[k + 1];
        int i = k;
        while (i >= 1) {
            counter_node counterNode = new counter_node();
            Queue_new<customer> counters = new Queue_new<>();
            countersForBilling[i] = counterNode;
            countersForBilling[i].counter = counters;
            i--;
        }
    }

    public void setM(int m) throws IllegalNumberException {
        if (m < 0)
            throw new IllegalNumberException(" ");
        this.m = m;
    }

    public void advanceTime(int t) throws IllegalNumberException {
        if (t < time) {
            throw new IllegalNumberException(" ");
        }

        while (time < t){// global time should be less than t.
            time++;
            while(orderDelivery.size_que()>0){
                orderDelivery.front_que().state++;//updating the state of the customer
                orderDelivery.remove_que();//removing customer from the stimulation
            }
            int i = k;
            while (i > 0){//for traversing all counters
                if (countersForBilling[i].counter.front_que() != null) {
                    if (countersForBilling[i].counter.front_que().counter_out_time == time) {//if out time of the customer from the queue is equal to time
                        countersForBilling[i].cust_num--;//removing customer from the queue
                        avl_node temp = idTree.find_tree(countersForBilling[i].counter.front_que().id);
                        heap.arrayOfQue[temp.node.curr_index].size_queue--;//updating the size of the queue
                        heap.heapify_up(temp.node.curr_index);//upadting the heap to ensure heap property

                        griddle_burger nwOrder = new griddle_burger();//to be inserted in queue of burgers
                        nwOrder.id = countersForBilling[i].counter.remove_que().id;//remove customer from queue
                        nwOrder.cust = idTree.find_tree(nwOrder.id).cst;

                        nwOrder.cust.state = k + 1;
                        burgersWithChef.add_que(nwOrder);
                    }
                }
                i--;
            }
            while (griddle.size_que()> 0) {
                if (griddle.front_que().griddle_in_time + 10 == time) {//if the element of griddle is cooked
                    if (griddle.front_que().numb==griddle.front_que().burgerNumber) {//if all burgers of customer are cooked
                        outTime += time + 1;
                        griddle.front_que().cust.outTime = time + 1;
                        orderDelivery.add_que(griddle.front_que().cust);
                    }
                    griddle.remove_que();//remove burger from the griddle
                } else {
                    break;//if ith element is not cooked then there is no chance that (i+1)th element will be cooked
                }
            }
            while (griddle.size_que() < m) {//if space is available on griddle
                if (burgersWithChef.front_que() != null) {
                    griddle_node newBurger = new griddle_node();//to create a new burger to be inserted in griddle
                    newBurger.id = burgersWithChef.front_que().id;
                    newBurger.numb = burgersWithChef.front_que().cust.total_burgers;
                    newBurger.griddle_in_time = time;
                    newBurger.cust = burgersWithChef.front_que().cust;
                    newBurger.burgerNumber = burgersWithChef.front_que().cust.total_burgers - burgersWithChef.front_que().cust.burgers_left + 1;

                    griddle.add_que(newBurger);//adding burger to the griddle

                    burgersWithChef.front_que().cust.burgers_left--;//reducing the number of remaining burgers
                    if (burgersWithChef.front_que().cust.burgers_left == 0) {//if all the burgers of the customer at the front of queue are cooked
                        burgersWithChef.remove_que();
                    }
                } else {//if no orders are present for the chef
                    break;
                }
            }
        }
    }

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException {
        if (t < time || numb < 0||idTree.find_tree(id) != null) {//if time is less than current time or number of burgers < 0 or if customer is already present
            throw new IllegalNumberException(" ");
        }

        advanceTime(t);//advance till time t
        inTime += t;//updating total in time
        numCustomer++;//updating total customers

        int i = heap.min_element();//counter number to which the customer will go
        node_heap node = heap.min_node_heap();
        heap.update_pos();//will increase the min length queue

        customer customer = new customer();//creating new customers
        customer.inTime = t;
        customer.id = id;
        customer.total_burgers = numb;
        customer.burgers_left = numb;
        customer.state = i;

        if (countersForBilling[i].cust_num != 0) {
            countersForBilling[i].cust_num++;
            customer.counter_out_time = t + (countersForBilling[i].cust_num - 1) * i + countersForBilling[i].counter.front_que().counter_out_time;//added the time of customers already present before the newly arrived customer
        } else {
            countersForBilling[i].cust_num++;
            customer.counter_out_time = i + t;
        }

        customer.outTime = customer.counter_out_time;
        countersForBilling[i].counter.add_que(customer);//customer is added to the billing queue

        avl_node temp = idTree.insert_tree( idTree.root,id); //inserting in avl tree to check if customer is alreay present or not
        temp.cst = customer;
        temp.node = node;
    }

    public int customerState(int id, int t) throws IllegalNumberException {
        //your implementation
        if (t < time)
            throw new IllegalNumberException(" ");

        advanceTime(t);

        avl_node node_nw = idTree.find_tree(id);//finds customer in avl tree
        if (node_nw == null || node_nw.node == null)//if customer is absent
            return 0;

        return node_nw.cst.state;
    }

    public int griddleState(int t) throws IllegalNumberException {
        if (t < time)
            throw new IllegalNumberException(" ");

        advanceTime(t);

        return griddle.size_que();
    }

    public int griddleWait(int t) throws IllegalNumberException {
        if (t < time)
            throw new IllegalNumberException(" ");

        advanceTime(t);

        int count = 0;
        griddle_burger curr = burgersWithChef.front();
        int front_indx = burgersWithChef.front_idx();
        for (int i = 0; i < burgersWithChef.size_que(); i++){//traverse the queue burger
            count += curr.cust.burgers_left;//adding remaining burgers in count
            front_indx++;
            curr = burgersWithChef.next(front_indx);//moving to the next element of queue
        }
        return count;
    }

    public int customerWaitTime(int id) throws IllegalNumberException {
        avl_node temp = idTree.find_tree(id);
        if (temp == null) {
            throw new IllegalNumberException(" ");
        }
        return temp.cst.outTime - temp.cst.inTime;
    }

    public float avgWaitTime(){
        float total_time = outTime - inTime;
        float total_customers = numCustomer;
        return total_time / total_customers;
    }
}

class counter_node{ // node for billing counters
    int cust_num = 0; // number of customer in that row
    Queue_new<customer> counter = new Queue_new<>(); //customers in the queue of each counter
}

class customer{
    int id; //id of a customer
    int state; //  state of customer
    int total_burgers;  // number of orders of burger
    int inTime; // in time of  the customer
    int outTime; //out time of the customer
    int counter_out_time;//out time from the queue at counter
    int burgers_left; // number of burger cooked to be used in griddle
}

class griddle_node{
    int id;//id of customer
    customer cust;//customer
    int griddle_in_time;//enter time for griddle
    int numb;
    int burgerNumber;
}

class griddle_burger { // node for griddle
    int id;
    customer cust;
}
