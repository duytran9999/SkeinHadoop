import java.util.*;
import java.math.BigInteger;
class FunctionProgram{
	public static void main(String[] args) {
		int[] array = {255, 194, 194, 194, 122, 219, 255, 255, 255, 194, 194, 194, 194, 219, 255, 255, 255, 255, 255, 255, 255, 219, 255, 255, 255, 207, 255, 255, 255, 255, 255, 255, 255, 207, 255, 255, 179, 219, 255, 255, 255, 219, 255, 255, 255, 255, 255, 255, 255, 194, 255, 255, 174, 194, 255, 255, 255, 194, 255, 255, 194, 194, 255, 255};
		//Arrays.stream(array).reduce((x,y) -> x|y[1]<<y[0]).ifPresent(s -> System.out.println(s));
		//Map<Integer, Integer> m1 = new HashMap<Integer, Integer>(); 
		BigInteger a[]=new BigInteger[array.length];
		for(int i = 0; i<array.length; i++) {
			if (array[i] < 250)
				a[i] = BigInteger.ZERO;
			else
				a[i] = BigInteger.ONE;
		}

		BigInteger avhash = new BigInteger("0");
		for (int i = 0; i<array.length; i++) {
			//System.out.println(a[i].shiftLeft(i));
    		avhash = avhash.or(a[i].shiftLeft(i));
    		System.out.println(avhash);
    		// do something with key and/or tab
		}


	}
}