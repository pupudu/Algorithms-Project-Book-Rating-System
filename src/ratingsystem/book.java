/*
 * Name- Dodangoda D.A.P.P
 * Index No- 110141R
 * 
 * CS2022 MiniProject
 */
package ratingsystem;

public final class book {
    list users=new list();
    int[] recentRates=new int[6];   //queue of length 5 to store most recent rates;
    list allUsers;                  //A list to contain details about all users
    int rateCount=0;                  //number of ratings
    
    list vendList= new list();      //Contains the list of vendors relevant to each book
    
    public book(){                  //method call to initialize queue
        initiate();     
    }
    
    double getOverallRating(){                  //traverse through each user to get their ratings
        node head=users.getHead();              //start with the head node of the user list
        
        String key=head.getKey();               
        node head1=allUsers.search(key);        //weight relevant to users taken from allusers list
        user thisUser1=(user)head1.getData();
        
        myUser thisUser=(myUser)head.getData(); //myUser contains details of each user releant to this book only
        double weightedAttempts=thisUser.getAttempts()*thisUser1.getWeight();    //attempts*weight by first user
        double weightedRate=thisUser.getRateSum()*thisUser1.getWeight();     //wighted rate by first user
        
        while(head.next!=null){
            head=head.next;                     //traversal
            thisUser=(myUser)head.getData();
            
            key=head.getKey();
            head1=allUsers.search(key);
            thisUser1=(user)head1.getData();
            
            weightedAttempts+=thisUser.getAttempts()*thisUser1.getWeight();   //summing up the total number of ratings*weight by all users
            weightedRate+=thisUser.getRateSum()*thisUser1.getWeight();   //total weighted rate by users
        }
        double overallRate=weightedRate/(weightedAttempts);  //overall rate=sum(weight*(sum(rates))/sum(weight
        return overallRate;
    }
    
    void initiate(){                //initialize the elements of the queue
        for(int i=0;i<5;i++){
            recentRates[i]=0;
        }
    }
    
    void addUser(String userName,int bookRate,int time,list allUsers){
        
        this.allUsers=allUsers;
        
        node n=allUsers.search(userName);              //check whether the user with given name has rated the book before
        addToRecentList(time, bookRate);
        if(n==null){                                //add a new user to the book
            user newUser=new user(userName);        //create a new user object
            allUsers.insert(userName, newUser);        //append it to the user list of the book
        }else{                                      
            user thisUser=(user) n.getData();       //cast the existing user to thisUser
            thisUser.rate(bookRate);                    //update the weight of user
        }
        
        node m=users.search(userName);              //check whether the user with given name has rated the book before
        if(m==null){                                //add a new user to the book
            myUser newUser=new myUser(userName,bookRate);        //create a new user object
            users.insert(userName, newUser);        //append it to the user list of the book
        }else{                                      
            myUser newUser=(myUser) m.getData();       //cast the existing user to thisUser
            newUser.rate(bookRate);                    //update the weight of user
        }
        
    }
    
    void addToRecentList(int time,int rate){    // "insertion sort" of timeStamp combined with rating of the book.
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
    String getRecentRates(){        //method to get the most recent ratings of the book
        String s=" ";
        int tempCount=rateCount;
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
        rateCount=tempCount;
        s=s.substring(0,s.length()-1);  //discard the last comma
        return s;
    }
    
    void addToVendList(String vendName,double rate){    //collects data about vendors who publish this book
        if(vendList.search(vendName)==null){
            vendList.insert(vendName,rate);
        }
    }
    void updateVendors(HashMap vendMap){        //update the rating of vendors of the book
        node Node=vendList.getHead();
        while(Node.next!=null){
            String key=Node.getKey();                       
            Vend newVend=(Vend)vendMap.search(key);         //reference the relevant vendor
            Node.setData(newVend.getOverallRating());       //sets the rating
            Node=Node.next;             //traverse
        }
    }
    void sortVendors(){
        vendList.append(vendList.MergeSort(vendList.getHead()));    //sorts vendors according to their rating using mergesort
    }   
}
