import java.util.*;
import Jama.*;
class shodorFrontEnd extends shodorMatrix
{

    public static void main()
    {
        Scanner sc=new Scanner(System.in);
        shodorFrontEnd obj=new shodorFrontEnd();
        boolean flag=false;
        do
        {
            obj.control();
            flag=false;
            System.out.println("Again?:");
            String s=sc.next();
            if(s.equalsIgnoreCase("y"))
                flag=true;  
        }
        while(flag);
    }

    public void control()
    {
        int mat3[][][]=matrixMaker();
        int noR=mat3[0][0][1];
        int mat[][]=twoDToThreeD(mat3);
        if(isReactionValid(mat,noR))
        {
            int A[][]=getMatrix(mat);
            int B[][]=getMatrixAns(mat);
            int diff=A.length-A[0].length;
            if(A.length!=A[0].length)
            {
                A=matrixFiller(A);
            }
            double mat_A[][]=intToDouble(A);
            double mat_B[][]=intToDouble(B);
            Matrix a=new Matrix(mat_A);
            Matrix b=new Matrix(mat_B);
            Matrix Ans=step1(a, b, diff);
            Ans=absoluter(Ans);
            Ans=matrixToZero(Ans);
            if(infinitesimalToZero(Math.abs(a.det()))!=0)
                Ans=addToMatrix(Ans,a.det());
            Ans=absoluter(Ans);
            Ans=zeroRemover(Ans);
            if(infinitesimalToZero(Math.abs(a.det()))==0)
                Ans=answerMatrix(a,b,Ans,diff);
            Ans=roundOff(Ans);
            Ans=wholeNo(Ans);
            int cFact=HCF(commonFactor(Ans));
            Ans=divider(Ans,(double)cFact);
            if(isNotInteger(Ans))
            {
                Ans=divider(Ans,minValue(Ans));
                Ans=roundOff(Ans);
            }
            dispMatrix(Ans);
        }
        else
        System.out.println("Invalid Reaction.");
    }

    double[][] multiply(double d[][], double a)
    {
        for(int i=0;i<d.length;i++)
        {
            for(int j=0;j<d[0].length;j++)
            {
                d[i][j]*=a;
            }
        }
        return d;
    }

    Matrix wholeNo(Matrix a)
    {
        double d[][]=a.getArray();
        double min=minValue(a);
        while(min<1)
        {
            min*=10;
            d=multiply(d,10);
        }
        Matrix D=new Matrix(d);
        return D;
    }

    public int[][] commonFactor(Matrix ans)
    {
        double[][] a=ans.getArray();
        int max=0;
        for(int i=0;i<a.length;i++)
        {
            if(max<a[i][0])
                max=(int)a[i][0];
        }
        int fact[][]=new int[max][a.length];
        for(int i=0;i<fact.length;i++)
        {
            for(int j=0;j<fact[0].length;j++)
            {
                fact[i][j]=0;
            }
        }
        for(int i=0;i<a.length;i++)
        {
            for(int j=2;j<=a[i][0];j++)
            {
                if(a[i][0]%j==0)
                {
                    fact[j-2][i]=1;
                }
            }
        }
        return fact;
    }

    int HCF(int[][] arr)
    {
        int max=1;
        boolean flag=true;
        for(int i=0;i<arr.length;i++)
        {
            flag=true;
            for(int j=0;j<arr[0].length;j++)
            {
                if(arr[i][j]!=1)
                {
                    flag=false;
                    break;
                }
            }
            if(flag)
                max=i+2;
        }
        return max;
    }

    double roundOff(double a)
    {
        int ctr=0;
        while(a<1)
        {
            a=a*10;
            ctr++;
        }
        double b=a%1;
        if((b)<=0.01||(b)>=0.99)
            a=Math.rint(a);
        a=a/(Math.pow(10,ctr));
        return a;
    }

