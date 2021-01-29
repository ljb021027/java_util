import com.MyUtils.j8.People;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujiabei
 * @since 2019/1/11
 */
public class Test3 {

    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        for (String s : list) {
            s = "2";
        }
        System.out.println(list);//[1, 1, 1]

        List<People> list2 = new ArrayList<>();
        People p1 = new People();
        p1.setName("ljb1");
        list2.add(p1);
        People p2 = new People();
        p2.setName("ljb2");
        list2.add(p2);
        for (People people : list2) {
            System.out.println(people.hashCode());
            people.setName("ljb haha");
        }
        System.out.println(list2);
    }
}
