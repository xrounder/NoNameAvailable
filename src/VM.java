import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class VM extends Thread {

	public final int NOP = 0;
	public final int LOAD = 1;
	public final int MOV = 2;
	public final int ADD = 3;
	public final int SUP = 4;
	public final int MUL = 5;
	public final int DIV = 6;
	public final int PUSH = 7;
	public final int POP = 8;
	public final int JMP = 9;
	public final int JIZ = 10;
	public final int JIH = 11;
	public final int JSR = 12;
	public final int RTS = 13;

	// Programmablaufspeicher
	volatile int[] memory = new int[4096];
	// Register
	volatile int[] register = new int[16];
	// Stack
	FastVector stack = new FastVector(25000000, 1000);
	FastVector routineStack = new FastVector(25000000, 1000);

	// das GANZE
	private int opcode = 0;
	private int pcounter = 0;
	// Index von Rx
	private int index_X = 0;
	// Index von Ry
	private int index_Y = 0;
	private int fromMem = 0;
	private int toMem = 0;
	private int wert = 0;
	private int command = 0;
	private int stackIndex = 0;
	private int routineIndex = 0;
	private boolean runner = true;
	private int[] profiler = new int[4096];

	public void run() {

		do {

			splitOpcode();
			profiler[pcounter]++; // profiler counts up, when pcounter is in
									// this line;

			switch (command) {

			// does absolutely nothing!
			case NOP:
				pcounter++;
				break;

			// Load value in R(0)
			case LOAD:
				register[0] = wert;
				pcounter++;
				break;

			// Move, flagged with fromMem & toMem (Operate in Register/Memory !)
			case MOV:
				if (fromMem == 0 && toMem == 0) {
					register[index_X] = register[index_Y];
				} else if (fromMem == 1 && toMem == 0) {
					register[index_X] = memory[register[index_Y]];
				} else if (fromMem == 0 && toMem == 1) {
					memory[register[index_X]] = register[index_Y];
				} else // fromMem ==1 && toMem == 1
				{
					memory[register[index_X]] = memory[register[index_Y]];

				}
				pcounter++;
				break;
			// add R(X) = R(X)+R(Y)
			case ADD:
				register[index_X] += register[index_Y];
				pcounter++;
				break;

			// subtract R(X)=R(X)-R(Y)
			case SUP:
				register[index_X] -= register[index_Y];
				pcounter++;
				break;

			// Multiply R(X)=R(X)*R(Y)
			case MUL:
				register[index_X] *= register[index_Y];
				pcounter++;
				break;

			// Divide R(X)=R(X)/R(Y)
			case DIV:
				try {
					register[index_X] /= register[index_Y];
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				pcounter++;
				break;

			// Push the Value in R(X) on the Stack
			case PUSH:
				stack.setValue(stackIndex++, (register[index_X]));
				pcounter++;
				break;

			// Pop a Value of the Stack, save it in R(X)
			case POP:
				register[index_X] = stack.getValue(--stackIndex);
				pcounter++;
				break;

			// Jump
			case JMP:
				pcounter = wert;

				break;

			// Jump (only if Register[0] is exactly 0!)
			case JIZ:
				if (register[0] == 0) {
					pcounter = wert;

				} else {
					pcounter++;
				}
				break;

			// Jump (only if Register[0] is > 0!)
			case JIH:
				if (register[0] > 0) {
					pcounter = wert;

				} else {
					pcounter++;
				}
				break;

			// Jump to Subroutine(pcounter push on stack!)
			case JSR:
				routineStack.setValue(routineIndex++, ++pcounter);
				pcounter = wert;

				break;

			// Return from Subroutine(getting pcounter per pop from stack!)
			case RTS:
				if (routineIndex != 0)
					pcounter = routineStack.getValue(--routineIndex);
				else
					runner = false;

				break;

			default:
				break;

			}

		} while (runner);

		// Write outputfile of Profiler
		try {
			profilerOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void profilerOutput() throws IOException {

		String toWrite = "";
		
		// current directory
			String directory = System.getProperty("user.dir") +"\\src\\";

		File file = new File(directory+"Profiler.txt");

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));

		bw.write("Anteil |Code     \t\t\t | Kommentar");
		bw.newLine();
		bw.write("-----------------------------------------------------------------"
				+ "---------------------------------------------------------------");
		bw.newLine();

		for (int i = 0; i < profiler.length; i++) {
			if (profiler[i] != 0) {
				toWrite = "(" + getLinePercentage(i) + "%) " + Integer.toBinaryString(memory[i])
						+ (Integer.toBinaryString(memory[i]).length() < 8 ? "\t" : "") + "\t\t\t Zeile["
						+ (i < 10 ? "0" + i : i) + "] wurde "
						+ (profiler[i] < 10 ? " {" + profiler[i] : "{" + profiler[i]) + "} mal ausgeführt - "
						+ getLinePercentage(i) + "% Rechenleistung wurde hier verbraucht.";
				bw.write(toWrite);
				bw.newLine();
			}
		}

		bw.close();
	}

	private float getLinePercentage(int line) {
		float LinePercentage = 0;
		int sum = 0;
		for (int i = 0; i < profiler.length; i++) {
			sum += profiler[i];
		}

		LinePercentage = 100 * ((float) profiler[line] / sum);
		LinePercentage = (float) (Math.round(LinePercentage * 100) / 100.0);
		return LinePercentage;
	}

	/**
	 * splits the opCode in it's fragments
	 */
	private void splitOpcode() {

		opcode = memory[pcounter];

		command = opcode & 0xF;
		wert = (opcode >> 4);
		index_X = (opcode >> 4) & 0xF;
		index_Y = (opcode >> 8) & 0xF;
		fromMem = (opcode >> 12) & 0x1;
		toMem = (opcode >> 13) & 0x1;

	}

}
