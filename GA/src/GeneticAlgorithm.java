import java.util.ArrayList;
import java.util.List;

public abstract class GeneticAlgorithm {
	private List<Chromosome> population = new ArrayList<Chromosome>();
	private int popSize = 100;// �ظs�ƶq
	private int geneSize;// ��]�̤j����
	private int maxIterNum = 20;// �̤j���N����
	private double mutationRate = 0.01;// ��]�ܲ������v
	private int maxMutationNum = 3;// �̤j�ܫD�P�B��
	private int generation = 1;// ��e��Ǩ�ĴX�N
	private double bestScore;// �̦n�o��
	private double worstScore;// ���a�o��
	private double totalScore;// �`�o��
	private double averageScore;// �����o��
	private double x; // �O�����v�ظs���̦n��X��
	private double y; // �O�����v�ظs���̦n��Y��
	private int geneI;// x y�Ҧb�N��

	public GeneticAlgorithm(int geneSize) {
		this.geneSize = geneSize;
	}

	public void caculte() {
//��l�ƺظs 
		generation = 1;
		init();
		while (generation < maxIterNum) {
//�ظs��� 
			evolve();
			print();
			generation++;
		}
	}

	/**
	 * @Description: ��X���G
	 */
	private void print() {
		System.out.println("--------------------------------");
		System.out.println("the generation is:" + generation);
		System.out.println("the best y is:" + bestScore);
		System.out.println("the worst fitness is:" + worstScore);
		System.out.println("the average fitness is:" + averageScore);
		System.out.println("the total fitness is:" + totalScore);
		System.out.println("geneI:" + geneI + "\tx:" + x + "\ty:" + y);
	}

	/**
	 * @Description: ��l�ƺظs
	 */
	private void init() {
		for (int i = 0; i < popSize; i++) {
			population = new ArrayList<Chromosome>();
			Chromosome chro = new Chromosome(geneSize);
			population.add(chro);
		}
		caculteScore();
	}

	/**
	 * @Author:lulei
	 * @Description:�ظs�i����
	 */
	private void evolve() {
		List<Chromosome> childPopulation = new ArrayList<Chromosome>();
//�ͦ��U�@�N�ظs 
		while (childPopulation.size() < popSize) {
			Chromosome p1 = getParentChromosome();
			Chromosome p2 = getParentChromosome();
			List<Chromosome> children = Chromosome.genetic(p1, p2);
			if (children != null) {
				for (Chromosome chro : children) {
					childPopulation.add(chro);
				}
			}
		}
//�s�ظs�����ºظs 
		List<Chromosome> t = population;
		population = childPopulation;
		t.clear();
		t = null;
//��]���� 
		mutation();
//�p��s�ظs���A���� 
		caculteScore();
	}

	/**
	 * @return
	 * @Description: ���L��k��ܥi�H��ǤU�@�N���V����
	 */
	private Chromosome getParentChromosome() {
		double slice = Math.random() * totalScore;
		double sum = 0;
		for (Chromosome chro : population) {
			sum = chro.getScore();
			if (sum > slice && chro.getScore() >= averageScore) {
				return chro;
			}
		}
		return null;
	}

	/**
	 * @Description: �p��ظs�A����
	 */
	private void caculteScore() {
		setChromosomeScore(population.get(0));
		bestScore = population.get(0).getScore();
		worstScore = population.get(0).getScore();
		totalScore = 0;
		for (Chromosome chro : population) {
			setChromosomeScore(chro);
			if (chro.getScore() > bestScore) { // �]�w�̦n��]��
				bestScore = chro.getScore();
				if (y < bestScore) {
					x = changeX(chro);
					y = bestScore;
					geneI = generation;
				}
			}
			if (chro.getScore() < worstScore) { // �]�w���a��]��
				worstScore = chro.getScore();
			}
			totalScore = chro.getScore();
		}
		averageScore = totalScore / popSize;
//�]����װ��D�ɭP�������Ȥj��̦n�ȡA�N�����ȳ]�w���̦n�� 
		averageScore = averageScore > bestScore ? bestScore : averageScore;
	}

	/**
	 * ��]����
	 */
	private void mutation() {
		for (Chromosome chro : population) {
			if (Math.random() < mutationRate) { // �o�Ͱ�]����
				int mutationNum = (int) (Math.random() * maxMutationNum);
				chro.mutation(mutationNum);
			}
		}
	}

	/**
	 * @param chro
	 * @Description: �]�w�V����o��
	 */
	private void setChromosomeScore(Chromosome chro) {
		if (chro == null) {
			return;
		}
		double x = changeX(chro);
		double y = caculateY(x);
		chro.setScore(y);
	}

	/**
	 * @param chro
	 * @return
	 * @Description: �N�G�i�����Ƭ�������X
	 */
	public abstract double changeX(Chromosome chro);

	/**
	 * @param x
	 * @return
	 * @Description: �ھ�X�p��Y�� Y=F(X)
	 */
	public abstract double caculateY(double x);

	public void setPopulation(List<Chromosome> population) {
		this.population = population;
	}

	public void setPopSize(int popSize) {
		this.popSize = popSize;
	}

	public void setGeneSize(int geneSize) {
		this.geneSize = geneSize;
	}

	public void setMaxIterNum(int maxIterNum) {
		this.maxIterNum = maxIterNum;
	}

	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}

	public void setMaxMutationNum(int maxMutationNum) {
		this.maxMutationNum = maxMutationNum;
	}

	public double getBestScore() {
		return bestScore;
	}

	public double getWorstScore() {
		return worstScore;
	}

	public double getTotalScore() {
		return totalScore;
	}

	public double getAverageScore() {
		return averageScore;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}