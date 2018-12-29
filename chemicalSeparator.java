import java.util.*;
class chemicalSeparator extends chemicalAccept
{
    String[] compoundSeparator(String ChemEq)//paremeter is an instance of reactants or products side
    {
        ChemEq+="+";
        int ctr=0;
        String compound_temp="";
        String compound[]=new String[100];
        int noR=0;
        for(int i=0;i<ChemEq.length();i++)
        {
            char c=ChemEq.charAt(i);
            if(c=='+')
            {
                compound[ctr]=compound_temp;
                compound_temp="";
                ctr++;
            }
            else if(c=='=')
            {
                noR=ctr+1;
                compound[ctr]=compound_temp;
                compound_temp="";
                ctr++;
            }
            else
            {
                compound_temp+=c;
            }
        }
        compound[ctr]=Integer.toString(noR);
        String compoundFinal[]=new String[ctr+1];
        for(int i=0;i<ctr+1;i++)
            compoundFinal[i]=compound[i];
        return compoundFinal;
    }

    public boolean isPresent(Map n, String element)
    {
        int flag=-1;
        Set set=n.entrySet();//To store the map as a set
        Iterator itr=set.iterator();//Iterating through the set
        while(itr.hasNext()) //Essentially iterarting through the map's keys and values
        { 
            Map.Entry entry=(Map.Entry)itr.next();//extarcting an entry from the map
            String key=(String)entry.getKey();
            //System.out.println();
            if(key.equals(element))
            {
                flag=1;
                //System.out.println("IN");
                break;
            }
        }
        //System.out.println();
        if(flag==1)
            return true;
        else
            return false;
    }

    public Map elementStorer(Map map, String element, int rad, int compound, int index, int m)
    {
        if(isPresent(map, element))
        {
            int u_arr[]=(int[])map.get(element);
            u_arr[index]+=rad*compound;
            map.put(element,u_arr);
        }
        else
        {
            int arr[]=new int[m];
            for(int i=0;i<m;i++)
                arr[i]=0;
            arr[index]=rad*compound;
            map.put(element,arr);
        }
        return map;
    }

    //     public boolean isMapValueNull(Map map, String key)
    //     {
    //         if(map.get(key)==null)
    //         return false;
    //         else
    //         return true; 
    //     }

    public Map elementSeparator(String arr[])
    {
        Map elements=new HashMap();
        for(int i=0;i<arr.length;i++)
        {
            String n=arr[i];
            int noCompound=1;
            String Element_temp="";
            int noRadical_temp=1;
            int ctr=0;
            int flag1=0;
            int elementCtr=0;
            for(int j=0;j<n.length();j++)
            {
                char c=n.charAt(j);
                if(Character.isUpperCase(c))
                {
                    if(j==ctr)
                    {
                        Element_temp=""+c;
                        noRadical_temp*=1;
                    }
                    else//n.charAt(i-1)=='+'||n.charAt(i-1)=='=')pay no mind to this
                    {

                        elements=elementStorer(elements, Element_temp, noRadical_temp, noCompound, i, arr.length);
                        noRadical_temp=1;
                        Element_temp=""+c;//resetting to char c(shortcut)
                    }
                }
                else if(Character.isLowerCase(c))
                {
                    Element_temp+=c;
                }
                else if(Character.isDigit(c))
                {
                    if(j==0)
                    {
                        noCompound=(int)c-48;
                        ctr++;
                    }

                    else if(Character.isLetter(n.charAt(j-1)))
                        noRadical_temp*=(int)c-48;
                    else if(Character.isDigit(n.charAt(j-1)))
                    {
                        noRadical_temp=noRadical_temp*10+((int)c-48);
                    }
                }
                else if (c=='.')
                {
                    n=n.replace(".","(");
                    n=n+")";
                    j--;
                }
                else if(c=='(')
                {
                    if(Element_temp!="")
                    {
                        elements=elementStorer(elements, Element_temp, noRadical_temp, noCompound, i, arr.length);
                        noRadical_temp=1;
                        Element_temp="";//resetting to char c(shortcut)
                    }
                    Map para=parenthesesSimplifier(n.substring(n.indexOf('(')+1,n.lastIndexOf(')')));//to extract string between parentheses
                    j=n.lastIndexOf(')');
                    if(n.length()>j+2&&!Character.isLetter(n.charAt(j+2)))
                        flag1=1;
                    if(n.length()>j+1&&Character.isDigit(n.charAt(j+1)))
                    {
                        j++;
                        c=n.charAt(j);
                        Set para_set=para.entrySet();
                        Iterator itr=para_set.iterator();
                        while(itr.hasNext())
                        {
                            Map.Entry entry=(Map.Entry)itr.next();
                            Element_temp=(String)entry.getKey();
                            //noRadical_temp*=noCompound;
                            int e=(int)((int[])entry.getValue())[0];
                            noRadical_temp=e*noCompound;  //Cu+HNO3=Cu(NO3)2+H2O+NO2
                            elements=elementStorer(elements, Element_temp, noRadical_temp, ((int)c-48), i, arr.length);
                            Element_temp="";
                        }
                    }
                    else
                    {
                        Set para_set=para.entrySet();
                        Iterator itr=para_set.iterator();
                        while(itr.hasNext())
                        {
                            Map.Entry entry=(Map.Entry)itr.next();
                            Element_temp=(String)entry.getKey();
                            int e=(int)((int[])entry.getValue())[0];
                            noRadical_temp=e*noCompound;
                            elements=elementStorer(elements, Element_temp, noRadical_temp, 1, i, arr.length);
                            Element_temp="";
                        }
                    }
                }
            }
            if(flag1==0)
                elements=elementStorer(elements, Element_temp, noRadical_temp, noCompound, i, arr.length);
            elements.remove("");
            //System.out.println(elements);
        }
        //         System.out.println("MAP: "+elements);
        return elements;
    }

    public Map parenthesesSimplifier(String n)
    {
        String str[]=new String[1];
        str[0]=n;
        Map p=elementSeparator(str);
        return p;
    }

    public int elementCounter(Map map)
    {
        return map.size();
    }
}
