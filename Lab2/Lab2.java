/* 
 * Deric Shaffer
 * CS372 - Lab2
 * Due Date: Feb. 20th, 2023
 */

import java.util.Arrays;

class Lab2{
    public static String long_hand(String x, String y){
        // variables
        int[] num1 = new int[x.length()];
        int[] num2 = new int[y.length()];
        int[] res = new int[num1.length + num2.length];

        // convert strings into integer arrays
        for(int i = 0; i < x.length(); i++){
            // subtract the character by '0' to get an integer. '1' = 49  & '0' = 48 in decimal based 
            // on the ASCII table which would result in either 49 - 48 = 1 or 48 - 48 = 0

            num1[i] = x.charAt(i) - '0';
            num2[i] = y.charAt(i) - '0';
        }// end of for loop

        // multiply the numbers together
        for(int i = num2.length - 1; i >= 0; i--){
            if(num2[i] == 1)
                for(int j = num1.length - 1; j >= 0; j--)
                    if(num1[j] == 1)
                        // i + j + 1 deals with shifting correctly
                        res[i + j + 1] += 1;
        }// end of for loop

        // deal with the carry involved with adding
        for(int i = res.length - 1; i >= 1; i--){
            res[i - 1] += res[i] / 2;
            res[i] %= 2;
        }// end of for loop

        // convert result from int array back to string and return it
        return Arrays.toString(res);
    }// end of long-hand multiplication method

    public static String div_conq(String x, String y){
        // variables
        String xl, xr, yl, yr, p1, p2, p3;

        // pad both strings with leading zeros if both have odd length > 1
        while(x.length() < y.length() || x.length() > y.length()) {
            if(x.length() < y.length()) 
                x = "0" + x;
            if(x.length() > y.length())
                y = "0" + y;
        }

        // define n
        int n = Math.max(x.length(), y.length()); 

        // base case
        if(n == 1){
            if(x.equals("1") && y.equals("1"))
                return "1";
            
            return "0";
        }

        // Changed bounds to be calculated without using floor and ceil functions
        int l_bound = n / 2;
        int r_bound = n - l_bound;

        // determine xl, xr, yl, yr
        xl = x.substring(0, l_bound);
        yl = y.substring(0, l_bound);

        xr = x.substring(l_bound);
        yr = y.substring(l_bound);

        p1 = div_conq(xl, yl);
        p2 = div_conq(xr, yr);
        p3 = div_conq(bin_add(xl, xr), bin_add(yl, yr));

        String temp1 = bin_sub(p3, p1); // (P3 - P1)
        String temp2 = bin_sub(temp1, p2); // (P3 - P1 - P2)

        // Shift by bound with floor value
        String temp3 = bin_lshift(temp2, r_bound); // (P3 - P1 - P2) * 2^n/2
        // Shift by 2 times bound with floor value 
        String temp4 = bin_add(bin_lshift(p1, 2*r_bound), temp3); // p1 * 2^n + (P3 - P1 - P2) * 2^n/2
        
        return bin_add(temp4, p2); // p1 * 2^n + (P3 - P1 - P2) * 2^n/2 + p2
    }// end of div_conq algorithm method

    public static String naive_div(String x, String y){
        // variables
        String xl, xr, yl, yr, p1, p2, p3, p4;

        // pad both strings with leading zeros if both have odd length > 1
        while(x.length() < y.length() || x.length() > y.length()) {
            if(x.length() < y.length()) 
                x = "0" + x;
            if(x.length() > y.length())
                y = "0" + y;
        }

        // define n
        int n = Math.max(x.length(), y.length()); 

        // base case
        if(n == 1){
            if(x.equals("1") && y.equals("1"))
                return "1";
            
            return "0";
        }

        // Changed bounds to be calculated without using floor and ceil functions
        int l_bound = n / 2;
        int r_bound = n - l_bound;

        // determine xl, xr, yl, yr
        xl = x.substring(0, l_bound);
        yl = y.substring(0, l_bound);

        xr = x.substring(l_bound);
        yr = y.substring(l_bound);

        p1 = naive_div(xl, yl);
        p2 = naive_div(xl, yr);
        p3 = naive_div(xr, yl);
        p4 = naive_div(xr, yr);

        String temp1 = bin_lshift(p1, 2*r_bound); // 2^n * (xl * yl)
        String temp2_inner = bin_add(p2, p3); // ((xl * yr) + (xr * yl))
        String temp2_outer = bin_lshift(temp2_inner, r_bound); // 2^n/2 * ((xl * yr) + (xr * yl))

        return bin_add(temp1, bin_add(temp2_outer, p4)); // 2^n * (xl * yl) + 2^n/2 * ((xl * yr) + (xr * yl)) + (xr * yr)

    }// end of naive div_conq function

