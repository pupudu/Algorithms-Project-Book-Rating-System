/*
 * Name- Dodangoda D.A.P.P
 * Index No- 110141R
 * 
 * CS2022 MiniProject
 */
package ratingsystem;

public class myUser {   //contains data relevant to specific product/vendor only
    String name;
    int attempts;       //number of times the user has rated the given specific product/vendor
    int rateSum;        //sum of the total ratings to the given book/vendor by a user
    public myUser(String name,int rate){    //constructor
        this.attempts=1;
        this.name=name;
        rateSum=rate;
    }
    void rate(int rate){    // update the total rate by a user to the specific book/vendor
        attempts++;
        rateSum+=rate;
    }
    double getAttempts(){   //returns the number of ratings done by a user to the specific book/vendor
        return attempts;
    }
    int getRateSum(){       //returns the total ratings by a user to the specific book/vendor
        return rateSum;
    }
}
