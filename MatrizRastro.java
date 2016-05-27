import java.util.*;
public class MatrizRastro{
	public Vertice[] vertices;
	//representa la matriz de rastro de los vertices;
	public double[][] rastro;
	
	
	public MatrizRastro(Vertice[] vertices){
		this.vertices=vertices;
		this.rastro=new double[vertices.length][vertices.length];
	}
	
	//llena la matriz de rastro tij la inicializa 
	//como en el articulo
	public void llenaRastro(){
		for(int i=0;i<vertices.length;i++){
			for(int j=0;j<vertices.length;j++){
				//condicional
				//1 si i y j  no son adyacentes
				if(!vertices[i].esAdyacente(vertices[j])){
					//pongo a 1
					rastro[i][j]=1;
				}
			}
		}
	}
	
	public String toString(){
		String temp="\n";
		for(int i=0;i<rastro.length;i++){
			for(int j=0;j<rastro.length;j++){
				temp+=rastro[i][j];
				temp+="\t";
				if(j==rastro.length-1){
					temp+="\n";
				}
			}
		}
		return temp;
	}
}