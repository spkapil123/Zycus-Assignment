package diskBackedMapImplementation;


/*This class is to Test the Disk backed map class i have created.*/
public class Test {

	public static void main(String[] args) {
		
		try {
			//to put vaues to map.
			DiskBackedMap diskBackedMap=new DiskBackedMap();
			for (int i = 0; i <=9000; i++) {
				diskBackedMap.put(i+"", "kapil"+i);
			}
			
			//Uncomment beow code statement for clearing map , execute below statement to clear the diskbacked map.
			//diskBackedMap.clear();
				
			//isEmpty to check if map is empty
			System.out.println("Is Map empty :"+diskBackedMap.isEmpty());
			
			//for getting values in map
			Object getValue=diskBackedMap.get("5643");
			if(getValue!=null){
				System.out.println("Value of Key :"+getValue);
			}else{
				System.out.println("There key is not present in map or its value is null");
			}
			
			//for Containskey
			System.out.println("ContainsKey :"+diskBackedMap.containsKey("900"));
			
			//for ContainsValue
			System.out.println("Contains value :"+diskBackedMap.containsValue("kapil857"));
			
			//cleaning up unwanted resources.
			diskBackedMap=null;
			System.gc();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


		}
