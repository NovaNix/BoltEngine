package IO.File;

// Note: H should extend the the stored class
public interface Storable <H>
{
	
	public String Save();
	
	public H Load(String Data);
	
}
