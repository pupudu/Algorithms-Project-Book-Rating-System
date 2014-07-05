/*
 * Name- Dodangoda D.A.P.P
 * Index No- 110141R
 * 
 * CS2022 MiniProject
 */
package ratingsystem;

public class list{       

    private node head;  //first node of the linked list
    private int size;   //define the size of the list
    
    list(){             //constructor
        head = null;
        size = 0;
    }
    
    public void insert(String key,Object data){     // add an element to the tail of the list
        node node = new node();
        node.setKey(key);
        node.setData(data);
        if(head ==null){
            head = node;        //case if it is the first element added to the list
        }
        else{
            node.setNext(head);         //append the node to the tail
            this.head = node;
        }
        size++;     //increent size after adding new node
    }
    
    node search(int index){         //search for an node in the list iteratively
        node node = head;
        for(int i=0;i<index;i++){
            node = node.getNext();
        }
        return node;
    }
    
    node search(String key){        //direct search and access of a node using the key value 
        node node = head;
        while(node!=null && !node.getKey().equals(key)){
            node = node.getNext();
           
        }
        return node;
    }
    
    public int getSize() {
        return size;
    }

    node getHead() {        //returns the head of the list
        return head;
    }
    void append(node newHead) {     //append a new head to the list
        this.head=newHead;
    }
    
    void printList(){
        System.out.println("\n\t\t*** contains data about "+this.getSize()+" items ****");
        try{
            node currentnode=head;
            System.out.println(currentnode.getKey()+" - "+currentnode.getData());
            while(currentnode.next!=null){
                if(this.getSize()>8){
                    if(currentnode.next.next==null)
                        break;
                }
                currentnode=currentnode.next;
                System.out.println(currentnode.getKey()+" - "+currentnode.getData());
            }
        }catch(Exception e){
            System.out.println("An error encountered while printing the list");
        }
    }
    
    node MergeSort(node headOriginal){ 
        if (headOriginal == null || headOriginal.next == null) 
            return headOriginal; 
        node leftlist = headOriginal;               //define left sub list
        node rightlist = headOriginal.next;         //define right sub list
        node temp=rightlist;
        while ((temp != null) && (temp.next != null)) {     //loop to identify the middle element of the list
            headOriginal = headOriginal.next;                   //one shift right
            temp = (temp.next).next;                            //two shift right s 
        } 
        rightlist = headOriginal.next;              //assign middle of original list to rightlist
        headOriginal.next = null;                   //define the end elemnt of left list
        return merge(MergeSort(leftlist), MergeSort(rightlist));   //recursive call to mergesort 1st half and 2nd half and then merge
          
    }    
 
    node merge(node left, node right){         // merge two sorted sublists to get the sorted list
        node temp = new node(); 
        node newHead = temp;
        node e = newHead; 
        while ((left != null) && (right != null)){             //merges the elements iteratively
            if ((double)left.getData() >= (double)right.getData()){ 
                e.next = left;         //add elements from left sublist
                e = left; 
                left = left.next; 
            }else{ 
                e.next = right;     //add elements from right sublist 
                e = right; 
                right = right.next;
            } 
        }
        e.next = (left == null) ? right : left; 
        return newHead.next; 
    } 
    
}
