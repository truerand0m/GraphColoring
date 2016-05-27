import java.util.*;
public class MatrizActualizacion{
	public Vertice[] vertices;
	//representa la matriz de actualizacion de los vertices
	//Es practimante igual a la de rastro solo que cambian los valores
	//al iniciarlas
	public double[][] actualizacion;
	
	
	public MatrizActualizacion(Vertice[] vertices){
		this.vertices=vertices;
		this.actualizacion=new double[vertices.length][vertices.length];
	}
	
	//inicializa la matriz de Actualizacion
	//definicion inicial como en el articulo
	public void llenaActualizacion(){
		for(int i=0;i<vertices.length;i++){
			for(int j=0;j<vertices.length;j++){
				//condicional
				//0 si i y j no son adyacentes
				if(!vertices[i].esAdyacente(vertices[j])){
					actualizacion[i][j]=0;
				}
			}
		}
	}
	
	//le paso k
	public void actualizaMatrizActualizacion(ArrayList<Vertice> vertices,double k){
		for(int i=0;i<vertices.size();i++){
			Vertice tempi=vertices.get(i);
			for(int j=i;j<vertices.size();j++){
				//Si no son iguales actualizo
				//no tengo que verificar la adyacencia entre estos vertices
				//porque vertices adyacentes no pueden estar en la misma clase de color
				//por la forma en la que se construyen estas clases de color.
				Vertice tempj=vertices.get(j);
				if(tempi!=tempj){
					//System.out.println("entre al if de actualizaMatrizAct en MA");
					//System.out.println("k es "+k);
					//double f=1/k;
					//renglon, columna
					int r=tempi.id;
					int c=tempj.id;
					actualizacion[r][c]+=1/k;
				}
			}
		}
	}
	
	public String toString(){
		String temp="\n";
		for(int i=0;i<actualizacion.length;i++){
			for(int j=0;j<actualizacion.length;j++){
				temp+=actualizacion[i][j];
				temp+="\t";
				if(j==actualizacion.length-1){
					temp+="\n";
				}
			}
		}
		return temp;
	}
}