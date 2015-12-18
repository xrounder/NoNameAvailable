import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;;

//Wunderschöner Assembler - WORK IN PROGRESS -

public class Assembler {

	public void start(VM vm) {

		try {

			//BufferedReader br = new BufferedReader(new FileReader("Z:/git/NoNameAvailable/src/test.txt"));
			BufferedReader br = new BufferedReader(new FileReader("Z:/git/NoNameAvailable/src/Fibonacci.txt"));
			// BufferedReader br = new BufferedReader(new
			// FileReader("C:/Users/Endze/git/NoNameAvailable/text"));

			String currentLine = null;

			// reads the given text-file, line by line, until text-file is
			// finished
			do {

				currentLine = br.readLine();

				if (currentLine != null) {
					vm = implementLineInVM(vm, currentLine);
					// System.out.println(currentLine);
				}

			} while (currentLine != null);

			br.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex2) {
			ex2.printStackTrace();
		}

		vm.start();
	}

	private static VM implementLineInVM(VM vm, String currentLine) {
		// index where to put the Line in the VM-Memory
		int insertCounter = 0;

		// search for next free space in Memory
		while (vm.memory[insertCounter] != 0) {
			insertCounter++;
		}

		// converts input String in OpCode
		vm.memory[insertCounter] = convertStringToOpcode(currentLine);

		return vm;

	}

	private static int convertStringToOpcode(String currentLine) {
		int opcode = 0b0000;
		int commandCode = 0b0000;
		int value = 0b0000;
		int index_X = 0b0000;
		int index_Y = 0b0000;
		int toMem = 0b0000;
		int fromMem = 0b0000;
		String[] indexSplit;
		String[] splittetLine = new String[2];

		if (currentLine.contains("NOP") || currentLine.contains("RTS")) {
			splittetLine[0] = currentLine;
		} else {
			splittetLine = currentLine.split(" ");
		}

		//System.out.println(splittetLine[1]);

		// System.out.println(command[0]);
		// System.out.println(command[1]);

		switch (splittetLine[0]) {
		case "NOP":
			commandCode = 0b0000;
			opcode = commandCode;
			break;
		case "LOAD":
			commandCode = 0b0001;

			value = Integer.parseInt(splittetLine[1]);
			opcode = commandCode + (value << 4);
			break;
		case "MOV":
			commandCode = 0b0010;
			indexSplit = splittetLine[1].split(",");
			if (splittetLine[1].matches("^[\\d][,][\\d]$")) {
				toMem = 0b0;
				fromMem = 0b0;
				index_X = Integer.parseInt(indexSplit[1]);
				index_Y = Integer.parseInt(indexSplit[0]);
			} else if (splittetLine[1].matches("^[(][\\d][)][,][\\d]$")) {
				toMem = 0b0;
				fromMem = 0b1;
				indexSplit[0] = indexSplit[0].substring(1, indexSplit[0].length() - 1);
				index_X = Integer.parseInt(indexSplit[1]);
				index_Y = Integer.parseInt(indexSplit[0]);
			} else if (splittetLine[1].matches("^[\\d][,][(][\\d][)]$")) {
				toMem = 0b1;
				fromMem = 0b0;
				indexSplit[1] = indexSplit[1].substring(1, indexSplit[1].length() - 1);
				index_X = Integer.parseInt(indexSplit[1]);
				index_Y = Integer.parseInt(indexSplit[0]);
			} else {
				// case "^[(][\\d][)][,][(][\\d][)]$"
				toMem = 0b1;
				fromMem = 0b1;
				indexSplit[0] = indexSplit[0].substring(1, indexSplit[0].length() - 1);
				indexSplit[1] = indexSplit[1].substring(1, indexSplit[1].length() - 1);
				index_X = Integer.parseInt(indexSplit[1]);
				index_Y = Integer.parseInt(indexSplit[0]);
			}

			opcode = commandCode + (index_X << 4) + (index_Y << 8) + (fromMem << 12) + (toMem << 13);

			break;
		case "ADD":
			commandCode = 0b0011;
			indexSplit = splittetLine[1].split(",");
			index_X = Integer.parseInt(indexSplit[0]);
			index_Y = Integer.parseInt(indexSplit[1]);

			opcode = commandCode + (index_X << 4) + (index_Y << 8);
			break;
		case "SUB":
			commandCode = 0b0100;
			indexSplit = splittetLine[1].split(",");
			index_X = Integer.parseInt(indexSplit[0]);
			index_Y = Integer.parseInt(indexSplit[1]);

			opcode = commandCode + (index_X << 4) + (index_Y << 8);
			break;
		case "MUL":
			commandCode = 0b0101;
			indexSplit = splittetLine[1].split(",");
			index_X = Integer.parseInt(indexSplit[0]);
			index_Y = Integer.parseInt(indexSplit[1]);

			opcode = commandCode + (index_X << 4) + (index_Y << 8);
			break;
		case "DIV":
			commandCode = 0b0110;
			indexSplit = splittetLine[1].split(",");
			index_X = Integer.parseInt(indexSplit[0]);
			index_Y = Integer.parseInt(indexSplit[1]);

			opcode = commandCode + (index_X << 4) + (index_Y << 8);
			break;
		case "PUSH":
			commandCode = 0b0111;

			index_X = Integer.parseInt(splittetLine[1]);

			opcode = commandCode + (index_X << 4);
			break;
		case "POP":
			commandCode = 0b1000;

			index_X = Integer.parseInt(splittetLine[1]);

			opcode = commandCode + (index_X << 4);
			break;
		case "JMP":
			commandCode = 0b1001;
			value = Integer.parseInt(splittetLine[1]);
			opcode = commandCode + (value << 4);
			break;
		case "JIZ":
			commandCode = 0b1010;
			value = Integer.parseInt(splittetLine[1]);
			opcode = commandCode + (value << 4);
			break;
		case "JIH":
			commandCode = 0b1011;
			value = Integer.parseInt(splittetLine[1]);
			opcode = commandCode + (value << 4);
			break;
		case "JSR":
			commandCode = 0b1100;
			value = Integer.parseInt(splittetLine[1]);
			opcode = commandCode + (value << 4);
			break;
		case "RTS":
			commandCode = 0b1101;

			opcode = commandCode;
			break;
		default:
			commandCode = 0b0000;
		}

		return opcode;
	}
}
