package pl.spring.demo.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.spring.demo.dao.BookDao;
import pl.spring.demo.to.BookTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonServiceTest-context.xml")
public class BookDaoImplSaveTest {

	@Autowired
	private BookDao bookdao;
	@Test
	public void shouldSaveBook() {
		// given
		BookTo book=new BookTo(null,"Blue Castle","Lucy M. Montogomery");
		// when
		BookTo savedBook=bookdao.save(book);
		// then
		List<BookTo> list=bookdao.findAll();
		Assert.assertTrue(list.contains(book));
		//fail("Not yet implemented");
	}

}
