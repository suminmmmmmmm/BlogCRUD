import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class JUnitQuiz {
    @Test
    public void test1() {
        String name1 = "홍길동";
        String name2 = "홍길동";
        String name3 = "홍길은";

        //모든 변수가 null이 아닌지 확인
        assertThat(name1).isNotNull();
        assertThat(name2).isNotNull();
        assertThat(name3).isNotNull();
        //name1과 name2가 같은지 확인
        assertThat(name1).isEqualTo(name2);
        //name1과 name3이 다른지 확인
        assertThat(name1).isNotEqualTo(name3);
    }


    @Test
    public void test2() {
        int number1 = 15;
        int number2 = 0;
        int number3 = -5;

        //number1은 양수인지
        assertThat(number1).isPositive();
        //number2는 0인지
        assertThat(number2).isZero();
        //number3은 음수인지
        assertThat(number3).isNegative();
        //number1은 number2보다 큰지
        assertThat(number1).isGreaterThan(number2);
        //number3은 number2보다 작은지
        assertThat(number3).isLessThan(number2);
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Hello!");
    }


    @AfterAll
    public static void afterAll() {
        System.out.println("Bye!");
    }

    @Test
    public void junitQuiz3() {
        System.out.println("This is first test");
    }

    @Test
    public void junitQuiz4() {
        System.out.println("This is second test");
    }

}
