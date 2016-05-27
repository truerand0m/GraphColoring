import java.util.*;
public class AntCol{
	//Parametros
	public static int numciclos=40;
	public static int numants=20;
	//controla el rango de evaporacion
	public static double p=0.8;
	//matriz de rastro tij
	public static MatrizRastro mrastro;
	//matriz actualizacion dij
	public static MatrizActualizacion mactualizacion;
	public static void main(String [] args){
		/* DEFINO LA GRAFICA */
		//Defino los Vertices
		Vertice v0=new Vertice(0);
		Vertice v1=new Vertice(1);
		Vertice v2=new Vertice(2);
		Vertice v3=new Vertice(3);
		Vertice v4=new Vertice(4);
		Vertice v5=new Vertice(5);
		Vertice v6=new Vertice(6);
		//Defino Adyacencias
		v0.agregaVerticesAdyacentes(new Vertice[]{v1,v3});
		v1.agregaVerticesAdyacentes(new Vertice[]{v0,v2,v3,v4});
		v2.agregaVerticesAdyacentes(new Vertice[]{v1,v5});
		v3.agregaVerticesAdyacentes(new Vertice[]{v0,v1,v4});
		v4.agregaVerticesAdyacentes(new Vertice[]{v3,v5});
		v5.agregaVerticesAdyacentes(new Vertice[]{v1,v2,v4,v5});
		v6.agregaVerticesAdyacentes(new Vertice[]{v4,v5});
		//arreglo de vertices para inicilizar matrices
		Vertice[] arreglovertices={v0,v1,v2,v3,v4,v5,v6};
		//Lista General de Vertices: V en el articulo
		ArrayList<Vertice> V=new ArrayList<Vertice>();
		//agrego los vertices a la lista V
		agregaVerticesLista(V,arreglovertices);
		/* FIN DEFINICION DE LA GRAFICA */
		
		//INICIALIZO LA MATRIZ DE RASTRO tij
		MatrizRastro mrastro=new MatrizRastro(arreglovertices);
		mrastro.llenaRastro();
		//Lista para guardar soluciones anteriores (es necesaria para actualizar tij)
		ArrayList <ArrayList<ArrayList<Vertice>>> soluciones=new ArrayList<ArrayList<ArrayList<Vertice>>>();
		for(int ciclos=1;ciclos<=numciclos;ciclos++){
			//INICIO LA MATRIZ DE ACTUALIZACION dij
			mactualizacion=new MatrizActualizacion(arreglovertices);
			mactualizacion.llenaActualizacion();
			//Lista para guardar las clases de color en esta solucion
			ArrayList<ArrayList<Vertice>> clasesdecolor=new ArrayList<ArrayList<Vertice>>();
			for(int ii=0;ii<numants;ii++){
				//Defino el conjunto de vertices, X del articulo
				ArrayList<Vertice> X=new ArrayList<Vertice>(V);
				//color
				int k=0;
				while(X.size()!=0){
					k=k+1;
					//Clase de color actual, vacia al inicio
					ArrayList<Vertice> Ck=new ArrayList<Vertice>();
					//Conjunto F de vertices del articulo
					ArrayList<Vertice> F=new ArrayList<Vertice>(X);
					//Selecciono un vertice
					Vertice i=seleccionaVertice(F);
					//Lo coloreo
					colorea(i,k,X,Ck,F);
					while(F.size()!=0){
						i=seleccionaVertice(F);
						colorea(i,k,X,Ck,F);
					}
					//AGREGO LA NUEVA CLASE DE COLOR A LA LISTA CON LAS CLASES DE COLOR
					clasesdecolor.add(Ck);
				}
				//La hormiga termino de colorear la grafica
				//System.out.println("La solucion fue "+clasesdecolor);
				//Agrego esta solucion a la lista de soluciones
				soluciones.add(clasesdecolor);
				//Actualizo la matriz delta dij del articulo
				actualizaMatrizDelta(clasesdecolor,k);
				//Reinicio la lista de colores
				clasesdecolor=new ArrayList<ArrayList<Vertice>>();
			}
			//Actualizo la trail update matrix tij del articulo
			actualizaTrailMatrix(soluciones,mrastro);
		}
		//DESCOMENTAR LO DE ABAJO PARA VER TODAS LAS SOLUCIONES
		//System.out.println("Las soluciones fueron "+soluciones);
		System.out.println("\n");
		System.out.println("Al final la matriz delta dij fue :"+mactualizacion);
		System.out.println("Al final la matriz tij fue :"+mrastro);
		//imprimo la solucion final
		System.out.println("Solucion final\n"+soluciones.get(soluciones.size()-1));
	}
	
