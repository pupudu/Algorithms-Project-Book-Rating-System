/*
 * Name- Dodangoda D.A.P.P
 * Index No- 110141R
 * 
 * CS2022 MiniProject
 */
package ratingsystem;

public class user {     //Contains data of users relevant to every product/vendor
    String name;                //name of user
    int attempts;               //number of times a user has rated a product/vendor
    double weight;
    public user(String name){   //constructor
        this.attempts=1;
        this.name=name;
    }
    void rate(int rate){        //sets the number of times a user has rated a product/vendor
        attempts++;
    }
    double getWeight(){         //calculate and return the weight of the user
        weight=2-(1.0/attempts);
        return weight;
    }
}
