package when.injection;

/**
 * @author: when
 * @create: 2020-03-02  17:13
 **/
public class Test {
    private int no;

    public Test(int no) {
        this.no = no;
    }

    public void test() {
        System.out.println(no);
    }
}
