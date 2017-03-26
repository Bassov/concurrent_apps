package k_nearest_neighbors.main;

import k_nearest_neighbors.data.BankMarketing;
import k_nearest_neighbors.loader.BankMarketingLoader;
import k_nearest_neighbors.parallel.ParallelKnn;

import java.util.Date;
import java.util.List;

import static k_nearest_neighbors.DataPathKt.DATA_TEST_PATH;
import static k_nearest_neighbors.DataPathKt.DATA_TRAIN_PATH;

public class ParallelIndividualMainSort {

	public static void main(String[] args) {

		BankMarketingLoader loader = new BankMarketingLoader();
		List<BankMarketing> train = loader.load(DATA_TRAIN_PATH);
		System.out.println("Train: " + train.size());
		List<BankMarketing> test = loader.load(DATA_TEST_PATH);
		System.out.println("Test: " + test.size());
		double currentTime = 0.0;
		int success = 0, mistakes = 0;
		int k = 10;
		success = 0;
		mistakes = 0;
		ParallelKnn classifier = new ParallelKnn(
				train, k, true);
		try {
			Date start, end;
			start = new Date();
			for (BankMarketing example : test) {
				String tag = classifier.classify(example);
				if (tag.equals(example.getTag())) {
					success++;
				} else {
					mistakes++;
				}
			}
			end = new Date();

			currentTime = end.getTime() - start.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		classifier.destroy();
		System.out.println("******************************************");
		System.out.println("Parallel Classifier Individual - K: " + k
				+ " - Factor 1 - Parallel Sort: true");
		System.out.println("Success: " + success);
		System.out.println("Mistakes: " + mistakes);
		System.out.println("Execution Time: " + (currentTime / 1000)
				+ " seconds.");
		System.out.println("******************************************");

	}

}
