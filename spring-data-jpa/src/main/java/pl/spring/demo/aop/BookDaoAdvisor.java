package pl.spring.demo.aop;


import org.springframework.aop.MethodBeforeAdvice;
import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.common.Sequence;
import pl.spring.demo.dao.impl.BookDaoImpl;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.to.IdAware;


import java.lang.reflect.Method;

public class BookDaoAdvisor implements MethodBeforeAdvice {

	private BookDaoImpl bookdaoimpl;
	private Sequence sequence;
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {

        if (hasAnnotation(method, o, NullableId.class)) {
            checkNotNullId(objects[0]);
        }
    	if(o instanceof BookDaoImpl){
    		if(method.equals("save")){
    			BookTo book=(BookTo) objects[0];
    			bookdaoimpl=(BookDaoImpl)o;
    	        if (book.getId() == null) {
    	            book.setId(sequence.nextValue(bookdaoimpl.findAll()));
    	        }
    		}
    			
    	}
    }
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    private void checkNotNullId(Object o) {
        if (o instanceof IdAware && ((IdAware) o).getId() != null) {
            throw new BookNotNullIdException();
        }
    }

    private boolean hasAnnotation (Method method, Object o, Class annotationClazz) throws NoSuchMethodException {
        boolean hasAnnotation = method.getAnnotation(annotationClazz) != null;

        if (!hasAnnotation && o != null) {
            hasAnnotation = o.getClass().getMethod(method.getName(), method.getParameterTypes()).getAnnotation(annotationClazz) != null;
        }
        return hasAnnotation;
    }
}
