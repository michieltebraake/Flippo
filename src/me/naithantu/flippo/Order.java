package me.naithantu.flippo;

import java.util.ArrayList;
import java.util.Random;

public class Order {

	public Order() {}

	ShuntingJava sh = new ShuntingJava();
	String n1, n2, n3, n4;
	ArrayList<String> calcText = new ArrayList<String>();
	ArrayList<String> tempCalcText = new ArrayList<String>();

	public ArrayList<String> generateNumbers() {
		calcText.clear();
		calcText.add("0");
		calcText.add("+");
		calcText.add("0");
		calcText.add("+");
		calcText.add("0");
		calcText.add("+");
		calcText.add("0");

		// Randomize numbers.
		randomizeNumbers();

		// RNOArray contains all the different possible orders of numbers.
		String RNOArray[][] = { // RNOArray = RandomNumberOrderArray
		{ "1", "2", "3", "4" }, { "1", "2", "4", "3" }, { "1", "3", "2", "4" }, { "1", "3", "4", "2" }, { "1", "4", "2", "3" }, { "1", "4", "3", "2" },

		{ "2", "1", "3", "4" }, { "2", "1", "4", "3" }, { "2", "3", "1", "4" }, { "2", "3", "4", "1" }, { "2", "4", "1", "3" }, { "2", "4", "3", "1" },

		{ "3", "1", "2", "4" }, { "3", "1", "4", "2" }, { "3", "2", "1", "4" }, { "3", "2", "4", "1" }, { "3", "4", "1", "2" }, { "3", "4", "2", "1" },

		{ "4", "1", "2", "3" }, { "4", "1", "3", "2" }, { "4", "2", "1", "3" }, { "4", "2", "3", "1" }, { "4", "3", "1", "2" }, { "4", "3", "2", "1" }, };
		while (cycleNumbers(RNOArray) != true) {
			randomizeNumbers();
		}
		System.out.println(tempCalcText);
		return calcText;
	}

	private void randomizeNumbers() {
		// create Random numbers
		Random randomNumber = new Random();

		// Numbers between 1 - 9.
		int number1 = randomNumber.nextInt(9) + 1;
		int number2 = randomNumber.nextInt(9) + 1;
		int number3 = randomNumber.nextInt(9) + 1;
		int number4 = randomNumber.nextInt(9) + 1;

		// convert int to String for pa
		n1 = Integer.toString(number1);
		n2 = Integer.toString(number2);
		n3 = Integer.toString(number3);
		n4 = Integer.toString(number4);
	}

	private boolean cycleNumbers(String x[][]) {
		for (int row = 0; row < x.length; row++) {
			for (int column = 0; column < x[row].length; column++) {
				if (x[row][column].equals("1")) {
					calcText.set(column * 2, n1);
				} else if (x[row][column].equals("2")) {
					calcText.set(column * 2, n2);
				} else if (x[row][column].equals("3")) {
					calcText.set(column * 2, n3);
				} else if (x[row][column].equals("4")) {
					calcText.set(column * 2, n4);
				}
			}
			if (cycleOperators() == true) {
				return true;
			}
		}
		return false;
	}

