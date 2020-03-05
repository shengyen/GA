
/**  
*@Description: ��]��ǬV����  
*/
import java.util.ArrayList;
import java.util.List;

public class Chromosome {
	private boolean[] gene;// ��]�ǦC
	private double score;// �������禡�o��

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	/**
	 * @param size �H���ͦ���]�ǦC
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
	 * �ͦ��@�ӷs��]
	 */
	public Chromosome() {
	}

	/**
	 * @param c
	 * @return
	 * @Description: �J����]
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
	 * @Description: ��l�ư�]����
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
	 * @Description: ��ǲ��ͤU�@�N
	 */
	public static List<Chromosome> genetic(Chromosome p1, Chromosome p2) {
		if (p1 == null || p2 == null) { // �V���馳�@�Ӭ��šA�����ͤU�@�N
			return null;
		}
		if (p1.gene == null || p2.gene == null) { // �V���馳�@�ӨS����]�ǦC�A�����ͤU�@�N
			return null;
		}
		if (p1.gene.length != p2.gene.length) { // �V�����]�ǦC���פ��P�A�����ͤU�@�N
			return null;
		}
		Chromosome c1 = clone(p1);
		Chromosome c2 = clone(p2);
//�H�����ͥ�e������m 
		int size = c1.gene.length;
		int a = ((int) (Math.random() * size)) % size;
		int b = ((int) (Math.random() * size)) % size;
		int min = a > b ? b : a;
		int max = a > b ? a : b;
//���m�W����]�i���e���� 
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
	 * @Description: ��]num�Ӧ�m�o���ܲ�
	 */
	public void mutation(int num) {
//���\�ܲ� 
		int size = gene.length;
		for (int i = 0; i < num; i++) {
//�M���ܲ���m 
			int at = ((int) (Math.random() * size)) % size;
//�ܲ��᪺�� 
			boolean bool = !gene[at];
			gene[at] = bool;
		}
	}

	/**
	 * @return
	 * @Description: �N��]��Ƭ��������Ʀr
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