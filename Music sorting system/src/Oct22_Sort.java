import java.util.Scanner;

public class Oct22_Sort {

	public static void main(String[] args) {

		int[] list = {5, 4, 12, 3, 10, 1};
		bubbleSort(list);
		printArray(list);
		//selectionSort(list);

		//		insertionSortV2(list);
		//		printArray(list);
		//		String[] list1 = {"e","j","m","v","s","n","m","u"};
		//		planetSort(list1);
		//		medianFinder();

	}

	/**
	 * Sorts and array of integers using the insertion sort algorithm. 
	 * Call the printArray method after each iteration of
	 * the algorithm to check that your method works correctly. 
	 * 
	 * @param list array of integers to be sorted
	 */
	public static void insertionSort(int[] list) {
		for(int i =1; i<list.length; i++) {
			int temp =i;
			while(temp>0&&list[temp]<list[temp-1]) {
				//swap temp and temp-1
				int temp1=list[temp];
				int temp2=list[temp-1];
				list[temp]=temp2;
				list[temp-1]=temp1;
				temp-=1;
			}
			printArray(list);
		}
	}
	public static void insertionSortV2(int[] list) {
		for(int i =1; i<list.length; i++) {
			for(int x=i; x>0&&list[x]<list[x-1]; x--) {

				int temp1=list[x];
				int temp2=list[x-1];
				list[x]=temp2;
				list[x-1]=temp1;

			}
			printArray(list);
		}
	}
	public static void planetSort(String[] list) {
		for(int i =1; i<list.length; i++) {
			for(int x=i; x>0&&list[x].compareToIgnoreCase(list[x-1])<0; x--) {

				String temp1=list[x];
				String temp2=list[x-1];
				list[x]=temp2;
				list[x-1]=temp1;

			}
			printArray(list);
		}
	}
	public static void medianFinder() {
		Scanner in = new Scanner(System.in);
		System.out.println("Give number of items");
		int[] list=new int[in.nextInt()];
		System.out.println("Give items");
		for(int i =0; i<list.length; i++) {
			list[i]=in.nextInt();
		}



		for(int i =1; i<list.length; i++) {
			for(int x=i; x>0&&list[x]<list[x-1]; x--) {

				int temp1=list[x];
				int temp2=list[x-1];
				list[x]=temp2;
				list[x-1]=temp1;

			}
			printArray(list);
		}
		int x=(list.length-1)/2;
		System.out.println("Median:"+list[x]);
	}


	public static void printArray(int[] x) {
		for(int e: x) {
			System.out.print(e + " ");
		}
		System.out.println();

	}
	public static void printArray(String[] x) {
		for(String e: x) {
			System.out.print(e + " ");
		}
		System.out.println();

	}
	public static void selectionSort(int[] list) {

		for (int i = 0; i < list.length-1; i++) {
			int smallestIndex = i;
			for (int x = i + 1; x < list.length; x++) {
				if (list[x] < list[smallestIndex]) {
					smallestIndex = x;
				}
			}
			int temp = list[smallestIndex];
			list[smallestIndex] = list[i];
			list[i] = temp;
			printArray(list);
		}
	}
	public static void question4B(int[]list) {
		for(int i = list.length-1; i > 0 ; i--)
		{
			int maxLocation = 0;

			for(int j = 0; j <= i ; j++)
			{
				if(list[j] > list[maxLocation])
				{
					maxLocation = j;
				}
			}
			int temp = list[maxLocation];
			list[maxLocation] = list[i]; 

			list[i] = temp; 

		}
	}
	public static void bubbleSort(int[] list) {
		int n = list.length;
		boolean swapped=true;
		while(swapped) {
			for (int i = 0; i < n - 1; i++) {
				swapped = false;
				for (int j = 0; j < n - 1 - i; j++) {
					if (list[j] > list[j + 1]) {
						int temp = list[j];
						list[j] = list[j + 1];
						list[j + 1] = temp;
						swapped = true;
					}
				}

			}
		}

	}

}