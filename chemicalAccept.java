import java.util.*;
class chemicalAccept 
{
    Scanner sc=new Scanner(System.in);
    String Chemical_Equation="";
    int noRadical_temp=0;
    String Reactants="";
    String Products="";
    String accept()
    {
        System.out.println("Enter Chemical Equation:");
        Chemical_Equation=sc.nextLine();

        return Chemical_Equation;
    }  
    String getReactants(String n)
    {
        int equalToSign=n.indexOf('=');
        return n.substring(0,equalToSign);
    }
    String getProducts(String n)
    {
        int equalToSign=n.indexOf('=');
        return n.substring(equalToSign+1);
    }
}
