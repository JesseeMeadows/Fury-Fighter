import static org.junit.Assert.*;
import org.junit.*;

public class TestVector2 {
    Vector2 vector;
    Vector2 vectorDos;
    
    @Before
    public void setUp(){
        vector = new Vector2(2.0, 2.0);
        vectorDos = new Vector2(3.0, 3.0);
    }
    
    @After
    public void setDown(){
        System.out.println("Test complete.");
    }
    
    @Test
    public void testNormalizeCopy(){
        double testLength = vector.length();
        Vector2 testNormalizeCopyVector = vector.normalizeCopy();
        assertTrue(testNormalizeCopyVector.getX() == (2/testLength));
        assertTrue(testNormalizeCopyVector.getY() == (2/testLength));
    }
    
    @Test
    public void testNormalize(){
        double testLength = vector.length();
        vector.normalize();
        assertTrue(vector.getX() == 2.0 / testLength);
        assertTrue(vector.getY() == 2.0 / testLength);
    }
    
    @Test
    public void testDot(){
        double testDouble = vector.dot(vectorDos);
        assertTrue(12.0 == testDouble);
    }
    
    @Test
    public void testLength(){
        assertTrue(vector.length() == Math.sqrt((vector.getX()*vector.getX()) + (vector.getY()*vector.getY())));
    }
    
    @Test
    public void testMul(){
        Vector2 testMulVector = vector.mul(3.0);
        assertTrue(testMulVector.getX() == 6.0);
        assertTrue(testMulVector.getY() == 6.0);
    }
    
    @Test
    public void testAdd(){
        Vector2 testAddVector = vector.add(vectorDos);
        assertTrue(testAddVector.getX() == 5);
        assertTrue(testAddVector.getY() == 5);
    }
    
    @Test
    public void testSub(){
        Vector2 testSubVector = vectorDos.sub(vector);
        assertTrue(testSubVector.getX() == 1);
        assertTrue(testSubVector.getY() == 1);
    }
    
    @Test
    public void testSetAndGetX(){
        vector.setX(20.5);
        assertTrue(20.5 == vector.getX());
    }
    
    @Test
    public void testSetAndGetY(){
        vector.setY(10.5);
        assertTrue(10.5 == vector.getY());
    }
}
