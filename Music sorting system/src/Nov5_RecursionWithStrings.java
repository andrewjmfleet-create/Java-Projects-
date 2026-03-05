public class Nov5_RecursionWithStrings {

	public static void main(String[] args) {
		System.out.println(reverseString("car"));
		System.out.println(reverseString1("car"));
		System.out.println(reverseString2("car"));
		System.out.println(reverseString3("car"));

	}
	
	/* #1
	 * A palindrome is a word that reads the same both forward 
	 * and backward, like “otto” and “palindromeemordnilap”. 
	 * Here’s one way to test whether a string is a palindrome:
	 *
	 * A single letter is a palindrome, a two-letter word is a 
	 * palindrome if the letters are the same, and any other 
	 * word is a palindrome if the first letter is the same as 
	 * the last and the middle is a palindrome.
	 * 
	 * Write a recursive method named isPalindrome that 
	 * takes a String and returns a boolean indicating whether 
	 * the word is a palindrome.
	 */
	public static boolean isPalindrome(String word) {
		//if word is a single letter - return true
		if(word.length()==1) {
			return true;
			//if word is two letter  & they are the same - return true
		}else if(word.length()==2 && word.charAt(0)==word.charAt(1)) {
			return true;
			//else - recursive call - compare first and last letter
			//						  call again if they are the same
			//						  without the first and last letters
		}else {
			if(word.charAt(0)==word.charAt(word.length()-1)) {
				return isPalindrome(word.substring(1,word.length()-1));
			}else {
				return false;
			}
		}
	}

	/* #2
	 * What does the following method do?
	 */
	public static String mystery(int n) {
		if(n == 0) {
			return "";
		}else {
			return mystery(n-1) + "a";
		}
	}
	
	/* #3
	 * String Reversal
	 * To reverse a String
	 * 	 if the string is not empty
	 * 	   - without the first character, reverse
	 *       the rest of the String 
	 *     - place the first character at the end
	 *       of the reversed substring. 
	 */

	public static String reverseString(String s){
		if (s.length() == 0) {
	        return "";
	    } else {	       
	        String reversedSubstring = reverseString(s.substring(1));
	        return reversedSubstring + s.charAt(0);
	    }
	}
	
	public static String reverseString3(String s) {
		if(s.length()==0) {
			return "";
		}else {
			return s.charAt(s.length()-1)+reverseString3(s.substring(1));
		}
	}
	public static String reverseString1(String s) {
	    if (s.length() == 0) {
	        return "";
	    } else {
	        // Recursive call: reverse the string up to, but not including, the last character
	        String reversedSubstring = reverseString1(s.substring(0, s.length() - 1));
	        // Place the last character at the front
	        return s.charAt(s.length() - 1) + reversedSubstring;
	    }
	}
	public static String reverseString2(String s) {
	    if (s.length() <= 1) {
	        return s;
	    } else {
	       
	        int mid = s.length() / 2;
	       
	        String reversedSecondHalf = reverseString2(s.substring(mid));
	        String reversedFirstHalf = reverseString2(s.substring(0, mid));
	        
	        
	        return reversedSecondHalf + reversedFirstHalf;
	    }
	}
}