package stefanholzmueller.pp2.checks;

import java.util.ArrayList;
import java.util.List;

public class Triple<T> {

	public T first;
	public T second;
	public T third;

	public Triple(T first, T second, T third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	public List<T> getList() {
		ArrayList<T> list = new ArrayList<T>();
		list.add(first);
		list.add(second);
		list.add(third);
		return list;
	}
}
