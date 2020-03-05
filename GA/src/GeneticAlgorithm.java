import java.util.ArrayList;
import java.util.List;

public abstract class GeneticAlgorithm {
	private List<Chromosome> population = new ArrayList<Chromosome>();
	private int popSize = 100;// 種群數量
	private int geneSize;// 基因最大長度
	private int maxIterNum = 20;// 最大迭代次數
	private double mutationRate = 0.01;// 基因變異的概率
	private int maxMutationNum = 3;// 最大變非同步長
	private int generation = 1;// 當前遺傳到第幾代
	private double bestScore;// 最好得分
	private double worstScore;// 最壞得分
	private double totalScore;// 總得分
	private double averageScore;// 平均得分
	private double x; // 記錄歷史種群中最好的X值
	private double y; // 記錄歷史種群中最好的Y值
	private int geneI;// x y所在代數

	public GeneticAlgorithm(int geneSize) {
		this.geneSize = geneSize;
	}

	public void caculte() {
//初始化種群 
		generation = 1;
		init();
		while (generation < maxIterNum) {
//種群遺傳 
			evolve();
			print();
			generation++;
		}
	}

	/**
	 * @Description: 輸出結果
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
	 * @Description: 初始化種群
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
	 * @Description:種群進行遺傳
	 */
	private void evolve() {
		List<Chromosome> childPopulation = new ArrayList<Chromosome>();
//生成下一代種群 
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
//新種群替換舊種群 
		List<Chromosome> t = population;
		population = childPopulation;
		t.clear();
		t = null;
//基因突變 
		mutation();
//計算新種群的適應度 
		caculteScore();
	}

	/**
	 * @return
	 * @Description: 輪盤賭法選擇可以遺傳下一代的染色體
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
	 * @Description: 計算種群適應度
	 */
	private void caculteScore() {
		setChromosomeScore(population.get(0));
		bestScore = population.get(0).getScore();
		worstScore = population.get(0).getScore();
		totalScore = 0;
		for (Chromosome chro : population) {
			setChromosomeScore(chro);
			if (chro.getScore() > bestScore) { // 設定最好基因值
				bestScore = chro.getScore();
				if (y < bestScore) {
					x = changeX(chro);
					y = bestScore;
					geneI = generation;
				}
			}
			if (chro.getScore() < worstScore) { // 設定最壞基因值
				worstScore = chro.getScore();
			}
			totalScore = chro.getScore();
		}
		averageScore = totalScore / popSize;
//因為精度問題導致的平均值大於最好值，將平均值設定成最好值 
		averageScore = averageScore > bestScore ? bestScore : averageScore;
	}

	/**
	 * 基因突變
	 */
	private void mutation() {
		for (Chromosome chro : population) {
			if (Math.random() < mutationRate) { // 發生基因突變
				int mutationNum = (int) (Math.random() * maxMutationNum);
				chro.mutation(mutationNum);
			}
		}
	}

	/**
	 * @param chro
	 * @Description: 設定染色體得分
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
	 * @Description: 將二進位制轉化為對應的X
	 */
	public abstract double changeX(Chromosome chro);

	/**
	 * @param x
	 * @return
	 * @Description: 根據X計算Y值 Y=F(X)
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