    // helper functions
    public static String bin_lshift(String s, int num_shifts){
        for(int i = 0; i < num_shifts; i++)
            s = s + "0";

        return s;
    }// end of left shifting helper function
    
    public static String bin_sub(String x, String y){
        StringBuilder res = new StringBuilder();
        int carry = 0;

        // pad both strings with leading zeros if both have odd length
        while(x.length() < y.length() || x.length() > y.length()) {
            if(x.length() < y.length())
                x = "0" + x;
            if(x.length() > y.length())
                y = "0" + y;
        }

        // When calculating sub I call complement of second string, and only complement if the second bitstring is nonzero
        for(int i = 0; i < y.length(); i ++) {
            if(y.charAt(i) == '1') {
                y = twos_comp(y);
                break;
            }
        }

        for(int i = x.length() - 1; i >= 0; i--) {
            if(x.charAt(i) == '1' && y.charAt(i) == '1'){
                if(carry == 1){
                    carry = 1;
                    res.append('1');
                }else{
                    carry = 1;
                    res.append('0');
                }
            }else if(x.charAt(i) == '1' && y.charAt(i) == '0'){
                if(carry == 1){
                    carry = 1;
                    res.append('0');
                }else{
                    carry = 0;
                    res.append('1');   
                }
            }else if(x.charAt(i) == '0' && y.charAt(i) == '1'){
                if(carry == 1){
                    carry = 1;
                    res.append('0');
                }else{
                    carry = 0;
                    res.append('1');
                }
            }else if(x.charAt(i) == '0' && y.charAt(i) == '0'){
                if(carry == 1){
                    carry = 0;
                    res.append('1');
                }else{
                    carry = 0;
                    res.append('0');
                }
            }
        }

        res.reverse();

        return res.toString();
    }

    public static String bin_add(String x, String y){
        StringBuilder res = new StringBuilder();
        int carry = 0;

        // pad both strings with leading zeros if both have odd length
        while(x.length() < y.length() || x.length() > y.length()) {
            if(x.length() < y.length())
                x = "0" + x;
            if(x.length() > y.length())
                y = "0" + y;
        }

        for(int i = x.length() - 1; i >= 0; i--) {
            if(x.charAt(i) == '1' && y.charAt(i) == '1'){
                if(carry == 1){
                    carry = 1;
                    res.append('1');
                }else{
                    carry = 1;
                    res.append('0');
                }
            }else if(x.charAt(i) == '1' && y.charAt(i) == '0'){
                if(carry == 1){
                    carry = 1;
                    res.append('0');
                }else{
                    carry = 0;
                    res.append('1');   
                }
            }else if(x.charAt(i) == '0' && y.charAt(i) == '1'){
                if(carry == 1){
                    carry = 1;
                    res.append('0');
                }else{
                    carry = 0;
                    res.append('1');
                }
            }else if(x.charAt(i) == '0' && y.charAt(i) == '0'){
                if(carry == 1){
                    carry = 0;
                    res.append('1');
                }else{
                    carry = 0;
                    res.append('0');
                }
            }
        }

        if(carry > 0)
            res.append(carry);

        res.reverse();

        return res.toString();
    }// end of adding helper function

    public static String twos_comp(String x){
        String temp = "";

        for(int i = 0; i < x.length(); i++){
            if(x.charAt(i) == '1')
                temp = temp + "0";
            else
                temp = temp + "1";
        }

        temp = bin_add(temp, "1");

        return temp;
    }// end of binary subtraction helper function

    public static void main(String[] args){
        // variables
        String n, f, l;
        long start, stop;
        String[] binary = {"11110000", "10111100", "11111111", "00001111", "10101010", "01010101", "10100001", "00110011", "11001100", "10000011"};

        // make 5 tests to use on each alg.
        for(int i = 0; i < binary.length; i += 2){
            System.out.println("------------------------------------");

            start = System.nanoTime();
            f = div_conq(binary[i], binary[i+1]);
            stop = System.nanoTime();

            System.out.println("non-naive runtime (ns): " + (stop - start));

            start = System.nanoTime();
            n = naive_div(binary[i], binary[i+1]);
            stop = System.nanoTime();

            System.out.println("naive runtime (ns): " + (stop - start));

            start = System.nanoTime();
            l = long_hand(binary[i], binary[i+1]);
            stop = System.nanoTime();

            System.out.println("long-hand runtime (ns): " + (stop - start));
        }

        System.out.println("------------------------------------");

    }// end of main
}// end of class