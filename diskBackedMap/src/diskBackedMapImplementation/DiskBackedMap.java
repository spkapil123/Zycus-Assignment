package diskBackedMapImplementation;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*This is Disk Backed Map class.which implements put,get,containsValue,containsKey,clear,isEmpty
 * PUT ,GET,clear methods are made thread safe.
 * */
public class DiskBackedMap{
	
	private String fileName="data"+System.currentTimeMillis()+".ser";
	private Map<Object,Object> map=new ConcurrentHashMap<Object,Object>();
	private Map<Object,Object> getMap=new ConcurrentHashMap<Object,Object>();
	FileOutputStream fileOut=null;
	ObjectOutputStream out=null;
	
	public DiskBackedMap() throws IOException {
		fileOut=new FileOutputStream(fileName);
		out=new ObjectOutputStream(fileOut);
	}


	/*
	 * This method is used to store key and value pairs to internal ConcurrentHashMap.
	 * if the entries in internal map exceed 1000 then further entries will be serialised 
	 * to disk as map objects having 1000 entries each via a file with .ser extension .
	 * */
	public synchronized void put(Object key,Object value)throws Exception{
		try {
			if(this.map.size()<1000){
				this.map.put(key, value);
			}else{
				out.writeObject(map);
				out.reset();
				this.map.clear();
				this.map.put(key, value);
			}
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
	/*
	 * This method is used to get value object of the given key object.First it searches
	 *  in the internal map, if key object is present it returns the corresponding value object.
	 *  if key is not present in internal map,then it searches in the catched map and if key is not
	 *   present in catched map,then it retrives map objects stored on disk, one at a time and loads the map to a
	 *  catched map and searches for key, if key is present it returns the corresponding value object.The catched reduces
	 *  number of hits to the disk to retrive value of a key
	 *	 * */
	@SuppressWarnings("unchecked")
	public synchronized Object get(Object key) throws Exception{
		Object value=map.get(key);
		Object value1=getMap.get(key);
		Object value2=null;
		if(value!=null){
			return value;
		}else if(value1!=null){
			return value1;
		}else{
			FileInputStream fileIn;
			ObjectInputStream in = null;
			try {
				fileIn = new FileInputStream(this.fileName);
				Object object = null;
				try {
					in = new ObjectInputStream(fileIn);
					object = in.readObject();
				} catch (EOFException e) {
			        fileIn.close();
			        return null;
				}
				while(object!=null){
					getMap = (ConcurrentHashMap<Object, Object>) object;
					value2=getMap.get(key);
					if(value2!=null){
						in.close();
				        fileIn.close();
						break;
					}
					try {
						object = in.readObject();
					} catch (EOFException e) {
						in.close();
				        fileIn.close();
				        return null;
					}
					System.gc();
					
				}
				return value2;
			} catch (IOException | ClassNotFoundException e) {
				throw e;
			}
		}
	}
	
	/*This method is used to know if a key object is present in map.*/
	public boolean containsKey(Object key) throws Exception{
		if(this.map.containsKey(key)){
			return true;
		}else{
			Object value=get(key);
			if(value==null){
				return false;
			}else{
				return true;
			}
		}
	}
	
	/*This method is used to know if a value object is present in map.*/
	@SuppressWarnings("unchecked")
	public boolean containsValue(Object value) throws Exception{
		if(this.map.containsValue(value)){
			return true;
		}else if(this.getMap.containsValue(value)){
			return true;			
		}else{
			boolean value1=false;
			FileInputStream fileIn;
			try {
				
				fileIn = new FileInputStream(this.fileName);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				
				Object object = in.readObject();
				while(object!=null){
					getMap = (ConcurrentHashMap<Object, Object>) object;
					value1=getMap.containsValue(value);
					if(value1){
						in.close();
				        fileIn.close();
					break;
					}
					try {
						object = in.readObject();
					} catch (EOFException e) {
						in.close();
				        fileIn.close();
				        break;
					}
					System.gc();
				}
				
			} catch (IOException | ClassNotFoundException e) {
				throw e;
			}
			return value1;
		}
	}
	
	/*This method is used to empty the map.It clears the internal map,catched map and on disk data.*/
	public synchronized void clear() throws Exception{
		this.map.clear();
		this.getMap.clear();
		new FileOutputStream(this.fileName).close();
	}
	
	/*This method is used to know if the map is empty*/
	public boolean isEmpty() throws Exception{
		if(this.map.isEmpty()){
			return true;
		}
		return false;
		
	}
	
	/*This method is the overridden method of garbage collector and this is used to delete map data
	 * on disk if this map object is no longer referenced and is eigible for garbage collection
	 * . Therefore it saves disk space and frees disk of unwanted data.*/
	 
	protected void finalize() throws IOException{
		 out.close();
		 fileOut.close();
		 File file=new File(this.fileName);
		 file.delete();
	    }
	
	

}
