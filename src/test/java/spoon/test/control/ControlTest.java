package spoon.test.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static spoon.test.TestUtils.build;

import java.util.List;

import org.junit.Test;

import spoon.reflect.code.CtFor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtSimpleType;
import spoon.reflect.visitor.filter.NameFilter;
import spoon.reflect.visitor.filter.TypeFilter;

public class ControlTest {

	@Test 
	public void testModelBuildingFor() throws Exception {
		CtSimpleType type = build ("spoon.test.control",  "Fors");
		assertEquals("Fors", type.getSimpleName());
		
		List<CtFor> fors = type.getElements(new TypeFilter(CtFor.class));
		
		assertEquals(4, fors.size());
		
		CtMethod normalFor = (CtMethod) type.getElements(new NameFilter("normalFor")).get(0);
		CtFor firstFor = (CtFor) normalFor.getBody().getStatements().get(0);
		assertEquals("int i = 0", firstFor.getForInit().get(0).toString());
		assertEquals("i < 2", firstFor.getExpression().toString());
		assertEquals("i++", firstFor.getForUpdate().get(0).toString());

		CtMethod empty1 = (CtMethod) type.getElements(new NameFilter("empty1")).get(0);
		CtFor empty1For = (CtFor) empty1.getBody().getStatements().get(1);
		assertEquals("i = 0", empty1For.getForInit().get(0).toString());		
		// TODO: is it good to return null??
		// I'm not sure I want to specify this
		// I would prefer to add a fake null object that is printed as empty in the output
		assertNull(empty1For.getExpression());
		assertEquals("i++", empty1For.getForUpdate().get(0).toString());

	}

}