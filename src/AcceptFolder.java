//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 
// Program Name:                  AcceptFolder.java
// Description:                       This Program Performs Following Two Tasks as follows:
//                                          1. It counts Unique Words of txt Files of specified path of folder 
//                                          2. It counts User specified Word per txt file in that folder.
// Author:                               Kaustubh
// Usage:                               1. To get count of Unique word then Enter full path to the folder and Unique. Then
//                                             It will Show count of Unique words from that Directory.
//                                             (  E.g /home/kaustubh/Desktop/Books Unique (It counts unique) )
//                                           2. To get count of User specified word. Then enter full path and count word.
//                                             ( E.g /home/kaustubh/Desktop/Books Count Word)
//
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


import java.util.*;
import java.lang.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


class CountAndUnique
{
	// This function count unique words of all txt files from specified folder
	protected void CountUnique(String Upath)throws IOException
	{
		//Created hashset to store Uniue words. The HashSet keeps only the unique words.
		//Doesn't allows duplicate values. 
		HashSet<String> words = new HashSet<String>();       
		 
		 //It is used to read all lines.
		BufferedReader k = new BufferedReader(new InputStreamReader(System.in));
        long endTime;
		long startTime;
		startTime=System.nanoTime();
			
			//Files.walk() returns a Stream. I applied filter to get only files with .txt extension. 
			
			Files.walk(Paths.get(Upath)).filter(file -> {
 
				if(Files.isRegularFile(file)) 
				{
					String fileName = file.getFileName().toString();
					int extenpos = fileName.lastIndexOf('.');
					if(fileName.substring(extenpos+1).toLowerCase().equals("txt"))
					{
						return true;
					}
					else
					{
						return false;
					}
				}
            else
			{
                return false;
			}
        }).forEach(txtfile -> {  
		//For each text file we go through each line and add words to the HashSet words. 
            try {
                System.out.println("Parsing File: "+txtfile.getFileName().toString());
                Files.lines(txtfile).forEach(line -> {
                    String w[] = line.split(" ");
                    for (String word: w) {
                        words.add(word);
                    }
                });
            }
            catch(IOException e)
			{
                e.printStackTrace();
            }
        });
		System.out.println("Total Unique words = "+ words.size()); //Print the output i.e. count of Unique Words
		endTime=System.nanoTime();
        System.out.println("Required time for searching is : "+ (endTime-startTime)/1000000000+" seconds");
	}
	
	// This function counts User entered  word of files specified	
	protected void CountSpecificWord(String Upath)throws IOException
	{
		int[] iCnt=new int[1]; //Counter to count user entered word
		
		Scanner sobj=new Scanner(System.in);
		System.out.println("Enter Word That You want to Count:");
		String UCWord=sobj.nextLine();  //String to store the entered word
		
		BufferedReader k = new BufferedReader(new InputStreamReader(System.in));
        
		long endTime;
		long startTime;
		startTime=System.nanoTime();
			//Files.walk() returns a Stream. I applied filter to get only files with .txt extension. 
			Files.walk(Paths.get(Upath)).filter(file -> {
			if(Files.isRegularFile(file)) 
			{
                String fileName = file.getFileName().toString();
                int extenpos = fileName.lastIndexOf('.');
                if(fileName.substring(extenpos+1).toLowerCase().equals("txt"))
				{
                    return true;
				}
                else
				{
                    return false;
				}
            }
            else
			{
                return false;
			}
        }).forEach(txtfile -> {
			//For each text file we go through each line and add words to count word. 
            try {
                System.out.println("Parsing File: "+txtfile.getFileName().toString());
                Files.lines(txtfile).forEach(line -> {
                    String w[] = line.split(" ");
                    for (String word: w) 
					{
                        if(word.equalsIgnoreCase(UCWord)) //If same word occurs in file then increment the counter. 
						{
							iCnt[0]++;
						}
							
                    }
                });
            }
            catch(IOException e)
			{
                e.printStackTrace();
            }
        });
		System.out.println("Count of your word is:"+iCnt[0]); //Print the count of word.
		endTime=System.nanoTime();
        System.out.println("Required time for searching is : "+ (endTime-startTime)/1000000000+" seconds");
	}
}
// Entry Point Function	
class AcceptFolder
{
	public static void main(String args[])throws IOException
	{
		CountAndUnique cu=new CountAndUnique();   //Created object of logic class
		
		Scanner sobj=new Scanner(System.in);
		
		//Accept Path and Activity From user
		System.out.println("Enter Path With Activity(Unique or Count Word):");
		String textPath=sobj.nextLine();
		
		//Entered String Splitted USing String Function
		String tokens[]=textPath.split("\\s");
		
		String p1=tokens[0];  //Path stroed in string p1
		
		// Filter to check wrong input
		if((tokens.length<=1)||(tokens.length>=4))
		{
			System.out.println("You entered Wrong Input");
			System.exit(0);
		}
		
		
		//Call to the unique Method
		if(tokens.length==2)
		{
			if (tokens[1].equalsIgnoreCase("Unique"))
			cu.CountUnique(p1); 		//Passed Folder path to CountUnique Function
		}
		//call to CountSpecificWord function 
		if(tokens.length==3)
		{
			if(tokens[1].equalsIgnoreCase("count")&&(tokens[2].equalsIgnoreCase("word")))
			cu.CountSpecificWord(p1); //Passed Folder path to CountUnique Function
		}
		
	}
}