    Matrix roundOff(Matrix mat)
    {
        double[][] arr=mat.getArray();
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr[0].length;j++)
                arr[i][j]=roundOff(arr[i][j]);
        }
        Matrix matFinal=new Matrix(arr);
        return matFinal;
    }

    Matrix divider(Matrix a,double b)
    {
        double A[][]=a.getArray();
        for(int i=0;i<A.length;i++)
        {
            for(int j=0;j<A[0].length;j++)
            {
                A[i][j]=A[i][j]/b;
            }
        }
        Matrix B=new Matrix(A);
        return B;
    }

    public boolean isZero(double a)
    {
        if(a<1.0E-13)
            return true;
        else
            return false;
    }

    double infinitesimalToZero(double n)
    {
        if(isZero(n))
            return 0.0;
        else
            return n;
    }

    public void dispMatrix(Matrix A)
    {
        double[][] a=A.getArray();
        for(int i=0;i<a.length;i++)
        {
            for(int j=0;j<a[0].length;j++)
            {
                System.out.print(a[i][j]+" ");
            }
            System.out.println();
        }
    }

    Matrix step1(Matrix a,Matrix b,int diff)
    {
        double det=-1;
        if(Math.abs(a.det())<1.0E-14)
            det=0;
        else
            det=a.det();
        if(det!=0) 
            return ((a.inverse()).times(b)).times(a.det());
        else
        {
            a=colRemover(a,diff);
            return a.inverse().times(b);
        }///FTTY
    }

    Matrix absoluter(Matrix a)
    {
        double[][] A=a.getArray();
        for(int i=0;i<A.length;i++)
        {
            for(int j=0;j<A[0].length;j++)
            {
                A[i][j]=Math.abs(A[i][j]);
            }
        }
        Matrix b=new Matrix(A);
        return b;
    }

    Matrix zeroRemover(Matrix A)
    {
        double a[][]=A.getArray();
        int ctr=0;
        for(int i=0;i<a.length;i++)
        {
            if(a[i][0]==0)
                ctr++;
        }
        double b[][]=new double[a.length-ctr][1];
        ctr=0;
        for(int i=0;i<a.length;i++)
        {
            if(a[i][0]!=0)
            {
                b[ctr][0]=a[i][0];
                ctr++;
            }
        }
        Matrix B=new Matrix(b);
        return B;
    }

    Matrix addToMatrix(Matrix m, double a)
    {
        double[][] arr=m.getArray();
        double arrFinal[][]=new double[arr.length+1][arr[0].length];
        int i;
        for(i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr[0].length;j++)
                arrFinal[i][j]=arr[i][j];
        }
        arrFinal[i][0]=a;
        Matrix mFinal=new Matrix(arrFinal);
        return mFinal;
    }

    Matrix matrixToZero(Matrix mat)
    {
        double[][] arr=mat.getArray();
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr[0].length;j++)
                arr[i][j]=infinitesimalToZero(arr[i][j]);
        }
        Matrix matFinal=new Matrix(arr);
        return matFinal;
    }

    Matrix colRemover(Matrix mat, int diff)
    {
        double[][] arr=mat.getArray();
        double[][] arrFinal=new double[arr.length][arr[0].length-diff];
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arrFinal[0].length;j++)
                arrFinal[i][j]=arr[i][j];
        }
        Matrix matFinal=new Matrix(arrFinal);
        return matFinal;
    }

    double lastValue(Matrix a, Matrix b, Matrix ans, int diff)
    {
        double[][] B=b.getArray();
        double[][] A=a.getArray();
        double[][] Ans=ans.getArray();
        int index=-1;
        for(int i=0;i<B.length;i++)//to get a non-zero coefficient of the last compound variable
        {
            if(B[i][0]!=0)
            {
                index=i;
                break;
            }
        }
        int len =Ans.length;
        double[][] row=new double[1][len];//to get the corresponding row from a
        double[][] col=new double[len][1];
        //for converting ans to col
        for(int i=0;i<len;i++)//copying ans
            col[i][0]=Ans[i][0];
        for(int i=0;i<len;i++)//copying the corresponding row from a
            row[0][i]=A[index][i];
        Matrix mRow=new Matrix(row);
        Matrix mCol=new Matrix(col);
        Matrix last=mRow.times(mCol);
        return (last.get(0,0))/B[index][0];
    }

    Matrix lastRemover(Matrix mat)
    {
        double[][] arr=mat.getArray();
        double[][] arrFinal=new double[arr.length-1][1];
        for(int i=0;i<arr.length-1;i++)
            arrFinal[i][0]=arr[i][0];
        Matrix matFinal=new Matrix(arrFinal);
        return matFinal;
    }

    Matrix answerMatrix(Matrix a, Matrix b, Matrix ans, int diff)
    {
        double last=lastValue(a,b,ans,diff);
        ans=addToMatrix(ans,last);
        return ans;
    }

    boolean isNotInteger(Matrix mat)
    {
        double[][] arr=mat.getArray();
        boolean flag=false;
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr[0].length;j++)
            {
                if(arr[i][j]%1!=0)
                {
                    flag=true;
                    break;
                }
            }
            if(flag)
                break;
        }
        return flag;
    }

    double minValue(Matrix mat)
    {
        double d[][]=mat.getArray();
        double min=d[0][0];
        for(int i=0;i<d.length;i++)
        {
            for(int j=0;j<d[0].length;j++)
            {
                if(d[i][j]<min)
                    min=d[i][j];
            }
        }
        return min;
    }
    
    int[][] twoDToThreeD(int[][][] m)
    {
        int a[][]=new int[m.length][m[0].length];
        for(int i=0;i<m.length;i++)
        {
            for(int j=0;j<m[0].length;j++)
            {
                a[i][j]=m[i][j][0];
            }
        }
        return a;
    }
}