	//Metodo Externo para actualizar la Matriz de Actualizaciones (Trail Update Matrix) dij
	public static void actualizaMatrizDelta(ArrayList<ArrayList<Vertice>> clasescolor,int k){
		double k1=k;
		for(int i=0;i<clasescolor.size();i++){
			mactualizacion.actualizaMatrizActualizacion(clasescolor.get(i),k1);
		}
	}
	
	//METODO PARA COLOREAR
	public static void colorea(Vertice i,int k,ArrayList<Vertice> X,ArrayList<Vertice> Ck,ArrayList<Vertice> F){
		X.remove(i);
		Ck.add(i);
		//quita a i y a sus vecinos de F
		quitaVertices(i,F);
	}
	
	//agrega los vertices de un arreglo a la lista parametro
	public static void agregaVerticesLista(ArrayList<Vertice> lista,Vertice[] arrayvertices){
		for(int i=0;i<arrayvertices.length;i++){
			lista.add(arrayvertices[i]);
		}
	}
	
	//selecciona un vertice de manera aleatoria uniformemente
	public static Vertice seleccionaVertice(ArrayList<Vertice> F){
		int max=F.size()*100;
		int index=(int)((Math.random()*max)%F.size());
		return F.get(index);
	}
	
	//metodo para quitar de F a i y a sus vecinos
	public static void quitaVertices(Vertice i,ArrayList<Vertice> F){
		F.remove(i);
		for(int ii=0;ii<i.adyacentes.size();ii++){
			Vertice j=i.adyacentes.get(ii);
			F.remove(j);
		}
	}
	
	//Los Metodos de abajo son para la actualizacion de tij
	
	//toma una lista sencilla de vertices y busca que este el
	//vertice parametro basado en su id
	public static boolean contiene(ArrayList<Vertice> vertices,int id){
		for(int i=0;i<vertices.size();i++){
			if(vertices.get(i).id==id){
				return true;
			}
		}
		return false;
	}
	
	//toma una lista de lista de vertices ie una coloracion
	// dos vertices (sus ids)
	// y llama a contiene
	//Busca a los 2 vertices en una solucion (coloracion) 
	//si los encuentra regresa un doble= 1/q(s)=1/coloracion.size()
	//esto se itera desde el metodo actualizaTrailMatriz para hacer
	//tij=ptij+Sigma(1/q(s)) para toda s:= es una solucion donde i y j tienen = color
	public static double sigma(ArrayList<ArrayList<Vertice>> coloracion,int v1,int v2){
		//si en esta solucion los vertices v1 y v2 tienen el mismo color
		//regresa 1/q(s) ie 1/coloracion.size()
		double cuenta=0;
		for(int i=0;i<coloracion.size();i++){
			if(contiene(coloracion.get(i),v1)&&contiene(coloracion.get(i),v2)&&v1!=v2){
				cuenta++;
			}
		}
		//1/q(s)
		return cuenta/coloracion.size();
	}
	
	//toma una lista de listas de listas de vertices ie soluciones
	//Busca soluciones para actualizar tij TRAIL MATRIX 
	public static void actualizaTrailMatrix(ArrayList soluciones,MatrizRastro mrastro){
		//recorro la matriz de rastros actualizando las entradas tij
		for(int k=0;k<soluciones.size();k++){
			//tomo la solucion k
			ArrayList coloracion=(ArrayList)soluciones.get(k);
			for(int i=0;i<mrastro.rastro.length;i++){
				//busco para actualizar la entrada tij
				for(int j=0;j<mrastro.rastro.length;j++){
					//BUSCO SOLUCIONES EN LAS QUE i y j tengan el mismo color
					//para actualizar la entrada tij
					double factor=sigma(coloracion,i,j);
					//tij=ptij+Sigma(1/q)
					mrastro.rastro[i][j]=(p*mrastro.rastro[i][j])+factor;
					
				}
			}
		}
	}
}