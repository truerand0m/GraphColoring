import java.util.*;
//Esta clase no la ocupo
//en su lugar ocupo la lista de adyacencias de los vertices
public class MatrizAdyacencias{
	public Vertice[] vertices;
	//representa la adyacencia de los vertices;
	public int[][] adyacencias;
	
	public MatrizAdyacencias(Vertice[] vertices){
		this.vertices=vertices;
		this.adyacencias=new int[vertices.length][vertices.length];
	}
	
	//llena la matriz de adyacencias
	public void llenaAdyacencias(){
		for(int i=0;i<vertices.length;i++){
			for(int j=0;j<vertices.length;j++){
				if(vertices[i].esAdyacente(vertices[j])){
					adyacencias[i][j]=1;
				}
			}
		}
	}
	
	public String toString(){
		String temp="";
		for(int i=0;i<adyacencias.length;i++){
			for(int j=0;j<adyacencias.length;j++){
				temp+=adyacencias[i][j];
				temp+="\t";
				if(j==adyacencias.length-1){
					temp+="\n";
				}
			}
		}
		return temp;
	}
}