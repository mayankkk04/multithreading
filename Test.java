import java.util.*;
import java.util.stream.*;
public class Test{
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        List<Integer> filteredList = Stream.iterate(0, x -> x+1).limit(101).skip(1).collect(Collectors.toList());
        System.out.println(filteredList);
    }
}