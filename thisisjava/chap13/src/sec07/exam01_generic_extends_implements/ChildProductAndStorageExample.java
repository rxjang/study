package sec07.exam01_generic_extends_implements;

public class ChildProductAndStorageExample {

	public static void main(String[] args) {
		ChildProduct<Tv,String,String> product=new ChildProduct<>();
		product.setKind(new Tv());
		product.setModel("Smart Tv");
		product.setCompany("Samsung");
		
		Storage<Tv> storage=new StorageImple<Tv>(100);
		storage.add(new Tv(), 0);
		Tv tv=storage.get(0);
		
	}

}
