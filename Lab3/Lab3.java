/*
 * Deric Shaffer
 * CS372 - Lab3
 * Due Date: March 6th, 2023
 */

 import java.util.Arrays;
 import java.util.Collections;

 public class Lab3{
    public static int[] insertion(int[] arr){
        // variables
        int key, j;

        for(int i = 2; i < arr.length; i++){
            key = arr[i];

            // insert arr[i] into sorted sequence arr[1... j-1]
            j = i - 1;

            while(j >= 0 && arr[j] > key){
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }

        return arr;
    }// end of insertion sort algorithm method

    public static int[] quicksort(int[] arr, int p, int r) {
        // variables
        int q;

        if(p < r){
            q = partition(arr, p, r);
            arr = quicksort(arr, p, q - 1);
            arr = quicksort(arr, q + 1, r);
        }
        
        return arr;
    }// end of quicksort algorithm method

    public static int partition(int[] arr, int p, int r) {
        // variables
        int x = arr[r];
        int i = p - 1;

        for(int j = p; j < r; j++){
            if(arr[j] <= x){
                i++;
                
                // swap arr[i] and arr[j]
                int temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
            }
        }

        // swap arr[i + 1] and arr[r]
        int temp = arr[r];
        arr[r] = arr[i + 1];
        arr[i + 1] = temp;

        return i + 1;
    }// end of partition method

    // helper function for question 1d
    public static int[] insertion_reverse(int[] arr){
        // variables
        int key, j;

        for(int i = 2; i < arr.length; i++){
            key = arr[i];

            // insert arr[i] into sorted sequence arr[1... j-1]
            j = i - 1;

            // MODIFICATION FOR QUESTION 1d - changed the '>' in the original function (line 32) to be a '<'
            while(j >= 0 && arr[j] < key){
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }

        return arr;
    }// end of reverse insertion sort algorithm method

    // helper functions for question 2b
    public static int[] quicksort_three(int[] arr, int p, int r) {
        // variables
        int q;

        if(p < r){
            q = partition_three(arr, p, r);
            arr = quicksort_three(arr, p, q - 1);
            arr = quicksort_three(arr, q + 1, r);
        }
        
        return arr;
    }// end of quicksort_three algorithm method

    public static int partition_three(int[] arr, int p, int r) {
        // variables
        int i = p - 1;
        int temp;

        // find the median
        int[] piv = {arr[p], arr[Math.floorDiv(r, 2)], arr[r]};
        Arrays.sort(piv);

        if(piv[1] == arr[p]){
            temp = arr[r];
            arr[r] = piv[1];
            arr[p] = temp;
        }else if(piv[1] == arr[Math.floorDiv(r, 2)]){
            temp = arr[r];
            arr[r] = piv[1];
            arr[Math.floorDiv(r, 2)] = temp;
        }else if(piv[1] == arr[r]){
            temp = arr[r];
            arr[r] = piv[1];
            arr[r] = temp;
        }

        int x = piv[1];

        for(int j = p; j < r; j++){
            if(arr[j] <= x){
                i++;
                
                // swap arr[i] and arr[j]
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // swap arr[i + 1] and arr[r]
        temp = arr[i + 1];
        arr[i + 1] = arr[r];
        arr[r] = temp;

        return i + 1;
    }// end of partition_three method

    public static void main(String[] args){
        // variables
        int n = 1000;
        long start, stop;

        // insertion test arrays
        int[] insert_arr = new int[n];
        int[] manual_arr = new int[n];

        // quicksort test arrays
        int[] quick_arr = new int[n];

        // populate arrays with random elements from 0 to 100
        for(int i = 0; i < insert_arr.length; i++){
            insert_arr[i] = (int) (Math.random() * 100);
            quick_arr[i] = (int) (Math.random() * 100);
        }

        //      insertion sort already-sorted/best-case scenario test
        // -------------------------------------------------------------------

        // sort array before calling insertion sort
        Arrays.sort(insert_arr);

        start = System.nanoTime();
        insert_arr = insertion(insert_arr);
        stop = System.nanoTime();

        System.out.println(Arrays.toString(insert_arr) + "\n");
        System.out.println("Insertion Best-Case Runtime: " + (stop - start) + "(ns)\n\n");


        //      insertion sort worst-case scenario test
        // ---------------------------------------------------
        
        // reverse sorted array
        Collections.reverse(Arrays.asList(insert_arr));

        start = System.nanoTime();
        insert_arr = insertion(insert_arr);
        stop = System.nanoTime();

        System.out.println(Arrays.toString(insert_arr) + "\n");
        System.out.println("Insertion Worst-Case Runtime: " + (stop - start) + "(ns)\n\n");


        //      insertion sort manual input scenario test
        // ---------------------------------------------------

        // populate array with the same number
        for(int i = 0; i < manual_arr.length; i++)
            manual_arr[i] = 19;

        start = System.nanoTime();
        manual_arr = insertion(manual_arr);
        stop = System.nanoTime();

        System.out.println(Arrays.toString(manual_arr) + "\n");
        System.out.println("Insertion Same Number Runtime: " + (stop - start) + "(ns)\n\n");


        //      insertion sort modified scenario test
        // ---------------------------------------------------

        start = System.nanoTime();
        insert_arr = insertion_reverse(insert_arr);
        stop = System.nanoTime();

        System.out.println(Arrays.toString(insert_arr) + "\n");
        System.out.println("Reverse Insertion Runtime: " + (stop - start) + "(ns)\n\n");




        //      quicksort already-sorted/best-case test
        // ---------------------------------------------------

        // sort array before calling quicksort
        Arrays.sort(quick_arr);

        start = System.nanoTime();
        quick_arr = quicksort(quick_arr, 0, quick_arr.length - 1);
        stop = System.nanoTime();

        System.out.println(Arrays.toString(quick_arr) + "\n");
        System.out.println("Quicksort w/o Median Pivot Runtime: " + (stop - start) + "(ns)\n\n");


        //          quicksort median-of-3 pivot test
        // ---------------------------------------------------
        start = System.nanoTime();
        quick_arr = quicksort_three(quick_arr, 0, quick_arr.length - 1);
        stop = System.nanoTime();

        System.out.println(Arrays.toString(quick_arr) + "\n");
        System.out.println("Quicksort w/ Median Pivot Runtime: " + (stop - start) + "(ns)\n\n");

    }// end of main
 }// end of class