import org.junit.jupiter.api.*;
public class JUnitCycleTest {
    @BeforeAll // 전체 테스트를 시작하기 전에 1회 실행하므로 메서드는 statc으로 선언
    // 예를 들어 디비를 연결해야하거나 테스트 환경을 초기화할때 사용
    static void beforeAll() {
        System.out.println("@BeforeAll");
    }
    @BeforeEach //테스트 케이스를 시작하기 전마다 실행
    public void beforeEach() {
        System.out.println("@BeforeEach");
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }
    @Test
    public void test2() {
        System.out.println("test2");
    }
    @Test
    public void test3() {
        System.out.println("test3");
    }

    @AfterAll // 전체 테스트를 마치고 종료하기 전에 1회 실행하므로 메서드는 static으로 선언
    // 예를 들어 디비 연결을 종료할 떄나 공통적으로 사용하는 자원을 해제할 때 사용
    static void afterAll() {
        System.out.println("@AfterAll");
    }
    @AfterEach // 테스트 케이스를 종료하기 전마다 실행
    public void afterEach() {
        System.out.println("@AfterEach");
    }
}


//테스트 결과
//@BeforeAll
//@BeforeEach
//test1
//        @AfterEach
//        @BeforeEach
//        test2
//@AfterEach
//@BeforeEach
//test3
//@AfterEach
//@AfterAll