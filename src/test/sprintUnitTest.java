/*
 * Unit Testing for Sprint 1:
 * Testing includes: 
 *      - create user account
 *      - login
 *      - logout
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class sprintUnitTest {
    private User testSubject, testSubject3, testSubject5;
    private loginController testSubject2;
    private SysAdminCreateAccountController testSubject4;
    private Profile testSubject6, testSubject7;
    private SysAdminCreateProfileController testSubject8;

    @Before
    public void setUp() throws Exception {
        this.testSubject = new User();
        this.testSubject2 = new loginController();
        this.testSubject3 = new User(0, "CafeStaff4", 10000, "12/12/2023", 5, "Cashier", "staff4", "staff4");
        this.testSubject4 = new SysAdminCreateAccountController();
        this.testSubject5 = new User(0, "CafeStaff5", 11000, "11/12/2023", 5, "Cashier", "staff5", "staff5");
        this.testSubject6 = new Profile(0,"newProfile");
        this.testSubject7 = new Profile();
        this.testSubject8 = new SysAdminCreateProfileController();
    }

    @After
        public void tearDown() throws Exception {
        testSubject = null;
        testSubject2 = null;
        testSubject3 = null;
        testSubject4 = null;
        testSubject5 = null;
        testSubject6 = null;
        testSubject7 = null;
        testSubject8 = null;
    }
    
    @Test
    public void testEntityLogin() {
        // Test Login - Expected Success
        testSubject = testSubject.loginUser("sysadmin1", "sysadmin1");
        assertEquals("Login SA E", 10002, testSubject.getEmpID());
        testSubject = testSubject.loginUser("owner1", "owner1");
        assertEquals("Login CO E", 10001, testSubject.getEmpID());
        testSubject = testSubject.loginUser("manager1", "manager1");
        assertEquals("Login CM E", 10003, testSubject.getEmpID());
        testSubject = testSubject.loginUser("staff1", "staff1");
        assertEquals("Login CS E", 10004, testSubject.getEmpID());
        // Test Login - Expected Failure
        testSubject = testSubject.loginUser("notaUser", "notaUser");
        assertEquals("Login Fail E", 0, testSubject.getEmpID());
        testSubject = testSubject.loginUser("notaUser2", "notaUser2");
        assertEquals("Login Fail E2", 0, testSubject.getEmpID());
    }

    @Test
    public void testControllerLogin() {
        // Test Login - Expected Success
        testSubject = testSubject2.loginUser("sysadmin1","sysadmin1");
        assertEquals("Login SA C", 10001, testSubject.getEmpID());
        testSubject = testSubject.loginUser("owner1", "owner1");
        assertEquals("Login CO C", 10002, testSubject.getEmpID());
        testSubject = testSubject.loginUser("manager1", "manager1");
        assertEquals("Login CM C", 10003, testSubject.getEmpID());
        testSubject = testSubject2.loginUser("staff1", "staff1");
        assertEquals("Login CS C", 10004, testSubject.getEmpID());
        // Test Login - Expected Failure
        testSubject = testSubject2.loginUser("notaUser", "notaUser");
        assertEquals("Login Fail C", 0, testSubject.getEmpID());
        testSubject = testSubject2.loginUser("notaUser2", "notaUser2");
        assertEquals("Login Fail C2", 0, testSubject.getEmpID());
    }

    @Test
    public void testEntityCreateUser() {
        // Test Create User - Expected Success
        boolean outcome1 = testSubject3.createUserRecord(testSubject3);
        assertEquals("createAccount CS E", true, outcome1);
        // Test Create User - Expected Fail - same details found
        boolean outcome2 = testSubject3.createUserRecord(testSubject3);
        assertEquals("createAccount CS E", false, outcome2);
    }

    @Test
    public void testControllerCreateUser() {
        // Test Create User - Expected Success
        boolean outcome1 = testSubject4.createUserRecord(testSubject5);
        assertEquals("createAccount CS C", true, outcome1);
        // Test Create User - Expected Fail - same details found
        boolean outcome2 = testSubject4.createUserRecord(testSubject5);
        assertEquals("createAccount CS C", false, outcome2);
    }

    @Test
    public void testEntityCreateProfile(){
        // Test Create Profile - Expected Success
        boolean outcome1 = testSubject6.createProfileRecord(testSubject6.getName());
        assertEquals("Create Profile E",true, outcome1);
        // Test Create Profile - Expected Fail - no Profile name
        boolean outcome2 = testSubject7.createProfileRecord(testSubject7.getName());
        assertEquals("Create Profile E",false, outcome2);
    }

    @Test
    public void testControllerCreateProfile(){
        // Test Create Profile - Expected Success
        boolean outcome1 = testSubject8.createProfileRecord("newProfile2");
        assertEquals("Create Profile C",true, outcome1);
    }
}
