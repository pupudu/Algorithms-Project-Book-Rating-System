/*
 * Name- Dodangoda D.A.P.P
 * Index No- 110141R
 * 
 * CS2022 MiniProject
 */
package ratingsystem;

class node {                            //implement an element of the linked list
    node next=null;      //option to go foreward
    private String key;  
    private Object data;

    public void setNext(node next){     
        this.next = next;
    }
    
    public node getNext() {             //returns the next node
        return next;
    }

    public void setKey(String key){     //sets the key of a node
        this.key = key;
    }
    
    public String getKey(){             //returns the key of a node
        return this.key;
    }

    public Object getData() {           //returns the data stored in a node
        return data;
    }

    public void setData(Object data) {  //store data in a node
        this.data = data;
    }
    
}