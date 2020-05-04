
import java.util.*; 
import java.lang.*; 
import java.io.*; 

class Graph 
{ 	

    class Edge implements Comparable<Edge> 
    { 
    int src, dest, weight; 
    // kenar sıralaması
    
    public int compareTo(Edge compareEdge) 
    { 
	return this.weight-compareEdge.weight; 
    } 
    }; 

	// alt kume temsil eden sınıf

	class subset 
	{ 
		int parent, rank; 
	}; 

	int A, B; // V-> hayır. köşeler ve E-> kenar sayısı
	Edge edge[]; // tkenarların toplanması


	// v ve e kenarları olan grafik olustur

	Graph(int a, int b) 
	{ 
		A = a; 
		B = b; 
		edge = new Edge[B]; 
		for (int i=0; i<b; ++i) 
			edge[i] = new Edge(); 
	} 
        

	// i ogesinin kumesinin bulunması
	
	int find(subset subsets[], int i) 
	{ 
     // root'u i'nin ust ogesı olarak olusturma
		if (subsets[i].parent != i) 
			subsets[i].parent = find(subsets, subsets[i].parent); 

		return subsets[i].parent; 
	} 

	// x ve y kumesinin birleşmesi

	
	void Union(subset subsets[], int x, int y) 
	{ 
		int xroot = find(subsets, x); 
		int yroot = find(subsets, y); 

	// daha kucuk rutbe agacı ekle

		if (subsets[xroot].rank < subsets[yroot].rank) 
			subsets[xroot].parent = yroot; 
		else if (subsets[xroot].rank > subsets[yroot].rank) 
			subsets[yroot].parent = xroot; 


        // rutbeler aynıysa, birini kök ve artış olarak yapın

		else
		{ 
			subsets[yroot].parent = xroot; 
			subsets[xroot].rank++; 
		} 
	} 

	// ana işlev
	void KruskalMST() 
	{ 
	Edge result[] = new Edge[A]; // mstyi depolama

	int e = 0; // sonuc
	int i = 0; // sıralanan kenarlar

	for (i=0; i<A; ++i) 
			result[i] = new Edge(); 

	// Adım 1: Tüm kenarları, küçülmeyecek şekilde sıralayın
	// ağırlık. Verilen grafiği değiştirmemize izin verilmezse,
	//kenar dizisinin bir kopyasını oluşturabilir

	Arrays.sort(edge); 

	// v alt kumeleri icin bellek
	subset subsets[] = new subset[A]; 
	for(i=0; i<A; ++i) 
	subsets[i]=new subset(); 

	// v alt kume olusturma
	for (int v = 0; v < A; ++v) 
	{ 
	subsets[v].parent = v; 
	subsets[v].rank = 0; 
	} 

	i = 0; // sonrakı kenarı secmek

	// kenar sayısı v-1 'e esit
	while (e < A - 1) 
	{ 
            
   //en kucuk kenarı sec ve arttır
            
	Edge next_edge = new Edge(); 
	next_edge = edge[i++]; 

	int x = find(subsets, next_edge.src); 
	int y = find(subsets, next_edge.dest); 

	//kenarı dahil etmek döngüye neden olmazsa sonucu dahil et ve dizini arttır
	 
	
	if (x != y) 
	{ 
	result[e++] = next_edge; 
	Union(subsets, x, y); 
	} 
	 
	} 

	// sonuc
	System.out.println("Kenarlar" ); 
	for (i = 0; i < e; ++i) 
	System.out.println(result[i].src+" -- " + 
	result[i].dest+" == " + result[i].weight); 
	} 

	// cıktı
	public static void main (String[] args) 
	{ 
	
		int V = 4; // köşe noktası sayısı
		int E = 5; // kenar sayısı

		Graph graph = new Graph(V, E); 

		// kenar 0-1 ekle

		graph.edge[0].src = 0; 
		graph.edge[0].dest = 1; 
		graph.edge[0].weight = 10; 

		// kenar 0-2 ekle
		graph.edge[1].src = 0; 
		graph.edge[1].dest = 2; 
		graph.edge[1].weight = 6; 

		// kenar 0-3 ekle 
		graph.edge[2].src = 0; 
		graph.edge[2].dest = 3; 
		graph.edge[2].weight = 5; 

		// kenar 1-3 ekle
		graph.edge[3].src = 1; 
		graph.edge[3].dest = 3; 
		graph.edge[3].weight = 15; 

		// kenar 2-3 ekle 
		graph.edge[4].src = 2; 
		graph.edge[4].dest = 3; 
		graph.edge[4].weight = 4; 
		graph.KruskalMST(); 
	} 
        } 

