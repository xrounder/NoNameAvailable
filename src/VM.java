
public class VM {
	
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

	int[] memory = new int[4096];
	int[] register = new int[16];

	int PC = 0;

	void startVM() {
		
		
		int cmd = 0;
		

		do {

			switch (cmd) {

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
