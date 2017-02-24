package pl.spring.demo.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

import pl.spring.demo.common.Sequence;
import pl.spring.demo.dao.impl.BookDaoImpl;
import pl.spring.demo.to.BookTo;

public class BookNextIDBeforeSaving implements MethodBeforeAdvice {
	private BookDaoImpl bookdaoimpl;
	private Sequence sequence;

	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		if (arg2 instanceof BookDaoImpl) {
			if (arg0.equals("save")) {
				BookTo book = (BookTo) arg1[0];
				bookdaoimpl = (BookDaoImpl) arg2;
				if (book.getId() == null) {
					book.setId(sequence.nextValue(bookdaoimpl.findAll()));
				}
			}

		}

	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

}
