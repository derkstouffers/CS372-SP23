/* 
 * Deric Shaffer
 * CS372 - Lab1
 * Due Date: Feb. 6th, 2023
 */

class Lab1{
    public static int FirstAttempt(int n){
        // base cases
        if(n == 0) return 0;
        if(n == 1) return 1;
        
        // recursive case
        return FirstAttempt(n - 1) + FirstAttempt(n - 2);
    }// end of FirstAttempt Algorithm

    public static int SecondAttempt(int n){
        // base case
        if(n == 0) return 0;
        
        // temp array for algorithm
        int[] arr = new int[n + 1];

        // default array values
        arr[0] = 0;
        arr[1] = 1;

        // fill in array
        for(int i = 2; i <= n; i++)
            arr[i] = arr[i - 1] + arr[i - 2];

        return arr[n];
    }// end of SecondAttempt Algorithm

    public static void main(String[] args){
        //variables
        int i = 0, res1, res2;
        long start1, start2, stop1, stop2;

        // get results for both algorithms for n = 0 to 20
        while(i <= 20){
            // get runtime for 1st Algorithm at n = i
            start1 = System.nanoTime();
            res1 = FirstAttempt(i);
            stop1 = System.nanoTime();  

            // get runtime for 2nd Algorithm at n = i
            start2 = System.nanoTime();
            res2 = SecondAttempt(i);
            stop2 = System.nanoTime();

            // print out runtime at n = i and value results to see if they are the same (res1 is supposed to equal res2)
            System.out.println(i + ": 1st Algorithm = " + res1 + " runtime: " + (stop1 - start1) + "ns");
            System.out.println(i + ": 2nd Algorithm = " + res2 + " runtime: " + (stop2 - start2) + "ns\n");

            // increment counter variable
            i++;
        }// end of while
    }// end of main
}// end of class