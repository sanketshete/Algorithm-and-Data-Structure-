import java.io.BufferedReader;	
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Graph {

	private Map<String, Vertex> vertices = new HashMap<String,Vertex>();
	
	public static void main(String[] args) {

		Graph graph = new Graph();
		String netWorkFile = args[0];		
		String line="";
		
		try {
			FileReader fileReader = new FileReader(netWorkFile);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line=bufferedReader.readLine()) != null){
				String source=null;
				String destination=null;
				double distance;
				StringTokenizer stringTokenizer =  new StringTokenizer(line);
				
				if(stringTokenizer.countTokens()!=3){
					continue;
				}
				
				source= stringTokenizer.nextToken();
				destination = stringTokenizer.nextToken();
				distance = Double.parseDouble(stringTokenizer.nextToken());
				graph.addEdge(source, destination, distance);
				graph.addEdge(destination, source, distance);
			}
			
			bufferedReader.close();
			boolean exit=false;
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			while(!exit){
				
				line=bufferedReader.readLine();
				if(line==null){
					exit=true;
					continue;
				}
				
				String source=null;
				String destination=null;
				double distance=0.0;
				StringTokenizer stringTokenizer =  new StringTokenizer(line);
					int numberOfTokens=0;
					int count=0;
					String operation= stringTokenizer.nextToken();
					
					switch(operation){
					
						case "print":
							graph.Printallvertices();
							break;
							
						case "reachable":
							graph.PrintReachableVertex();
							break;
							
						case "vertexdown":
							 numberOfTokens=stringTokenizer.countTokens();
							 count=0;
							
							while(count!=numberOfTokens){
								source=stringTokenizer.nextToken();
								graph.vertexDown(source);							
								count++;
							}
							break;	
							
						case "vertexup":
							 numberOfTokens=stringTokenizer.countTokens();
							 count=0;
							while(count!=numberOfTokens){
								source=stringTokenizer.nextToken();
								graph.vertexUp(source);							
								count++;
							}
							break;
							
						case "path":
							source=stringTokenizer.nextToken();
							destination=stringTokenizer.nextToken();
							graph.findShortestPath(source, destination);
							break;
							
						case "edgeup":
							source=stringTokenizer.nextToken();
							destination=stringTokenizer.nextToken();						
							graph.edgeUp(source, destination);;
							break;
							
						case "edgedown":
							source=stringTokenizer.nextToken();
							destination=stringTokenizer.nextToken();						
							graph.edgeDown(source, destination);;
							break;
	
						case "deleteedge":
							source=stringTokenizer.nextToken();
							destination=stringTokenizer.nextToken();						
							graph.deleteEdge(source, destination);
							break;
	
						case "addedge":
							source=stringTokenizer.nextToken();
							destination=stringTokenizer.nextToken();						
							distance = Double.parseDouble(stringTokenizer.nextToken());
							graph.addEdge(source, destination, distance);
							break;
						case "quit":
							exit=true;
							break;
						default:
							System.out.println("Not valid Query");
							break;
					}
			}			
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class Path{
		String vertexName;
		double distance;
		
		public Path(String vertexName, double distance) {
			super();
			this.vertexName = vertexName;
			this.distance = distance;
		}
		
	}

	class MinHeap{
		
		Path[] minHeap;
		int maximumSize;
		int size;
		
		public MinHeap(int maximumSize) {
			super();
			this.maximumSize = maximumSize;
			this.minHeap = new Path[this.maximumSize+1];
			this.size = 0;
		}

		public Path[] getMinHeap() {
			return minHeap;
		}

		public void setMinHeap(Path[] minHeap) {
			this.minHeap = minHeap;
		}

		public int getMaximumSize() {
			return maximumSize;
		}

		public void setMaximumSize(int maximumSize) {
			this.maximumSize = maximumSize;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}
		
		public int getParent(int pos){
			
			return(pos/2);
		}
		public void SwapElement(int pos,int pos1){
			
		}
		
		public void insert(String vertexName,double distance){ // perform insert opertaion in min heap
		
			Path newPath = new Path(vertexName, distance);
			this.size++;
			this.getMinHeap()[this.size]=newPath;
			int currentSize=this.size;
			
		
			while(currentSize!=1 && minHeap[currentSize].distance < minHeap[getParent(currentSize)].distance){
				
				Path tempPath = minHeap[currentSize];
				minHeap[currentSize]=minHeap[getParent(currentSize)];
				minHeap[getParent(currentSize)]=tempPath;
				currentSize=getParent(currentSize);
			}
			
		}
		
		public Path remove(){ // perform remove opertaion in min heap
			
			Path tempPath = minHeap[1];
			minHeap[1] = minHeap[this.size];
			this.size--;
			MinHeapify();
			return tempPath;
		}
		public boolean isNodeLeaf(int pos){
			
			if(pos-1>=this.size/2 && pos<=this.size){
				
				return true;
				
			}
			return false;
			
		}
		
		public void MinHeapify(){ // perform minheapify opertaion in min heap
			int pos=1;
			if(this.size>0){
				while(!isNodeLeaf(pos)){
						int minChild=-1;
						if((this.size>=pos*2) &&(minHeap[pos].distance>minHeap[pos*2].distance)){	
							minChild=pos*2;
							
							if((this.size>=(pos*2+1)) && (minHeap[pos].distance>minHeap[pos*2+1].distance) &&
									(minHeap[pos*2].distance>minHeap[pos*2+1].distance)){
								
								minChild=pos*2+1;
							}
						}
						if(minChild!=-1){
						Path tempPath=minHeap[pos];
						minHeap[pos]=minHeap[minChild];
						minHeap[minChild]=tempPath;
						}else{
							break;
						}
						pos=minChild;
				}
			}
			
		}
		
	}
	public Vertex getVertex(String source){
		
		Vertex vertex = vertices.get(source);
		if(vertex==null){
			vertex = new Vertex(source);
			vertices.put(source, vertex);
			return vertex;
		}else{
			return vertex;
		}
		
	}
	
	
	public void addEdge(String source, String destination,double distance){ // Add edge opertaion in graph
		
		Vertex sourcevertex= getVertex(source);
		Vertex destinationVertex= getVertex(destination);
		
		List<Edge> adjecentList = sourcevertex.getAdjecentList();
		
		Edge newEdge = new Edge(destinationVertex, distance, "up");
		boolean exist=false;
		for(int i=0;i<adjecentList.size();i++){
			
			Edge edge = adjecentList.get(i);
			if(edge.getDestination().getName().equals(destination)){
				adjecentList.get(i).setDistance(distance);
				exist=true;
			}
			
		}
		
		if(!exist){			
		adjecentList.add(newEdge);
		}
		vertices.get(source).setAdjecentList(adjecentList);
	}

	public void deleteEdge (String source,String destination){ // delete edge opertaion from graph if exist
		
		Vertex sourcevertex= vertices.get(source);
		Vertex destinationvertex= vertices.get(source);
		
		if(sourcevertex!=null && destinationvertex!=null ){
			List<Edge> adjecentList = sourcevertex.getAdjecentList();
			
			for(int i=0;i<adjecentList.size();i++){
				
				Edge edge = adjecentList.get(i);
				if(edge.getDestination().getName().equals(destination)){
					adjecentList.remove(i);
				}
				
			}
			vertices.get(source).setAdjecentList(adjecentList);
		}
	}
	
	public void PrintReachableVertex(){
		
		List<String> list= new ArrayList<String>();
		Queue<String> queue= new LinkedList<String>();		
		for(String s: vertices.keySet()){
			
			if(vertices.get(s).getStatus()=="down"){
				continue;
			}
			list.add(s);
		}
		
		Collections.sort(list, new sortByName());
	
		for(int i=0;i<list.size();i++){
			
			writeIntoFile(list.get(i));
			queue= new LinkedList<String>();
			
			vertices.get(list.get(i)).setVisited(1);
			
			queue.add(list.get(i));
			
			TreeSet<String> treeSet = new TreeSet<String>();
			List<Edge> newListOfVertetx = new ArrayList<Edge>();
			while(!queue.isEmpty()){
				
				String node=queue.poll();
				List<Edge> listOfVertetx = vertices.get(node).getAdjecentList();
				 
				
				if(!node.equals(list.get(i))){
				treeSet.add(node);
				}
				
				for(Edge e:listOfVertetx){
					if(e.getDestination().getVisited()!=1 && e.getDestination().getStatus()!="down"){
						vertices.get(e.getDestination().getName()).setVisited(1);
						queue.add(e.getDestination().getName());
						newListOfVertetx.add(e);
					}
				}
			}
			Collections.sort(newListOfVertetx, new sortByNameForadjecent());
			for(Edge s:newListOfVertetx){
				writeIntoFile("  "+s.getDestination().getName());
			}
			for(Vertex e:vertices.values()){
				
				e.setVisited(0);
			}
			
		}

		}
	public void edgeDown(String source,String destination){
		
		Vertex sourceVertex=vertices.get(source);
		Vertex destinationVertex=vertices.get(source);
		
		if(sourceVertex!=null && destinationVertex!=null){
			List<Edge> adjecentList = sourceVertex.getAdjecentList();
			
			for(int i=0;i<adjecentList.size();i++){
				
				Edge edge = adjecentList.get(i);
				if(edge.getDestination().getName().equals(destination)){
					adjecentList.get(i).setStatus("down");
				}
			}
			vertices.get(source).setAdjecentList(adjecentList);
		}
	}
	
	public void edgeUp(String source,String destination){
		
		Vertex sourceVertex=vertices.get(source);
		Vertex destinationVertex=vertices.get(source);
		
		if(sourceVertex!=null && destinationVertex!=null){
			List<Edge> adjecentList = sourceVertex.getAdjecentList();
			
			for(int i=0;i<adjecentList.size();i++){
				
				Edge edge = adjecentList.get(i);
				if(edge.getDestination().getName().equals(destination)){
					adjecentList.get(i).setStatus("up");
				}
				
			}
			vertices.get(source).setAdjecentList(adjecentList);
		}
	}
	
	public void vertexDown(String name){
		
		Vertex vertex = vertices.get(name);
		if(vertex!=null){
		vertex.setStatus("down");
		vertices.put(name,vertex);
		}
		
	}
	public void vertexUp(String name){
		
		Vertex vertex = vertices.get(name);
		if(vertex!=null){
		vertex.setStatus("up");
		vertices.put(name,vertex);
		}
	}
	
	public void findShortestPath(String source ,String destination){
		
		resetAll();
		
		Vertex vertex = vertices.get(source);
		if(vertex==null){
			System.out.println("vertex not exist");
			return;
		}else if(vertex.status=="down"){
			System.out.println("Vertex is Down");
			return;
		}
		
		vertex.distance=0;
		
		MinHeap priorityQueue = new MinHeap(vertices.size());
		
		priorityQueue.insert(source, vertex.distance);
		
		while(priorityQueue.size!=0){
			
			Path path= priorityQueue.remove();
			Vertex vertex1 = vertices.get(path.vertexName);
			
			List<Edge> adjecentList = vertex1.getAdjecentList();
			
			for(Edge edge:vertex1.getAdjecentList()){
				
				Vertex vertex2 = edge.destination;
				
					if(vertex2.status=="down")
						continue;
					
					if(edge.status=="down")
						continue;
					
					if(vertex2.getDistance()>(vertex1.getDistance()+edge.getDistance())){
						vertex2.setDistance(vertex1.getDistance()+edge.getDistance());
						vertex2.prev=vertex1;
						priorityQueue.insert(vertex2.getName(), vertex2.getDistance());
					}					
				}
		}
				
		Vertex v = vertices.get(destination);
		List<String> list = new ArrayList<String>();
		while(v!=null){
			list.add(v.getName());
			v=v.getPrev();
		}
		
		//System.out.println();
		String s="";
		for(int i=list.size()-1;i>=0;i--){
			s=s+list.get(i)+" ";
		}
		writeIntoFile(s+" "+vertices.get(destination).getDistance());
	}
	public void Printallvertices(){
		List<String> list= new ArrayList<String>();
		Queue<String> queue= new LinkedList<String>();		
		for(String s: vertices.keySet()){
			
			list.add(s);
		}
		
		Collections.sort(list, new sortByName());
	
		for(int i=0;i<list.size();i++){
			
			//System.out.println(list.get(i));
			if(vertices.get(list.get(i)).getStatus()=="down"){
				writeIntoFile(list.get(i) + " "+"DOWN");	
			}else{
			writeIntoFile(list.get(i));
			}
			List<Edge> treeSet = new ArrayList<Edge>();
			String node=list.get(i);
			List<Edge> listOfVertetx = vertices.get(node).getAdjecentList();
			Collections.sort(listOfVertetx, new sortByNameForadjecent());	
			for(Edge s:listOfVertetx){
				if(s.status =="down"){
					writeIntoFile("  "+s.getDestination().getName()+" "+s.getDistance()+" "+"DOWN");
					
				}else{
					writeIntoFile("  "+s.getDestination().getName()+" "+s.getDistance());
					
				}
			}
			
			for(Vertex e:vertices.values()){
				e.setVisited(0);
			}
		}	
	}
	
	private void writeIntoFile(String s){
		
			System.out.println(s);
		
	}
	public void resetAll(){

		for(Vertex vertex: vertices.values()){
			vertex.reset();
		}
	}
	
	class sortByNameForadjecent implements Comparator<Edge>{

		@Override
		public int compare(Edge o1, Edge o2) {
			// TODO Auto-generated method stub
			return o1.getDestination().getName().compareTo(o2.getDestination().getName());
		}
		
	}
	class sortByNameForVertex implements Comparator<Vertex>{

		@Override
		public int compare(Vertex o1, Vertex o2) {
			// TODO Auto-generated method stub
			return o1.getName().compareTo(o2.getName());
		}
		
	}
	class sortByName implements Comparator<String>{

		@Override
		public int compare(String o1, String o2) {
			// TODO Auto-generated method stub
			return o1.compareTo(o2);
		}
		
	}

}
