package tarefas;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class CdiUtil {
	
	private static WeldContainer container;
	static{
		Weld weld = new Weld();
		container = weld.initialize();
	}

	public static <T> T get(Class<T> c){
		return container.select(c).get();
	}

}
