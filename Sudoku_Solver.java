//Brendon Lyra
//1/18/18

import java.util.Scanner;


public class Sudoku_Solver {

    public static void main(String[] args) {
        
        int num_programs;
        Scanner sc = new Scanner(System.in);        
        num_programs = sc.nextInt();
        
        //will hold puzzle given by inputs
        int[] sudoku = new int[81];
        
        //iterate through all sudoku puzzles.
        for (int i =0; i<num_programs; i++)
        {            
            System.out.println("Test case " + (i+1) + ":"); 
            System.out.println("");            
         
            //get new puzzle
            for (int j=0; j<81; j++) 
            {
                sudoku[j] = sc.nextInt();
            }
            
            if ( bad_board(sudoku) < 1 || solve_puzzle(0, sudoku) < 1)
            {                
                System.out.println("No solution possible.");
                System.out.println("");
                System.out.println("");
            }
        }
    }
    
    //checks if the board given is invalid.
    private static int bad_board(int sudoku[])
    {
                        
        for (int i =0; i<81; i+=9)
        {
            if (row_incorrect(i, sudoku))
                return -1;            
        }
        
        
        for (int i =0; i<9; i++)
        {
            if (col_incorrect(i, sudoku))
                return -1;
        }
        
        for (int i =0; i<81; i+=3)
        {            
            if (box_incorrect(i, sudoku))
                return -1;
        }
        
        
        //board is fine.
        return 1;
    }
    
    //recursively solves sudoku puzzle, with backtracking.
    private static int solve_puzzle(int location, int[] sudoku)
    {
        //puzzle is complete
        if (location == 81)
        {            
            print_sudoku(sudoku);
            return 1;            
        }
        
        //if slot was already filled when puzzle read, move to next slot.
        if (sudoku[location] != 0)
            return solve_puzzle(location+1, sudoku);
        
        int correct = 0;
        
        for (int i=1; i<10; i++)
        {
            sudoku[location] = i;
                           
            //if nothing wrong with this number in this slot, move to next slot.
            if (!row_incorrect(location, sudoku))                
                if (!col_incorrect(location, sudoku))                 
                    if (!box_incorrect(location, sudoku))                  
                        correct += solve_puzzle(location +1, sudoku);
            
            
            //reset back to 0 if none of the 9 numbers worked in this slot.
            sudoku[location] = 0;
            
            if (correct > 1)
                return correct;                          
            
        }        
        return correct;        
    }
    
    //prints solved sudoku puzzle.
    private static void print_sudoku(int[] puzzle)
    {       
        for (int i =0; i<81; i++)
        {
            if (i%9==0 && i>0)
                System.out.println("");
            
            System.out.print(puzzle[i] + " ");            
        }
        System.out.println("");  
        System.out.println("");
        System.out.println("");
    }
    
    //checks if the row current slot is in has any duplicate numbers.
    private static boolean row_incorrect(int location, int[] puzzle)
    {
        int[] row = new int[10];        
        
        //finding start of current row.
        location /=9;
        location *=9;
        
        //making a frequency array of this row.
        for (int i =location; i<location+9; i++)                                
            row[puzzle[i]]++;        
        
        //checking for a duplicate. true if there was an error.
        for (int i=1; i<10; i++)
            if (row[i] > 1)
                return true;
        
        //row had no duplicates
        return false;
    }
    
    //checks if the column current slot is in has any duplicate numbers.
    private static boolean col_incorrect(int location, int[] puzzle)
    {
        location%=9;
        int[] col = new int[10];
        
        //making frequency array of current column.
        for (int i = location; i<81; i+=9)        
            col[puzzle[i]]++;        
        
        //returns true if duplicate found.
        for (int i =1; i<10; i++)
            if (col[i] >1)
                return true;
                
        //column was correct
        return false;
    }
    
    //checks if the 3x3 box currrent slot is in has any duplicate numbers.
    private static boolean box_incorrect(int location, int[] puzzle)
    {        
        int [] box_frequency = new int[10];
        
        //getting starting column for the 3x3 box.
        int col = location/3;        
        col%=3;
        col*=3;
        
        //getting starting row for the 3x3 box
        int row = location /27;
        row*=27;
        
        //first slot of 3x3 block.
        int slot = row + col;
        
        //every 3 slots, increment bylocation by 7 (next row of 3x3 box)
        int next_row = 0;
        for (int i = 0; i<9; i++)
        {                     
            if (next_row >0 && next_row%3 == 0)
                slot+=6;          
            box_frequency[puzzle[slot]]++;           
            slot++;
            next_row++;            
        }        
        
        //there was a duplicate in this 3x3 box.
        for (int i=1; i<10; i++)
            if (box_frequency[i] > 1)
                return true;
        
        //3x3 box was correct
        return false;
    }        
}