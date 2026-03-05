public class Hanoi {
  
  public static void main(String[] args) {
    //hanoi1HardCoded("A", "C");
    hanoi("A", "B", "C",3);
  }
  
  public static void hanoi1HardCoded(String start, String end) {
    System.out.println(start + " -> " + end);  
  }
  
  public static void hanoi2HardCoded(String start, String spare, String end) {
    System.out.println(start + " -> " + spare);  
    System.out.println(start + " -> " + end);  
    System.out.println(spare + " -> " + end);  
  }
  
  public static void hanoi3HardCoded(String start, String spare, String end) {
    System.out.println(start + " -> " + end);  
    System.out.println(start + " -> " + spare);  
    System.out.println(end + " -> " + spare);  
    System.out.println(start + " -> " + end);  
    System.out.println(spare + " -> " + start);  
    System.out.println(spare + " -> " + end);  
    System.out.println(start + " -> " + end);  
  }
  
  public static void hanoi1CallingSimplerMethods(String start, String end) {
    System.out.println(start + " -> " + end);  
  }
  
  public static void hanoi2CallingSimplerMethods(String start, String spare, String end) {
    hanoi1CallingSimplerMethods(start, spare);  
    System.out.println(start + " -> " + end);  
    hanoi1CallingSimplerMethods(spare, end);  
  }
  
  public static void hanoi3CallingSimplerMethods(String start, String spare, String end) {
    hanoi2CallingSimplerMethods(start, end, spare);  
    System.out.println(start + " -> " + end);  
    hanoi2CallingSimplerMethods(spare, start, end);  
  }
  
  public static void hanoi4(String start, String spare, String end) {
	  hanoi3CallingSimplerMethods(start, end, spare); 
	  System.out.println(start + " -> " + end);  
	  hanoi3CallingSimplerMethods(spare, start, end);  
  }
  public static void hanoi(String start, String spare, String end, int n){
	  if(n==1) {
		  System.out.println(start+"->"+end);
	  }else {
		  //move n-1 discs to middle
		  hanoi(start,end,spare,n-1);
		  //move biggest discs to end
		  System.out.println(start+"->"+end);
		  //move n-1 discs
		  hanoi(spare,start,end,n-1);
	  }
  }
}