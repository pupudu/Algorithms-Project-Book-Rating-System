/*
 * Name- Dodangoda D.A.P.P
 * Index No- 110141R
 * 
 * CS2022 MiniProject
 */
package ratingsystem;
public class HashMap {
    
    list[] map;     // a list for mapping
    list data;      // list to store all the data
    
    public HashMap(int size){   //constructor
        map = new list[size];   
        data = new list();      
    }
    
    public int getHashKey(String text){        //evaluate hash key
        int sum =0;
        for(int i=0;i<text.length();i++){       //sum up the values of (ascii value*2^position)to get a nearly perfect hash function
            int ascii=text.charAt(i);
            double power=Math.pow(2, i);
            sum+=ascii*(power);
        }
        int hash = sum%991;                     //for the modulo, chosen a prime value to reduce collisions
        return hash;
    }
    
    public void insert(String text,Object data){
        int key = this.getHashKey(text);
        if(map[key]==null){
            map[key] = new list();
        }
        if(this.data.search(text)==null){   //search for the existance of data
            map[key].insert(text, data);
            this.data.insert(text,data);    //Store all the data
        }
    }
    
    public Object search(String text){          //search for an item using a text as the key
        int key = this.getHashKey(text);        //evaluate the hash value of the text to get the hash key
        if(map[key] ==null || map[key].search(text)==null){ //check whether an element corresponding to the key exists
            return null;
        }
        return map[key].search(text).getData(); //return the data corresponding to text
    }
}
