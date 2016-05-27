import java.util.*;
//Clase que modela a un vertice de la grafica
//con su lista de adyacencias
public class Vertice{
	//su identificador, debe ser unico
	public int id;
	//su color, al inicio es -1
	public int color;
	//la lista de vertices adyacentes
	public ArrayList<Vertice> adyacentes;
	
	public Vertice(int id){
		this.id=id;
		this.color=-1;
		this.adyacentes=new ArrayList<Vertice>();
	}
	
	public Vertice(int id,int color){
		this.id=id;
		this.color=color;
		this.adyacentes=new ArrayList<Vertice>();
	}
	
	//Agrega el vertice parametro como adyacente al vertice this
	public void agregaVertice(Vertice v){
		this.adyacentes.add(v);
	}
	
	//Agrega los vertice parametro como adyacentes al vertice this
	public void agregaVerticesAdyacentes(Vertice[] vertices){
		for(int i=0;i<vertices.length;i++){
			this.adyacentes.add(vertices[i]);
		}
	}
	
	public void setColor(int color){
		this.color=color;
	}
	
	public int getColor(){
		return this.color;
	}
	
	public int getId(){
		return this.id;
	}
	
	//regresa true si el vertice v es adyacente a this
	//false e.o.c
	public boolean esAdyacente(Vertice v){
		return this.adyacentes.contains(v);
	}
	
	public String toString(){
		return "v"+id;
	}
}