	private boolean cycleOperators() {
		String[] operators = { "+", "-", "*", "/" };
		String[] currentOperators = { "+", "+", "+" };
		int columns = 3;
		int rows = 64;
		int counter = 0;
		String ROOArray[][] = new String[rows][columns];
		
		for (int c = 1; c < 10; c++) {
			if (c == 1) { // without brackets
				counter = 0;
				for (int i1 = 0; i1 <= 3; i1++) {
					currentOperators[0] = operators[i1];
					for (int i2 = 0; i2 <= 3; i2++) {
						currentOperators[1] = operators[i2];
						for (int i3 = 0; i3 <= 3; i3++) {
							currentOperators[2] = operators[i3];
							ROOArray[counter][0] = currentOperators[0];
							ROOArray[counter][1] = currentOperators[1];
							ROOArray[counter][2] = currentOperators[2];
							counter++;
							tempCalcText.addAll(calcText);
							tempCalcText.set(1, currentOperators[0]);
							tempCalcText.set(3, currentOperators[1]);
							tempCalcText.set(5, currentOperators[2]);
							if (sh.calcAnswer(tempCalcText) == 24) {
								return true;
							}
							tempCalcText.clear();
						}
					}
				}
			} else if (c == 2) { // (2*2)*2*2
				counter = 0;
				for (int i1 = 0; i1 <= 3; i1++) {
					currentOperators[0] = operators[i1];
					for (int i2 = 0; i2 <= 3; i2++) {
						currentOperators[1] = operators[i2];
						for (int i3 = 0; i3 <= 3; i3++) {
							currentOperators[2] = operators[i3];
							ROOArray[counter][0] = currentOperators[0];
							ROOArray[counter][1] = currentOperators[1];
							ROOArray[counter][2] = currentOperators[2];
							counter++;
							tempCalcText.addAll(calcText);
							tempCalcText.set(1, currentOperators[0]);
							tempCalcText.set(3, currentOperators[1]);
							tempCalcText.set(5, currentOperators[2]);
							tempCalcText.add(0, "(");
							tempCalcText.add(4, ")");
							if (sh.calcAnswer(tempCalcText) == 24) {
								return true;
							}
							tempCalcText.clear();	
						}
					}
				}

			} else if (c == 3) { // 2*2*(2*2)
				counter = 0;
				for (int i1 = 0; i1 <= 3; i1++) {
					currentOperators[0] = operators[i1];
					for (int i2 = 0; i2 <= 3; i2++) {
						currentOperators[1] = operators[i2];
						for (int i3 = 0; i3 <= 3; i3++) {
							currentOperators[2] = operators[i3];
							ROOArray[counter][0] = currentOperators[0];
							ROOArray[counter][1] = currentOperators[1];
							ROOArray[counter][2] = currentOperators[2];
							counter++;
							tempCalcText.addAll(calcText);
							tempCalcText.set(1, currentOperators[0]);
							tempCalcText.set(3, currentOperators[1]);
							tempCalcText.set(5, currentOperators[2]);
							tempCalcText.add(4, "(");
							tempCalcText.add(8, ")");
							if (sh.calcAnswer(tempCalcText) == 24) {
								return true;
							}
							tempCalcText.clear();
						}
					}
				}
			} else if (c == 4) { // 2*(2*2)*2
				counter = 0;
				for (int i1 = 0; i1 <= 3; i1++) {
					currentOperators[0] = operators[i1];
					for (int i2 = 0; i2 <= 3; i2++) {
						currentOperators[1] = operators[i2];
						for (int i3 = 0; i3 <= 3; i3++) {
							currentOperators[2] = operators[i3];
							ROOArray[counter][0] = currentOperators[0];
							ROOArray[counter][1] = currentOperators[1];
							ROOArray[counter][2] = currentOperators[2];
							counter++;
							tempCalcText.addAll(calcText);
							tempCalcText.set(1, currentOperators[0]);
							tempCalcText.set(3, currentOperators[1]);
							tempCalcText.set(5, currentOperators[2]);
							tempCalcText.add(2, "(");
							tempCalcText.add(6, ")");
							if (sh.calcAnswer(tempCalcText) == 24) {
								return true;
							}
							tempCalcText.clear();
						}
					}
				}
			} else if (c == 5) { // (2*2)*(2*2)
				counter = 0;
				for (int i1 = 0; i1 <= 3; i1++) {
					currentOperators[0] = operators[i1];
					for (int i2 = 0; i2 <= 3; i2++) {
						currentOperators[1] = operators[i2];
						for (int i3 = 0; i3 <= 3; i3++) {
							currentOperators[2] = operators[i3];
							ROOArray[counter][0] = currentOperators[0];
							ROOArray[counter][1] = currentOperators[1];
							ROOArray[counter][2] = currentOperators[2];
							counter++;
							tempCalcText.addAll(calcText);
							tempCalcText.set(1, currentOperators[0]);
							tempCalcText.set(3, currentOperators[1]);
							tempCalcText.set(5, currentOperators[2]);
							tempCalcText.add(0, "(");
							tempCalcText.add(4, ")");
							tempCalcText.add(6, "(");
							tempCalcText.add(10, ")");
							if (sh.calcAnswer(tempCalcText) == 24) {
								return true;
							}
							tempCalcText.clear();
						}
					}
				}
			} else if (c == 6) { // (2*(2*2))*2
				counter = 0;
				for (int i1 = 0; i1 <= 3; i1++) {
					currentOperators[0] = operators[i1];
					for (int i2 = 0; i2 <= 3; i2++) {
						currentOperators[1] = operators[i2];
						for (int i3 = 0; i3 <= 3; i3++) {
							currentOperators[2] = operators[i3];
							ROOArray[counter][0] = currentOperators[0];
							ROOArray[counter][1] = currentOperators[1];
							ROOArray[counter][2] = currentOperators[2];
							counter++;
							tempCalcText.addAll(calcText);
							tempCalcText.set(1, currentOperators[0]);
							tempCalcText.set(3, currentOperators[1]);
							tempCalcText.set(5, currentOperators[2]);
							tempCalcText.add(0, "(");
							tempCalcText.add(3, "(");
							tempCalcText.add(7, ")");
							tempCalcText.add(8, ")");
							if (sh.calcAnswer(tempCalcText) == 24) {
								return true;
							}
							tempCalcText.clear();
						}
					}
				}
			} else if (c == 7) { // ((2*2)*2)*2
				counter = 0;
				for (int i1 = 0; i1 <= 3; i1++) {
					currentOperators[0] = operators[i1];
					for (int i2 = 0; i2 <= 3; i2++) {
						currentOperators[1] = operators[i2];
						for (int i3 = 0; i3 <= 3; i3++) {
							currentOperators[2] = operators[i3];
							ROOArray[counter][0] = currentOperators[0];
							ROOArray[counter][1] = currentOperators[1];
							ROOArray[counter][2] = currentOperators[2];
							counter++;
							tempCalcText.addAll(calcText);
							tempCalcText.set(1, currentOperators[0]);
							tempCalcText.set(3, currentOperators[1]);
							tempCalcText.set(5, currentOperators[2]);
							tempCalcText.add(0, "(");
							tempCalcText.add(1, "(");
							tempCalcText.add(5, ")");
							tempCalcText.add(8, ")");
							if (sh.calcAnswer(tempCalcText) == 24) {
								return true;
							}
							tempCalcText.clear();
						}
					}
				}
			} else if (c == 8) { // 2*((2*2)*2)
				counter = 0;
				for (int i1 = 0; i1 <= 3; i1++) {
					currentOperators[0] = operators[i1];
					for (int i2 = 0; i2 <= 3; i2++) {
						currentOperators[1] = operators[i2];
						for (int i3 = 0; i3 <= 3; i3++) {
							currentOperators[2] = operators[i3];
							ROOArray[counter][0] = currentOperators[0];
							ROOArray[counter][1] = currentOperators[1];
							ROOArray[counter][2] = currentOperators[2];
							counter++;
							tempCalcText.addAll(calcText);
							tempCalcText.set(1, currentOperators[0]);
							tempCalcText.set(3, currentOperators[1]);
							tempCalcText.set(5, currentOperators[2]);
							tempCalcText.add(2, "(");
							tempCalcText.add(3, "(");
							tempCalcText.add(7, ")");
							tempCalcText.add(10, ")");
							if (sh.calcAnswer(tempCalcText) == 24) {
								return true;
							}
							tempCalcText.clear();
						}
					}
				}
			} else { // 2*(2*(2*2))
				counter = 0;
				for (int i1 = 0; i1 <= 3; i1++) {
					currentOperators[0] = operators[i1];
					for (int i2 = 0; i2 <= 3; i2++) {
						currentOperators[1] = operators[i2];
						for (int i3 = 0; i3 <= 3; i3++) {
							currentOperators[2] = operators[i3];
							ROOArray[counter][0] = currentOperators[0];
							ROOArray[counter][1] = currentOperators[1];
							ROOArray[counter][2] = currentOperators[2];
							counter++;
							tempCalcText.addAll(calcText);
							tempCalcText.set(1, currentOperators[0]);
							tempCalcText.set(3, currentOperators[1]);
							tempCalcText.set(5, currentOperators[2]);
							tempCalcText.add(2, "(");
							tempCalcText.add(5, "(");
							tempCalcText.add(9, ")");
							tempCalcText.add(10, ")");
							if (sh.calcAnswer(tempCalcText) == 24) {
								return true;
							}
							tempCalcText.clear();
						}
					}
				}
			}
		}
		return false;
	}
}