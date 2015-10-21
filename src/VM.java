
public class VM {

	final int NOP = 0;
	final int LOAD = 1;
	final int MOV = 2;
	final int ADD = 3;
	final int SUP = 4;
	final int MUL = 5;
	final int DIV = 6;
	final int PUSH = 7;
	final int POP = 8;
	final int JMP = 9;
	final int JIZ = 10;
	final int JIH = 11;
	final int JSR = 12;
	final int RTS = 13;

	int[] memory = new int[4096];
	int[] register = new int[16];
	
	
	int PC = 0;

	void startVM() {

		do {

			switch (CMD) {

			case NOP:break;
			case LOAD:break;
			case MOV:break;
			case ADD:break;
			case SUP:break;
			case MUL:break;
			case DIV:break;
			case PUSH:break;
			case POP:break;
			case JMP:break;
			case JIZ:break;
			case JIH:break;
			case JSR:break;
			case RTS:break;

			default:

			}

		} while (true);

	}

}
