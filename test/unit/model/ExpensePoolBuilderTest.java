package unit.model;

import models.ExpensePool;
import models.ExpensePoolBuilder;

import org.junit.Test;

import play.test.UnitTest;

public class ExpensePoolBuilderTest extends UnitTest {

	@Test
	public void testBuilderWithPoolName() {

		ExpensePoolBuilder builder = new ExpensePoolBuilder().setName("default pool");
		
		ExpensePool pool = builder.build();
		
		assertNotNull(pool);
		assertEquals("default pool", pool.name);
	}

	
	
	
}
