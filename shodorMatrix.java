import java.util.*;
class shodorMatrix extends chemicalSeparator
{
    public int[][][] matrixMaker()
    {
        String n=accept();
        String compounds[]=compoundSeparator(n);
        int noR=Integer.valueOf(compounds[compounds.length-1]);
        String[] comp=new String[compounds.length-1];
        for(int i=0;i<compounds.length-1;i++)
            comp[i]=compounds[i];

        Map matMap=elementSeparator(comp);
        int m=matMap.size();
        int o=comp.length;

        int mat[][][]=new int[m][o][2];

        Set set=matMap.entrySet();
        Iterator itr=set.iterator();

        int j=0;

        while(itr.hasNext())
        {
            Map.Entry entry=(Map.Entry)itr.next();
            int arr[]=(int[])entry.getValue();
            for(int i=0;i<noR;i++)
            {
                mat[j][i][0]=arr[i];
            }
            for(int i=noR;i<arr.length;i++)
            {
                mat[j][i][0]=-arr[i];
            }
            j++;
        }
        mat[0][0][1]=noR;
        return mat;
    }

    public int[][] getMatrix(int[][] mat)
    {
        int r=mat.length;
        int c=mat[0].length;
        int[][] nMat=new int[r][c-1];
        for(int i=0;i<r;i++)
        {
            for(int j=0;j<c-1;j++)
            {
                nMat[i][j]=mat[i][j];
            }
        }
        return nMat;
    }

    public int[][] getMatrixAns(int[][] mat)
    {
        int r=mat.length;
        int c=mat[0].length;
        int[][] nMat=new int[r][1];
        for(int i=0;i<r;i++)
        {
            nMat[i][0]=mat[i][c-1];
            nMat[i][0]=Integer.valueOf(Math.abs(nMat[i][0]));
        }
        return nMat;
    }

    int[][] matrixFiller(int[][] arr)
    {
        int diff=0;
        boolean row=arr.length>arr[0].length;
        boolean col=arr.length<arr[0].length;
        int[][] arrFinal=new int[100][100];
        if(row)
        {
            diff=arr.length-arr[0].length;
            arrFinal=new int[arr.length][arr[0].length+diff];
            for(int m=0;m<arr.length;m++)
            {
                for(int n=0;n<arr[0].length;n++)
                    arrFinal[m][n]=arr[m][n];
            }
        }
        else if(col)
        {
            diff=arr[0].length-arr.length;
            arrFinal=new int[arr.length+diff][arr[0].length];
            for(int m=0;m<arr.length;m++)
            {
                for(int n=0;n<arr[0].length;n++)
                    arrFinal[m][n]=arr[m][n];
            }
        }
        for(int i=1;i<=diff;i++)
        {
            if(row)
            {
                for(int j=0;j<arr.length;j++)
                    arrFinal[j][arrFinal[0].length-i]=1;
            }
            if(col)
            {
                for(int j=0;j<arr[0].length;j++)
                    arrFinal[arrFinal.length-i][j]=1;
            }
        }
        return arrFinal;
    }

    double[][] intToDouble(int[][] arr)
    {
        double[][] darr= new double[arr.length][arr[0].length];
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr[0].length;j++)
                darr[i][j]=arr[i][j];
        }
        return darr;
    }

    boolean isNotNull(int n[])
    {
        boolean flag=true;
        for(int i=0;i<n.length;i++)
        {
            if(n[i]!=0)
            {
                flag=false;
                break;
            }
        }
        return flag;
    }

    boolean isReactionValid(int[][] arr, int n)
    {
        boolean flag=false;
        for(int i=0;i<arr.length;i++)
        {
            int reac[]=new int[n];
            int prod[]=new int[arr[0].length-n];
            for(int j=0;j<arr[0].length;j++)
            {
                if(j<n)
                    reac[j]=arr[i][j];
                else
                    prod[j-n]=arr[i][j];
            }
            if(isNotNull(reac)&&isNotNull(prod))
                flag=true;
            else
            {
                flag=false;
                break;   
            }
        }
        return flag;
    }
}