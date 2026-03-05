
public class Nov13_RecursiveMethods {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {1,2,3,4,5,6,7,8,9};
		System.out.println(binaryRec(a, 10, 0, a.length-1));
	}
	public static int binaryRec(int[] a, int v, int low, int high) {
		int middle = (low+high)/2;
		
		
		
		//Base case- v is the middle
		if(a[middle]==v) {
			return middle;
		}else if(low>high) {
			return -1;
			//Recursive check 
			//Check if v is in the first or second half
			//Perform binary search on half of the array
		}else if(v<a[middle]){
			return binaryRec(a,v,low,middle-1);
		}else{
			return binaryRec(a,v,middle+1, high);
		}
		
	}
	public static int brinarySearch(int[] a, int v) {
		int low=0;
		int high=a.length-1;
		int middle=(low+high)/2;
		while(low<=high) {
			if(a[middle]==v) {
				return middle;
			}else if(a[middle]>v) {
				high=middle-1;
				middle = (low+high)/2;
			}else if(a[middle]<v) {
				low = middle+1;
				middle = (low+high)/2;
			}
		}

		return -1;

	}
}
