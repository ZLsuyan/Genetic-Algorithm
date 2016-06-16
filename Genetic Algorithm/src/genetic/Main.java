package genetic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/** 
 * @author 曾丽
 * @date 2016/1/17
 */

public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		PrintWriter pw1 = new PrintWriter(new File("F:"+File.separator+"averg.txt")) ;
		PrintWriter pw2 = new PrintWriter(new File("F:"+File.separator+"maxfit.txt")) ;
		
		CoreControl control = new CoreControl();
		control.initialize();        //进行初始化
		
		SpeciesGroup speciesGroup = new SpeciesGroup(control.getMembers());
		System.out.println("初始的种群成员为：");
		speciesGroup.print();       //将做遗传算法之前种群成员输出
		double theBest;
		
		speciesGroup.decode();
		System.out.println("第0代：");
		theBest = speciesGroup.sufficiency();
		System.out.println("f="+theBest); //输出每次迭代适应度最大对应的结果值
		System.out.println();
		
		double[] values = speciesGroup.getValues() ;
//		pw1.println("0 "+ values[0]) ;
//		pw2.println("0 "+ values[1]) ;
		pw1.println(values[0]) ;
		pw2.println(values[1]) ;
		pw1.flush() ;
		pw2.flush() ;
		
		
		int n = 1;
		do{
			speciesGroup.gambleWheel();        //运用赌盘方法进行选择			          
			speciesGroup.intersect();          //交叉
			speciesGroup.variation();          //变异

			values = speciesGroup.getValues() ;
//			pw1.println(n+" "+ values[0]) ;
//			pw2.println(n+" "+ values[1]) ;
			pw1.println(values[0]) ;
			pw2.println(values[1]) ;
			pw1.flush() ;
			pw2.flush() ;
			
//			double avergfitness = 0;
//			double[] fitness = new double[50];
//			for()
			speciesGroup.decode();
			System.out.println("第"+n+"代：");
			theBest = speciesGroup.sufficiency();
			System.out.println("f="+theBest); //输出每次迭代适应度最大对应的结果值
			System.out.println();

			
			SpeciesGroup.generation++;
			n++;

		}while(!control.isEnded());           //当迭代次数没达到时反复迭代
			
		System.out.println("最终群成员为：");
		speciesGroup.print();      //将做遗传算法之后种群成员输出
	}

}
