/*
 * Name- Dodangoda D.A.P.P
 * Index No- 110141R
 * 
 * CS2022 MiniProject
 */
package ratingsystem;

public final class Vend {
    list users=new list();
    int[] recentRates=new int[6];   //queue of length 5 to store most recent rates;
    int rateCount=0;                //Number of ratings
    
    list bookList=new list();       // a list of books that the vendor publishes 
    list allUsers;                  //all users of a vendor
    
    public Vend(){
        initiate();     //method call to initialize queue
    }
    
    void initiate(){                //initialize the elements of the queue
        for(int i=0;i<5;i++){
            recentRates[i]=0;
        }
    }
    
    double getOverallRating(){                  //traverse through each user to get their ratings
        node head=users.getHead();              //start with the head node of the user list
        
        String key=head.getKey();
        node head1=allUsers.search(key);        //reference the relevant user in the all users list
        user thisUser1=(user)head1.getData();   
        myUser thisUser=(myUser)head.getData();
        
        double weightedAttempts=thisUser.getAttempts()*thisUser1.getWeight();    //attempts*weight by first user
        double weightedRate=thisUser.getRateSum()*thisUser1.getWeight();     //wighted rate by first user
        
        while(head.next!=null){
            head=head.next;                     //traversal
            thisUser=(myUser)head.getData();
            
            key=head.getKey();                  //reference the relevant user in allusers list
            head1=allUsers.search(key);
            thisUser1=(user)head1.getData();
            
            weightedAttempts+=thisUser.getAttempts()*thisUser1.getWeight();   //summing up the total number of ratings*weight by all users
            weightedRate+=thisUser.getRateSum()*thisUser1.getWeight();   //total weighted rate by users
        }
        double overallRate=weightedRate/(weightedAttempts);  //overall rate=sum(weight*(sum(rates))/sum(weight
        return overallRate;
    }
    
    void addUser(String userName,int vendorRate,int time,list allUsers){
        
        this.allUsers=allUsers;
        node n=allUsers.search(userName);              //check whether the user with given name has rated the vendor before
        addToRecentList(time, vendorRate);
        if(n==null){                                //add a new user to the vendor
            user newUser=new user(userName);        //create a new user object
            allUsers.insert(userName, newUser);        //append it to the user list of the vendor
        }else{                                      
            user thisUser=(user) n.getData();       //cast the existing user to thisUser
            thisUser.rate(vendorRate);                    //update the weight of user
        }
        
        node m=users.search(userName);              //check whether the user with given name has rated the vendor before
        if(m==null){                                //add a new user to the vendor
            myUser newUser=new myUser(userName,vendorRate);        //create a new user object
            users.insert(userName, newUser);        //append it to the user list of the vendor
        }else{                                      
            myUser newUser=(myUser) m.getData();       //cast the existing user to thisUser
            newUser.rate(vendorRate);                    //update the weight of user
        }
        
    }
    
    void addToRecentList(int time,int rate){    // "insertion sort" of timeStamp combined with rating of the vendor.
        rateCount++;
        for(int i=0;i<5;i++){
            int inTime=recentRates[i]/10;       // an element contains its least significant digit with 'rate' and others the 'time'
            if(time>inTime){
                int temp=recentRates[i];
                recentRates[i]=time*10+rate;
                time=temp/10;                   //divide into time and rate
                rate=temp%10;                   //divide into time and rate
            }
        }
    }
    String getRecentRates(){        //method to get the most recent ratings of the vendor
        String s="";
        int temCount=rateCount;
        if(rateCount<5)
            System.out.println("Only "+rateCount+" ratings available");
        for(int i=0;i<5;i++){
            if(rateCount==0){
                s+="_,";
            }else{    
                s+=recentRates[i]%10;
                s+=",";                     // a comma between each rating
                rateCount--;
            }
        }
        rateCount=temCount;
        s=s.substring(0,s.length()-1);
        return s;
    }
    
    void addToBookList(String bookName,double rate){        //add book to the list of books published by the vendor
        if(bookList.search(bookName)==null){
            bookList.insert(bookName,rate);
        }
    }
    void updateBooks(HashMap bookMap){
        node Node=bookList.getHead();
        while(Node.next!=null){
            String key=Node.getKey();
            book newBook=(book)bookMap.search(key);
            Node.setData(newBook.getOverallRating());
            Node=Node.next;
        }
    }
}
