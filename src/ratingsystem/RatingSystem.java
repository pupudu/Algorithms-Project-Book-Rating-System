/*
 * Name- Dodangoda D.A.P.P
 * Index No- 110141R
 * 
 * CS2022 MiniProject
 */
package ratingsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class RatingSystem {
    public static void main(String[] args) throws IOException {
        RateSys rs=new RateSys();
        int option;
        String fileName;
        fileName=getFileName();
        rs.getWords(fileName);  //reads the ratings from the file and then do the required calculations
        System.out.println("****---- Book Rating System ----****");
        do{
            option=getUserInputs();         //chosse between vendor Search and Book Search
            switch(option){
                case 1: //book Search
                    rs.searchBook(getProductName("Book"));  //get the name of the book and then search
                    break;
                case 2: //vendor Search
                    rs.searchVendor(getProductName("Vendor"));  //get the name of the vendor and then search
                    break;
                case 3: // option to exit the system
                    break;
                default:
                    System.out.println("##########  Invalid Input\n");
                    break;
            }
        }while(option !=3);
        
       
    }
    static String getFileName(){
        System.out.println("Enter File Name(default-UserRating.txt) to take Inputs:");
        Scanner sc=new Scanner(System.in);
        String fileName=sc.next();
        File file=new File(fileName);
        if (!file.exists()) {       //create new input file if a file does not exist
            System.out.println("No input File with name, \""+fileName+"\".Data will be loaded from default file, UserRating.txt");
            return "UserRating.txt";
        }
        return fileName;
    }
    static String getProductName(String product){   //scan the keyboard input for product name
        Scanner sc=new Scanner(System.in);
        String name="default";
        System.out.print("Enter "+product+" Name:");
        try{
            name=sc.next();
        }catch(Exception e){}
        return name;
    }
    static int getUserInputs(){     // scan keyboard input to choose between booksearch and vendor search
        Scanner sc=new Scanner(System.in);
        int option=0;
        System.out.println("(Main Menu)Select an Option:\n1-Book Search\n2-Vendor Search\n3-Exit");
        System.out.print(" ** Enter Your Choice:");
        try{
            option=sc.nextInt();
        }catch(Exception e){}
        return option;
    }
}
class RateSys {                     //reads the inputs from file,add them to a database.
    
    book newBook=new book();    //default book for each input from file
    Vend newVend=new Vend();    //default vendor for each input from file
    
    HashMap bookMap=new HashMap(1000);  //hash map to check the pre existence of a book
    HashMap vendorMap=new HashMap(1000);    ////hash map to check the pre existence of a vendor
    list allUsers=new list();   //this list contains all users subscribed.Seperate for books
    list allUsers1=new list();  //this list contains all users subscribed.Seperate for vendors
    
    void getWords(String fileName) throws IOException {
        
        File file=new File(fileName);
        if (!file.exists()) {       //create new input file if a file does not exist
            file.createNewFile();
            System.out.println("No input File.A new File was created with name, "+fileName+". add inputs to it");
        }
        try (FileReader fr = new FileReader(file.getAbsoluteFile())) {
            BufferedReader br = new BufferedReader(fr);
            String s;
            while((s = br.readLine()) != null) {    //reads inputs from the file line by line
                StringTokenizer st = new StringTokenizer(s);
                
                String timeStamp=st.nextToken();   //next 6 lines to tokenize the input
                String user=st.nextToken();
                String book=st.nextToken();
                String vend=st.nextToken();
                
                int vendorRate=Integer.parseInt(st.nextToken());
                int bookRate=Integer.parseInt(st.nextToken());
                
                int time=getTime(timeStamp);    // convert time in string format to integer format
                
                storeData(book,bookRate,time,user,vend,vendorRate); //stores the data of the current input line
                
            }
        }
    }
    void storeData(String book,int bookRate,int time,String user,String vend,int vendorRate){
        
                //next 7 lines to store data relevant to the book of current input
        if(bookMap.search(book)==null){     
            newBook=new book();
            bookMap.insert(book, newBook);
        }else{
            newBook=(book)bookMap.search(book);
        }
        newBook.addUser(user, bookRate, time,allUsers);

                //next 7 lines to store data relevant to the book of current input
        if(vendorMap.search(vend)==null){   
            newVend=new Vend();
            vendorMap.insert(vend, newVend);
        }else{
            newVend=(Vend)vendorMap.search(vend);
        }
        newVend.addUser(user, vendorRate, time,allUsers1);

        newVend.addToBookList(book,newBook.getOverallRating());     //stores book data relevant to each vendor
        newBook.addToVendList(vend, newVend.getOverallRating());    //stores vendor data relevant to each book
    }
    
    int getTime(String s){      //converts time string into an Integer
        s=s.replace("-", "");
        s=s.replace("T","");
        s=s.replace(":","");
        int n=Integer.parseInt(s.substring(3));
        return n;
    }
    
    void searchBook(String name){               //book Search
        try{
            book b=(book)bookMap.search(name);
            if(b==null){
                System.out.println("--####### Book Data not Available");
                return;
            }
            int option=getProductInputData("Top Rated Vendors");
            
            switch(option){
                case 1:
                    System.out.println("~~~~~~~~~~~ Overall aggregate rating of "+name+":"+b.getOverallRating());
                    searchBook(name);
                    break;
                case 2:
                    System.out.println("~~~~~~~~~~~ Five Most recent ratings of "+name+":"+b.getRecentRates());
                    searchBook(name);
                    break;
                case 3:
                    System.out.println("~~~~~~~~~~~ Top Rated Vendors of "+name+"(sorted in order):");
                    b.updateVendors(vendorMap);
                    b.sortVendors();
                    b.vendList.printList();
                    searchBook(name);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid Input");
                    searchBook(name);
            }
        
            
        }catch(Exception e){
            System.out.println("--####### Book Data not Available");
        }
    }
    
    void searchVendor(String name){
        try{
            Vend v=(Vend)vendorMap.search(name);
            if(v==null){
                System.out.println("--###### Vendor Not Registered");
                return;
            }
            int option=getProductInputData("List of Books published");
            switch(option){
                case 1:
                    System.out.println("~~~~~~~~~~~ Overall aggregate rating of "+name+":"+v.getOverallRating());
                    searchVendor(name);
                    break;
                case 2:
                    System.out.println("~~~~~~~~~~~ Five Most recent ratings of "+name+":"+v.getRecentRates());
                    searchVendor(name);
                    break;
                case 3:
                    System.out.println("~~~~~~~~~~~ List of Books Published by "+name+":");
                    v.updateBooks(bookMap);
                    v.bookList.printList();
                    searchVendor(name);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("############# Invalid Input");
                    searchVendor(name);
            }
        }catch(Exception e){
            System.out.println("--###### Vendor Not Registered");
        }
    }
    int getProductInputData(String details){
        Scanner sc=new Scanner(System.in);
        int input=0;
        System.out.println("\t(Sub Menu)\nSelect an option to View\n1-Overall Aggregate Rating\n2-Five Most Recent Ratings");
        System.out.println("3-List of "+details );
        System.out.println("4-Back To Main Menu");
        
        System.out.print("Enter option:");
        try{
            input=sc.nextInt();
        }catch(Exception e){}
        return input;
    }
}