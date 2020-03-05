
/**  
*@Description: 基因遺傳染色體  
*/
import java.util.ArrayList;
import java.util.List;

public class Chromosome {
	private boolean[] gene;// 基因序列
	private double score;// 對應的函式得分

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * @param size 隨機生成基因序列
	 */
	public Chromosome(int size) {
		if (size <= 0) {
			return;
		}
		initGeneSize(size);
		for (int i = 0; i < size; i++) {
			gene[i] = Math.random() >= 0.5;
		}
	}

	/**
	 * 生成一個新基因
	 */
	public Chromosome() {
	}

	/**
	 * @param c
	 * @return
	 * @Description: 克隆基因
	 */
	public static Chromosome clone(final Chromosome c) {
		if (c == null || c.gene == null) {
			return null;
		}
		Chromosome copy = new Chromosome();
		copy.initGeneSize(c.gene.length);
		for (int i = 0; i < c.gene.length; i++) {
			copy.gene[i] = c.gene[i];
		}
		return copy;
	}

	/**
	 * @param size
	 * @Description: 初始化基因長度
	 */
	private void initGeneSize(int size) {
		if (size <= 0) {
			return;
		}
		gene = new boolean[size];
	}

	/**
	 * @param c1
	 * @param c2
	 * @Description: 遺傳產生下一代
	 */
	public static List<Chromosome> genetic(Chromosome p1, Chromosome p2) {
		if (p1 == null || p2 == null) { // 染色體有一個為空，不產生下一代
			return null;
		}
		if (p1.gene == null || p2.gene == null) { // 染色體有一個沒有基因序列，不產生下一代
			return null;
		}
		if (p1.gene.length != p2.gene.length) { // 染色體基因序列長度不同，不產生下一代
			return null;
		}
		Chromosome c1 = clone(p1);
		Chromosome c2 = clone(p2);
//隨機產生交叉互換位置 
		int size = c1.gene.length;
		int a = ((int) (Math.random() * size)) % size;
		int b = ((int) (Math.random() * size)) % size;
		int min = a > b ? b : a;
		int max = a > b ? a : b;
//對位置上的基因進行交叉互換 
		for (int i = min; i <= max; i++) {
			boolean t = c1.gene[i];
			c1.gene[i] = c2.gene[i];
			c2.gene[i] = t;
		}
		List<Chromosome> list = new ArrayList<Chromosome>();
		list.add(c1);
		list.add(c2);
		return list;
	}

	/**
	 * @param num
	 * @Description: 基因num個位置發生變異
	 */
	public void mutation(int num) {
//允許變異 
		int size = gene.length;
		for (int i = 0; i < num; i++) {
//尋找變異位置 
			int at = ((int) (Math.random() * size)) % size;
//變異後的值 
			boolean bool = !gene[at];
			gene[at] = bool;
		}
	}

	/**
	 * @return
	 * @Description: 將基因轉化為對應的數字
	 */
	public int getNum() {
		if (gene == null) {
			return 0;
		}
		int num = 0;
		for (boolean bool : gene) {
			num <<= 1;
			if (bool) {
				num = 1;
			}
		}
		return num;
	}
}